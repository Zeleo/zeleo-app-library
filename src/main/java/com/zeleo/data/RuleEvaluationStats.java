/*  Copyright (c) 2018
 *  by Zeleo, Inc., Boston, MA
 *
 *  This software is furnished under a license and may be used only in
 *  accordance with the terms of such license. This software may not be
 *  provided or otherwise made available to any other party. No title to
 *  nor ownership of the software is hereby transferred.
 *
 *  This software is the intellectual property of Zeleo, Inc.,
 *  and is protected by the copyright laws of the United States of America.
 *  All rights reserved internationally.
 *
 */
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
