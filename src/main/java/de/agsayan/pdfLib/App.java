package de.agsayan.pdfLib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import de.agsayan.pdfLib.pdfObject.PDF;
import de.agsayan.pdfLib.pdfObject.TypeObjects.IndirectObject;
import de.agsayan.pdfLib.pdfObject.page.PageObject;
import de.agsayan.pdfLib.pdfObject.page.PageObject.PageFormat;
import de.agsayan.pdfLib.pdfObject.page.streamObj.ImageObject;
import de.agsayan.pdfLib.pdfObject.page.streamObj.TextObject;

public class App {
  public static void main(String[] args) {

    IndirectObject indirectObject = new IndirectObject(1);
    System.out.println(indirectObject.toString());
    ImageObject img = new ImageObject(
        "/home/agsayan/Documents/Workspace/Github/PDFLib/src/main/resources/images/0038.jpg");
    img.setPosition(true, 70, 50);
    img.setHeigth(100);
    img.setWidth(100);
    img.setObjectPos(3);
    System.out.println(img.buildStream());

    try {
      OutputStream os =
          new FileOutputStream(new File("/home/agsayan/agsayan.jpg"));
      img.write(os);
      os.close();
    } catch (FileNotFoundException e) {
    } catch (IOException e) {
    }
  }

  public static void createPDF() throws Exception {
    URL resourceURL =
        App.class.getClassLoader().getResource("PDFWithImage.json");
    String resourceFile = resourceURL.getFile();
    String generatedPdfFile = "/home/agsayan/Downloads/test.pdf";

    InputStream is = new FileInputStream(resourceFile);
    JSONTokener tokener = new JSONTokener(is);

    JSONObject object = new JSONObject(tokener);
    JSONArray jsonArray = object.getJSONArray("test");

    PDF pdf = new PDF(generatedPdfFile);
    pdf.addCatalog();
    pdf.addPagesCollection(jsonArray.length());
    App.writePDFFromJson(pdf, jsonArray);
    pdf.createPDF();
    is.close();
  }

  public static void writePDFFromJson(PDF pdf, JSONArray array) {
    for (int i = 0; i < array.length(); i++) {
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
          img.setObjectPos(pdf.numberOfObjects() + 1);
          page.addStreamObjects("XObject", img);
          try {
            pdf.addObject(img.embedXObjectWithImg());
          } catch (IOException e) {
            e.printStackTrace();
          }
          page.addStreamContent(img);
          break;
        }
      }

      pdf.addPage(page);
    }
  }
}
