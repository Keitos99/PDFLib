package de.agsayan.pdfLib;

import static org.junit.Assert.assertTrue;

import de.agsayan.pdfLib.pdfObject.Filter;
import de.agsayan.pdfLib.pdfObject.TypeObjects.IndirectObject;
import de.agsayan.pdfLib.pdfObject.page.streamObj.TextObject;
import org.junit.Test;

/**
 * IndirectObjectTest
 */
public class IndirectObjectTest {

  @Test
  public void objectWithText() {
    IndirectObject indirectObject = new IndirectObject(1);

    TextObject txt = new TextObject();
    txt.setBold(true);
    txt.setText("Hello World!", 12);
    txt.setPosition(true, 70, 50);
    txt.setLength(100);
    txt.addFilter(Filter.DCTDecode);
    txt.addFilter(Filter.LZWDecode);

    indirectObject.setContent(txt);

    String result = "1 0 obj"
                    + "\n"
                    + "<<"
                    + "\n"
                    + "/LENGTH 100"
                    + "\n"
                    + "/Filter [/DCTDecode /LZWDecode ]"
                    + "\n\n"
                    + ">>"
                    + "\n"
                    + "stream"
                    + "\n"
                    + "BT"
                    + "\n"
                    + "70.0 50.0 TD"
                    + "\n"
                    + "/F0 12 Tf"
                    + "\n"
                    + "(Hello World!) TJ"
                    + "\n"
                    + "ET"
                    + "\n"
                    + "endstream"
                    + "\n"
                    + ""
                    + "\n"
                    + "endobj";

    System.out.println(indirectObject.toString());
    assertTrue(indirectObject.toString().equals(result));
  }
}
