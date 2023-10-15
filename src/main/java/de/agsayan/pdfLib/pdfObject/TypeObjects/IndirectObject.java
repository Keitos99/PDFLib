package de.agsayan.pdfLib.pdfObject.TypeObjects;

import java.nio.ByteBuffer;

/**
 * IndirectObject
 */
public class IndirectObject {
  private int objectNumber;
  private int generationNumber;
  private byte[] object = new byte[0];

  public IndirectObject(int objectNumber, int generationNumber) {
    this.objectNumber = objectNumber;
    this.generationNumber = generationNumber;
  }

  public void addBytes(byte[] object) { this.object = object; }

  // @Override
  // public String toString() {
  //
  //   return +this.object + "\n"
  //       + "endobj\n";
  // }
  

  public byte[] getBytes() {
    String beginningString = objectNumber + " " + generationNumber + "obj\n";
    String endString = "endobj\n";
    byte[] b = new byte[beginningString.getBytes().length + this.object.length + endString.getBytes().length];

    ByteBuffer buff = ByteBuffer.wrap(b);
    buff.put(beginningString.getBytes());
    buff.put(this.object);
    buff.put(endString.getBytes());
    return buff.array();
  }
}
