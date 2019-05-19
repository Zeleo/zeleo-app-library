package com.zeleo.data;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OAuth2AdditionalField implements Serializable {
	private static final long serialVersionUID = 3555306983758223453L;
	private String id;
	private String fieldKey;
	private String fieldValue;
	private OAuth2TokenDataKey tokenDataID;
}
