/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.cybercom.rest.doc;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import static se.cybercom.rest.doc.RestDocumentationResourceConstants.RESOURCE_TYPE;

/**
 *
 * @author Peter Ivarsson Peter.Ivarsson@cybercom.com
 */
@Path( "/resources" )
public class RestDocumentationResource {

   private static int methodNumber = -1;

   /**
    * Returns a list of movies that is currently running in a specific city. Ordered by movie name in ascending order.
    *
    * @return
    */
   @GET
   @Produces( { MediaType.TEXT_HTML } )
   @Path( "" )
   public String getRestDocumentation() {

      StringBuffer htmlBuffer = htmlHeader();
      
      htmlBodyHeader( htmlBuffer, "REST Resources" );
      
      htmlRestResourcesList( htmlBuffer );

      htmlFooter( htmlBuffer );

      return htmlBuffer.toString();
   }

   /**
    * Returns a list of movies that is currently running in a specific city. Ordered by movie name in ascending order.
    *
    * @return
    */
   @GET
   @Produces( { MediaType.TEXT_HTML } )
   @Path("/{" + RESOURCE_TYPE + "}")
   public String getResouresDocumentation( @PathParam(RESOURCE_TYPE) final String resourceType ) {

      StringBuffer htmlBuffer = htmlHeader();
      
      htmlBodyHeader( htmlBuffer, resourceType );

      htmlRestResourceDetail( htmlBuffer, resourceType );
      
      htmlFooter( htmlBuffer );

      return htmlBuffer.toString();
   }
   
   private StringBuffer htmlHeader() {
   
      StringBuffer htmlBuffer = new StringBuffer( "<html lang=\"en\">\r\t<head>\r\t\t\r\t<style>\r\ttable, th, td {\r\t\tborder: 1px solid;\r\t\tborder-collapse: collapse;\r\t\tborder-color: #D6D6C2;\r\t}\r\tth, td {\r\t\tpadding: 8px;\r\t}\r\t</style>\r\t</head>\r\t<title>REST documentation</title>\r\t<body>\r" );

      return htmlBuffer;
   }

   private void htmlBodyHeader( StringBuffer htmlBuffer, String headerText ) {
   
      htmlBuffer.append( "\t<a name=\"top\"><h1>" );
      htmlBuffer.append( headerText );
      htmlBuffer.append( "\t</h1></a>" );
   }
   
   private void htmlText( StringBuffer htmlBuffer, String text ) {
   
      htmlBuffer.append( text );
   }
   
   private void htmlRestResourcesList( StringBuffer htmlBuffer ) {
   
      htmlBuffer.append( "\t<ul>\r" );
              
      RestDocHandler.restInfo.getClassInfo().stream().forEach( (res) -> {
         
         htmlBuffer.append( "\t\t<li>\r\t\t\t<a href=\"resources\\" );
         htmlBuffer.append( res.getClassName() );
         htmlBuffer.append( "\">" );
         
         htmlBuffer.append( res.getClassName() );

         htmlBuffer.append( "\t\t\t</a>\t\t</li><BR>\r" );
      } );
      
      htmlBuffer.append( "\t</ul>\r" );
   }
   
   private void htmlRestResourceDetail( StringBuffer htmlBuffer, final String resourceType ) {
   
      List<MethodInfo>  methodInfoList = new ArrayList<>();
              
      RestDocHandler.restInfo.getClassInfo().stream().forEach( (res) -> {
         
         if( res.getClassName().equals( resourceType ) ) {
            
            methodInfoList.addAll( res.getMethodInfo() );
         };
      } );
      
      methodNumber = 1;
      
      htmlBuffer.append( "\t<ul>\r" );
         
      methodInfoList.stream().forEach( (method) -> {

         htmlBuffer.append( "\t\t<li><a href=\"#method" );
         htmlBuffer.append( methodNumber );
         htmlBuffer.append( "\">" );
         htmlBuffer.append( method.getRestPath() );
         htmlBuffer.append( "</a></li><BR>\r" );
         
         methodNumber++;
      } );

      methodNumber = 1;

      methodInfoList.stream().forEach( (method) -> {
         
         htmlMethodDetail( htmlBuffer, method, methodNumber );
         
         methodNumber++;
      } );
}
   
   private void htmlMethodDetail( StringBuffer htmlBuffer, final MethodInfo methodInfo, int methodNumber ) {
   
      htmlBuffer.append( "\t\t<p><a name=\"method" );
      htmlBuffer.append( methodNumber );
      htmlBuffer.append( "\"><h3>" );
      htmlBuffer.append( methodInfo.getRestPath() );
      htmlBuffer.append( "</h3></a>\r\t\t" );
      htmlBuffer.append( methodInfo.getHttpRequestType() );
      htmlBuffer.append( "<BR><BR>\t\t<table>\r\t\t\t<tr><td>Class</td><td>Name</td><td>Parameter type</td></tr>" );

      List<ParameterInfo> parameterInfoList = methodInfo.getParameterInfo();
      
      parameterInfoList.stream().forEach( (param) -> {
      
         htmlBuffer.append( "\r\t\t\t<tr><td>" );
         htmlBuffer.append( param.getParameterClassName() );
         htmlBuffer.append( "</td><td>" );
         htmlBuffer.append( param.getParameterAnnotationName() );
         htmlBuffer.append( "</td><td>" );
         switch( param.getParameterType() ) {
            
            case "javax.ws.rs.PathParam":
               htmlBuffer.append( "Path parameter" );
               break;
               
            case "javax.ws.rs.HeaderParam":
               htmlBuffer.append( "Header parameter" );
               break;
               
            default:
               htmlBuffer.append( param.getParameterType() );
               break;
         }
         htmlBuffer.append( "</td></tr>\r" );
      });
      
      htmlBuffer.append( "\r\t\t<table><BR>" );
      
      htmlBuffer.append( "Response Body<BR>" );      

      htmlBuffer.append( "<BR>\t\t<table>\r\t\t\t<tr><td>Element</td><td>Media Type</td></tr>" );
      
      htmlBuffer.append( "\r\t\t\t<tr><td>" );
      
      if( (methodInfo.getReturnInfo().getAnnotatedReturnType() == null) ||
           methodInfo.getReturnInfo().getAnnotatedReturnType().isEmpty() ) {
         
         htmlBuffer.append( methodInfo.getReturnInfo().getReturnClassName() );
      }
      else  {
         
         htmlBuffer.append( methodInfo.getReturnInfo().getAnnotatedReturnType() );
      }
        
      htmlBuffer.append( "\r\t\t\t</td><td>" );
      
      htmlBuffer.append( methodInfo.getProducesType() );
      
      htmlBuffer.append( "\r\t\t\t</td></tr>\r\t\t<table><BR>" );
   }
   
   private void htmlFooter( StringBuffer htmlBuffer ) {
   
      htmlBuffer.append( "\r\t<BR><a href=\"#top\"><h4>To top</a></h4> \r\t</body>\r</html>" );
   }

}
