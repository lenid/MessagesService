package messagesservice.dao;

import java.util.Set;

import messagesservice.model.ShortUser;
import messagesservice.model.State;
import messagesservice.model.User;

public interface UserDao {
	public void persist(User user);
	public Set<User> getAll();
	public Set<User> getAll(State state);
	public Set<ShortUser> getOutOfContactList(ShortUser shortUser, String token, int limit);
	public User findById(int id);
	public User findBySso(String sso);
	public void update(User user);
	public void delete(User user);
	public void markDelete(int userId);
}

