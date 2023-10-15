package de.agsayan.pdfLib.pdfObject.TypeObjects;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DictionaryObject
 */
public class DictionaryObject<T> {

  LinkedHashMap<String, T> dict;

  public DictionaryObject() { this.dict = new LinkedHashMap<>(); }

  public void put(String key, T value) {
    dict.put(new NameObject(key).toString(), value);
  }

  public T getValue(String key) { return dict.get(key); }

  public T getFirst() {
    Map.Entry<String, T> firstEntry = dict.entrySet().iterator().next();
    return firstEntry.getValue();
  }

  public int size() { return dict.size(); }

  @Override
  public String toString() {
    String entries = "";
    for (String key : dict.keySet()) {
      T value = dict.get(key);
      if (value instanceof NameObject) {
        String t = value.toString();
      }
      String entry = key + " " + value + "\n";
      entries += entry;
    }
    return "<<\n" + entries + ">>";
  }
}
