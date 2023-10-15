package de.agsayan.pdfLib.pdfObject.TypeObjects;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DictionaryObject
 */
public class DictionaryObject<T> {

  LinkedHashMap<String, Object> dict;

  public DictionaryObject() { this.dict = new LinkedHashMap<>(); }

  public void put(String key, Object value) {
    if (value instanceof NameObject) {
      String t = value.toString();
    }
    dict.put(new NameObject(key).toString(), value);
  }

  public Object getValue(String key) { return dict.get(key); }

  public Object getFirst() {
    Map.Entry<String, Object> firstEntry = dict.entrySet().iterator().next();
    return firstEntry.getValue();
  }

  public int size() { return dict.size(); }

  @Override
  public String toString() {
    String entries = "";
    for (String key : dict.keySet()) {
      Object value = dict.get(key);
      if (value instanceof NameObject) {
        String t = value.toString();
      }
      String entry = key + " " + value + "\n";
      entries += entry;
    }
    return "<<\n" + entries + ">>";
  }

  public static void main(String[] args) {
    DictionaryObject obj = new DictionaryObject();
    DictionaryObject subDict = new DictionaryObject();

    subDict.put("/Item1", 0.4);
    subDict.put("/Item2", true);
    subDict.put("/LastItem", "(not!)");
    subDict.put("/VeryLastItem", "(OK)");

    obj.put("Type", "/XObject");
    obj.put("/Type", "/Example");
    obj.put("/Subtype", "/DictionaryExample");
    obj.put("/Version", "0.01");
    obj.put("/IntegerItem", 12);
    obj.put("/SubDictionary", subDict);
    System.out.println(obj);
  }
}
