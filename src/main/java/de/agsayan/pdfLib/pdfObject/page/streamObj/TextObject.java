package de.agsayan.pdfLib.pdfObject.page.streamObj;

import de.agsayan.pdfLib.pdfObject.TypeObjects.NameObject;
import de.agsayan.pdfLib.pdfObject.TypeObjects.StringObjects;
import java.util.ArrayList;
import java.util.Arrays;

public class TextObject extends StreamObject {

  private static final String BEGIN_TEXT = "BT";
  private static final String END_TEXT = "ET";
  private static final String TEXT_FONT = "Tf";
  private static final String TEXT_OFFSET = "TD";
  private static final String SHOW_TEXT = "Tj";

  private String text;
  private String textAlignment;
  private String textFont;
  private boolean isCursive;
  private boolean isBold;
  private boolean isUnderlined;
  private int fontReference;
  private int textSize;
  private String textColor;

  public void setText(String text, int textSize) {
    this.text = text;
    this.textSize = textSize;
  }

  public String getText() { return this.text; }

  public String getTextAlignment() { return textAlignment; }

  public void setTextAlignment(String textAlignment) {
    this.textAlignment = textAlignment;
  }

  public boolean isCursive() { return isCursive; }

  public void setCursive(boolean cursive) { isCursive = cursive; }

  public boolean isBold() { return isBold; }

  public void setBold(boolean bold) { isBold = bold; }

  public boolean isUnderlined() { return isUnderlined; }

  public void setUnderlined(boolean underlined) { isUnderlined = underlined; }

  public int getFontReference() { return fontReference; }

  public void setFontReference(int fontReference) {
    this.fontReference = fontReference;
  }

  public int getTextSize() { return textSize; }

  public void setTextSize(int textSize) { this.textSize = textSize; }

  public String getTextColor() { return textColor; }

  public void setTextColor(String textColor) { this.textColor = textColor; }

  public String getTextFont() { return textFont; }

  public void setTextFont(String textFont) { this.textFont = textFont; }

  @Override
  public String getContent() {
    StringObjects string = new StringObjects(getText());

    String result = BEGIN_TEXT + "\n";
    result += xPos + " " + yPos + " " + TEXT_OFFSET + "\n";
    result += new NameObject("F" + fontReference) + " " + getTextSize() + " " +
              TEXT_FONT + "\n";
    result += string + " " + SHOW_TEXT + "\n";
    result += END_TEXT + "";

    return result;
  }
}
