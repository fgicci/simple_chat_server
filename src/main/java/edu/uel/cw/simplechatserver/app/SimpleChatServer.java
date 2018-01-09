package edu.uel.cw.simplechatserver.app;

import javax.swing.UIManager;

import edu.uel.cw.simplechatserver.gui.SimpleChatServerFrame;

public class SimpleChatServer {

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				} catch (Exception ex) {
				    ex.printStackTrace();
                }
                new SimpleChatServerFrame().setVisible(true);
            }
		});
	}
}
