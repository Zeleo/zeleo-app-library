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
package com.zeleo.exceptions;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * This is thrown if you are calling anything that needs specific environment variables. Call getMissingVariables() 
 * to get a List of any variables that are expected but missing.
 * 
 * @author benjamin.flynn@zeleoinc.com
 * @version 1.0.1
 */
public class ZeleoMissingVariablesException extends Exception {
	private static final long serialVersionUID = -4562308597854651721L;
	@Getter @Setter private List<String> missingVariables = new ArrayList<>();
}
