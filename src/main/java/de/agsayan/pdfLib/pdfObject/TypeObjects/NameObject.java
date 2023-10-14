package de.agsayan.pdfLib.pdfObject.TypeObjects;

/**
 * NameObject
 * 3.2.4
 */
public class NameObject {
  public final String INTRODUCER = "/";
  public String name;

  public NameObject(String name) {
    name = name.replace(" ", "#20");
    this.name = name;
  }

  @Override
  public String toString() {
    return INTRODUCER + this.name;
  }

  public static void main(String[] args) {
    System.out.println(new NameObject("Adobe Green")); }
}
