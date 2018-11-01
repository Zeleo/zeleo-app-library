package com.zeleo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsequencePhone {
	private String number;
	private Boolean verified;
	private Boolean enabled;
}
