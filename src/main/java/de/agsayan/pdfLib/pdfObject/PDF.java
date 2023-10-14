package de.agsayan.pdfLib.pdfObject;

import de.agsayan.pdfLib.pdfObject.page.PageObj;
import de.agsayan.pdfLib.pdfObject.page.PageObj.PageFormat;
import de.agsayan.pdfLib.pdfObject.page.streamObj.ImageObject;
import de.agsayan.pdfLib.pdfObject.page.streamObj.TextObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

public class PDF {

  OutputStream os = null;
  InputStream is = null;

  private final String HEADER = "%PDF-1.4\n\n";

  ArrayList<String> pdfObjects = new ArrayList<>();

  public void createPDF(JSONArray array, String pdfPath) throws IOException {
    os = new FileOutputStream(new File(pdfPath));
    addCatalog();
    addPagesCollection(array.length());

    for (int i = 0; i < array.length(); i++) {
      JSONObject obj = array.getJSONObject(i);
      JSONArray objectList = obj.getJSONArray("objectList");

      String pageFormat = obj.getString("pageFormat").toUpperCase();
      PageObj page = new PageObj(PageFormat.valueOf(pageFormat));
      page.setParentReference("2 0 R");

      for (int x = 0; x < objectList.length(); x++) {
        JSONObject object = objectList.getJSONObject(x);
        String objectType = object.getString("name");

        float xPos = object.getFloat("positionX");
        float yPos = object.getFloat("positionY");
        switch (objectType) {
          case "text":
            int textSize = object.getInt("textSize");
            boolean isCursive = object.getBoolean("textCursive");
            boolean isBold = object.getBoolean("textBold");
            boolean isUnderlined = object.getBoolean("textUnderlined");
            String textFont = object.getString("textFont");
            String textColor = object.getString("textColor");
            String text = object.getString("text");

            TextObject txtObj = new TextObject();
            txtObj.setText(text, textSize);
            txtObj.setPosition(page, xPos, yPos);
            txtObj.setCursive(isCursive);
            txtObj.setBold(isBold);
            txtObj.setUnderlined(isUnderlined);
            txtObj.setTextColor(textColor);
            txtObj.setTextFont(textFont);

            page.addStreamObjects("Font", txtObj);
            page.addStreamContent(txtObj);
            break;

          case "img":
            String imgPath = object.getString("imgPath");
            float height = object.getFloat("height");
            float width = object.getFloat("width");

            ImageObject img = new ImageObject(imgPath);
            img.setPosition(page, xPos, yPos);
            img.setHeigth(height);
            img.setWidth(width);

            // TODO: Warum +1?
            img.setObjectPos(pdfObjects.size() + 1);
            page.addStreamObjects("XObject", img);
            pdfObjects.add(img.embedXObjectWithImg());
            page.addStreamContent(img);
            break;
        }
      }

      pdfObjects.add(page.getDic(pdfObjects.size() + 2));
      pdfObjects.add(page.generateStream() + "\n");
    }


    setKids();

    writeHeader();
    generateObjs(pdfObjects);
    writeTrailer(pdfObjects.size());

    if (os != null)
      os.close();
    if (is != null)
      is.close();
  }

  private void setKids() {
    int parentObjPosition = 0;
    String kids = "";
    for (int t = 0; t < pdfObjects.size(); t++) {
      String object = pdfObjects.get(t);
      if (object.contains("/Page") && !object.contains("/Pages")) {
        kids += " " + (t + 1) + " 0 R";
      } else if (object.contains("/Kids")) {
        parentObjPosition = t;
      }
    }

    String parentObj = pdfObjects.get(parentObjPosition).split(Pattern.quote("["))[0] + "[" +
      kids + "] >>";
    pdfObjects.set(parentObjPosition, parentObj);
  }

  private void generateObjs(ArrayList<String> pdfObjects) throws IOException {
    for (int i = 0; i < pdfObjects.size(); i++) {
      String beginningObj = (i + 1) + " 0 " + "obj\n";
      os.write(beginningObj.getBytes());

      if ((pdfObjects.get(i).contains("imgPath"))) {
        String[] streamParts = pdfObjects.get(i).split("imgPath");
        String imgPath = streamParts[1].split("endstream")[0].replace("\n", "");
        is = new FileInputStream(new File(imgPath));
        os.write(streamParts[0].getBytes());
        int length;
        byte[] buffer = new byte[1024];
        while ((length = is.read(buffer)) > 0) {
          os.write(buffer, 0, length);
        }
        os.write(streamParts[1].getBytes());

      } else {
        os.write(pdfObjects.get(i).getBytes());
      }

      String endObj = "\nendobj\n\n";
      os.write(endObj.getBytes());
    }
  }

  private void writeHeader() throws IOException {
    os.write(HEADER.getBytes());
  }

  private void addCatalog() {
    String catalog = "  <<\n"
      + "       /Pages 2 0 R\n"
      + "       /Type /Catalog  >>";
    pdfObjects.add(catalog);
  }

  private void addPagesCollection(int count) {
    String childs = "[";
    int t = 3;

    for (int i = 0; i < count; i++) {
      childs += " " + (i + t) + " 0 R";
      t++;
    }
    childs += "]";
    String pagesDic = "  <<\n"
      + "     /Type /Pages\n"
      + "     /Count " + count + "\n"
      + "     /Kids " + childs + "  >>";
    pdfObjects.add(pagesDic);
  }

  public void writeTrailer(int size) throws IOException {
    String trailer = "trailer\n"
      + "  <<\n"
      + "    /Root 1 0 R\n"
      + "    /Size " + size + "\n"
      + "  >>\n"
      + "%%EOF";
    os.write(trailer.getBytes());
  }
}
