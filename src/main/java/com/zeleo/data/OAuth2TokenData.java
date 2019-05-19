package com.zeleo.data;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OAuth2TokenData {
	private OAuth2TokenDataKey id;
	private Date expirationDate;
	private Date lastUpdate;
	private String refreshToken;
	private String accessToken;
	private String idToken;
	private String tokenType;
	private String scope;
	private String fieldId;
	private List<OAuth2AdditionalField> additionalFields;
	
}
