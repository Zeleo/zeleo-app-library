package com.zeleo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventProperty {
	private String id;
	private String type;
	private String name;
	private String value;
	private String nonce;
}
