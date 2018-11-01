package com.zeleo.data;

import java.util.List;

import lombok.Data;

@Data
public class ResultSummary {
	String ruleID;
	String ruleName;
	long ruleBranchNumber = -1L;
	double queryTime = 0.0d; // in milliseconds
	List<Action> actions;
	RuleEvaluationError error;
	boolean skipped = false;
	
	public boolean hasError() {
		return error != null;
	}
	
	public boolean isSkipped() { return skipped; }

}
