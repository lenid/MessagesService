package messagesservice.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;

//import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import messagesservice.model.State;

public abstract class AbstractDao<PK extends Serializable, T> {
	private final Class<T> persistentClass;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public AbstractDao(){
		this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	public void persist(T entity) {
		getSession().persist(entity);
	}

	@SuppressWarnings("unchecked")
	public Set<T> getAll() {
		return (Set<T>) new HashSet<T>(getSession().createCriteria(persistentClass).list());
	}
	
	@SuppressWarnings("unchecked")
	public Set<T> getAll(State state) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("state", state.toString()));
		return new HashSet<T>(criteria.list());
	}
	
	@SuppressWarnings("unchecked")
	public T getByKey(PK key) {
		return (T) getSession().get(persistentClass, key);
	}

	public void update(T entity) {
		getSession().merge(entity);
	}
	
	public void delete(T entity) {
		getSession().delete(entity);
	}
	
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	protected Criteria createEntityCriteria(){
		return getSession().createCriteria(persistentClass);
	}
	
}
