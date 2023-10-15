package de.agsayan.pdfLib;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import de.agsayan.pdfLib.pdfObject.page.streamObj.TextObject;
import org.junit.Test;

/**
 * TextObjectTest
 */
public class TextObjectTest {

  @Test
  public void checkTextBuildStream() {
    TextObject txt = new TextObject();
    txt.setBold(true);
    txt.setText("Hello World!", 12);
    txt.setPosition(true, 70, 50);

    String result = txt.buildStream();
    String resultCheck = "stream\n"
                         + "BT\n"
                         + "70.0 50.0 TD\n"
                         + "/F0 12 Tf\n"
                         + "(Hello World!) TJ\n"
                         + "ET\n"
                         + "endstream\n";

    assertTrue(result.equals(resultCheck));
    ;
  }
}
