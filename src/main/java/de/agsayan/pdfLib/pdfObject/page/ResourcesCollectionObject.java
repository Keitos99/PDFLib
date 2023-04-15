package de.agsayan.pdfLib.pdfObject.page;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class ResourcesCollectionObject {

  Hashtable<String, ArrayList<ResourceObject>> resources = new Hashtable<>();

  public void addResource(String resourceType, ResourceObject resObj) {
    if (resources.containsKey(resourceType)) {
      resources.get(resourceType).add(resObj);
    } else {
      ArrayList<ResourceObject> resObjs = new ArrayList<>();
      resObjs.add(resObj);

      resources.put(resourceType, resObjs);
    }
  }

  public String getResources() {
    String res = "";
    for (Map.Entry<String, ArrayList<ResourceObject>> resource :
         resources.entrySet()) {
      if (resource.getKey().equals("Font")) {
        for (ResourceObject resObject : resource.getValue()) {
          FontObject fontObj = (FontObject)resObject;
          res += fontObj.getFont() + "\n";
        }
      }
    }
    return "";
  }
}
