package edu.uel.cw.simplechatserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServerSocket {

	private ServerSocket ss;
	
	private static final int PORT = 7070;
	
	public ChatServerSocket() throws Exception {
		this(PORT);
	}
	
	public ChatServerSocket(int portNumber) throws Exception {
		ss = new ServerSocket(portNumber);
	}

	public Socket accept() throws IOException {
		return ss.accept();
	}
}