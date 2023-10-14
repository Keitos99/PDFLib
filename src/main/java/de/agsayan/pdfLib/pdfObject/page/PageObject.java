package de.agsayan.pdfLib.pdfObject.page;

import java.util.ArrayList;
import java.util.List;

import de.agsayan.pdfLib.pdfObject.PDFObject;
import de.agsayan.pdfLib.pdfObject.page.streamObj.ImageObject;
import de.agsayan.pdfLib.pdfObject.page.streamObj.StreamObj;
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
  private ArrayList<StreamObj> streamObjects = new ArrayList<>();
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
      return "/MediaBox[0 0 " + getPageWidth() + " " + getHeight() + "]\n";
    }
  }

  public String getDic(int contentReference) {
    String dic = "    <<\n"
                 + "/Type /Page\n"
                 + "/Contents " + contentReference +
                 " 0 R\n" // Referenz zu den Contents vom Page
                 + "/Parent " + getParentReference() + buildResourcesBlock() +
                 buildMediaBox() // For sizing the pdf page
                 + "     >>\n";
    ;
    return dic;
  }

  private String buildDictionary(List<String> values) {
    String content = "";
    for(String value : values)
      content += value + "\n";

    return "<<\n" + content + ">>\n";
  }

  private String buildFontDictionary() {
    String fontDic = "";
    for (String font : fonts) {
      fontDic += font + "\n";
    }
    return "/Font " + buildDictionary(fonts);
  }

  // PDF Reference 9.1
  private String buildProcedureSet(String referrenceString) {
    return "\n/ProcSet [/PDF /Text " + GRAYSCALE_IMAGE + " " + COLOR_IMAGE +
        " " + INDEXED_IMAGE + " ]\n"
        + "/XObject <<\n" + referrenceString + ">>";
  }

  // RES die von den Obj der Page genutzt werden
  private String buildResourcesBlock() {
    String referrenceString = "";
    for (String imgRef : imgRefs) {
      referrenceString += imgRef;
    }
    String result = "\n/Resources <<\n" + buildFontDictionary() +
                    buildProcedureSet(referrenceString) + ">>\n";

    System.out.println(result);
    return result;
  }

  public void addStreamObjects(String key, StreamObj objects) {
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
    if (imgRefs.isEmpty()) {
      imgPos = fonts.size();
      imgRefs.add("/I" + referenceIndex + " " + img.getObjectPos() + " 0 R\n");
    } else {
      imgRefs.add("/I" + referenceIndex + " " + img.getObjectPos() + " 0 R\n");
    }
    img.setReference("/I" + referenceIndex);
  }

  private void addFont(TextObject txtObj, String coursive) {
    String font = txtObj.getTextFont();
    int fontReference = fonts.size();

    if (txtObj.getTextFont().equals("Times-Roman"))
      font = "Times";

    if (txtObj.isBold() && txtObj.isCursive()) {
      font += "-Bold" + coursive;
    } else if (txtObj.isBold()) {
      font += "-Bold";
    } else if (txtObj.isCursive()) {
      font += "-" + coursive;
    } else if (font.equals("Times")) {
      font += "-Roman";
    }

    fonts.add("                         /F" + fontReference + "\n"
              + "                             <<\n"
              + "                                 /Type /Font\n"
              + "                                 /BaseFont /" + font + "\n"
              + "                                 /Subtype /Type1\n"
              + "                            >>\n");

    txtObj.setFontReference(fontReference);
  }

  public String generateStream() {
    String streamContent = "";
    for (StreamObj streamObject : streamObjects) {
      streamContent += streamObject.buildStream();
    }

    return "   <<\n"
        + "      /Length 100\n"
        + "      >>\n"
        + "     stream\n" + streamContent + "\n"
        + "     endstream\n";
  }

  public void setPageSize(int pageHeight, int pageWidth) {
    this.pageWidth = pageWidth;
    this.pageHeight = pageHeight;
  }

  public int getPageWidth() { return pageWidth; }

  public int getHeight() { return pageHeight; }

  public void addStreamContent(StreamObj streamObj) {
    streamObjects.add(streamObj);
  }

  public String getParentReference() { return parentReference; }

  public void setParentReference(String parentReference) {
    this.parentReference = parentReference;
  }
}
