package de.agsayan.pdfLib.pdfObject.page;

import de.agsayan.pdfLib.pdfObject.page.streamObj.StreamObj;

public class XObject extends StreamObj {

  public XObject() {
    type = "XObject";
    bitsPerComponent = 8;
  }

  @Override
  public String buildStream() {
    return "<<\n"
        + "/Type /" + type + "\n"
        + "/Subtype /" + subtype + "\n"
        + "/Width " + width + "\n"
        + "/Height " + height + "\n"
        + "/BitsPerComponent " + bitsPerComponent + "\n"
        + "/Filter /" + filter + "\n"
        + "/ColorSpace /" + colorSpace + "\n"
        + "/Length " + length + "\n"
        + ">>\n"
        + "stream\n" +
        // content muss hier rein
        getContent() + "\n"
        + "endstream\n";
  }

  public void setSubtype(String subtype) { this.subtype = subtype; }

  public void setColorSpace(String colorSpace) { this.colorSpace = colorSpace; }

  public void setLength(long length) { this.length = length; }

  public void setFilter(String filter) { this.filter = filter; }

  public void setWidth(int width) { this.width = width; }

  public void setHeight(int height) { this.height = height; }
}
