package edu.uel.cw.simplechatserver.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import edu.uel.cw.simplechatserver.model.LocalUser;

public class LocalUserServiceManager implements LocalUserService {

	private static final String RECORD_EXIST = "Record already exists!";
	private static final String RECORD_NOT_FOUND = "Record not found!";
	private static final String RECORD_INVALID = "Record has constraints!";
	
	private static Integer lastId = 0;
	private Map<Integer, LocalUser> localUsers = new HashMap<Integer, LocalUser>();
	
	@Override
	public LocalUser create(LocalUser localUser) throws RuntimeException {
		if (localUsers.containsKey(localUser.getId())) throw new RuntimeException(RECORD_EXIST);
		if (!isValid(localUser)) throw new RuntimeException(RECORD_INVALID);
		localUser.setId(++lastId);
		return localUsers.put(localUser.getId(), localUser);
	}

	@Override
	public LocalUser update(LocalUser localUser) throws RuntimeException {
		if (!localUsers.containsKey(localUser.getId())) throw new RuntimeException(RECORD_NOT_FOUND);
		if (!isValid(localUser)) throw new RuntimeException(RECORD_INVALID);
		return localUsers.put(localUser.getId(), localUser);
	}

	@Override
	public LocalUser delete(int id) throws RuntimeException {
		if (!localUsers.containsKey(id)) throw new RuntimeException(RECORD_NOT_FOUND);
		return localUsers.remove(id);
	}

	@Override
	public LocalUser find(String nickName) throws RuntimeException {
		try {
			return localUsers.values()
							 .stream()
							 .filter(localUser -> localUser.getNickName().equals(nickName))
							 .collect(singletonCollector());
		} catch (NoSuchElementException | IllegalStateException ex) {
			throw new RuntimeException(RECORD_NOT_FOUND);
		}
	}

	private static <T> Collector<T, ?, T> singletonCollector() {
	    return Collectors.collectingAndThen(
	            Collectors.toList(),
	            list -> {
	                if (list.size() == 0) {
	                    throw new NoSuchElementException();
	                } else if (list.size() > 1) {
	                		throw new IllegalStateException();
	                }
	                return list.get(0);
	            }
	    );
	}
	@Override
	public List<LocalUser> findAll() {
		return localUsers.values()
						 .stream()
						 .collect(Collectors.toList());
	}

	private boolean isValid(LocalUser localUser) {
		return (!localUser.getFirstName().equals("")
				&& !localUser.getLastName().equals("")
				&& !localUser.getNickName().equals("")
				&& !localUser.getPassword().equals(""));
	}
}
