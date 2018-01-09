package edu.uel.cw.simplechatserver.model;

import java.util.Arrays;

public class Message {

	public static int OK = 0;
	public static int ERROR = -1;
	public static int WARNING = -2;
	private int status;
	private int function;
	private String message;
	private String[] args;
	
	public Message() {
		super();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFunction() {
		return function;
	}

	public void setFunction(int function) {
		this.function = function;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	@Override
	public String toString() {
		return "Message [status=" + status + ", function=" + function + ", message=" + message + ", args="
				+ Arrays.toString(args) + "]";
	}
}
