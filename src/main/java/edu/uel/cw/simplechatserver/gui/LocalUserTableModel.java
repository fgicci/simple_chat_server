package edu.uel.cw.simplechatserver.gui;

import javax.swing.table.AbstractTableModel;

import edu.uel.cw.simplechatserver.model.LocalUser;
import edu.uel.cw.simplechatserver.service.LocalUserService;
import edu.uel.cw.simplechatserver.service.LocalUserServiceManager;

public class LocalUserTableModel extends AbstractTableModel {
	private String[] columnNames = {"Id", "First Name", "Last Name", "Nick"};
	private LocalUserService localUserService;
	
	public LocalUserTableModel() {
		localUserService = new LocalUserServiceManager();
	}
	
	public LocalUserTableModel(LocalUserService localUserService) {
		this.localUserService = localUserService;
	}
	
	@Override
	public int getRowCount() {
		return localUserService.findAll().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		LocalUser localUser = localUserService.findAll().get(row);
		switch (col) {
			case 0:
				return localUser.getId();
			case 1:
				return localUser.getFirstName();
			case 2:
				return localUser.getLastName();
			case 3:
				return localUser.getNickName();
			default:
				return null;
		}
	}
	
	public boolean isCellEditable(int row, int col) {
		return false;
	}
}
