package edu.uel.cw.simplechatserver.service;

import java.util.List;

import edu.uel.cw.simplechatserver.model.LocalUser;

public interface LocalUserService {
	public LocalUser create(LocalUser product) throws RuntimeException;
	public LocalUser update(LocalUser product) throws RuntimeException;
	public LocalUser delete(int id) throws RuntimeException;
	public LocalUser find(String nickName) throws RuntimeException;
	public List<LocalUser> findAll();
}
