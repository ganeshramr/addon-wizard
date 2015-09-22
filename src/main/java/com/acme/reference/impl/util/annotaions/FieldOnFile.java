package com.acme.reference.impl.util.annotaions;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target( { METHOD})
public @interface FieldOnFile {
	public String name();
}
