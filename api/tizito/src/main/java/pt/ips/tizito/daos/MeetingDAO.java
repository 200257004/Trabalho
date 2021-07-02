package pt.ips.tizito.daos;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import pt.ips.tizito.entities.Meeting;
import pt.ips.tizito.entities.User;
import pt.ips.tizito.enums.Response;
import pt.ips.tizito.messages.Info;

public class MeetingDAO {

	private final EntityManager entityManager;

	private final Logger logger;

	@Inject
	public MeetingDAO(EntityManager entityManager, Logger logger) {
		this.entityManager = entityManager;
		this.logger = logger;
	}

	public void add(Meeting meeting) {
		this.logger.info(String.format(Info.MEETING_PERSIST, meeting.getDescription()));

		this.entityManager.persist(meeting);
	}

	public Meeting findById(Long id) {
		this.logger.info(String.format(Info.MEETING_FIND_BY_ID, id));

		return this.entityManager.find(Meeting.class, id);
	}

	public List<Meeting> list(User user) {
		this.logger.info(Info.MEETING_LIST);

		StringBuilder jpql = new StringBuilder();
		jpql.append("FROM Meeting m WHERE m.inviter = :inviter ");
		jpql.append("OR m IN (SELECT i.meeting FROM Invite i WHERE i.invited = :invited AND i.response = :response) ");
		jpql.append("ORDER BY m.register DESC");

		TypedQuery<Meeting> query = this.entityManager.createQuery(jpql.toString(), Meeting.class);
		query.setParameter("inviter", user);
		query.setParameter("invited", user);
		query.setParameter("response", Response.ACCEPTED);

		return query.getResultList();
	}

}
