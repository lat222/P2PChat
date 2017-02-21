import java.net.*;
import java.io.*;

/**
* Vincent Messenger, Thomas Back, Leia Trice
* 
*/
public class ReadThread extends Thread{
   private Socket read_socket;  // client socket
   private int port_num = 8080;
   
   public ReadThread() throws IOException{
       super();  // call super constructor
   }
   
   @Override
   public void run() {

         // create stuffs
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
       
       // try to create io streams
       try(
           DatagramSocket read_socket = new DatagramSocket(port_num);
               )
       {
            // check if user passed in IP to connect to, connect to self if not
            if (Node.next_node == ""){
                Node.next_node = InetAddress.getLocalHost().getHostAddress().toString();
            }
            
            // start receiving packets
            while(true) {
                // receive packet
                read_socket.receive(packet);

                buffer = packet.getData();
                
                // todo switch statement
                System.out.println("Just received packet from " + packet.getSocketAddress());
                System.out.println("Text: " + new String(buffer));

                // this code sets the next_node value to the IP that sent packet
                Node.next_node = packet.getAddress().getHostName().toString();
                System.out.println("next_node: " + Node.next_node);
                
                // send packet back
                //read_socket.send(packet);
            }
       
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
}