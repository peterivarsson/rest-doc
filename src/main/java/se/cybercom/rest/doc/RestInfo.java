/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.cybercom.rest.doc;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Peter Ivarsson Peter.Ivarsson@cybercom.com
 */
public class RestInfo {
   
   private List<ClassInfo>      classInfo;
   private HashMap<String, DataModelInfo>  dataModelInfo = new HashMap<>();

   public List<ClassInfo> getClassInfo() {
      return classInfo;
   }

   public void setClassInfo( List<ClassInfo> classInfo ) {
      this.classInfo = classInfo;
   }

   public HashMap<String, DataModelInfo> getDataModelInfo() {
      return dataModelInfo;
   }

   public void setDataModelInfo( HashMap<String, DataModelInfo> dataModelInfo ) {
      this.dataModelInfo = dataModelInfo;
   }

}
