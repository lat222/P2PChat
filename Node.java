/* Thomas Back, Leia Trice, Vince Messenger
   P2P Chat App
   14 Feb. 2017
*/

import java.io.*;

class Node{
	public static String next_node = "";
	public static String user_name;
	
	// note: optionally accept IP address as command line arg
	//       1) if they pass it in, connect to that IP
	//       2) if they don't start new server

	public static void main(String[] args) throws IOException{

		// check args for IP address, connect to it if so
		if (args.length == 0){
			System.out.println("Usage: Node <username> <optional IP address>");
			return;
		}

		// save other command line args
		if (args.length > 0) {
			Node.user_name = args[0];
		}
		if (args.length > 1) {
			Node.next_node = args[1];
		}

		new ReadThread().start();
		new WriteThread().start();

	}
}
