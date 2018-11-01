package com.zeleo.data;

import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Data;

@Data
public class ConsequenceField {
	private String name;
    private String description;
	private Boolean required;
    private String fieldType;
	private String value;
	private Set<String> options = new LinkedHashSet<String>();
	private String fieldReference;
}
