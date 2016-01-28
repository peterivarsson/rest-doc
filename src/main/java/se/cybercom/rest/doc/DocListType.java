/**
 * Rest documentation handler, Cybercom
 */
package se.cybercom.rest.doc;

import static java.lang.annotation.ElementType.METHOD;
 
import java.lang.annotation.Retention;
 
import static java.lang.annotation.RetentionPolicy.RUNTIME;
 
import java.lang.annotation.Target;
import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * Mark metods that returns a list so that content in list will shown
 * 
 * I.e. In the Data object for MovieList a link to Movie data object will be shown
 *
 * @DocListType(key = "se.sfbio.mobilebackend.domain.Movie")
 * public List<Movie> getMovies() {
 *
 * @author Peter Ivarsson Peter.Ivarsson@cybercom.com
 */
@Qualifier
@Retention(RUNTIME)
@Target({METHOD})
public @interface DocListType {
   
    @Nonbinding
    String key() default "";
}
