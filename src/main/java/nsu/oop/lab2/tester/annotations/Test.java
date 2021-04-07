package nsu.oop.lab2.tester.annotations;

import java.io.Serial;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Test {
    Class<? extends Throwable> expected() default Test.None.class;

    // CR: you don't support timeout anyways, why add it
    long timeout() default 0L;

    public static class None extends Throwable {
        // CR: why do you need serialization?
        @Serial
        private static final long serialVersionUID = 1L;

        private None() {
        }
    }
}
