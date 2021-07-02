package pt.ips.tizito.daos;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import pt.ips.tizito.entities.Invite;
import pt.ips.tizito.entities.User;
import pt.ips.tizito.messages.Info;

public class InviteDAO {

	private final EntityManager entityManager;

	private final Logger logger;

	@Inject
	public InviteDAO(EntityManager entityManager, Logger logger) {
		this.entityManager = entityManager;
		this.logger = logger;
	}

	public Invite findById(Long id) {
		this.logger.info(String.format(Info.INVITE_FIND_BY_ID, id));

		return this.entityManager.find(Invite.class, id);
	}

	public List<Invite> list(User invited) {
		this.logger.info(Info.INVITE_LIST);

		String jpql = "FROM Invite i WHERE i.invited = :invited AND i.response is null ORDER BY i.id DESC";

		TypedQuery<Invite> query = this.entityManager.createQuery(jpql, Invite.class);
		query.setParameter("invited", invited);

		return query.getResultList();
	}

	public List<Invite> listAll(User invited) {
		this.logger.info(Info.INVITE_LIST);

		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT NEW pt.ips.tizito.entities.Invite(i, m) ");
		jpql.append("FROM Invite i, Meeting m WHERE i.meeting = m ");
		jpql.append("AND i.invited = :invited AND i.response is null ");
		jpql.append("ORDER BY m.register DESC");

		TypedQuery<Invite> query = this.entityManager.createQuery(jpql.toString(), Invite.class);
		query.setParameter("invited", invited);

		return query.getResultList();
	}

}
