package de.agsayan.pdfLib.pdfObject.TypeObjects;

import java.util.ArrayList;

/**
 * ArrayDictionary
 * 3.2.5
 */
public class ArrayObject<T> {
  // TODO: maybe extend from ArrayList?

  private final int MAX_ITEMS = 8191;
  private ArrayList<T> items;

  public ArrayObject() { items = new ArrayList<>(); }

  public void add(T item) { items.add(item); }

  public int size() { return items.size(); }

  public Object get(int index) { return items.get(index); }

  @Override
  public String toString() {
    String content = "";
    for (Object item : items)
      content += item + " ";

    return "[" + content + "]\n";
  }
}
