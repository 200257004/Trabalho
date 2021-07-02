package pt.ips.tizito.daos;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import pt.ips.tizito.entities.User;
import pt.ips.tizito.messages.Info;

public class UserDAO {

	private final EntityManager entityManager;

	private final Logger logger;

	@Inject
	public UserDAO(EntityManager entityManager, Logger logger) {
		this.entityManager = entityManager;
		this.logger = logger;
	}

	public void add(User user) {
		this.logger.info(String.format(Info.USER_PERSIST, user.getEmail()));

		this.entityManager.persist(user);
	}

	public User findById(Long id) {
		this.logger.info(String.format(Info.USER_FIND_BY_ID, id));

		return this.entityManager.find(User.class, id);
	}

	public User findByEmail(String email) {
		this.logger.info(String.format(Info.USER_FIND_BY_EMAIL, email));

		String jpql = "FROM User u WHERE u.email = :email";

		TypedQuery<User> query = this.entityManager.createQuery(jpql, User.class);
		query.setParameter("email", email);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<User> listAll(User requester) {
		this.logger.info(Info.USER_LIST);

		StringBuilder jpql = new StringBuilder();
		jpql.append("FROM User u ");
		jpql.append("WHERE u <> :requester ");
		jpql.append("AND u.active = true ");
		jpql.append("AND u.admin = false ");
		jpql.append("ORDER BY u.name ASC");

		TypedQuery<User> query = this.entityManager.createQuery(jpql.toString(), User.class);
		query.setParameter("requester", requester);

		return query.getResultList();
	}

	public List<User> listByName(String name, User requester) {
		this.logger.info(String.format(Info.USER_LIST, name));

		StringBuilder jpql = new StringBuilder();
		jpql.append("FROM User u ");
		jpql.append("WHERE u <> :requester ");
		jpql.append("AND u.active = true ");
		jpql.append("AND u.admin = false ");
		jpql.append("AND LOWER(u.name) LIKE :name ");
		jpql.append("ORDER BY u.name ASC");

		TypedQuery<User> query = this.entityManager.createQuery(jpql.toString(), User.class);
		query.setParameter("requester", requester);
		query.setParameter("name", "%" + name.toLowerCase() + "%");

		return query.getResultList();
	}

	public void remove(User user) {
		this.logger.info(String.format(Info.USER_REMOVE, user.getEmail()));

		this.entityManager.remove(this.entityManager.merge(user));
	}

}
