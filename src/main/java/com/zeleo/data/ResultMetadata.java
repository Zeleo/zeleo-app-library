package com.zeleo.data;

import lombok.Data;

@Data
public class ResultMetadata {
	String cycleID;
	String timeStamp;
	long database = -1L;
	RuleEvaluationStats stats;
}
