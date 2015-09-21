package com.acme.reference.impl.util;

@FunctionalInterface
public interface MapFunction<T> {

	public String format(T t);
}
