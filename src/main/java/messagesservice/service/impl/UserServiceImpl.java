package messagesservice.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import messagesservice.dao.UserDao;
import messagesservice.model.State;
import messagesservice.model.User;
import messagesservice.service.ContactService;
import messagesservice.service.UserService;
import messagesservice.util.SecurityHelper;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ContactService contactService;
	
	@Override
	public void persist(User user) {
		user.setPasswd(passwordEncoder.encode(user.getNewPasswd()));
		dao.persist(user);
	}
	

	@Override
	public Set<User> getAllActived() {
		Set<User> users = dao.getAll(State.ACTIVE);
		User currentUser = dao.findBySso(SecurityHelper.getSso());
		users.remove(currentUser);
		return users;
	}

	@Override
	public User findById(int id) {
		return dao.findById(id);
	}

	@Override
	public User findBySso(String sso) {
		return dao.findBySso(sso);
	}

	@Override
	public void update(User user) {
		if (!user.getNewPasswd().equals("")) {
			user.setPasswd(passwordEncoder.encode(user.getNewPasswd()));
		}

		user.setContacts(contactService.getContactList());
		dao.update(user);
	}

	@Override
	public void delete(int userId) {
		dao.markDelete(userId);
	}

	@Override
	public boolean isDuplicatedSsoId(User user) {
		User userBySso = dao.findBySso(user.getSsoId());

		if (userBySso == null) {
			return false;
		} else if (userBySso.getId() == user.getId()) {
			return false;
		}

		return true;
	}

}
