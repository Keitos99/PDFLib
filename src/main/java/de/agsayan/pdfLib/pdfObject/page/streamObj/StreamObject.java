package de.agsayan.pdfLib.pdfObject.page.streamObj;

import java.io.IOException;
import java.io.OutputStream;

import de.agsayan.pdfLib.pdfObject.Filter;
import de.agsayan.pdfLib.pdfObject.PDFObject;
import de.agsayan.pdfLib.pdfObject.TypeObjects.ArrayObject;
import de.agsayan.pdfLib.pdfObject.TypeObjects.DictionaryObject;
import de.agsayan.pdfLib.pdfObject.page.PageObject;

/*
 * 3.2.7 Stream Objects
 *
 */
public abstract class StreamObject extends PDFObject {
  private final String START_STREAM = "stream";
  private final String END_STREAM = "endstream";

  DictionaryObject<Object> streamDictionary;
  protected final String LENGTH = "LENGTH";

  // following are optional
  private final String FILTER = "Filter";            // array or name
  private final String DECODE_PARMS = "DecodeParms"; // dictionary or name
  protected String FILE_SPECIFICATION = "F";         // F
  protected String F_FILTER = "FFilter";             // name or array
  protected String F_DECODE_PARMS = "FDecodeParms";  // array or dictionary

  private String content;
  protected float xPosTextSpaceUnit;
  protected float yPosTextSpaceUnit;

  public StreamObject() {
    this.streamDictionary = new DictionaryObject<Object>();
    this.streamDictionary.putItem(LENGTH, 0); // LENGTH is not optional
  }

  public void setPosition(PageObject page, float x, float y) {
    this.xPosTextSpaceUnit = milimeterToPtsConverter(x);
    this.yPosTextSpaceUnit = milimeterToPtsConverter(y);
    // this.xPos = milimeterToPtsConverter(x);
    // this.yPos = page.getHeight() - milimeterToPtsConverter(y);
  }

  /**
   * sets the x and y position of the object. The bottom left corner of a page is the point, where x and y is 0
   */
  public void setPosition(float xTextSpaceUnit, float yTextSpaceUnit) {
    this.xPosTextSpaceUnit = xTextSpaceUnit;
    this.yPosTextSpaceUnit = yTextSpaceUnit;
  }

  private float milimeterToPtsConverter(float mm) {
    double pts = 2.8346438836889;
    return (float)(mm * pts);
  }

  public String buildStream() {
    return START_STREAM + "\n" + getContent() + "\n" + END_STREAM + "\n";
  }

  @Override
  public String toString() {
    return this.streamDictionary.toString() + "\n" + buildStream();
  }

  public void write(OutputStream os) {
    try {
      os.write(buildStream().getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getContent() { return content; }

  public void setContent(String content) { this.content = content; }

  public void setLength(long length) {
    this.streamDictionary.putItem(LENGTH, length);
  }

  public long getLength() {
    return (long)this.streamDictionary.getValue(LENGTH);
  }

  public void addFilter(Filter filter) {
    Object filterS = this.streamDictionary.getValue(FILTER);
    if (filterS == null) {
      this.streamDictionary.putItem(FILTER, filter);
      return;
    }

    if (filterS instanceof Filter) {
      ArrayObject<Filter> filters = new ArrayObject<>();
      filters.add((Filter)filterS);
      filters.add(filter);
      this.streamDictionary.putItem(FILTER, filters);
      return;
    }

    ((ArrayObject<Filter>)this.streamDictionary.getValue(FILTER)).add(filter);
  }

  public String getFilter() {
    return this.streamDictionary.getValue(FILTER).toString();
  }

  public void setDecodeParms(String key, String value) {
    this.streamDictionary.putItem(DECODE_PARMS, value);

    Object filterS = this.streamDictionary.getValue(DECODE_PARMS);
    if (filterS == null) {
      DictionaryObject<String> sub = new DictionaryObject<>();
      sub.putItem(key, value);

      this.streamDictionary.putItem(DECODE_PARMS, sub);
      return;
    }

    DictionaryObject<Object> sub =
        (DictionaryObject<Object>)this.streamDictionary;
    sub.putItem(key, value);
    this.streamDictionary.putItem(DECODE_PARMS, sub);
  }

  public String getDECODE_PARMS() {
    DictionaryObject<String> dict =
        (DictionaryObject<String>)this.streamDictionary.getValue(DECODE_PARMS);
    if (dict == null || dict.size() == 0)
      return "";

    if (dict.size() == 1)
      return dict.getFirst().toString();

    return dict.toString();
  }

  public void setFileSpecification(String fileSpecification) {
    this.streamDictionary.putItem(FILE_SPECIFICATION, fileSpecification);
  }

  public String getFileSpecification() {
    return this.streamDictionary.getValue(FILE_SPECIFICATION).toString();
  }

  public void addfFilter(Filter filter) {

    Object filterS = this.streamDictionary.getValue(F_FILTER);
    if (filterS == null) {
      this.streamDictionary.putItem(F_FILTER, filter);
      return;
    }

    if (filterS instanceof Filter) {
      ArrayObject<Filter> filters = new ArrayObject<>();
      filters.add((Filter)filterS);
      filters.add(filter);
      this.streamDictionary.putItem(F_FILTER, filters);
      return;
    }

    ((ArrayObject<Filter>)this.streamDictionary.getValue(F_FILTER)).add(filter);
  }

  public String getfFilter() {
    return this.streamDictionary.getValue(F_FILTER).toString();
  }

  public void addFDecodeParms(Object filter) {
    // protected DictionaryObject fDecodeParms;
    Object filterS = this.streamDictionary.getValue(F_DECODE_PARMS);
    if (filterS == null) {
      this.streamDictionary.putItem(F_DECODE_PARMS, filter);
      return;
    }

    if (filterS instanceof Filter) {
      ArrayObject<Object> filters = new ArrayObject<>();
      filters.add(filterS);
      filters.add(filter);
      this.streamDictionary.putItem(F_DECODE_PARMS, filters);
      return;
    }

    ((ArrayObject<Object>)this.streamDictionary.getValue(F_DECODE_PARMS))
        .add(filter);
  }

  public String getFDecodeParms() {
    return this.streamDictionary.getValue(F_DECODE_PARMS).toString();
  }
}
