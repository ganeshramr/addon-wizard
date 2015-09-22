package com.acme.reference.impl.util.contentwriter;

public interface TemplateI <T>{

	public String hydrateContentWith(T content);
	
}
