package pt.ips.tizito.daos;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import pt.ips.tizito.entities.Category;
import pt.ips.tizito.messages.Info;

public class CategoryDAO {

	private final EntityManager entityManager;

	private final Logger logger;

	@Inject
	public CategoryDAO(EntityManager entityManager, Logger logger) {
		this.entityManager = entityManager;
		this.logger = logger;
	}

	public void add(Category category) {
		this.logger.info(String.format(Info.CATEGORY_PERSIST, category.getDescription()));

		this.entityManager.persist(category);
	}

	public void update(Category category) {
		this.logger.info(String.format(Info.CATEGORY_UPDATE, category.getId()));

		this.entityManager.merge(category);
	}

	public Category findById(Long id) {
		this.logger.info(String.format(Info.CATEGORY_FIND_BY_ID, id));

		return this.entityManager.find(Category.class, id);
	}

	public List<Category> list() {
		this.logger.info(Info.CATEGORY_LIST);

		String jpql = "FROM Category ORDER BY description ASC";

		TypedQuery<Category> query = this.entityManager.createQuery(jpql, Category.class);

		return query.getResultList();
	}

	public void remove(Category category) {
		this.logger.info(String.format(Info.CATEGORY_REMOVE, category.getId()));

		this.entityManager.remove(this.entityManager.merge(category));
	}

}
