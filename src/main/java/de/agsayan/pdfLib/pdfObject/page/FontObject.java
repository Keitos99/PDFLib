package de.agsayan.pdfLib.pdfObject.page;

import de.agsayan.pdfLib.pdfObject.TypeObjects.DictionaryObject;
import de.agsayan.pdfLib.pdfObject.TypeObjects.NameObject;
import javax.sound.sampled.AudioFileFormat.Type;

public class FontObject extends ResourceObject {

  private final String TYPE = "/Font";
  private int fontReference;
  private String fontName;
  private boolean isBold;
  private boolean isCursive;
  private String cursiveString = ""; // TODO: this a stupid way to do it

  public FontObject(int fontReference) { this.fontReference = fontReference; }

  public int getFontReference() { return fontReference; }

  public void setFontReference(int fontReference) {
    this.fontReference = fontReference;
  }

  public String getFontName() {
    // typographical emphasis
    String font = "";
    if (isBold()) {
      font += "-Bold";
    }
    if (isCursive()) {
      font += "-" + cursiveString;
    }

    return fontName + font;
  }

  public void setFontName(String fontName) {

    if (fontName.equals("Times-Roman")) {
      fontName = "Times";
      cursiveString = "Italic";
      this.fontName = fontName;
      return;
    }
    this.fontName = fontName;
    cursiveString = "Oblique";
  }

  public boolean isBold() { return isBold; }

  public void setBold(boolean isBold) { this.isBold = isBold; }

  public boolean isCursive() { return isCursive; }

  public void setCursive(boolean isCursive) { this.isCursive = isCursive; }

  @Override
  public String toString() {
    String result = new NameObject("F" + getFontReference()).toString();


    DictionaryObject items = new DictionaryObject();
    DictionaryObject item = new DictionaryObject();

    items.putItem("Type", TYPE);
    items.putItem("BaseFont", new NameObject(getFontName()));
    items.putItem("Subtype", "Type1");
    result = result + " " + items.toString();
    return result;
  }
}
