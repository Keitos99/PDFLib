package de.agsayan.pdfLib.pdfObject.TypeObjects;

/**
 * StringObjects
 */
public class StringObjects {
  private String value;
  private boolean asHex;

  // TODO: no support for asHex = true
  private StringObjects(String value, boolean asHex) {
    this.value = value;
    this.asHex = asHex;
  }

  public StringObjects(String value) {
    this(value, false);
  }

  @Override
  public String toString() {
    if (asHex) {
      return "<" + value + ">";
    }
    return "(" + value + ")";
  }
}
