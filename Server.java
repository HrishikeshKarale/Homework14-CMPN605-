/**
* Server.java
*
* @version   $Id: Server.java,v_1.2 2014/12/07 17:20:00
*
* @author    hhk9433 (Hrishikesh Karale)
*
* Revisions:
*      Initial revision
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

/**
 * This Program acts as a server and returns the scores found on a particular 
 * given webpage to its client.
 */
public class Server 
{
	
	/**
	 * This is the main method of our server class and this method takes in 
	 * the name of the team as input and sends back the score found on the 
	 * webpage. 
	 * 
	 * @param args
	 */
	public static void main(String args[])
	{
		//stores actual line being scanned for a match
		String line= null;
		//stores the team name to be matched
		String input_line= null;
		//keeps track if a match has been found
		boolean found= false;
		//serverSocket is initialized to null
		ServerSocket server_socket_object= null;
		//socket is initialized to null
		Socket socket_object= null;
		//printwriter is initialized to null 
		PrintWriter print_writer_object =null;
		//url is initialized to null 
		URL url = null;
		//BufferedReader is initialized to null 
		BufferedReader bufferedreader_object_1 = null;
		//BufferedReader is initialized to null 
		BufferedReader bufferedreader_object_2 = null;
		//InputStreamReader is initialized to null 
		InputStreamReader input_stream_reader_object= null;

		//Initialize Server Socket with port no 15123
		try 
		{
			server_socket_object= new ServerSocket(15123);
		} 
		catch (IOException e1) 
		{
			System.err.println("An IOError has been thrown by ServerSocket");
		}
		
		//Initialize Socket
		try 
		{
			System.out.println("Server: Waiting for Client.");
			socket_object= server_socket_object.accept();
			System.out.println("Server: Recieved Request from Client..");
		} 
		catch (IOException e1) 
		{
			System.err.println("An IOError has been thrown by ServerSocket");
		}

		//Initializing inputStreamReader
		try 
		{
			input_stream_reader_object = new InputStreamReader(socket_object.getInputStream());
		} 
		catch (IOException e) 
		{
			System.err.println("Error Initializing InputStreamReader");
		}
		
		bufferedreader_object_1 = new BufferedReader(input_stream_reader_object);
		
		//Input from Client is loaded in input_line 
		try 
		{
			input_line= bufferedreader_object_1.readLine();
		} 
		catch (IOException e) 
		{
			System.err.println("Error reading line from webpage");
		}

		//initialize url and send the link as parameter
		try 
		{
			url = new URL("http://sagarin.com/sports/nflsend.htm");
		} 
		catch (MalformedURLException e) 
		{
			System.err.println("Check for errors in Address Entered");
		}
		
		//Loads urs in buffered reader using inputStreamReader Object
		try 
		{
			bufferedreader_object_2= new BufferedReader(new InputStreamReader(url.openStream()));
		} 
		catch (IOException e) 
		{
			System.err.println("Error loading Webpage");
		}
		
		/*
		 * source code of webpage is iterated to search for client input and the
		 * line containing the desired input is stored and loop is broken. 
		 */ 
		try 
		{
			while((line=bufferedreader_object_2.readLine())!= null)
			{
				
				if(line.contains(input_line))
				{
					line=line.replaceAll("\\<.*?>","");
					found= true;
					break;
				}
			}
		} 
		catch (IOException e) 
		{
			System.err.println("Error reading line from webpage");
		}
		
		
		try 
		{
			print_writer_object = new PrintWriter(socket_object.getOutputStream(), true);
		} 
		catch (IOException e) 
		{
			System.err.println("Error Initializing printWeiter Object");
		}
		print_writer_object.println("Server: Servicing your request please wait...");
		
		//checks if desired score is found on webpage
		if(!found)
		{
			print_writer_object.println("NoSuch String Found in File");
		}
		
		//Output the result onto the client console
		print_writer_object.println(line);
		
		try 
		{
			input_line= bufferedreader_object_1.readLine();
		} 
		catch (IOException e) 
		{
			System.err.println("Error reading Client request");
		}
		System.out.println(input_line);	
	}
}
