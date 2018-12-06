import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Webserver {

    static String path = System.getProperty("user.dir") + "/";
    public static void main(String[] args) {
        runWebserver();
    }

    public static void runWebserver(){
        //1.How to "listen" to requests from browser?
        try { // p > 1000 , p < 6500
            ServerSocket serverSocket = new ServerSocket(80);
            System.out.println("server's started");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("socket accepted");
                //2. How to get data from the socket?
                // e.g print out the browser's request
                InputStream inputStream = socket.getInputStream();
                Scanner scanner = new Scanner(inputStream);
                String requestLine = scanner.nextLine();
                System.out.println(requestLine);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                int forwardPos = requestLine.indexOf("/");
                int end = requestLine.indexOf(" ", forwardPos);
                String fileName = requestLine.substring(forwardPos+1, end);
                File file = new File(fileName);
                if(!file.isFile()){
                    System.out.println("bad request " + fileName);
                    fileName = path + "error404.html";
                }
                int numBytes = (int)file.length(); // casts to int
                byte[] fileArray = new byte[numBytes];
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(fileArray, 0, numBytes);
                fileInputStream.close();
                dataOutputStream.writeBytes("HTTP/1.0 200 OK\r\n");
                if(fileName.endsWith(".jpg")){
                    dataOutputStream.writeBytes("Content-Type:image/jpeg\r\n");
                }
                dataOutputStream.writeBytes("Content-Length:"+numBytes+"\r\n");
                dataOutputStream.writeBytes("\r\n");// it is required for protocol
                dataOutputStream.write(fileArray,0,numBytes); // here file is sent
                dataOutputStream.writeBytes("\r\n");


                //3. Use DataOutputStream to send data back to the browser

                //4.Now follow the HTTP protocol, to give a valid response
                /*dataOutputStream.writeBytes("HTTP/1.1 200 OK");
                dataOutputStream.writeBytes("Content-type: text/HTML;\r\n");
                dataOutputStream.writeBytes("\r\n");
                dataOutputStream.writeBytes("<html><h3 style=\"color:red;\">Hey hi hello !</h3></html>");
                dataOutputStream.flush();
                */

                dataOutputStream.close();
                socket.close();
            }
            //serverSocket.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
