package de.agsayan.pdfLib.pdfObject;

public class PDFReference {

  private int objectNumeber;
  private int generation = 0;

  public int getGeneration() { return generation; }

  public void setGeneration(int generation) { this.generation = generation; }

  public int getObjectNumeber() { return objectNumeber; }

  public void setObjectNumeber(int objectNumeber) {
    this.objectNumeber = objectNumeber;
  }
}
