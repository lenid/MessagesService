package messagesservice.dao.impl;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import messagesservice.dao.AbstractDao;
import messagesservice.dao.MessageDao;
import messagesservice.model.Message;
import messagesservice.model.ShortUser;
import messagesservice.model.State;

@Repository("messageDao")
public class MessageDaoImpl extends AbstractDao<Integer, Message> implements MessageDao {

	@Override
	public Message findById(int id) {
		return getByKey(id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<Message> findIncoming(ShortUser user) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("from", user));
		criteria.add(Restrictions.eq("state", State.ACTIVE.toString()));
		return new HashSet<Message>(criteria.list());
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<Message> findOutcoming(ShortUser user) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("to", user));
		criteria.add(Restrictions.eq("state", State.ACTIVE.toString()));
		return new HashSet<Message>(criteria.list());
	}

	@Override
	public void markDelete(int messageId) {
		Message message = findById(messageId);
		message.setState(State.DELETED.toString());
		update(message);
	}

}
