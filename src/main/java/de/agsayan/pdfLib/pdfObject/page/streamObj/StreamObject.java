package de.agsayan.pdfLib.pdfObject.page.streamObj;

import de.agsayan.pdfLib.pdfObject.PDFObject;
import de.agsayan.pdfLib.pdfObject.page.PageObject;
import java.io.IOException;
import java.io.OutputStream;

public abstract class StreamObject extends PDFObject {
  private final String START_STREAM = "stream";
  private final String END_STREAM = "endstream";

  protected long length;
  protected String type;
  private String content;
  protected float xPos;
  protected float yPos;

  public void setPosition(PageObject page, float x, float y) {
    this.xPos = milimeterToPtsConverter(x);
    this.yPos = page.getHeight() - milimeterToPtsConverter(y);
  }

  public void setPosition(float x, float y) {
    this.xPos = milimeterToPtsConverter(x);
    this.yPos = milimeterToPtsConverter(y);
  }

  public void setPosition(boolean t, float x, float y) {
    this.xPos = x;
    this.yPos = y;
  }

  private float milimeterToPtsConverter(float mm) {
    double pts = 2.8346438836889;
    return (float)(mm * pts);
  }

  public String buildStream() {
    return START_STREAM + "\n" + getContent() + "\n" + END_STREAM + "\n";
  }

  public void write(OutputStream os) {
    try {
      os.write(buildStream().getBytes());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public String getContent() { return content; }

  public void setContent(String content) { this.content = content; }
}
