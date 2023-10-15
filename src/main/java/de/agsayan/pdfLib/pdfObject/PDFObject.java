package de.agsayan.pdfLib.pdfObject;

import java.io.IOException;
import java.io.OutputStream;

public abstract class PDFObject {

  PDFReference reference = new PDFReference();

  public PDFReference getPDFReference() { return reference; }

  public  void write(OutputStream os) {
    try {
      os.write(toString().getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
