package de.agsayan.pdfLib.pdfObject.TypeObjects;

import java.awt.image.IndexColorModel;

/**
 * IndirectObject
 */
public class IndirectObject {
  private int objectNumber;
  private int generationNumber;
  private byte[] object;

  public IndirectObject(int objectNumber, int generationNumber) {
    this.objectNumber = objectNumber;
    this.generationNumber = generationNumber;
  }

  public void addBytes(byte[] object) { this.object = object; }

  @Override
  public String toString() {
    return objectNumber + " " + generationNumber + "obj\n" 
      + this.object + "\n"
      + "endobj\n";
  }
}
