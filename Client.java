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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
* This Program acts as a client and requests for the scores found on a particular 
* given webpage to its server.
*/
public class Client 
{
	/**
	 * This is the main method of our client class and this method takes in 
	 * the name of the team as input and requests the score found on the 
	 * webpage from the server. 
	 * 
	 * @param args
	 */
	public static void main(String args[])
	{
		//Socket is initialized to null
		Socket socket_object= null;
		//InputStreamReader is initialized to null
		InputStreamReader input_stream_reader_object= null;
		//BufferedReader is initialized to null
		BufferedReader buffered_reader_object= null;
		//PrintWriter is initialized to null
		PrintWriter print_writer_object= null;
		//Scanner is initialized
		Scanner scanner_object= new Scanner(System.in);
		//input is initialized to null
		String input= null;
		//file is initialized to null
		File file= null;
		//BufferedWriter is initialized to null
		BufferedWriter buffered_writer_object= null;
		
		System.out.println("Client: Enter Team Name: ");
		input= scanner_object.nextLine();
		
		// Initialize Socket for Client
		try 
		{
			socket_object = new Socket(InetAddress.getLocalHost(), 15123);
		} 
		catch (UnknownHostException e) 
		{
			System.err.println("An Unknown Host exception has been thrown");
		} 
		catch (IOException e) 
		{
			System.err.println("An IOExceotion has been thrown by Socket");
		}
		
		// request server for info on input
		try 
		{
			print_writer_object = new PrintWriter(socket_object.getOutputStream(), true);
		} 
		catch (IOException e) 
		{
			System.err.println("Error Initializing PrintWriter");
		}
		print_writer_object.println(input);
		print_writer_object.println("Client: Thank You");	
		
		
		try 
		{
			input_stream_reader_object= new InputStreamReader(socket_object.getInputStream());
		} 
		catch (IOException e) 
		{
			System.err.println("Error Initializing InputstreamObject");
		}
		buffered_reader_object= new BufferedReader(input_stream_reader_object);
		
		//wait for a reply
		try 
		{
			input = buffered_reader_object.readLine();
		} 
		catch (IOException e) 
		{
			System.err.println("Error reading input from server");
		}
		System.out.println(input);
		
		//Receive the result from the server	
		try 
		{
			input = buffered_reader_object.readLine();
		} 
		catch (IOException e) 
		{
			System.err.println("Error recieving data from server");
		}
		System.out.println(input);
		
		//create file named Info.txt
		file = new File("Info.txt");
		
		//Initialize bufferedWriter and send FileWriter as parameter
		try 
		{
			buffered_writer_object = new BufferedWriter(new FileWriter(file));
		} 
		catch (IOException e) 
		{
			System.err.println("Error Initializing BufferedWriter");
		}
		
		//write input in file
		try 
		{
			buffered_writer_object.write(input);
		} 
		catch (IOException e) 
		{
			System.err.println("Error Writting input in file");
		}
		
		//close scanner and bufferedWriter
		try 
		{
			buffered_writer_object.close();
		} 
		catch (IOException e) 
		{
			System.err.println("Error closing bufferedWriter");
		}
		scanner_object.close();
	}
}
