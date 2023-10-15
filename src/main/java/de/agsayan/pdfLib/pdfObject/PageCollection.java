package de.agsayan.pdfLib.pdfObject;

import de.agsayan.pdfLib.pdfObject.TypeObjects.ArrayObject;
import de.agsayan.pdfLib.pdfObject.TypeObjects.DictionaryObject;
import de.agsayan.pdfLib.pdfObject.TypeObjects.IndirectObject;
import de.agsayan.pdfLib.pdfObject.TypeObjects.NameObject;

public class PageCollection extends IndirectObject {

  // 2 0 obj
  // <<
  // /Type /Pages
  // /MediaBox [ 0 0 200 200 ]
  // /Count 1
  // /Kids [ 3 0 R ]
  // >>
  // endobj

  private DictionaryObject<Object> dictionary;
  private final NameObject TYPE = new NameObject("Pages");
  private int count;
  private String mediaBox;
  private ArrayObject<String> kids;

  public PageCollection(int count) {
    super(2);
    this.dictionary = new DictionaryObject<>();
    this.kids = new ArrayObject<>();
    this.mediaBox = "[ 0 0 200 200 ]";
  }

  @Override
  public String toString() {
    this.dictionary.put("Type", TYPE);
    if (mediaBox != null && !mediaBox.isEmpty())
      this.dictionary.put("MediaBox", mediaBox);
    this.dictionary.put("Count", count);
    this.dictionary.put("Kids", kids.toString());
    return this.dictionary.toString();
  }
}
