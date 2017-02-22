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

       
        // try to create io streams
        try(
            DatagramSocket write_socket = new DatagramSocket(port_num);
                )
        {
                BufferedReader stdin =  new BufferedReader(new InputStreamReader(System.in));

                // start listening for keyboard input
                while((inString = stdin.readLine()) != null){

                    // check what type of message user is trying to send, build message with protocol
                    if (inString == "L"){
                        toSend = "Fake";
                        //
                    }
                    else {
                        toSend = "M " + InetAddress.getLocalHost().getHostAddress().toString() + " " + inString;
                    }
                    // send out packet
                    packet = new DatagramPacket(toSend.getBytes(), toSend.getBytes().length, InetAddress.getByName(Node.next_node), Node.send_port);
                    write_socket.send(packet);

                }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
   }
}