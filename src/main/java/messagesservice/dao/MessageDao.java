package messagesservice.dao;

import java.util.Set;

import messagesservice.model.Message;
import messagesservice.model.ShortUser;

public interface MessageDao {
	public void persist(Message message);
	public Set<Message> getAll(); // wc wd
	public Message findById(int id);
	public Set<Message> findIncoming(ShortUser user);
	public Set<Message> findOutcoming(ShortUser user);
	public void delete(Message message);
	public void markDelete(int messageId);
}
