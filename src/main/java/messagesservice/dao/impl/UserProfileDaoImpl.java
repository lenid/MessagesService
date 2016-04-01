package messagesservice.dao.impl;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import messagesservice.dao.AbstractDao;
import messagesservice.dao.UserProfileDao;
import messagesservice.model.UserProfile;

@Repository("userProfileDao")
public class UserProfileDaoImpl extends AbstractDao<Integer, UserProfile>implements UserProfileDao{

	@SuppressWarnings("unchecked")
	public Set<UserProfile> getAll(){
		Criteria crit = createEntityCriteria();
		crit.addOrder(Order.asc("type"));
		return new HashSet<UserProfile>(crit.list());
	}
	
	public UserProfile findById(int id) {
		return getByKey(id);
	}
	
	public UserProfile findByType(String type) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("type", type));
		return (UserProfile) crit.uniqueResult();
	}
	
}
