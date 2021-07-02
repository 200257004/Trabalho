package pt.ips.tizito;

import java.lang.reflect.Field;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;

import pt.ips.tizito.cdi.producers.EntityManagerProducer;
import pt.ips.tizito.daos.UserDAO;
import pt.ips.tizito.entities.User;

public class App {

	public static void main(String[] args) {
		EntityManager entityManager = new EntityManagerProducer().createEntityManager();

		Logger logger = Logger.getLogger(App.class);

		UserDAO userDAO = new UserDAO(entityManager, logger);

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		User admin = new User("Administrator", "admin@ips.pt", "!10Admin");
		admin.setActive(true);

		setAdmin(admin);

		userDAO.add(admin);

		User fulano = new User("Fulano", "fulano@ips.pt", "!10Fulano");
		fulano.setActive(true);

		userDAO.add(fulano);

		User sicrano = new User("Sicrano", "sicrano@ips.pt", "!10Sicrano");
		sicrano.setActive(true);

		userDAO.add(sicrano);

		User active = new User("Active", "active@ips.pt", "!10Active");
		active.setActive(true);

		userDAO.add(active);
		
		sicrano.setActive(false);
		
		User beltrano = new User("Beltrano", "beltrano@ips.pt", "!10Beltrano");
		beltrano.setActive(true);

		userDAO.add(beltrano);

		User inactive = new User("Inactive", "inactive@ips.pt", "!10Inactive");

		userDAO.add(inactive);

		User pedro = new User("Pedro", "pedro@ips.pt", "!10Pedro");

		userDAO.add(pedro);

		User patricia = new User("Patricia", "patricia@ips.pt", "!10Patricia");

		userDAO.add(patricia);

		User benedito = new User("Benedito", "benedito@ips.pt", "!10Benedito");

		userDAO.add(benedito);

		transaction.commit();

		entityManager.close();
	}

	private static void setAdmin(User user) {
		try {
			Field adminField = user.getClass().getDeclaredField("admin");
			adminField.setAccessible(true);
			adminField.set(user, true);
			adminField.setAccessible(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
