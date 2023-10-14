package de.agsayan.pdfLib.pdfObject.page;

import de.agsayan.pdfLib.pdfObject.TypeObjects.DictionaryObject;
import de.agsayan.pdfLib.pdfObject.TypeObjects.NameObject;
import de.agsayan.pdfLib.pdfObject.page.streamObj.StreamObject;

public class XObject extends StreamObject {

  public XObject() {
    type = "XObject";
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

    String result = dictionary.toString() + super.buildStream();

    return result;
      
  }

  public void setSubtype(String subtype) { this.subtype = subtype; }

  public void setColorSpace(String colorSpace) { this.colorSpace = colorSpace; }

  public void setLength(long length) { this.length = length; }

  public void setFilter(String filter) { this.filter = filter; }

  public void setWidth(int width) { this.width = width; }

  public void setHeight(int height) { this.height = height; }
}
