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
package com.zeleo.utlities;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import lombok.Getter;
import lombok.Setter;


@SuppressFBWarnings({"EI_EXPOSE_REP","EI_EXPOSE_REP2"})
public class ZeleoMapper extends ObjectMapper {
	
	private static final long serialVersionUID = -8043538434388139100L;
	
	@Getter
	@Setter
	transient private Field[] fields;
	
	@Override
	public JavaType constructType(Type t) {
		return super.constructType(t);
	}
	
	@Override
	public void acceptJsonFormatVisitor(Class<?> type, JsonFormatVisitorWrapper visitor) throws JsonMappingException {
		fields = type.getDeclaredFields();
		super.acceptJsonFormatVisitor(type, visitor);
	}
}
