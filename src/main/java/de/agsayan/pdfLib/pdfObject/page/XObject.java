package de.agsayan.pdfLib.pdfObject.page;

import de.agsayan.pdfLib.pdfObject.TypeObjects.DictionaryObject;
import de.agsayan.pdfLib.pdfObject.TypeObjects.NameObject;
import de.agsayan.pdfLib.pdfObject.page.streamObj.StreamObject;
import java.io.IOException;
import java.io.OutputStream;

public class XObject extends StreamObject {

  private final String TYPE = "XObject";
  protected String subtype;
  protected String colorSpace;
  protected int width;
  protected int height;
  protected int bitsPerComponent;
  protected String filter;
  protected String type;

  public XObject() {
    type = TYPE;
    bitsPerComponent = 8;
  }

  @Override
  public String buildStream() {
    DictionaryObject<Object> dictionary = new DictionaryObject<>();
    dictionary.putItem("Type", new NameObject(type));
    dictionary.putItem("Subtype", new NameObject(subtype));
    dictionary.putItem("Width", width);
    dictionary.putItem("Height", height);
    dictionary.putItem("BitsPerComponent", bitsPerComponent);
    dictionary.putItem("Filter", new NameObject(filter));
    dictionary.putItem("ColorSpace", new NameObject(colorSpace));
    dictionary.putItem("Length", getLength());

    String result = dictionary.toString() + "\n" + super.buildStream();

    return result;
  }

  @Override
  public void write(OutputStream os) {
    super.write(os);
  }

  public void setSubtype(String subtype) { this.subtype = subtype; }

  public void setColorSpace(String colorSpace) { this.colorSpace = colorSpace; }

  public void setFilter(String filter) { this.filter = filter; }

  public void setWidth(int width) { this.width = width; }

  public void setHeight(int height) { this.height = height; }
}
