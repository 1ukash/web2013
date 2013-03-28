package ru.spbstu.telematics.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Description {
	String title();
	int version() default 1;
	String text();
	EnumExample ex();
}
