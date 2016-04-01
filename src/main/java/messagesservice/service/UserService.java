package messagesservice.service;

import java.util.Set;

import messagesservice.model.User;

public interface UserService {
	public void persist(User user);
	public Set<User> getAllActived();
	public User findById(int id);
	public User findBySso(String sso);
	public void update(User user);
	public void delete(int userId);
	public boolean isDuplicatedSsoId(User user);
}