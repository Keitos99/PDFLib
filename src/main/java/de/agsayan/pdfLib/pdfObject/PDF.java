package de.agsayan.pdfLib.pdfObject;

import de.agsayan.pdfLib.pdfObject.TypeObjects.ArrayObject;
import de.agsayan.pdfLib.pdfObject.TypeObjects.DictionaryObject;
import de.agsayan.pdfLib.pdfObject.TypeObjects.IndirectObject;
import de.agsayan.pdfLib.pdfObject.TypeObjects.NameObject;
import de.agsayan.pdfLib.pdfObject.page.PageObject;
import de.agsayan.pdfLib.pdfObject.page.PageObject.PageFormat;
import de.agsayan.pdfLib.pdfObject.page.streamObj.ImageObject;
import de.agsayan.pdfLib.pdfObject.page.streamObj.TextObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
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
    int pag = 0;
    os = new FileOutputStream(new File(pdfPath));
    addCatalog();
    addPagesCollection(array.length());

    for (int i = 0; i < array.length(); i++) {
      pag += 1;
      JSONObject obj = array.getJSONObject(i);
      JSONArray objectList = obj.getJSONArray("objectList");

      String pageFormat = obj.getString("pageFormat").toUpperCase();
      PageObject page = new PageObject(PageFormat.valueOf(pageFormat));
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
    writeObjects(pdfObjects);
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

    String parentObj =
        pdfObjects.get(parentObjPosition).split(Pattern.quote("["))[0] + "[" +
        kids + "] >>";
    System.out.println(parentObj);
    pdfObjects.set(parentObjPosition, parentObj);
  }

  private void writeObjects(ArrayList<String> pdfObjects) throws IOException {
    // TODO: use IndirectObject
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

  private void writeHeader() throws IOException { os.write(HEADER.getBytes()); }

  private void addCatalog() {
    DictionaryObject dictionaryObject = new DictionaryObject();
    dictionaryObject.put("Pages", "2 0 R");
    dictionaryObject.put("Type", new NameObject("Catalog"));

    String catalog = dictionaryObject.toString();
    pdfObjects.add(catalog);
  }

  private void addPagesCollection(int count) {
    DictionaryObject dictionary = new DictionaryObject();
    ArrayObject array = new ArrayObject();
    int t = 3;

    for (int i = 0; i < count; i++) {
      array.add((i + t));
      array.add(0);
      array.add("R");
      t++;
    }

    dictionary.put("Type", new NameObject("Pages"));
    dictionary.put("Count", count);
    dictionary.put("Kids", array);

    pdfObjects.add(dictionary.toString());
  }

  public void writeTrailer(int size) throws IOException {
    DictionaryObject dictionary = new DictionaryObject();
    dictionary.put("Root", "1 0 R");
    dictionary.put("Size", size);

    String trailer = "trailer\n" + dictionary + "\n%%EOF";
    os.write(trailer.getBytes());
  }
}
