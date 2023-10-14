package de.agsayan.pdfLib.pdfObject.TypeObjects;

/**
 * NumericObject
 * 3.2.2
 */
public class NumericObject {
  private int value;

  public NumericObject(int value) { this.value = value; }

  @Override
  public String toString() {
      return value + "";
  }
}
