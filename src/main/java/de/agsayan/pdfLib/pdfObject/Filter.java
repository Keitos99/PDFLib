package de.agsayan.pdfLib.pdfObject;

import de.agsayan.pdfLib.pdfObject.TypeObjects.NameObject;

/**
 * Filter
 * 3.3
 */
public enum Filter {
  ASCIIHexDecode(false),
  ASCII85Decode(false),
  LZWDecode(true),
  FlateDecode(true),
  RunLengthDecode(false),
  CCITTFaxDecode(true),
  JBIG2Decode(true),
  DCTDecode(true);

  public final boolean withParameter;

  private Filter(boolean withParameter) { this.withParameter = withParameter; }

  // private Filter() { this(false); }

  @Override
  public String toString() {
    return new NameObject(this.name()).toString();
  }
}
