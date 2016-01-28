/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.cybercom.rest.doc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import static se.cybercom.rest.doc.RestDocumentationResourceConstants.DOMAIN_DATA_TYPE;
import static se.cybercom.rest.doc.RestDocumentationResourceConstants.RESOURCE_TYPE;

/**
 *
 * @author Peter Ivarsson Peter.Ivarsson@cybercom.com
 */
@Path( "/resources" )
public class RestDocumentationResource {

   private static final Logger logger = org.slf4j.LoggerFactory.getLogger( RestDocumentationResource.class.getName() );

   private static int methodNumber = -1;

   @Context
   UriInfo uriInfo;


   /**
    * Returns a list of movies that is currently running in a specific city. Ordered by movie name in ascending order.
    *
    * @return
    */
   @GET
   @Produces( { MediaType.TEXT_HTML } )
   @Path( "" )
   public String getRestDocumentation() {

      logger.debug( "getResouresDocumentation() main" );
      
      final StringBuffer htmlBuffer = htmlHeader();
      
      htmlBodyHeader( htmlBuffer, "REST Resources" );
      
      htmlRestResourcesList( htmlBuffer );

      htmlGoToProgrammersInfo( htmlBuffer );

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

      logger.debug( "getResouresDocumentation( " + resourceType + " )" );
      
      final StringBuffer htmlBuffer = htmlHeader();

      htmlBodyHeader( htmlBuffer, resourceType );

      htmlGoBack( htmlBuffer );

      htmlRestResourceDetail( htmlBuffer, resourceType );
      
      htmlGoBack( htmlBuffer );
      
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
   @Path("/{" + RESOURCE_TYPE + "}/data/{" + DOMAIN_DATA_TYPE + "}")
   public String getDomainDataDocumentation( @PathParam(RESOURCE_TYPE) final String resourceType,
                                             @PathParam(DOMAIN_DATA_TYPE) final String domainDataType ) {

      logger.debug( "getResouresDocumentation( " + resourceType + ", " + domainDataType + " )" );
      
      final StringBuffer htmlBuffer = htmlHeader();
      
      htmlBodyHeader( htmlBuffer, domainDataType );

      htmlGoBack( htmlBuffer, resourceType );
      
      htmlRestResourceDomainData( htmlBuffer, domainDataType );
      
      htmlGoBack( htmlBuffer, resourceType );
      
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
   @Path("/info")
   public String getRestProgrammerInfo( ) {

      final StringBuffer htmlBuffer = htmlHeader();
      
      htmlBodyHeader( htmlBuffer, "Programmers information" );

      htmlRestProgrammerInfo( htmlBuffer );
      
      htmlGoBack( htmlBuffer );
      
      htmlFooter( htmlBuffer );

      return htmlBuffer.toString();
   }
   
   private StringBuffer htmlHeader() {
   
      final StringBuffer htmlBuffer = new StringBuffer( 4096 );
      
      htmlBuffer.append( "<html lang=\"en\">\r\t<head>" );
      htmlBuffer.append( "\r\r\t<style>\r\t\ttable, th, td {\r\t\t\tborder: 1px solid;\r\t\t\tborder-collapse: collapse;\r\t\t\tborder-color: #D6D6C2;\r\t\t}\r\t\tth, td {\r\t\t\tpadding: 8px;\r\t\t}\r\t</style>" );
      htmlBuffer.append( "\r\r\t\t<title>REST documentation</title>\r\t</head>\r\r\t<body>" );

      return htmlBuffer;
   }

   private void htmlBodyHeader( final StringBuffer htmlBuffer, String headerText ) {
   
      htmlBuffer.append( "\r\t\t<a name=\"top\"><h1>" );
      htmlBuffer.append( headerText );
      htmlBuffer.append( "</h1></a>" );
   }
   
   private void htmlRestResourcesList( final StringBuffer htmlBuffer ) {
   
      htmlBuffer.append( "\r\r\t<ul>" );
              
      logger.debug( "htmlRestResourcesList()  restInfo.ClassInfo size = " + 
                    RestDocHandler.restInfo.getClassInfo().size() );
      
      RestDocHandler.restInfo.getClassInfo().stream().forEach( (res) -> {
         
         htmlBuffer.append( "\r\t\t<li><a href=\"" );
         htmlBuffer.append(  uriInfo.getBaseUri().getPath() );   // '/restdoc/'
         htmlBuffer.append( "resources/" );
         htmlBuffer.append( res.getClassName() );
         htmlBuffer.append( "\">" );

         htmlBuffer.append( res.getClassName() );

         htmlBuffer.append( "</a></li>\r\t\t<BR>" );
      } );
      
      htmlBuffer.append( "\r\t</ul>" );
   }
   
   private void htmlRestResourceDetail( final StringBuffer htmlBuffer, final String resourceType ) {
   
      List<MethodInfo>  methodInfoList = new ArrayList<>();
              
      logger.debug( "htmlRestResourceDetail()  restInfo.ClassInfo size = " + 
                    RestDocHandler.restInfo.getClassInfo().size() );
      
      RestDocHandler.restInfo.getClassInfo().stream().forEach( (res) -> {
         
         if( res.getClassName().equals( resourceType ) ) {
            
            methodInfoList.addAll( res.getMethodInfo() );
         };
      } );
      
      methodNumber = 1;
      
      htmlBuffer.append( "\r\t\t<ul>" );
         
      methodInfoList.stream().forEach( (method) -> {

         htmlBuffer.append( "\r\t\t\t<li><a href=\"#method" );
         htmlBuffer.append( methodNumber );
         htmlBuffer.append( "\">" );
         htmlBuffer.append( method.getRestPath() );
         htmlBuffer.append( "</a></li><BR>" );
         
         methodNumber++;
      } );

      htmlBuffer.append( "\r\t\t</ul>" );
         
      methodNumber = 1;

      methodInfoList.stream().forEach( (method) -> {
         
         htmlMethodDetail( htmlBuffer, method, resourceType, methodNumber );
         
         methodNumber++;
      } );
}
   
   private void htmlMethodDetail( final StringBuffer htmlBuffer, final MethodInfo methodInfo, final String resourceType, final int methodNumber ) {
   
      htmlBuffer.append( "\r\r\r\t\t<p><a name=\"method" );
      htmlBuffer.append( methodNumber );
      htmlBuffer.append( "\"><h3>" );
      htmlBuffer.append( methodInfo.getRestPath() );
      htmlBuffer.append( "</h3></a></p>" );

      htmlBuffer.append( "\r\r\t\t<p>" );
      htmlBuffer.append( methodInfo.getHttpRequestType() );
      htmlBuffer.append( "</p>" );
      htmlBuffer.append( "\r\r\t\t<table>\r\t\t\t<tr><td>Class</td><td>Name</td><td>Parameter type</td></tr>" );

      List<ParameterInfo> parameterInfoList = methodInfo.getParameterInfo();
      
      parameterInfoList.stream().forEach( (param) -> {
      
         htmlBuffer.append( "\r\t\t\t<tr><td>" );
         htmlBuffer.append( param.getParameterAnnotationName() );
         htmlBuffer.append( "</td><td>" );
         if ( isDomainData( param.getParameterClassName() ) ) {
            
            // Domain data
            htmlBuffer.append( "\r\t\t<a href=\"" );
            htmlBuffer.append(  uriInfo.getBaseUri().getPath() );   // '/restdoc/'
            htmlBuffer.append( "resources/" );
            htmlBuffer.append( resourceType );
            htmlBuffer.append( "/data/" );
            htmlBuffer.append( param.getParameterClassName() );
            htmlBuffer.append( "\">" );
            htmlBuffer.append( param.getParameterClassName() );
            htmlBuffer.append( "</a>" );
         }
         else {
            
            htmlBuffer.append( param.getParameterClassName() );
         }
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
         htmlBuffer.append( "</td></tr>" );
      });
      
      htmlBuffer.append( "\r\t\t</table><BR>" );
      
      htmlBuffer.append( "\r\r\t\tResponse Body" );      

      htmlBuffer.append( "<BR><BR>\r\r\t\t<table>\r\t\t\t<tr><td>Element</td><td>Media Type</td></tr>" );
      
      htmlBuffer.append( "\r\t\t\t<tr><td>" );
      
      if( ( ( methodInfo.getReturnInfo().getAnnotatedReturnType() == null ) ||
            methodInfo.getReturnInfo().getAnnotatedReturnType().isEmpty() ) &&
          ( isDomainData( methodInfo.getReturnInfo().getReturnClassName() ) == false)  ) {
         
         htmlBuffer.append( methodInfo.getReturnInfo().getReturnClassName() );
      }
      else  {
         
         // Domasin data
         htmlBuffer.append( "\r\t\t<a href=\"" );
         htmlBuffer.append(  uriInfo.getBaseUri().getPath() );   // '/restdoc/'
         htmlBuffer.append( "resources/" );
         htmlBuffer.append( resourceType );
         htmlBuffer.append( "/data/" );
         htmlBuffer.append( methodInfo.getReturnInfo().getAnnotatedReturnType() );
         htmlBuffer.append( "\">" );
         htmlBuffer.append( methodInfo.getReturnInfo().getAnnotatedReturnType() );
         htmlBuffer.append( "</a>" );
      }
        
      htmlBuffer.append( "</td><td>" );
      
      htmlBuffer.append( methodInfo.getProducesType() );
      
      htmlBuffer.append( "</td>\r\t\t\t</tr>\r\t\t</table><BR>" );
   }
   
   private void htmlRestResourceDomainData( final StringBuffer htmlBuffer, final String domainDataType ) {
   
      logger.debug( "htmlRestResourceDomainData()  restInfo.ClassInfo size = " + 
                    RestDocHandler.restInfo.getClassInfo().size() );
      
      final List<FieldInfo> fields = RestDocHandler.restInfo.getDataModelInfo().get( domainDataType ).getFields();
      
      htmlBuffer.append( "\r\r\t\t<table>" );

      htmlBuffer.append( "\r\t\t\t<tr><td>Field name</td><td>Field type</td></tr>" );
      
      fields.stream().forEach( (field) -> {
         
         htmlBuffer.append( "\r\t\t\t<tr><td>" );
         htmlBuffer.append( field.getFieldName() );
         htmlBuffer.append( "</td><td>" );
         htmlBuffer.append( field.getFieldType() );
         htmlBuffer.append( "</td></tr>" );
      } );
      
      htmlBuffer.append( "\r\t\t</table><BR>" );
   }
   
   private void htmlRestProgrammerInfo( final StringBuffer htmlBuffer ) {
   
      htmlBuffer.append( "\r\r\t\t<table>" );

      htmlBuffer.append( "\r\t\t\t<tr><td>Annotation</td><td>Comment</td></tr>" );
      htmlBuffer.append( "\r\t\t\t<tr><td>DocReturnType</td><td>If you wan't to set an other return type than the default return type. <BR>Se example below</td></tr>" );
      htmlBuffer.append( "\r\t\t\t<tr><td colspan=2></td></tr>" );
      htmlBuffer.append( "\r\t\t\t<tr><td colspan=2>" );
      
      htmlBuffer.append( "@POST<BR>@Produces( { MediaType.APPLICATION_JSON } )<BR>@Path( PATH_VALIDATE )<BR><b>@DocReturnType( key = \"se.cybercom.rest.doc.PaymentValidation\" )</b><BR>public Response validatePayment( ) {<BR><BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PaymentValidation paymentValidation = new PaymentValidation();<BR><BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return Response.ok( paymentValidation ).build();<BR>}" );

      htmlBuffer.append( "</td></tr>" );
      htmlBuffer.append( "\r\t\t</table><BR>" );
      
         
   }
      
   private void htmlGoBack( final StringBuffer htmlBuffer ) {
   
      htmlBuffer.append( "\r\r\t<ul style=\"list-style-type: none\">" );
      htmlBuffer.append( "\r\t\t<li><a href=\"" );
      htmlBuffer.append(  uriInfo.getBaseUri().getPath() );   // '/restdoc/'
      htmlBuffer.append( "resources\"/><h4>Go Back</h4></a></li>" );
      htmlBuffer.append( "\r\t</ul>" );
   }
   
   private void htmlGoBack( final StringBuffer htmlBuffer, final String resourceType ) {
   
      RestDocHandler.restInfo.getClassInfo().stream().forEach( (res) -> {
         
         if( res.getClassName().equals( resourceType ) ) {

            htmlBuffer.append( "\r\t<ul style=\"list-style-type: none\">" );
            htmlBuffer.append( "\r\t\t<li><a href=\"" );
            htmlBuffer.append(  uriInfo.getBaseUri().getPath() );   // '/restdoc/'
            htmlBuffer.append( "resources/" );
            htmlBuffer.append( res.getClassName() );
            htmlBuffer.append( "\"><h4>Go Back</h4></a></li>" );
            htmlBuffer.append( "\r\t</ul>" );
         }
      } );
   }

   private void htmlGoToProgrammersInfo( final StringBuffer htmlBuffer ) {
   
      htmlBuffer.append( "\r\r\t<ul style=\"list-style-type: none\">" );
      htmlBuffer.append( "\r\t\t<li><a href=\"" );
      htmlBuffer.append(  uriInfo.getBaseUri().getPath() );   // '/restdoc/'
      htmlBuffer.append( "resources/info\"><h4>Programmers Information</h4></a></li>" );
      htmlBuffer.append( "\r\t</ul>" );
   }
   

   private void htmlFooter( final StringBuffer htmlBuffer ) {
   
      htmlBuffer.append( "\r\r\t<ul style=\"list-style-type: none\">" );
      htmlBuffer.append( "\r\t\t<li><a href=\"#top\"><h4>To top</h4></a></li>" );
      htmlBuffer.append( "\r\t</ul>\r\t</body>\r</html>" );
   }

   private boolean isDomainData( final String parameterName ) {
      
      // Check for 'Java classes' or 'Primitive Data Types'
      
      if( parameterName.startsWith( "java" ) ) {
         
         // Is a Java class
         return false;
      }
      else if( parameterName.equals( "byte" ) ) {
         
         // Is a Primitive Data Type
         return false;
      }
      else if( parameterName.equals( "short" ) ) {
         
         // Is a Primitive Data Type
         return false;
      }
      else if( parameterName.equals( "int" ) ) {
         
         // Is a Primitive Data Type
         return false;
      }
      else if( parameterName.equals( "long" ) ) {
         
         // Is a Primitive Data Type
         return false;
      }
      else if( parameterName.equals( "float" ) ) {
         
         // Is a Primitive Data Type
         return false;
      }
      else if( parameterName.equals( "double" ) ) {
         
         // Is a Primitive Data Type
         return false;
      }
      else if( parameterName.equals( "boolean" ) ) {
         
         // Is a Primitive Data Type
         return false;
      }
      else if( parameterName.equals( "char" ) ) {
         
         // Is a Primitive Data Type
         return false;
      }
      
      return true;
   }

}
