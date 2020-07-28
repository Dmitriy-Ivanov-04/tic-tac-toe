package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import game.Game;
import implementation.GameImplementation;
import implementation.GameImplementationModel;

import java.util.logging.FileHandler;

class Client {
	
    private static final Logger logger = Logger.getLogger(Client.class.getName());
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
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (SecurityException ex) {
            logger.log(Level.SEVERE, "Exception: ", ex);
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

		logger.log(Level.INFO,"Checking the correctness of the data entered by the client");
		
		if (args.length != 2) {
			logger.log(Level.WARNING,"Mistake. Check if the data is correct. Try like this: java Client nickname hostname");
			System.err.println("Mistake. Check if the data is correct. Try like this: java Client nickname hostname");
			System.exit(1);
		}

		String nickname = args[0];
		String hostname = args[1];

		PrintStream toServerPrint = null;
		BufferedReader fromServerReader = null;
		Socket server = null;

		try {
			logger.log(Level.INFO,"Create a socket");
			server = new Socket(hostname, number);
			toServerPrint = new PrintStream(server.getOutputStream());
			fromServerReader = new BufferedReader(new InputStreamReader(server.getInputStream()));
		
		} catch (UnknownHostException e) {	
			logger.log(Level.WARNING,"No such host exists: " + hostname);
			System.err.println("No such host exists: " + hostname);
			System.exit(1);
		
		} catch (IOException e) {	
			logger.log(Level.WARNING,"Server could not start " + e.getMessage());
			System.err.println("Server could not start " + e.getMessage());
			System.exit(1);
		}

		GameImplementation board = new GameImplementation();
		GameImplementationModel model = new GameImplementationModel(board, toServerPrint);

		Game game = new Game(board,  model);

		ClientReceiver receiver = new ClientReceiver(fromServerReader, game, toServerPrint, nickname);

		logger.log(Level.INFO,"We receive information from the server");
		receiver.start();

		toServerPrint.println(nickname);
		toServerPrint.println("information");
		toServerPrint.println("information");

		try {
			logger.log(Level.INFO,"You are connected to a server");
			receiver.join();
			toServerPrint.println("me");
			toServerPrint.println("remove");
			toServerPrint.println("Quit");

			logger.log(Level.INFO,"You are disconnected from the server");
			toServerPrint.close();
			fromServerReader.close();
			server.close();
			
		} catch (IOException e) {
			logger.log(Level.WARNING,"Something went wrong " + e.getMessage());
			System.err.println("Something went wrong " + e.getMessage());
			System.exit(1);
			
		} catch (InterruptedException e) {
			logger.log(Level.WARNING,"The program was interrupted" + e.getMessage());
			System.err.println("The program was interrupted " + e.getMessage());
			System.exit(1);
		}
	}
	
}
