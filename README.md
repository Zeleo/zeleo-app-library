![Zeleo Logo](https://www.zeleo.io/assets/Zeleo_Blue.png "Zeleo Logo")

---
# Zeleo Application Library
This is the Java client library for creating Zeleo Applications. You can read the details on how to do this in the Development section [here](https://zeleo.github.io/zeleo/).

## What is Zeleo?
[Zeleo](https://www.zeleo.io) is an event-driven rule engine that allows you to automate system interactions and task-based workflows for a team of people. Think IFTTT or Zapier with task/workflow automation built in, and focused on a team of people.

Read more [here](http://zeleo.github.io/zeleo/development/overview/).

## Setup
To add this library to your project, do one of the following depending on your build system:

### Maven
Add the following XML to your *pom.xml* in the _dependencies_ section:
```xml
<dependency>
  <groupId>io.zeleo.application</groupId>
  <artifactId>zeleo-application-library</artifactId>
  <version>1.0.1</version>
</dependency>
```

### Gradle
In your *build.gradle*, add the following to your _dependencies_ section:
```groovy
dependencies {
  compile 'io.zeleo.application:zeleo-application-library:1.0.1'
}
```

## Use
This library aims to reduce bootstrap code that you would need to write yourself. In practice, this means that the JWT authentication logic is taken care of by methods in this library, and the serialization of the json sent to you from Zeleo to the _ConsequenceData_ object is handled for you.

Start by reading [this](http://zeleo.github.io/zeleo/development/tools/) to define your service in the Zeleo UI.

You will need to define the following, all of which are available in the _Security_ section of your service definition that you created in the _Development Tab_ in the [Zeleo](https://www.zeleo.io) client. The _Token_ is generated for you, and the rest you define; all the fields *must* match _exactly_ for the everything to work. Methods available from this library allow you to pass these values or set them as environment variables which will then be accessed and used automatically. 

The following code in the library gets the values from the environment, but if you prefer every method allows you to alternatively pass these as parameters.

```java
final private static String ZELEO_TOKEN = System.getenv("ZELEO_TOKEN");
final private static String ZELEO_ISSUER = System.getenv("ZELEO_ISSUER");
final private static String ZELEO_AUDIENCE = System.getenv("ZELEO_AUDIENCE");
final private static String ZELEO_SUBJECT = System.getenv("ZELEO_SUBJECT");
final private static String ZELEO_HOST = System.getenv("ZELEO_HOST");
```

Learn more about the JWT security system for Zeleo [here](http://zeleo.github.io/zeleo/development/security/).

Also read [this](http://zeleo.github.io/zeleo/development/authorization/) to learn how to handle the authentication of the other system your service will undoubtedly be interacting with.

## Methods
The entry point for all methods is the ZeleoApplication class. It exposes a handful of static methods that help you converse with the Zeleo platform. Note that each function an overload that allows you to specify the JWT variables or let the library get them from environment variables.

```java
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
public static ConsequenceData getZeleoConsequence(final String token, final String jwtToken, final String issuer, final String audience, final String subject) throws InvalidJwtException, IOException;

/**
 * Extract the JWT encrypted string from Zeleo into the ConsequenceData object. The following Environment Variables 
 * are expected to be set and an exception will be thrown if any are missing:
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
public static ConsequenceData getZeleoConsequence(final String token) throws InvalidJwtException, ZeleoMissingVariablesException, IOException;

/**
 * Extract the JWT token from Zeleo to a json String. This method assumes the following environment variables 
 * have been defined and will throw an exception if missing:
 * 
 * ZELEO_TOKEN
 * ZELEO_ISSUER
 * ZELEO_AUDIENCE
 * ZELEO_SUBJECT
 * 
 * @param token The token to extract.
 * @return The json string.
 * @throws InvalidJwtException Thrown if there's an error extracting the token.
 * @throws ZeleoMissingVariablesException Thrown if the required environment variable sare missing.
 */
public static String extractToken(final String token) throws InvalidJwtException, ZeleoMissingVariablesException;

/**
 * 
 * @param token The token to extract.
 * @param jwtToken The encryption key from the Development tab in the Zeleo client.
 * @param issuer The issuer you defined in the Zeleo Development tab.
 * @param audience The audience field you defined in the Zeleo Development tab.
 * @param subject The subject field you defined in the Zeleo Development tab.
 * @return The json string extracted from the JWT token.
 * @throws InvalidJwtException If the JWT token is invalid.
 */
public static String extractToken(final String token, final String jwtToken, final String issuer, final String audience, final String subject) throws InvalidJwtException;

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
public static String generateJWTToken(final String json, final String token, final String issuer, final String audience, final String subject) throws JoseException;

/**
 * Generate the JWT token for the json event. This assumes you have set the following environment 
 * variables and will throw an exception of any are missing:
 * 
 * ZELEO_TOKEN
 * ZELEO_ISSUER
 * ZELEO_AUDIENCE
 * ZELEO_SUBJECT
 * 
 * @param json The json you want to include in your JWT.
 * @return The JWT token.
 * @throws JoseException Whether the JWT creation throws an error.
 * @throws ZeleoMissingVariablesException Thrown if any required environment variables are not defined. 
 * Call getMissingVariables() to see what is missing.
 */
public static String generateJWTToken(final String json) throws JoseException, ZeleoMissingVariablesException;

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
 * @throws ZeleoMissingVariablesException Thrown if the necessary environment variable sare not set. 
 * You can get a list of what is missing by calling getMissingVariables() on the Exception object.
 * @throws IOException Thrown if there's an issue serializing the json payload.
 * @throws JoseException Thrown if there's an error generating the JWT token.
 */
public static Response fireEvent(final String eventID, Map<String, Object> payload) throws ZeleoMissingVariablesException, IOException, JoseException;

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
public static Response fireEvent(final String eventID, Map<String, Object> payload, final String host, final String token, final String issuer, final String audience, final String subject) throws IOException, JoseException;

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
public static Response fireEvent(final String eventID, Map<String, Object> payload, final String token, final String issuer, final String audience, final String subject) throws IOException, JoseException
```

## Examples

The following is a simple event fired to Zeleo. This assumes that you have defined it in the _Development Tab_ in the Zeleo client and have an id for that event that can be seen by clicking on the _preview_ icon next to the event in the _Development Tab_.

Read more [here](http://zeleo.github.io/zeleo/development/condition/).

Firing and Event (to be evaluated in the _Condition_ section of a _Rule_):

```java
Map<String, Object> eventData = new HashMap<>();
eventData.put("some_field", "true");
eventData.put("another_field", "something");
ZeleoApplication.fireEvent(EVENT_ID, eventData, HOST, TOKEN, ISSUER, AUDIENCE, SUBJECT);
```

Receiving a Condition. This means you will receive the ConsequenceData object from a webhook if a _Rule Condition_ is *true*.

Read more [here](http://zeleo.github.io/zeleo/development/consequence/).

```java
public void handleConsequence(final String jwtToken) {
  try {
    ConsequenceData consequence = ZeleoApplication.getZeleoConsequence(jwtToken, TOKEN, ISSUER, AUDIENCE, SUBJECT);
    // Do whatever you need to do with the ConsequenceData object.
  } catch (InvalidJwtException | IOException e) {
    context.getLogger().log(ex.getMessage());
  }
}
```
