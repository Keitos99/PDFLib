package de.agsayan.pdfLib.pdfObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

import de.agsayan.pdfLib.pdfObject.TypeObjects.ArrayObject;
import de.agsayan.pdfLib.pdfObject.TypeObjects.DictionaryObject;
import de.agsayan.pdfLib.pdfObject.TypeObjects.NameObject;
import de.agsayan.pdfLib.pdfObject.page.PageObject;

public class PDF {

  OutputStream os = null;
  public PDF(String pdfPath) {
    try {
      os = new FileOutputStream(new File(pdfPath));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private final String HEADER = "%PDF-1.4\n\n";

  ArrayList<String> pdfObjects = new ArrayList<>();

  public void createPDF() throws IOException {
    setKids();
    writeHeader();
    writeObjects(pdfObjects);
    writeTrailer(pdfObjects.size());

    if (os != null)
      os.close();
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
      String beginningObj = (i + 1) + " 0 "
                            + "obj\n";

      os.write(beginningObj.getBytes());

      if ((pdfObjects.get(i).contains("imgPath"))) {
        String[] streamParts = pdfObjects.get(i).split("imgPath");
        System.out.println("0: " + streamParts[0]);
        System.out.println("1:" + streamParts[1]);
        String imgPath = streamParts[1].split("endstream")[0].replace("\n", "");
        FileInputStream is = new FileInputStream(new File(imgPath));
        os.write(streamParts[0].getBytes());
        int length;
        byte[] buffer = new byte[1024];
        while ((length = is.read(buffer)) > 0) {
          os.write(buffer, 0, length);
        }
        os.write("\nendstream".getBytes());

      } else {
        os.write(pdfObjects.get(i).getBytes());
      }

      String endObj = "\nendobj\n\n";
      os.write(endObj.getBytes());
    }
  }

  private void writeHeader() throws IOException { os.write(HEADER.getBytes()); }

  public void addCatalog() {
    DictionaryObject dictionaryObject = new DictionaryObject();
    dictionaryObject.put("Pages", "2 0 R");
    dictionaryObject.put("Type", new NameObject("Catalog"));

    String catalog = dictionaryObject.toString();
    pdfObjects.add(catalog);
  }

  public void addPage(PageObject page) {
    pdfObjects.add(page.getDic(pdfObjects.size() + 2));
    pdfObjects.add(page.generateStream() + "\n");
  }

  public void addPagesCollection(int count) {
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

  public void addObject(String object) {
    pdfObjects.add(object);
  }
  public int numberOfObjects() {
    return pdfObjects.size();
  }
}
