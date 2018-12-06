import com.sun.imageio.spi.OutputStreamImageOutputStreamSpi;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static com.sun.deploy.net.protocol.ProtocolType.HTTP;
import static javax.security.auth.callback.ConfirmationCallback.OK;

public class Main {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1409);
            Socket socket = serverSocket.accept(); //the code waits here.."blocking"
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            System.out.println("connected to browser");
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            
            // homework: add code here to give a different answer back to the browser,
            // if the user enters localhost:1409/small.change in the browser.
            //otherwise, just return a standard greeting.
            //homework 2: send an image back to the browser

            while (bufferedReader.ready()){
                System.out.println(bufferedReader.readLine());
            }
            // 1.find out how to send data back to the browser
            // 2.send a proper Http response to the browser
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-type: text/html; charset=UTF-8");
            printWriter.println("\r\n");
            //printWriter.println("<h3> Hi there<h3/>");
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
