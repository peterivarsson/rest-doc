/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.cybercom.rest.doc;

import java.util.List;

/**
 *
 * @author Peter Ivarsson Peter.Ivarsson@cybercom.com
 */
public class DataModelInfo {
   
   private String          packageAndClassName;
   private List<FieldInfo> fields;

   public String getPackageAndClassName() {
      return packageAndClassName;
   }

   public void setPackageAndClassName( String packageAndClassName ) {
      this.packageAndClassName = packageAndClassName;
   }

   public List<FieldInfo> getFields() {
      return fields;
   }

   public void setFields( List<FieldInfo> fields ) {
      this.fields = fields;
   }

   
}
