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
package com.zeleo.test;

import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;
import org.junit.Assert;
import org.junit.Test;

import com.zeleo.ZeleoApplication;
import com.zeleo.exceptions.ZeleoMissingVariablesException;

/**
 * Test for the Zeleo library. Basically the library handles the JWT security,
 * so we test by generating a JWT token then decrypting it to make sure the string is the
 * same as the one that went in. 
 * 
 * @author benjamin.flynn@zeleoinc.com
 * @version 1.0.1
 */
public class ZeleoApplicationTests {
	
	private static String TEST_TOKEN = "9lTFKErp2ZEf4CKxfq0Apg==";
	private static String TEST_ISSUER = "Issuer";
	private static String TEST_AUDIENCE = "Audience";
	private static String TEST_SUBJECT = "Subject";
	private static String TEST_JSON = "{\"sample\": \"json\"}";

	/**
	 * Make sure we raise an exception if environment variables are missing on extract.
	 * 
	 * @throws InvalidJwtException Fails the test if JWT isn't extracted.
	 */
	@Test
	public void testDecryptMissingVariables() throws InvalidJwtException {
		boolean exceptionThrown = false;
		try {
			ZeleoApplication.extractToken("");
		} catch (ZeleoMissingVariablesException missing) {
			exceptionThrown = true;
			Assert.assertTrue(missing.getMissingVariables().size() == 4);
		}
		Assert.assertTrue(exceptionThrown);
	}
	
	/**
	 * Make sure we raise an exception if environment variables are missing on encrypt.
	 * 
	 * @throws InvalidJwtException Fails the test if JWT isn't extracted.
	 * @throws JoseException If JSON serialization fails.
	 */
	@Test
	public void testEncryptMissingVariables() throws InvalidJwtException, JoseException {
		boolean exceptionThrown = false;
		try {
			ZeleoApplication.generateJWTToken("{}");
		} catch (ZeleoMissingVariablesException missing) {
			exceptionThrown = true;
			Assert.assertTrue(missing.getMissingVariables().size() == 4);
		}
		Assert.assertTrue(exceptionThrown);
	}
	
	/**
	 * Do a full round trip- encrypt ad decrypt using default JWT variables.
	 * 
	 * @throws JoseException Thrown if JSON serialization fails.
	 * @throws InvalidJwtException If JWT encrypt/decrypt fails.
	 */
	@Test
	public void roundTripEnv() throws JoseException, InvalidJwtException {
		String jwtToken = ZeleoApplication.generateJWTToken(TEST_JSON, TEST_TOKEN, TEST_ISSUER, TEST_AUDIENCE, TEST_SUBJECT);
		String json = ZeleoApplication.extractToken(jwtToken, TEST_TOKEN, TEST_ISSUER, TEST_AUDIENCE, TEST_SUBJECT);
		Assert.assertEquals(TEST_JSON, json);
		
	}

}
