import java.net.*;
import java.io.*;
import static java.lang.System.in;

/**
* Vincent Messenger, Thomas Back, Leia Trice
* 
*/
public class WriteThread extends Thread{
   private Socket write_socket;  // client socket
   private int port_num = Node.write_port;
   
   public WriteThread() throws IOException{
       super();  // call super constructor
   }
   
   @Override
   public void run() {

        // create stuffs
        DatagramPacket packet = null;
        String inString, toSend;
        Boolean running = true;

       
        // try to create io streams
        try(
            DatagramSocket write_socket = new DatagramSocket(port_num);
                )
        {
                BufferedReader stdin =  new BufferedReader(new InputStreamReader(System.in));

                // start listening for keyboard input
                while(running){
                    // read from keyboard
                    inString = stdin.readLine();

                    // check what type of message user is trying to send, build message with protocol
                    // there are only 2 message types the user would write: message to leave and just
                    // just a regular message
                    if (inString.equals("L")){

                        // message structure "L ip_address next_ip_address message_with_username"
                        toSend = "L " + InetAddress.getLocalHost().getHostAddress().toString() +
                            " " + Node.next_node + " " + Node.user_name + " is leaving.";

                        // send out packet
                        packet = new DatagramPacket(toSend.getBytes(), toSend.getBytes().length, InetAddress.getByName(Node.next_node), Node.send_port);
                        write_socket.send(packet);
                        write_socket.close();
                        running = false;
                    }
                    else{
                        // message structure "M ip_address message_with_username"
                        toSend = "M " + InetAddress.getLocalHost().getHostAddress().toString() + " " + Node.user_name + " says: " + inString;

                        // send out packet
                        packet = new DatagramPacket(toSend.getBytes(), toSend.getBytes().length, InetAddress.getByName(Node.next_node), Node.send_port);
                        write_socket.send(packet);
                    }

                    // new line for formatting
                    System.out.print("\n");
                }

        
        } catch (IOException e) {
            e.printStackTrace();
        }
   }
}