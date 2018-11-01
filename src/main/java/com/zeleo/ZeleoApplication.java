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
package com.zeleo;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;

import com.zeleo.data.ConsequenceData;
import com.zeleo.exceptions.ZeleoMissingVariablesException;
import com.zeleo.utlities.JSONUtils;
import com.zeleo.utlities.JWTUtil;

/**
 * Use this class to simplify the JWT-Based security employed by Zeleo. With this you can create a service
 * easily to send events and receive consequences and not worry about the authorization details.
 * 
 * Go to https://zeleo.github.io/zeleo/ for more information.
 * 
 * @author benjamin.flynn@zeleoinc.com
 * @version 1.0.1
 */
public class ZeleoApplication {
	final private static String ZELEO_TOKEN = System.getenv("ZELEO_TOKEN");
	final private static String ZELEO_ISSUER = System.getenv("ZELEO_ISSUER");
	final private static String ZELEO_AUDIENCE = System.getenv("ZELEO_AUDIENCE");
	final private static String ZELEO_SUBJECT = System.getenv("ZELEO_SUBJECT");
	final private static String ZELEO_HOST = System.getenv("ZELEO_HOST");
	final private static int CLOCK_SKEW = 60; // seconds
	
	/**
	 * Extract the JWT encrypted string from Zeleo into the ConsequenceData object.
	 * 
	 * @param token The JWT token you want to extract.
	 * @param jwtToken The encryption key that you can find in the Development tab in Zeleo.
	 * @param issuer The Issuer string that you can find in the Development tab in Zeleo.
	 * @param audience The audience string that you can find in the Development tab in Zeleo.
	 * @param subject The subject that you can find in the Development tab in Zeleo.
	 * @return The ConsequenceData object sent from the Zeleo rule.
	 * @throws InvalidJwtException The JWt token could not be extracted.
	 * @throws IOException The string extracted from the token could not be serialized to the ConsequenceData object.
	 */
	public static ConsequenceData getZeleoConsequence(final String token, final String jwtToken, final String issuer, final String audience, final String subject) throws InvalidJwtException, IOException {
		final String json = extractToken(token, jwtToken, issuer, audience, subject);
		ConsequenceData data = JSONUtils.fromJSON(json, ConsequenceData.class);
		return data;
	}
	
	/**
	 * Extract the JWT encrypted string from Zeleo into the ConsequenceData object. The following Environment Variables are expected to be set and an exception will be thrown if any are missing:
	 * 
	 * ZELEO_TOKEN
	 * ZELEO_ISSUER
	 * ZELEO_AUDIENCE
	 * ZELEO_SUBJECT
	 * 
	 * @param token The JWT token you want to extract.
	 * @return The ConsequenceData object that represents the return from Zeleo.
	 * @throws InvalidJwtException The JWT token was invalid.
	 * @throws ZeleoMissingVariablesException You need to set the variables needed to encrypt/decrypt JWT tokens to and from Zeleo.
	 * @throws IOException The JSON serialization failed.
	 */
	public static ConsequenceData getZeleoConsequence(final String token) throws InvalidJwtException, ZeleoMissingVariablesException, IOException {
		final String json = extractToken(token);
		ConsequenceData data = JSONUtils.fromJSON(json, ConsequenceData.class);
		return data;
	}
	
	/**
	 * Extract the JWT token from Zeleo to a json String. This method assumes the following environment variables have been defined and will throw an exception if missing:
	 * 
	 * ZELEO_TOKEN
	 * ZELEO_ISSUER
	 * ZELEO_AUDIENCE
	 * ZELEO_SUBJECT
	 * 
	 * @param Extract the JWT token.
	 * @return The json string.
	 * @throws InvalidJwtException Thrown if there's an error extracting the token.
	 * @throws ZeleoMissingVariablesException Thrown if the required environment variable sare missing.
	 */
    public static String extractToken(final String token) throws InvalidJwtException, ZeleoMissingVariablesException {
    	checkVariables(false);
    	String json = "";
    	final Key key = JWTUtil.generateAESKey(JWTUtil.base64Decode(ZeleoApplication.ZELEO_TOKEN));
    	JwtClaims claims = JWTUtil.validateTokenAndProcessClaims(key, ZeleoApplication.ZELEO_ISSUER, ZeleoApplication.ZELEO_AUDIENCE, ZeleoApplication.ZELEO_SUBJECT, ZeleoApplication.CLOCK_SKEW, token);
    	final Map<String, List<Object>> claimsMap = claims.flattenClaims();
    	json = claimsMap.get("json").get(0).toString();
    	return json;
    }
    
    /**
     * 
     * @param token The token to extract.
     * @param jwtToken The encryption key from the Development tab in the Zeleo client.
     * @param issuer The issuer you defined in the Zeleo Development tab.
     * @param audience The audience field you defined in the Zeleo Development tab.
     * @param subject The subject field you defined in the Zeleo Development tab.
     * @return The json string extracted from the JWT token.
     * @throws InvalidJwtException
     */
    public static String extractToken(final String token, final String jwtToken, final String issuer, final String audience, final String subject) throws InvalidJwtException {
    	String json = "";
    	final Key key = JWTUtil.generateAESKey(JWTUtil.base64Decode(token));
    	JwtClaims claims = JWTUtil.validateTokenAndProcessClaims(key, issuer, audience, subject, ZeleoApplication.CLOCK_SKEW, jwtToken);
    	final Map<String, List<Object>> claimsMap = claims.flattenClaims();
    	json = claimsMap.get("json").get(0).toString();
    	return json;
    }
    
    /**
     * Generate the JWT token for the json event.
     * 
     * @param json The json you want to send.
     * @param token The token you use to sign the JWT; available in the Development tab of the Zeleo client.
     * @param issuer The issuer for the JWT token; available in the Development tab of the Zeleo client.
     * @param audience The audence for the JWT token; available in the Development tab of the Zeleo client.
     * @param subject The subject for the JWT token; available in the Development tab of the Zeleo client.
     * @return The JWT token that you will send to Zeleo.
     * @throws JoseException Thrown if there's an error generating the JWT token.
     */
	public static String generateJWTToken(final String json, final String token, final String issuer, final String audience, final String subject) throws JoseException {
        final Key key = JWTUtil.generateAESKey(JWTUtil.base64Decode(token));
		final Map<String, List<String>> claimsMap = new HashMap<>();
		claimsMap.put("json", Arrays.asList(json));
        return JWTUtil.generateJWT_AES128(key, issuer, audience, subject, claimsMap, CLOCK_SKEW);
	}
	
	/**
	 * Generate the JWT token for the json event. This assumes you have set the following environment variables and will throw an exception of any are missing:
	 * 
	 * ZELEO_TOKEN
	 * ZELEO_ISSUER
	 * ZELEO_AUDIENCE
	 * ZELEO_SUBJECT
	 * 
	 * @param json The json you want to include in your JWT.
	 * @return The JWT token.
	 * @throws JoseException Whether the JWT creation throws an error.
	 * @throws ZeleoMissingVariablesException Thrown if any required environment variables are not defined. Call getMissingVariables() to see what is missing.
	 */
	public static String generateJWTToken(final String json) throws JoseException, ZeleoMissingVariablesException {
		checkVariables(false);
        final Key key = JWTUtil.generateAESKey(JWTUtil.base64Decode(ZELEO_TOKEN));
		final Map<String, List<String>> claimsMap = new HashMap<>();
		claimsMap.put("json", Arrays.asList(json));
        return JWTUtil.generateJWT_AES128(key, ZELEO_ISSUER, ZELEO_AUDIENCE, ZELEO_SUBJECT, claimsMap, CLOCK_SKEW);
	}
	
	/**
	 * Fire the event to Zeleo; all JWT variables are assumed to be defined in the environment variables.
	 * 
	 * ZELEO_TOKEN
	 * ZELEO_ISSUER
	 * ZELEO_AUDIENCE
	 * ZELEO_SUBJECT
	 * ZELEO_HOST
	 * 
	 * @param eventID The id of the event. You can find this in the Development tab of the Zeleo client.
	 * @param payload The Map that will be the json file sent as the event.
	 * @throws ZeleoMissingVariablesException Thrown if the necessary environment variable sare not set. You can get a list of what is missing by calling getMissingVariables() on the Exception object.
	 * @throws IOException Thrown if there's an issue serializing the json payload.
	 * @throws JoseException Thrown if there's an error generating the JWT token.
	 */
	public static Response fireEvent(final String eventID, Map<String, Object> payload) throws ZeleoMissingVariablesException, IOException, JoseException {
		checkVariables(true);
		String endpoint = ZeleoApplication.ZELEO_HOST + "/server-core/services/integrationmanager/fire/event/" + eventID;
		final ResteasyClient client = new ResteasyClientBuilder().build();
		final ResteasyWebTarget target = client.target(endpoint);
		final String json = JSONUtils.toJSON(payload);
		final String token = generateJWTToken(json, ZeleoApplication.ZELEO_TOKEN, ZeleoApplication.ZELEO_ISSUER, ZeleoApplication.ZELEO_AUDIENCE, ZeleoApplication.ZELEO_SUBJECT);
		final Response response = target.request().post(Entity.entity(token, MediaType.TEXT_PLAIN));
		return response;
    }
	
	/**
	 * Fire the event to Zeleo.
	 * 
	 * @param eventID The id for the event. You can get this by clicking the preview icon next to the event in the Zeleo Development tab.
	 * @param payload The flat map that will be sent as a json file to Zeleo as the event.
	 * @param host The root url to send the event to. This will most likely be https://www.zeleo.io.
	 * @param token The JWT token as defined by the Zeleo Application definition in the Zeleo development tab.
	 * @param issuer The issuer as defined by the Zeleo Application definition in the Zeleo development tab.
	 * @param audience The audience as defined by the Zeleo Application definition in the Zeleo development tab.
	 * @param subject The subject as defined by the Zeleo Application definition in the Zeleo development tab.
	 * @return A Response from the REST event.
	 * @throws IOException Thrown if there's an issue serializing the json payload.
	 * @throws JoseException Thrown if there's an error generating the JWT token.
	 */
	public static Response fireEvent(final String eventID, Map<String, Object> payload, final String host, final String token, final String issuer, final String audience, final String subject) throws IOException, JoseException {
		String endpoint = host + "/server-core/services/integrationmanager/fire/event/" + eventID;
		final ResteasyClient client = new ResteasyClientBuilder().build();
		final ResteasyWebTarget target = client.target(endpoint);
		final String json = JSONUtils.toJSON(payload);
		final String jwttoken = generateJWTToken(json, token, issuer, audience, subject);
		final Response response = target.request().post(Entity.entity(jwttoken, MediaType.TEXT_PLAIN));
		return response;
    }
	
	/**
	 * Fire the event to Zeleo, using https://www.zeleo.io as the default host.
	 * 
	 * @param eventID The id for the event. You can get this by clicking the preview icon next to the event in the Zeleo Development tab.
	 * @param payload The flat map that will be sent as a json file to Zeleo as the event.
	 * @param token The JWT token as defined by the Zeleo Application definition in the Zeleo development tab.
	 * @param issuer The issuer as defined by the Zeleo Application definition in the Zeleo development tab.
	 * @param audience The audience as defined by the Zeleo Application definition in the Zeleo development tab.
	 * @param subject The subject as defined by the Zeleo Application definition in the Zeleo development tab.
	 * @return A Response from the REST event.
	 * @throws IOException Thrown if there's an issue serializing the json payload.
	 * @throws JoseException Thrown if there's an error generating the JWT token.
	 */
	public static Response fireEvent(final String eventID, Map<String, Object> payload, final String token, final String issuer, final String audience, final String subject) throws IOException, JoseException {
		String endpoint = "http://www.zeleo.io/server-core/services/integrationmanager/fire/event/" + eventID;
		final ResteasyClient client = new ResteasyClientBuilder().build();
		final ResteasyWebTarget target = client.target(endpoint);
		final String json = JSONUtils.toJSON(payload);
		final String jwttoken = generateJWTToken(json, token, issuer, audience, subject);
		final Response response = target.request().post(Entity.entity(jwttoken, MediaType.TEXT_PLAIN));
		return response;
    }
	
	private static void checkVariables(final boolean host_required) throws ZeleoMissingVariablesException {
		List<String> missing = new ArrayList<>();
		if(ZELEO_TOKEN == null) {
			missing.add("ZELEO_TOKEN");
		}
		if(ZELEO_ISSUER == null) {
			missing.add("ZELEO_ISSUER");
		}
		if(ZELEO_AUDIENCE == null) {
			missing.add("ZELEO_AUDIENCE");
		}
		if(ZELEO_SUBJECT == null) {
			missing.add("ZELEO_SUBJECT");
		}
		if(ZELEO_HOST == null && host_required) {
			missing.add("ZELEO_HOST");
		}
		if(missing.size() > 0) {
			ZeleoMissingVariablesException exception = new ZeleoMissingVariablesException();
			exception.setMissingVariables(missing);
			throw exception;
		}
	}
}
