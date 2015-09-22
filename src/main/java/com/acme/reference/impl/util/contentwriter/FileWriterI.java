package com.acme.reference.impl.util.contentwriter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface FileWriterI<T> {

public void write(List<T> content, Path writeTo, CSVTemplate templateI) throws JsonParseException, JsonMappingException, IOException;
public void write(List<T> content, Path writeTo) throws JsonParseException, JsonMappingException, IOException;


}

