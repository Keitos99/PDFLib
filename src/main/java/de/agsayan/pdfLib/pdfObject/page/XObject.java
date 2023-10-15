package de.agsayan.pdfLib.pdfObject.page;

import java.io.IOException;
import java.io.OutputStream;

import de.agsayan.pdfLib.pdfObject.TypeObjects.DictionaryObject;
import de.agsayan.pdfLib.pdfObject.TypeObjects.NameObject;
import de.agsayan.pdfLib.pdfObject.page.streamObj.StreamObject;

public class XObject extends StreamObject {

  private final String TYPE = "XObject";
  protected String subtype;
  protected String colorSpace;
  protected int width;
  protected int height;
  protected int bitsPerComponent;
  protected String filter;

  public XObject() {
    type = TYPE;
    bitsPerComponent = 8;
  }

  @Override
  public String buildStream() {
    DictionaryObject dictionary = new DictionaryObject();
    dictionary.put("Type", new NameObject(type));
    dictionary.put("Subtype", new NameObject(subtype));
    dictionary.put("Width", width);
    dictionary.put("Height", height);
    dictionary.put("BitsPerComponent", bitsPerComponent);
    dictionary.put("Filter", new NameObject(filter));
    dictionary.put("ColorSpace", new NameObject(colorSpace));
    dictionary.put("Length", length);

    String result = dictionary.toString() + "\n" + super.buildStream();

    return result;
  }

  @Override
  public void write(OutputStream os) {
      super.write(os);
  }

  public void setSubtype(String subtype) { this.subtype = subtype; }

  public void setColorSpace(String colorSpace) { this.colorSpace = colorSpace; }

  public void setLength(long length) { this.length = length; }

  public void setFilter(String filter) { this.filter = filter; }

  public void setWidth(int width) { this.width = width; }

  public void setHeight(int height) { this.height = height; }
}
