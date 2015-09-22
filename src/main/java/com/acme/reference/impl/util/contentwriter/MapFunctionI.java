package com.acme.reference.impl.util.contentwriter;

@FunctionalInterface
public interface MapFunctionI<T> {

	public String format(T t);
}
