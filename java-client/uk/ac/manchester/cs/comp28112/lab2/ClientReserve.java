package uk.ac.manchester.cs.comp28112.lab2;

import java.io.IOException;

import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import org.apache.commons.httpclient.*;

import java.util.concurrent.TimeUnit;
///
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

/// example

// Reserve Band 50
// Check Band

/*
 * Simple client that reserves slot 80 for user "username" and password "password". The message is
 * uniquely identified by the triple "my_request_id_1, username, password".
 */
public class ClientReserve
{
	static String username = "8so114";
	static String password = "7exXep";

	static String xmlString;
	static String msg_uri;

	static String optionChosen;  // Read user input
	static String bookingType;
	static String slotNumber;  // Read user input

	static PutMethod putMethod;
	static String theResponse = "Service unavailable";
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

		System.out.println("Nothing Happens");

		msg_uri = msg_uri + "?username=8so114&password=7exXep";
		/* usage instructions

    System.out.println("Enter 1 To Check For Free Slots");
		System.out.println("Enter 2 To Reserve A Slot");
    System.out.println("Enter 3 To Cancel A Slot");
    System.out.println("Enter 4 To Retrieve Bookings");
		*/
		optionChosen = args[0];  // Read user input
		bookingType = args[1];
		slotNumber = args[2];  // Read user input

		System.out.println(optionChosen);
		System.out.println("Nothing Happens");



///////////////////////// check for free slots ////////////////////////////////
		if (optionChosen.trim().equals("Check"))
		{
			if (bookingType.trim().equals("Band"))
			{
				putMethod = new PutMethod(
						"http://jewel.cs.man.ac.uk:3020/queue/enqueue");
			}


			else if (bookingType.trim().equals("Hotel"))
			{
				putMethod = new PutMethod(
				"http://jewel.cs.man.ac.uk:3010/queue/enqueue");
			}

			else {
				putMethod = null;
			}


			try {
				int responseCode = -729;
				while (theResponse.trim().equals("Service unavailable") || theResponse.trim().equals("Message unavailable"))
				{
					//TimeUnit.SECONDS.sleep(1);
					Thread.sleep(1000);
					//String theResponse;
					xmlString = XMLRequest.availability("k "+ System.currentTimeMillis(), username, password);
					RequestEntity entity = new StringRequestEntity(xmlString);
					putMethod.setRequestEntity(entity);
					putMethod.addRequestHeader("Content-Type", "application/xml");
					putMethod.addRequestHeader("Accept", "application/xml");
					HttpClient client = new HttpClient();
					responseCode = client.executeMethod(putMethod);
					if (responseCode == HttpStatus.SC_OK) {
						theResponse = putMethod.getResponseBodyAsString();
						System.out.println(theResponse);

						getMethod = new GetMethod(Response.getMsgURI(theResponse));

						getMethod.addRequestHeader("Content-Type", "application/xml");
						getMethod.addRequestHeader("Accept", "application/xml");

						HttpClient client = new HttpClient();
						int responseCode = client.executeMethod(getMethod);

					} else {
						System.out.println("Error code:" + responseCode);
						System.out.println("Error message:" + putMethod.getResponseBodyAsString());
					}

					/// get method bit
					// get the actual content of the response
					getMethod = new GetMethod(msg_uri);

					getMethod.addRequestHeader("Content-Type", "application/xml");
					getMethod.addRequestHeader("Accept", "application/xml");

					HttpClient client = new HttpClient();
					int responseCode2 = client.executeMethod(getMethod);

					if (responseCode2 == HttpStatus.SC_OK){
						theResponse = Response.parseGetResponse(getMethod.getResponseBodyAsString());
						System.out.println("Response type: " + response.getResponseType());
						System.out.println(response.toString());
					} else if (responseCode2 == HttpStatus.SC_NOT_FOUND){
						System.out.println("Message not found:" + responseCode);
						System.out.println("Error message:" + getMethod.getResponseBodyAsString());
					} else if (responseCode2 == HttpStatus.SC_UNAUTHORIZED) {
						System.out.println("Invalid username/password to retrieve message:" + responseCode);
						System.out.println("Error message:" + getMethod.getResponseBodyAsString());
					} else {
						System.out.println("Error code:" + responseCode2);
						System.out.println("Error message:" + getMethod.getResponseBodyAsString());
					}
				}
				// print when successful
			}

			catch (RequestException e) {
				e.printStackTrace();
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			catch(InterruptedException ex)
			{
    		Thread.currentThread().interrupt();
			}
			catch (Exception err) {
				System.out.println("Usage: 'java ClientRecMsg msg_uri'");

				err.printStackTrace();
			}
		}

///////////////////////// resserve a slot  ////////////////////////////////

		if (optionChosen.trim().equals("Reserve"))
		{
			if (bookingType.trim().equals("Band"))
			{
				putMethod = new PutMethod(
						"http://jewel.cs.man.ac.uk:3020/queue/enqueue");
			}


			else if (bookingType.trim().equals("Hotel"))
			{
				putMethod = new PutMethod(
				"http://jewel.cs.man.ac.uk:3010/queue/enqueue");
			}

			else {
				putMethod = null;
			}

			String xmlString;
			try {
				/*
				 * Prepare the body of a put method. Create the string, in XML syntax, a request to
				 * reserve (book) a slot.
				 */
				xmlString = XMLRequest.Reservation("k " + System.currentTimeMillis(), username, password, Integer.parseInt(slotNumber));

				/*
				 * Set the request's entity (body).
				 */
				RequestEntity entity = new StringRequestEntity(xmlString);
				putMethod.setRequestEntity(entity);

				/*
				 * Set the put method's headers
				 */
				putMethod.addRequestHeader("Content-Type", "application/xml");
				putMethod.addRequestHeader("Accept", "application/xml");

				/*
				 * Create a client and the execute the put method.
				 */
				HttpClient client = new HttpClient();
				int responseCode = client.executeMethod(putMethod);

				/*
				 * Examine and print the response.
				 */
				if (responseCode == HttpStatus.SC_OK) {
					System.out.println("Message uri: " + Response.getMsgURI(putMethod.getResponseBodyAsString()));
					System.out.println("Successfully reserved");

				} else {
					System.out.println("Error code:" + responseCode);
					System.out.println("Error message:" + putMethod.getResponseBodyAsString());
				}

				// print when successful

			}

			catch (RequestException e) {
				e.printStackTrace();
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
///////////////////////// cancel a slot  ////////////////////////////////

		if (optionChosen.trim().equals("Cancel"))
		{
			if (bookingType.trim().equals("Band"))
			{
				putMethod = new PutMethod(
						"http://jewel.cs.man.ac.uk:3020/queue/enqueue");
			}


			else if (bookingType.trim().equals("Hotel"))
			{
				putMethod = new PutMethod(
				"http://jewel.cs.man.ac.uk:3010/queue/enqueue");
			}

			else {
				putMethod = null;
			}

			try {
				xmlString = XMLRequest.cancel("k "+ System.currentTimeMillis(), username, password, Integer.parseInt(slotNumber));
				RequestEntity entity = new StringRequestEntity(xmlString);
				putMethod.setRequestEntity(entity);
				putMethod.addRequestHeader("Content-Type", "application/xml");
				putMethod.addRequestHeader("Accept", "application/xml");
				HttpClient client = new HttpClient();
				int responseCode = client.executeMethod(putMethod);
				if (responseCode == HttpStatus.SC_OK) {
					System.out.println("Message uri: " + Response.getMsgURI(putMethod.getResponseBodyAsString()));
					System.out.println("Successfully cancelled booking");
				} else {
					System.out.println("Error code:" + responseCode);
					System.out.println("Error message:" + putMethod.getResponseBodyAsString());
				}
				// print when successful

			}

			catch (RequestException e) {
				e.printStackTrace();
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

///////////////////////// check for retrieve booking ////////////////////////////////

		if (optionChosen.trim().equals("Retrieve"))
		{
			if (bookingType.trim().equals("Band"))
			{
				putMethod = new PutMethod(
						"http://jewel.cs.man.ac.uk:3020/queue/enqueue");
			}


			else if (bookingType.trim().equals("Hotel"))
			{
				putMethod = new PutMethod(
				"http://jewel.cs.man.ac.uk:3010/queue/enqueue");
			}

			else {
				putMethod = null;
			}
			try {
				xmlString = XMLRequest.bookings("k " + System.currentTimeMillis(), username, password);
				RequestEntity entity = new StringRequestEntity(xmlString);
				putMethod.setRequestEntity(entity);
				putMethod.addRequestHeader("Content-Type", "application/xml");
				putMethod.addRequestHeader("Accept", "application/xml");
				HttpClient client = new HttpClient();
				int responseCode = client.executeMethod(putMethod);
				if (responseCode == HttpStatus.SC_OK) {
					System.out.println("Message uri: " + Response.getMsgURI(putMethod.getResponseBodyAsString()));
				} else {
					System.out.println("Error code:" + responseCode);
					System.out.println("Error message:" + putMethod.getResponseBodyAsString());
				}
				// print when successful
			}

			catch (RequestException e) {
				e.printStackTrace();
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
}
