/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.ByteBuffer;
import lombok.SneakyThrows;
import util.FileUtility;

/**
 *
 * @author ASUS
 */
public class TCPServer {

    public static void main(String[] args) throws Exception {
        //bu kodlar java-nin ozunden gelir ve deqiq olaraq tcp-ye emel edir. 
        readAsByte();
    }

    @SneakyThrows
    public static void readAsByte() {
        ServerSocket ourFirstServerSocket = new ServerSocket(6789);

        while (true) {
            System.out.println("Yeni sokcket ucun ve ya basqa sozle desek, ynei musteri ucun gozleyirem...");
            Socket connectionSocket = ourFirstServerSocket.accept();
//            System.out.println("Ura yeni musteri geldi...");
            DataInputStream dataStream = new DataInputStream(connectionSocket.getInputStream());
            String result = readRequest(dataStream);
            System.out.println(result);
//            System.out.println("messageLength2=" + arr.length);
//            FileUtility.writeBytes(arr, "dog.jpg");

//            Qarsi terefden requesti aldim. Men buna response gondermek lazimdir. 
            OutputStream outputStream = connectionSocket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            String fileName = "dog.jpg";
            byte[] bytes = FileUtility.readBytes(fileName);
//            byte[] bytes = "Test message".getBytes();
//            dataOutputStream.writeInt(bytes.length);
//            dataOutputStream.write(bytes);
//            dataOutputStream.flush();
//            connectionSocket.close();
//            writeTextResponse(dataOutputStream, "Hey I am cucumber".getBytes());
            writeImageResponse(dataOutputStream, bytes);
            connectionSocket.close();

        }
    }
    //metni yazmaq ucun
    public static void writeImageResponse(OutputStream out, byte [] s) throws Throwable { 
        String response = "HTTP/1.1 200 OK\r\n"
                + "Server: YarServer/2009-09-09\r\n"
                + "Content-Type: image/jpg\r\n"
                + "Content-Length: " + s.length + "\r\n"
                + "Connection: close\r\n\r\n";
        String result = response + s;
        out.write(result.getBytes());
        out.flush();
    }
    //metni yazmaq ucun
    public static void writeTextResponse(OutputStream out, byte [] s) throws Throwable { 
        String response = "HTTP/1.1 200 OK\r\n"
                + "Server: YarServer/2009-09-09\r\n"
                + "Content-Type: text/html\r\n"
                + "Content-Length: " + s.length + "\r\n"
                + "Connection: close\r\n\r\n";
        String result = response + s;
        out.write(result.getBytes());
        out.flush();
    }

    @SneakyThrows
    public static String readRequest(InputStream sin) {
        InputStreamReader isr = new InputStreamReader(sin);
        BufferedReader reader = new BufferedReader(isr);
        String msg = "";
        while (true) {
            String s = reader.readLine();
            if (s == null || s.trim().length() == 0) {
                break;
            } else {
                msg = msg + s + "\r\n";
            }
            System.out.println("Server request: " + s);
            System.out.println("");
        }
        return msg;
    }

    @SneakyThrows
    public static byte[] readMessage(DataInputStream din) {
        int msgLen = din.readInt();
        System.out.println("messageLength1=" + msgLen);
        byte[] msg = new byte[msgLen];
        din.readFully(msg);
        return msg;
    }

    @SneakyThrows
    public static void readAsString() {
        ServerSocket ourFirstServerSocket = new ServerSocket(6789);//6789-portdur. PC-de run olan app-in portu olur. 
        //Data gonderen zaman da port ayrilir.
        //IP - menim hazirda islediyim PC-nin local hostudur. 127.0.0.1
        //Global ip ise - 89.219.166.45 - kenar adamlarin gorduyudur. 
        while (true) {
            System.out.println("Yeni socket ucun ve ya basqa sozle desek, yeni musterini gozleyirem.");
            //Socket-den extends edib ozumuze xas basqa xususiyyetler getire bilerik. 
            Socket connection = ourFirstServerSocket.accept();
            System.out.println("Bura yeni musteri geldi.");
            InputStream is = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);

            BufferedReader messageFromClient = new BufferedReader(reader);
            String clientSentence = messageFromClient.readLine();
            System.out.println("Musteri deyir ki: " + clientSentence);
        }
    }

}
