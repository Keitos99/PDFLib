package de.agsayan.pdfLib.pdfObject.TypeObjects;

import java.util.ArrayList;

/**
 * ArrayDictionary
 * 3.2.5
 */
public class ArrayObject {

  private ArrayList<Object> items;

  public ArrayObject() { items = new ArrayList<>(); }

  public void add(Object item) { items.add(item); }

  @Override
  public String toString() {
    String content = "";
    for (Object item : items)
      content += item + " ";

    return "[" + content + "]"
        + "\n";
  }

  public static void main(String[] args) {
    ArrayObject array = new ArrayObject();
    array.add("0");
    array.add("0");
    array.add("100");
    array.add("400");
    System.out.println(array);
  }
}
