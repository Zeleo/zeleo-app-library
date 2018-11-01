package com.zeleo.data;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsequenceUser {
	private String email;
	private String firstName;
	private String lastName;
	private List<ConsequencePhone> phoneNumbers = new ArrayList<>();
}
