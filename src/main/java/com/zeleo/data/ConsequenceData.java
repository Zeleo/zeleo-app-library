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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * This object that represents a Zeleo consequence. If you define a webhook as a Consequence in Zeleo, this is what
 * will be sent to your endpoint when that Consequence is triggered.
 * 
 * @author benjamin.flynn@zeleoinc.com
 * @version 1.0.1
 */
@Data
public class ConsequenceData {
	
	/**
	 * The configuration settings at the team level. These are set by the Team Administrator.
	 */
	private List<ConsequenceField> teamConfig = new ArrayList<>();
	
	/**
	 * The configuration options for the person this Consequence is targeting. 
	 */
	private List<ConsequenceField> personConfig = new ArrayList<>();
	
	/**
	 * The original event that fired this Consequence.
	 */
	private Map<String, Object> event;
	
	/**
	 * Internal event data, for instance if a task is assigned in the consequence or is the cause of the event, it will be defined here.
	 */
	private Map<String, Object> zeleoEvent;
	
	/**
	 * Metrics and from the rule engine when it executed the rule that caused this Consequence.
	 */
	private ResultMetadata results;
	
	/**
	 * Any fields added to the Consequence definition.
	 */
	private List<EventProperty> fields = new ArrayList<>();
	
	/**
	 * The User object who is the target of this Consequence.
	 */
	private ConsequenceUser user;
	
	/**
	 * If there was an authentication done by a team administrator for everyone, the token data will be here.
	 */
	private OAuth2TokenData teamAuthenticationToken;
	
	/**
	 * Any authentication tokens for the target of this consequence will be here.
	 */
	private OAuth2TokenData userAuthenticationToken;
	
	/**
     * Gets the field by the name of the field. 
     * 
     * @param name The name to search for.
     * @return The EventProperty that corresponds to this name, and null of nothing is found.
     */
    public EventProperty getFieldByName(String name) {
    	for(EventProperty field : getFields()) {
    		if(field.getName().toLowerCase().equals(name)) {
    			return field;
    		}
    	}
    	return null;
    }
}
