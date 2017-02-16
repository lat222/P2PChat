import java.net.*;
import java.io.*;
import java.util.Arrays;


/**
 *
 * @author Vince
 */
public class DatagramEchoServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                
        // get port number from args
        int portNumber = Integer.parseInt(args[0]);
        
        // create input buffer and packet object
        byte[] buf = new byte[256];
        String received_string;
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        
        // try creating server socket
        try(DatagramSocket serv_socket = new DatagramSocket(portNumber);) {
            
            // log where listening
            System.out.println("Listening on port: " + portNumber);
            
            // start accepting clients
            while(true) {
                // receive packet
                serv_socket.receive(packet);
                
                // read data and display
                received_string = new String(packet.getData());
                System.out.println("Client: " + received_string);
                
                // send packet back
                //serv_socket.send(packet);
            }
        }
        catch (IOException e) {
            System.err.println("Couldn't listen on port.");
            System.exit(1);
        }
    }
}
    

