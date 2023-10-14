package de.agsayan.pdfLib.pdfObject.page.streamObj;

import de.agsayan.pdfLib.pdfObject.PDFObject;
import de.agsayan.pdfLib.pdfObject.page.PageObject;

public abstract class StreamObject extends PDFObject {

  protected String type;
  protected String subtype;
  protected String filter;
  protected String colorSpace;
  protected int width;
  protected int height;
  protected long length;
  protected int bitsPerComponent;
  private String content;
  protected float xPos;
  protected float yPos;

  public void setPosition(PageObject page, float x, float y) {
    this.xPos = milimeterToPtsConverter(x);
    this.yPos = page.getHeight() - milimeterToPtsConverter(y);
  }

  private float milimeterToPtsConverter(float mm) {
    double pts = 2.8346438836889;
    return (float)(mm * pts);
  }

  public String buildStream() {
    return "\nstream\n" + getContent() + "\nendstream\n";
  }

  public String getContent() { return content; }

  public void setContent(String content) { this.content = content; }
}
