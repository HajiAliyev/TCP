/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import util.FileUtility;

/**
 *
 * @author ASUS
 */
public class TCPClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("192.168.1.100",6789); 
        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        String fileName = "dog.jpg";
//        byte [] bytes  = FileUtility.readBytes(fileName);
        byte [] bytes  = "Test".getBytes();
        dataOutputStream.writeInt(bytes.length);
        dataOutputStream.write(bytes);
        socket.close();
        
    }
}
