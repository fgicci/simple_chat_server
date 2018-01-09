package edu.uel.cw.simplechatserver.server;

import edu.uel.cw.simplechatserver.model.Message;

public class ChatServerProtocol {

	private static final int LOGIN = 2;
	private static final int LOGOUT = 3;
	private static final int PRIVATE_MESSAGE = 4;
	private static final int BROADCAST_MESSAGE = 5;
	
	public Message processInput(Message message) {
		if (message.getFunction() == LOGIN) {
			// verifiy user
			// return message
		} else if (message.getFunction() == LOGOUT) {
			// stop thread
			// return message
		} else if (message.getFunction() == PRIVATE_MESSAGE) {
			// send private message
			// return message
		} else if (message.getFunction() == BROADCAST_MESSAGE) {
			// send broadcast message
		} else {
			message.setStatus(Message.ERROR);
			message.setMessage("Instruction Not Found...");
		}
		return message;
	}
}
