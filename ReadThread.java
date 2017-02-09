import java.net.*;
import java.io.*;

/**
*
* @author vjm36
*/
public class ReadThread extends Thread{
   private Socket read_socket;  // client socket
   private int port_num = 808;
   
   public ReadThread(Socket socket) {
       super();  // call super constructor
   }
   
   @Override
   public void run() {
       
       // try to create io streams
       try(
           DatagramSocket read_socket = new DatagramSocket(port_num);
               )
       {

            // start accepting clients
            while(true) {
                // receive packet
                serv_socket.receive(packet);
                
                // todo switch statement
                
                // send packet back
                serv_socket.send(packet);
            }
       
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
}