package messagesservice.service;

import java.util.Set;

import messagesservice.model.Message;
import messagesservice.model.ShortUser;

public interface MessageService {
	public void persist(Message message);
	public Set<Message> findAll(); // wd
	public Message findById(int id);
	public Set<Message> findIncoming(ShortUser user);
	public Set<Message> findOutcoming(ShortUser user);
	public void delete(int messageId);
}
