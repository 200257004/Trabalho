package pt.ips.tizito.daos;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import pt.ips.tizito.entities.Request;
import pt.ips.tizito.entities.User;
import pt.ips.tizito.enums.Response;
import pt.ips.tizito.messages.Info;

public class RequestDAO {

	private final EntityManager entityManager;

	private final Logger logger;

	@Inject
	public RequestDAO(EntityManager entityManager, Logger logger) {
		this.entityManager = entityManager;
		this.logger = logger;
	}

	public void add(Request request) {
		this.logger.info(Info.REQUEST_PERSIST);

		this.entityManager.persist(request);
	}

	public Request findById(Long id) {
		this.logger.info(String.format(Info.REQUEST_FIND_BY_ID, id));

		return this.entityManager.find(Request.class, id);
	}

	public Request findByRequesterAndRequested(User requester, User requested) {
		this.logger.info(Info.REQUEST_FIND_BY_REQUESTER_AND_REQUESTED);

		StringBuilder jpql = new StringBuilder();
		jpql.append("FROM Request r ");
		jpql.append("WHERE ((r.requester = :requester AND r.requested = :requested) OR (r.requester = :requested AND r.requested = :requester)) ");
		jpql.append("AND (r.response IS NULL OR r.response = :response)");

		TypedQuery<Request> query = this.entityManager.createQuery(jpql.toString(), Request.class);
		query.setParameter("requester", requester);
		query.setParameter("requested", requested);
		query.setParameter("response", Response.ACCEPTED);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Request> listByRequested(User requested) {
		this.logger.info(String.format(Info.REQUEST_LIST, requested.getId()));

		StringBuilder jpql = new StringBuilder();
		jpql.append("FROM Request r ");
		jpql.append("WHERE r.requested = :requested ");
		jpql.append("AND r.response IS NULL ");
		jpql.append("AND r.requester.active is true ");
		jpql.append("ORDER BY r.register DESC");

		TypedQuery<Request> query = this.entityManager.createQuery(jpql.toString(), Request.class);
		query.setParameter("requested", requested);

		return query.getResultList();
	}

	public void remove(Request request) {
		this.logger.info(String.format(Info.REQUEST_REMOVE, request.getId()));

		this.entityManager.remove(this.entityManager.merge(request));
	}

}
