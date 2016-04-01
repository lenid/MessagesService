package messagesservice.service.impl;

import java.util.Iterator;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import messagesservice.dao.UserDao;
import messagesservice.model.ShortUser;
import messagesservice.model.User;
import messagesservice.service.ContactService;
import messagesservice.util.SecurityHelper;

@Service("contactService")
@Transactional
@PropertySource(value = { "classpath:application.properties" })
public class ContactServiceImpl implements ContactService {

	@Autowired
	private UserDao dao;
	
	@Autowired
	private Environment environment;
	
	@Override
	public void addBySsoId(String ssoId) {
		User newContact = dao.findBySso(ssoId);
		User currentUser = dao.findBySso(SecurityHelper.getSso());
		Set<ShortUser> contactList = currentUser.getContacts();
		contactList.add(newContact);
		dao.update(currentUser);
	}

	@Override
	public Set<ShortUser> getContactList() {
		User user = dao.findBySso(SecurityHelper.getSso());
		Hibernate.initialize(user.getContacts()); // wc maybe delete the line
		return user.getContacts();
	}

	@Override
	public Set<ShortUser> getOutOfContactList(String token) {
		int autocompliteListLength = Integer.parseInt(environment.getRequiredProperty("jsp.autocompliteListLength"));

		return dao.getOutOfContactList(dao.findBySso(SecurityHelper.getSso()), "%" + token + "%", autocompliteListLength);
	}

	@Override
	public void deletebyIds(Set<Integer> contactIdSet) {
		User currentUser = dao.findBySso(SecurityHelper.getSso());
		Iterator<ShortUser> it = currentUser.getContacts().iterator();

		while (it.hasNext()) {
			if (contactIdSet.contains(it.next().getId())) {
				it.remove();
			}
		}

		dao.update(currentUser);
	}
	
}
