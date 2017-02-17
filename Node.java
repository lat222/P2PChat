/* Thomas Back, Leia Trice, Vince Messenger
   P2P Chat App
   14 Feb. 2017
*/

import java.io.*;

class Node{
	public static String next_node;
	
	// note: optionally accept IP address as command line arg
	//       1) if they pass it in, connect to that IP
	//       2) if they don't start new server

	public static void main(String[] args) throws IOException{
		// check args for IP address, connect to it if so

		new ReadThread().start();
	}
}
