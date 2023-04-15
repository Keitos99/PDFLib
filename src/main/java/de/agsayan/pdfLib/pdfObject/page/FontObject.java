package de.agsayan.pdfLib.pdfObject.page;

public class FontObject extends ResourceObject {
  private int fontReference;
  private String fontName;
  private String fontType;

  public FontObject(int fontReference) { this.fontReference = fontReference; }

  public String getFont() {
    return "/F" + getFontReference() + "\n"
        + "   <<\n"
        + "   /Type /Font\n"
        + "   /BaseFont /+" + getFontName() + "\n"
        + "   /Subtype /Type1\n"
        + "   >>\n";
  }

  public String getFontName() { return fontName; }

  public void setFontName(String fontName) { this.fontName = fontName; }

  public String getFontType() { return fontType; }

  public void setFontType(String fontType) { this.fontType = fontType; }

  public int getFontReference() { return fontReference; }
}
