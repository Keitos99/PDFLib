package de.agsayan.pdfLib;

import static org.junit.Assert.assertTrue;

import de.agsayan.pdfLib.pdfObject.Filter;
import de.agsayan.pdfLib.pdfObject.page.streamObj.StreamObject;
import org.junit.Test;

/**
 * StreamObjectTest
 */
public class StreamObjectTest {
  @Test
  public void objectWithText() {
    StreamObject streamObject = new StreamObject() {};
    streamObject.setLength(100);
    streamObject.addFilter(Filter.DCTDecode);
    streamObject.setFileSpecification("adjaslkd");
    System.out.println(streamObject.toString());

    String result = "<<"
                    + "\n"
                    + "/LENGTH 100"
                    + "\n"
                    + "/Filter /DCTDecode"
                    + "\n"
                    + "/F adjaslkd"
                    + "\n"
                    + ">>"
                    + "\n"
                    + "stream"
                    + "\n"
                    + "null"
                    + "\n"
                    + "endstream\n";

    assertTrue(streamObject.toString().equals(result));
  }
}
