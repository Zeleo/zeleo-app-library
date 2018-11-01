package com.zeleo.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ConsequenceData {
	private List<ConsequenceField> teamConfig = new ArrayList<>();
	private List<ConsequenceField> personConfig = new ArrayList<>();
	private Map<String, Object> event;
	private Map<String, Object> zeleoEvent;
	private ResultMetadata results;
	private List<EventProperty> fields = new ArrayList<>();
	private ConsequenceUser user;
}
