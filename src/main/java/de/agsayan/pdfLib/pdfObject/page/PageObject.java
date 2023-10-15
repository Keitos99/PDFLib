package de.agsayan.pdfLib.pdfObject.page;

import java.util.ArrayList;

import de.agsayan.pdfLib.pdfObject.PDFObject;
import de.agsayan.pdfLib.pdfObject.TypeObjects.ArrayObject;
import de.agsayan.pdfLib.pdfObject.TypeObjects.DictionaryObject;
import de.agsayan.pdfLib.pdfObject.page.streamObj.ImageObject;
import de.agsayan.pdfLib.pdfObject.page.streamObj.StreamObject;
import de.agsayan.pdfLib.pdfObject.page.streamObj.TextObject;

public class PageObject extends PDFObject {

  public enum PageFormat {
    A4(765, 630),
    // must be adjusted
    A5(0, 0),
    A6(0, 0);

    public final int pageWidth;
    public final int pageHeight;

    private PageFormat(int pageHeight, int pageWidth) {
      this.pageHeight = pageHeight;
      this.pageWidth = pageWidth;
    }
  }

  public final String a4Format = "a4";
  private final String GRAYSCALE_IMAGE = "/IMAGEB";
  private final String COLOR_IMAGE = "/IMAGEC";
  private final String INDEXED_IMAGE = "/IMAGEI";

  // ungefahr richtiger DinA4 size
  private int pageWidth = 630;
  private int pageHeight = 765;

  int imgPos;
  PageFormat pageFormat;
  private String parentReference;
  private ArrayList<StreamObject> streamObjects = new ArrayList<>();
  ArrayList<String> imgRefs = new ArrayList<>();
  ArrayList<String> fonts = new ArrayList<>();

  public PageObject(PageFormat pageFormat) {
    this.pageFormat = pageFormat;
    setPageSize(this.pageFormat.pageHeight, this.pageFormat.pageWidth);
  }

  private String buildMediaBox() {
    if (this.pageFormat.equals(PageFormat.A4)) {
      return "\n";
    } else {
      ArrayObject arrayObject = new ArrayObject();
      arrayObject.add(0);
      arrayObject.add(0);
      arrayObject.add(getWidth());
      arrayObject.add(getHeight());
      return "/MediaBox " + arrayObject + "\n";
    }
  }

  public String getDic(int contentReference) {
    String contentReferenceString = "/Contents " + contentReference + " 0 R";
    return buildDictionary(
        "obj", "/Type /Page",
        contentReferenceString, // Referenz zu den Contents vom Page
        getParentReference(),   // parent referenc
        buildResourcesBlock(),  // ressource block
        buildMediaBox()         // For sizing the pdf page
    );
  }

  private String buildDictionary(String key, String... values) {
    if (key.equals("obj")) {
      key = "";
    }
    String content = "";
    for (String value : values)
      content += value + "\n";

    return key + " "
        + "<<\n" + content + ">>\n";
  }

  private String buildFontDictionary() {
    return buildDictionary("/Font", fonts.toArray(new String[0]));
  }

  private String buildXObject() {
    String referrenceString = "";
    for (String imgRef : imgRefs) {
      referrenceString += imgRef;
    }
    return buildDictionary("/XObject", referrenceString);
  }

  // PDF Reference 9.1
  private String buildProcedureSet() {
    ArrayObject array = new ArrayObject();
    array.add("/PDF");
    array.add("/TEXT");
    array.add(GRAYSCALE_IMAGE);
    array.add(COLOR_IMAGE);
    array.add(INDEXED_IMAGE);

    return "/ProcSet " + array + buildXObject();
  }

  // RES die von den Obj der Page genutzt werden
  // PDFReference 1.4: 3.7.2
  private String buildResourcesBlock() {
    String result = buildDictionary("/Resources", buildFontDictionary(),
                                    buildProcedureSet() + buildXObject());
    return result;
  }

  public void addStreamObjects(String key, StreamObject objects) {
    if (key.equals("Font")) {
      TextObject txtObj = (TextObject)objects;
      String coursive = "";
      if (txtObj.getTextFont().equals("Times-Roman")) {
        coursive = "Italic";
      } else {
        coursive = "Oblique";
      }
      addFont((TextObject)objects, coursive);
    } else {
      addImage((ImageObject)objects);
    }
  }

  private void addImage(ImageObject img) {
    int referenceIndex = imgRefs.size();
    img.setReference(referenceIndex);
    imgRefs.add(img.getReference() + " " + img.getObjectPos() + " 0 R\n");
  }

  private void addFont(TextObject txtObj, String coursive) {
    int fontReference = fonts.size();
    FontObject fontObject = new FontObject(fontReference);

    fontObject.setFontName(txtObj.getTextFont());
    fontObject.setBold(txtObj.isBold());
    fontObject.setCursive(txtObj.isCursive());

    txtObj.setFontReference(fontObject.getFontReference());
    fonts.add(fontObject.toString());
  }

  private String buildStream() {
    String streamContent = "";
    for (StreamObject streamObject : streamObjects) {
      streamContent += streamObject.buildStream();
    }
    return "stream\n" + streamContent + "\n"
        + " endstream\n";
  }

  public String generateStream() {
    DictionaryObject dictionaryObject = new DictionaryObject();
    dictionaryObject.put("Length", 100);
    return dictionaryObject.toString() + buildStream();
  }

  public void setPageSize(int pageHeight, int pageWidth) {
    this.pageWidth = pageWidth;
    this.pageHeight = pageHeight;
  }

  public int getWidth() { return pageWidth; }

  public int getHeight() { return pageHeight; }

  public void addStreamContent(StreamObject streamObj) {
    streamObjects.add(streamObj);
  }

  public String getParentReference() { return "/Parent " + parentReference; }

  public void setParentReference(String parentReference) {
    this.parentReference = parentReference;
  }
}
