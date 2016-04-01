package messagesservice.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import messagesservice.dao.MessageDao;
import messagesservice.model.Message;
import messagesservice.model.ShortUser;
import messagesservice.service.MessageService;

@Service("messageService")
@Transactional
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageDao dao;

	@Override
	public void persist(Message message) {
		dao.persist(message);
	}

	@Override
	public Set<Message> findAll() {
		return dao.getAll();
	}

	@Override
	public Message findById(int id) {
		Message message = dao.findById(id);
		message.setFrom(new ShortUser(message.getFrom()));
		message.setTo(new ShortUser(message.getTo()));
		
		return message;
	}
	
	@Override
	public Set<Message> findIncoming(ShortUser user) {
		return dao.findIncoming(user);
	}
	
	@Override
	public Set<Message> findOutcoming(ShortUser user) {
		return dao.findOutcoming(user);
	}

	@Override
	public void delete(int messageId) {
		dao.markDelete(messageId);
	}
	
//	private User getShortUser(User user) {
//		User unproxyUser = AbstractDao.initializeAndUnproxy(user);
////		unproxyUser.setMessagesIn(null);
////		unproxyUser.setMessagesOut(null);
//		unproxyUser.setRecipients(null);
//		unproxyUser.setSenders(null);
//		unproxyUser.setUserProfiles(null);
//		unproxyUser.setPasswd("");
//		
//		return unproxyUser;
//	}

}
