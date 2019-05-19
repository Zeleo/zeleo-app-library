package com.zeleo.data;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2TokenDataKey implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String secondId;
	private String groupid;
	private String userid;
	private String serviceid;
}
