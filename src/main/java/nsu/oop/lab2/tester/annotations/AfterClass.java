package nsu.oop.lab2.tester.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
// CR: why do you need this class if you don't support such type of annotations? same for AfterClass
public @interface AfterClass {
}
