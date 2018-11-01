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
