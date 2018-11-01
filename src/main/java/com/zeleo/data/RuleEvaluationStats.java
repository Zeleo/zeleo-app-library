package com.zeleo.data;

import lombok.Data;

@Data
public class RuleEvaluationStats {
	
	// Note: numRules may not equal to the number of rule definitions
	long numRules = -1L;
	long numBranches = -1L;
	long evaluationTime = -1L;
	long numActions = -1L;
	long numEvaluated = -1L;
	long numErrored = -1L;
	long numSkipped = -1L;
	
	// below are based "evaluated" branches.
	long averageQueryTime = -1L;
	long maxQueryTime = -1L;
	long minQueryTime = -1L;
}
