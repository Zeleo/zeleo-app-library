package com.zeleo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OAuth2ObjectAdditionalField {
	private String id;
	private String authObjectId;
	private String name;
	private String key;
}
