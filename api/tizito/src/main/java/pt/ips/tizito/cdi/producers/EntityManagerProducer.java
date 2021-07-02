package pt.ips.tizito.cdi.producers;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class EntityManagerProducer {

	private static EntityManagerFactory factory;

	static {
		factory = Persistence.createEntityManagerFactory("default");
	}

	@Produces
	@RequestScoped
	public EntityManager createEntityManager() {
		return factory.createEntityManager();
	}

	public void close(@Disposes EntityManager manager) {
		if (manager.isOpen()) {
			manager.close();
		}
	}

	@PreDestroy
	public void preDestroy() {
		if (factory.isOpen()) {
			factory.close();
		}
	}

}
