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
 *
 * @author Peter Ivarsson Peter.Ivarsson@cybercom.com
 */
@Qualifier
@Retention(RUNTIME)
@Target({METHOD})
public @interface DocReturnType {
   
    @Nonbinding
    String key() default "";
}
