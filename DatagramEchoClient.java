import java.net.*; 
import java.io.*;
import static java.lang.System.in;
import java.util.Arrays;
/**
 *
 * @author Thomas
 */
public class DatagramEchoClient{
	
	public static void main(String[] args) throws UnknownHostException, IOException{
		
		//take in command line arguments for host and port
		InetAddress host = InetAddress.getByName("127.0.0.1");
                //InetAddress host = InetAddress.getByName("192.168.0.107");
		int portNumber = 8080;
		
		DatagramSocket socket = new DatagramSocket();
		DatagramPacket packet = null;
		byte[] buf = new byte[256];
                String[] char_array;
		
		String inString;
                String received_string;
                BufferedReader stdin =  new BufferedReader(new InputStreamReader(System.in));
		
		while((inString = stdin.readLine()) != null){
                    
                    // split input string into char array
                    packet = new DatagramPacket(inString.getBytes(), inString.getBytes().length, host, portNumber);
                socket.send(packet);
                    //char_array = inString.split("");
			
                //     for(int i = 0; i < char_array.length; i++){
                //             packet = new DatagramPacket(char_array[i].getBytes(), char_array[i].getBytes().length, host, portNumber);
                //             socket.send(packet);
                //     }
                    
                    // we will expect the server to send char_array.length packets back 
                //     for(int i = 0; i < char_array.length; i++){
                //         socket.receive(packet);
                //         received_string = new String(packet.getData());
                //         System.out.println("Echo: " + received_string);
                //     }
		}
		
		
	}
}