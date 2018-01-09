package edu.uel.cw.simplechatserver.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.uel.cw.simplechatserver.model.LocalUser;
import edu.uel.cw.simplechatserver.server.ChatServerSocket;
import edu.uel.cw.simplechatserver.service.LocalUserService;
import edu.uel.cw.simplechatserver.service.LocalUserServiceManager;

public class SimpleChatServerFrame extends JFrame {

	private LocalUserService localUserService;
	private JLabel lblFirstName, lblLastName, lblNickName, lblPassword;
	private JTextField txtFirstName, txtLastName, txtNickName;
	private JPasswordField txtPassword;
	private JButton btnAction, btnSave, btnDelete, btnExit;
	private JTable tblLocalUsers;
	private LocalUserTableModel localUserTableModel;
	private JScrollPane tblLocalUsersPane;
	private ServerTask st;
	
	public SimpleChatServerFrame() {
		initialize();
		defineLayout();
		setTitle("Simple Chat Server - #");
		pack();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	private void initialize() {
		localUserService = new LocalUserServiceManager();
		
		lblFirstName = new JLabel("First Name: ");
		lblLastName = new JLabel("Last Name: ");
		lblNickName = new JLabel("Nick Name: ");
		lblPassword = new JLabel("Password: ");
		
		txtFirstName = new JTextField();
		txtLastName = new JTextField();
		txtNickName = new JTextField();
		txtPassword = new JPasswordField();
		
		btnAction = new JButton("Start");
		btnAction.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				try {
					if ("Stop" == ae.getActionCommand()) {
						st.finish();
						btnAction.setText("Start");
					} else {
						st = new ServerTask();
						st.execute();
						btnAction.setText("Stop");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					System.exit(ERROR);
				}
			}
		});
		
		btnSave = new JButton("Add User");
		btnSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				saveLocalUser();
			}
		});
		
		btnDelete = new JButton("Delete User");
		btnDelete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				deleteLocalUser();
			}
		});
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				exitFrame();
			}
		});
		
		localUserTableModel = new LocalUserTableModel(localUserService);
		tblLocalUsers = new JTable(localUserTableModel);
		tblLocalUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblLocalUsers.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent evt) {
				if (tblLocalUsers.getSelectedRow() != -1) {
					LocalUser localUser = localUserService.find(tblLocalUsers.getValueAt(tblLocalUsers.getSelectedRow(), 0).toString());
					txtFirstName.setText(localUser.getFirstName());
					txtLastName.setText(localUser.getLastName());
					txtNickName.setText(localUser.getNickName());
					txtPassword.setText(localUser.getPassword());
				}
			}
		});
		
		tblLocalUsersPane = new JScrollPane(tblLocalUsers);
	}
	
	private void defineLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)	
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(lblFirstName)
						.addComponent(lblLastName)
						.addComponent(lblNickName)
						.addComponent(lblPassword)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(txtFirstName)
						.addComponent(txtLastName)
						.addComponent(txtNickName)
						.addComponent(txtPassword)
					)
				)
				.addComponent(tblLocalUsersPane)
			)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(btnAction)
				.addComponent(btnSave)
				.addComponent(btnDelete)
				.addComponent(btnExit)
			)
		);

        layout.linkSize(SwingConstants.HORIZONTAL, btnAction, btnSave, btnDelete, btnExit);
        
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(lblFirstName)
				.addComponent(txtFirstName)
				.addComponent(btnAction)
			)
			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(lblLastName)
				.addComponent(txtLastName)
				.addComponent(btnSave)
			)
			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(lblNickName)
				.addComponent(txtNickName)
				.addComponent(btnDelete)
			)
			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(lblPassword)
				.addComponent(txtPassword)
				.addComponent(btnExit)
			)
			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(tblLocalUsersPane)
			)
		);

        createBufferStrategy(1);
	}
	
	private void saveLocalUser() {
		try {
			LocalUser localUser = new LocalUser(txtFirstName.getText(), txtLastName.getText(), txtNickName.getText(), txtPassword.getPassword().toString());
			try {
				localUserService.update(localUser);
			} catch (RuntimeException ex) {
				localUserService.create(localUser);
			}
			localUserTableModel.fireTableDataChanged();
		} catch (RuntimeException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void deleteLocalUser() {
		try {
			localUserService.delete(localUserService.find(txtNickName.getText()).getId());
			localUserTableModel.fireTableDataChanged();
		} catch (RuntimeException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void exitFrame() {
		System.exit(JFrame.EXIT_ON_CLOSE);
	}
	
	private class ServerTask extends SwingWorker<Void, Void> {
		private boolean shutdown = false;
		private Socket cs;
		private PrintWriter pw;
		private BufferedReader bf;
		private ChatServerSocket css;
		
		public ServerTask() throws Exception {
			this(new ChatServerSocket());
		}
		
		public ServerTask(ChatServerSocket css) {
			this.css = css;
		}
		
		@Override
        protected Void doInBackground() {
            while (!shutdown) {
	            	try {
	            		cs = css.accept();
	            		pw = new PrintWriter(cs.getOutputStream(), true);
	            		bf = new BufferedReader(new InputStreamReader(cs.getInputStream()));
	    				String line;
	    				while ((line = readMessage()) != null) {
	    					sendMessage(line);
	    				}
	    			} catch (Exception ex) {
	    				JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    					System.exit(ERROR);
	    				
	            }
            }
			return null;
		}

		@Override
		protected void done() {
			css = null;
		}
		
		public String readMessage() throws IOException {
			return bf.readLine();
		}
		
		public void sendMessage(String message) {
			pw.println(message);
		}
		
		public void finish() {
			this.shutdown = true;
		}
	}
}
