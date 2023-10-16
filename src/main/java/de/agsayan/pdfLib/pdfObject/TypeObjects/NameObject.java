package de.agsayan.pdfLib.pdfObject.TypeObjects;

import java.util.jar.Attributes.Name;

/**
 * NameObject
 * 3.2.4
 */
public class NameObject {
  public final String INTRODUCER = "/";
  public String name;

  public NameObject(String name) { this.name = name; }

  @Override
  public String toString() {
    return INTRODUCER + toHEX(this.name);
  }

  @Override
  public int hashCode() {
      return name.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
      return toString().equals(obj.toString());
  }

  private String toHEX(String value) {

    String result = "";
    for (char ch : value.toCharArray()) {
      if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'z' ||
          ch >= '0' && ch <= '9') {
        result += ch + "";
        continue;
      }
      String hex = "#" + String.format("%02x", (int)ch);
      result += hex;
    }
    return result;
  }

  public static void main(String[] args) {
    NameObject name = new NameObject("TESt NameObeje ct?");
    NameObject name2 = new NameObject("TESt NameObeje ct?");
    System.out.println(name.equals(name2));
  }
}
