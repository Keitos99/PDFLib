package de.agsayan.pdfLib.pdfObject.TypeObjects;

import de.agsayan.pdfLib.pdfObject.PDFObject;

/**
 * IndirectObject
 * 3.2.9
 */
public class IndirectObject extends PDFObject {

  private final String START_OBJECT = "obj";
  private final String END_OBJECT = "endobj";

  private int objectNumber;
  private int generationNumber;
  private PDFObject objectContent;

  public IndirectObject(int objectNumber) {
    this.objectNumber = objectNumber;
    // should only be higher as 0 if the pdf was updated
    this.generationNumber = 0;
  }

  @Override
  public String toString() {
    String content = "";
    if (this.objectContent == null)
      content = "";
    else
      content = this.getContent().toString();

    return this.objectNumber + " " + this.generationNumber + " " + START_OBJECT + "\n" + content + "\n" + END_OBJECT;
  }

  public int getObjectNumber() { return objectNumber; }

  public void setObjectNumber(int objectNumber) {
    this.objectNumber = objectNumber;
  }

  public int getGenerationNumber() { return generationNumber; }

  public PDFObject getContent() { return objectContent; }

  public void setContent(PDFObject content) {
    if (content instanceof IndirectObject)
      return;
    this.objectContent = content;
  }
}
