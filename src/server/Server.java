package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import client.ClientData;

public class Server {
	
	private static final Logger logger = Logger.getLogger(Server.class.getName());
    public static final int number = 4567;

	public static void main(String[] args) {
		
		long time = System.nanoTime();
        int numLogFiles = 5;
        int limit = 1024;
		
		try {
            FileHandler fh = new FileHandler("system.log", limit, numLogFiles, true);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.addHandler(fh);
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Exception: ", ex);
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (SecurityException ex) {
            logger.log(Level.SEVERE, "Exception: ", ex);
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

		logger.log(Level.INFO,"Client information");
		ClientData data = new ClientData();

		logger.log(Level.INFO,"Create a server socket");
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(number);
			
		} catch (IOException e) {
			logger.log(Level.WARNING,"Failed to listen on port " + number);
			System.err.println("Failed to listen on port " + number);
			System.exit(1);
		}

		try {
			while (true) {
				
				Socket socket = serverSocket.accept();
				BufferedReader clientReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintStream clientPrint = new PrintStream(socket.getOutputStream());
				String nick = clientReader.readLine();

				if (!data.existPlayer(nick)) {
					logger.log(Level.INFO, nick + "connected");
					System.out.println(nick + " connected");
					data.addToQueueTable(nick);
					data.addNewPlayer(nick);
					(new ServerReceiver(nick, clientReader, data)).start();
					clientPrint = new PrintStream(socket.getOutputStream());
					(new ServerSender(data.getQueue(nick), clientPrint, data)).start();
				} else
					clientPrint.println("Exist");
			}
			
		} catch (IOException e) {
			logger.log(Level.WARNING,"IO error " + e.getMessage());
			System.err.println("IO error " + e.getMessage());
		}
	}
	
}