package de.agsayan.pdfLib.pdfObject.TypeObjects;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DictionaryObject
 */
public class DictionaryObject<T> {

  LinkedHashMap<NameObject, T> dict;

  public DictionaryObject() { this.dict = new LinkedHashMap<>(); }

  public void putItem(String key, T value) {
    dict.put(new NameObject(key), value);
  }

  public void putItem(NameObject key, T value) { dict.put(key, value); }

  public T getValue(String key) { return dict.get(new NameObject(key)); }

  public T getFirst() {
    Map.Entry<NameObject, T> firstEntry = dict.entrySet().iterator().next();
    return firstEntry.getValue();
  }

  public int size() { return dict.size(); }

  @Override
  public String toString() {
    String entries = "";
    for (NameObject key : dict.keySet()) {
      T value = dict.get(key);
      String entry = key + " " + value + "\n";
      entries += entry;
    }
    return "<<\n" + entries + ">>";
  }
}
