package messagesservice.dao.impl;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import messagesservice.dao.AbstractDao;
import messagesservice.dao.UserDao;
import messagesservice.model.ShortUser;
import messagesservice.model.State;
import messagesservice.model.User;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	@Override
	public User findById(int id) {
		return getByKey(id);
	}

	@Override
	public User findBySso(String sso) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("ssoId", sso));
		return (User) crit.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<ShortUser> getOutOfContactList(ShortUser shortUser, String token, int limit) {
		Query query = getSession().getNamedQuery(ShortUser.QUERY_FIND_OUT_OF_CONTACT_LIST).setParameter("id", shortUser.getId())
				.setParameter("token", token).setMaxResults(limit);

		return new HashSet<ShortUser>(query.list());
	}
	@Override
	public void markDelete(int userId) {
		User user = findById(userId);
		user.setState(State.DELETED.toString());
		update(user);
	}

}
