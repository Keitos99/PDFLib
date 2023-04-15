package de.agsayan.pdfLib;

import de.agsayan.pdfLib.pdfObject.PDF;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class App {
  public static void main(String[] args) throws Exception {
    URL resourceURL = App.class.getClassLoader().getResource("PDFWithImage.json");
    String resourceFile = resourceURL.getFile();
    String generatedPdfFile = "/home/agsayan/Downloads/test.pdf";

    InputStream is = new FileInputStream(resourceFile);
    JSONTokener tokener = new JSONTokener(is);

    JSONObject object = new JSONObject(tokener);
    JSONArray jsonArray = object.getJSONArray("test");
    PDF pdf = new PDF();
    pdf.createPDF(jsonArray, generatedPdfFile);

    is.close();
  }
}
