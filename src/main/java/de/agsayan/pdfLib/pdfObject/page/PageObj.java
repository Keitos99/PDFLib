package de.agsayan.pdfLib.pdfObject.page;

import java.util.ArrayList;

import de.agsayan.pdfLib.pdfObject.PDFObject;
import de.agsayan.pdfLib.pdfObject.page.streamObj.ImageObject;
import de.agsayan.pdfLib.pdfObject.page.streamObj.StreamObj;
import de.agsayan.pdfLib.pdfObject.page.streamObj.TextObject;

public class PageObj extends PDFObject {

  public enum PageFormat {
    A4,
    A5,
    A6;
  }

  public final String a4Format = "a4";

  // ungefahr richtiger DinA4 size
  private int pageWidth = 630;
  private int pageHeight = 765;

  private String dic;
  private String content;
  int imgPos;
  ArrayList<String> imgRefs = new ArrayList<>();
  PageFormat PageFormat;
  private ArrayList<StreamObj> streamObjects = new ArrayList<>();
  private String parentReference;
  private String contentReference;

  public PageObj(String pageFormat) {

    if (pageFormat.equals(a4Format)) {
      PageFormat = PageFormat.A4;
      setPageSize(765, 630);
    }
  }

  private String getMediaBox() {
    if (PageFormat.equals(PageFormat.A4)) {
      return "\n";
    } else {
      return "/MediaBox[0 0 " + getPageWidth() + " " + getHeight() + "]\n";
    }
  }

  public String getDic(int contentReference) {

    String fontDic = "";
    for (String font : fonts)
      fontDic += font + "\n";

    this.dic =
        "    <<\n"
        + "/Type /Page\n"
        + "/Contents " + contentReference +
        " 0 R\n" // Referenz zu den Contents vom Page
        + "/Parent " + getParentReference() +
        "\n/Resources <<\n" // RES die von den Obj der Page genutzt werden
        + "/Font <<\n"
        + "" + fontDic + ">>\n"
        + ">>\n" + getMediaBox() // For sizing the pdf page
        + "     >>\n";
    ;
    return this.dic;
  }

  public void setDic(int contentReference) {
    String fonts = "";
    this.dic =
        "    <<\n"
        + "         /Type /Page\n"
        + "         /Contents " + contentReference +
        " 0 R\n" // Referenz zu den Contents vom Page
        + "         /Parent 2 0 R"
        + "         /Resources\n" // RES die von den Obj der Page genutzt werden
        + "             <<\n"
        + "                  /Font\n"
        + "                     <<\n"
        + "                         /F1\n"
        + "                             <<\n"
        + "                                 /Type / Font\n"
        + "                                 /BaseFont /Times-Italic\n"
        + "                                 /Subtype /Type1\n"
        + "                              >>\n"
        + "                        /F2\n"
        + "                             <<\n"
        + "                                 /Type / Font\n"
        + "                                 /BaseFont /Helvetica-Bold\n"
        + "                                 /Subtype /Type1\n"
        + "                            >>\n"
        + "                     >>\n"
        + "             >>\n"
        + "      /MediaBox [0 0 " + getPageWidth() + " " + getHeight() +
        "]" // For sizing the pdf page
        + "     >>\n";
  }

  ArrayList<String> fonts = new ArrayList<>();

  public void setDictionary(String key, StreamObj objects) {
    int fontReference = 0;
    if (key.equals("Font")) {
      TextObject txtObj = (TextObject)objects;
      String coursive = "";
      if (txtObj.getTextFont().equals("Times-Roman")) {
        coursive = "Italic";
      } else {
        coursive = "Oblique";
      }
      setFontMode((TextObject)objects, coursive);
    } else {
      setImgRef((ImageObject)objects);
    }
  }

  private void setFont(TextObject txtObj) {
    switch (txtObj.getTextFont()) {
    case "Times-Roman":
      // setFontMode(txtObj);
      break;
    case "Helvetica":
      break;
    default:
      break;
    }
  }

  private void setImgRef(ImageObject img) {
    int reference = imgRefs.size();
    if (imgRefs.isEmpty()) {
      imgPos = fonts.size();
      fonts.add(">>\n/ProcSet [/PDF /Text /ImageB /ImageC /ImageI]\n"
                + "/XObject <<\n"
                + ">>");

      imgRefs.add("/I" + reference + " " + img.getObjectPos() + " 0 R\n");
    } else {
      imgRefs.add("/I" + reference + " " + img.getObjectPos() + " 0 R\n");
      String referr = "";
      for (String imgRef : imgRefs) {
        referr += imgRef;
      }

      fonts.set(imgPos, ">>\n/ProcSet [/PDF /Text /ImageB /ImageC /ImageI]\n"
                            + "/XObject <<\n" + referr + ">>");
    }
    img.setReference("/I" + reference);
  }

  private void setFontMode(TextObject txtObj, String coursive) {
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
              + "                            >>\n"
              + "");

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
