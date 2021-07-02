//package pt.ips.tizito.daos;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.hamcrest.Matchers.hasSize;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//
//import java.lang.reflect.Field;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//
//import org.apache.log4j.Logger;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.platform.runner.JUnitPlatform;
//import org.junit.runner.RunWith;
//
//import pt.ips.tizito.cdi.producers.EntityManagerProducer;
//import pt.ips.tizito.entities.User;
//
//@RunWith(JUnitPlatform.class)
//public class UserDAOTest {
//
//	private EntityManager entityManager;
//
//	private Logger logger;
//
//	private UserDAO userDAO;
//
//	private User requester;
//
//	@BeforeEach
//	public void inicializa() {
//		this.entityManager = new EntityManagerProducer().createEntityManager();
//
//		this.logger = mock(Logger.class);
//
//		this.userDAO = new UserDAO(this.entityManager, this.logger);
//
//		this.entityManager.getTransaction().begin();
//
//		this.populate();
//	}
//
//	private void populate() {
//		this.requester = new User("Beltrano", "beltrano@gmail.com", "!20Fulano");
//		this.requester.setActive(true);
//
//		this.userDAO.add(this.requester);
//
//		this.clear();
//	}
//
//	@Test
//	public void adicionaUmUsuario() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//
//		this.userDAO.add(user);
//
//		assertNotNull(user.getId());
//		assertTrue(this.entityManager.contains(user));
//
//		verify(this.logger).info("Persisting a new user. Email: fulano@gmail.com");
//	}
//
//	@Test
//	public void buscaUmUsuarioPorEmail() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//
//		this.userDAO.add(user);
//
//		this.clear();
//
//		User entity = this.userDAO.findByEmail(user.getEmail());
//
//		assertTrue(entity.equals(user));
//
//		verify(this.logger).info("Finding an user. Email: fulano@gmail.com");
//	}
//
//	@Test
//	public void naoBuscaUmUsuarioInexistente() {
//		String email = "sicrano@gmail.com";
//
//		User entity = this.userDAO.findByEmail(email);
//
//		assertNull(entity);
//
//		verify(this.logger).info("Finding an user. Email: sicrano@gmail.com");
//	}
//
//	@Test
//	public void naoListaUsuariosInativos() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//
//		this.userDAO.add(user);
//
//		this.clear();
//
//		String name = "Fulano";
//
//		List<User> users = this.userDAO.listByName(name, this.requester);
//
//		assertTrue(users.isEmpty());
//
//		verify(this.logger).info("Listing users. Name: " + name);
//	}
//
//	@Test
//	public void naoListaUsuariosQuandoONomeForInexistente() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//		user.setActive(true);
//
//		this.userDAO.add(user);
//
//		this.clear();
//
//		String name = "Sicrano";
//
//		List<User> users = this.userDAO.listByName(name, this.requester);
//
//		assertTrue(users.isEmpty());
//
//		verify(this.logger).info("Listing users. Name: " + name);
//	}
//
//	@Test
//	public void listaUsuariosPorNome() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//		user.setActive(true);
//
//		this.userDAO.add(user);
//
//		this.clear();
//
//		String name = "Fulano";
//
//		List<User> users = this.userDAO.listByName(name, this.requester);
//
//		assertThat(users, hasSize(1));
//		assertThat(users, hasItem(user));
//
//		verify(this.logger).info("Listing users. Name: " + name);
//	}
//
//	@Test
//	public void listaUsuariosPeloInicioDoNome() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//		user.setActive(true);
//
//		this.userDAO.add(user);
//
//		this.clear();
//
//		String name = "Ful";
//
//		List<User> users = this.userDAO.listByName(name, this.requester);
//
//		assertThat(users, hasSize(1));
//		assertThat(users, hasItem(user));
//
//		verify(this.logger).info("Listing users. Name: " + name);
//	}
//
//	@Test
//	public void listaUsuariosPeloMeioDoNome() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//		user.setActive(true);
//
//		this.userDAO.add(user);
//
//		this.clear();
//
//		String name = "ula";
//
//		List<User> users = this.userDAO.listByName(name, this.requester);
//
//		assertThat(users, hasSize(1));
//		assertThat(users, hasItem(user));
//
//		verify(this.logger).info("Listing users. Name: " + name);
//	}
//
//	@Test
//	public void listaUsuariosPeloFimDoNome() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//		user.setActive(true);
//
//		this.userDAO.add(user);
//
//		this.clear();
//
//		String name = "lano";
//
//		List<User> users = this.userDAO.listByName(name, this.requester);
//
//		assertThat(users, hasSize(1));
//		assertThat(users, hasItem(user));
//
//		verify(this.logger).info("Listing users. Name: " + name);
//	}
//
//	@Test
//	public void listaUsuariosPeloNomeEmMaiusculo() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//		user.setActive(true);
//
//		this.userDAO.add(user);
//
//		this.clear();
//
//		String name = "FULANO";
//
//		List<User> users = this.userDAO.listByName(name, this.requester);
//
//		assertThat(users, hasSize(1));
//		assertThat(users, hasItem(user));
//
//		verify(this.logger).info("Listing users. Name: " + name);
//	}
//
//	@Test
//	public void listaUsuariosPeloNomeEmMinusculo() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//		user.setActive(true);
//
//		this.userDAO.add(user);
//
//		this.clear();
//
//		String name = "fulano";
//
//		List<User> users = this.userDAO.listByName(name, this.requester);
//
//		assertThat(users, hasSize(1));
//		assertThat(users, hasItem(user));
//
//		verify(this.logger).info("Listing users. Name: " + name);
//	}
//
//	@Test
//	public void listaUsuariosPeloNomeEmMaiusculoEMinusculo() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//		user.setActive(true);
//
//		this.userDAO.add(user);
//
//		this.clear();
//
//		String name = "FuLaNo";
//
//		List<User> users = this.userDAO.listByName(name, this.requester);
//
//		assertThat(users, hasSize(1));
//		assertThat(users, hasItem(user));
//
//		verify(this.logger).info("Listing users. Name: " + name);
//	}
//
//	@Test
//	public void naoListaUsuariosRequisitantes() {
//		String name = "Beltrano";
//
//		List<User> users = this.userDAO.listByName(name, this.requester);
//
//		assertNotNull(this.userDAO.findByEmail(this.requester.getEmail()));
//
//		assertTrue(users.isEmpty());
//
//		verify(this.logger).info("Listing users. Name: " + name);
//	}
//
//	@Test
//	public void naoListaUsuariosAdministradores() {
//		User admin = new User("Administrator", "admin@ips.pt", "!10Admin");
//		admin.setActive(true);
//
//		this.setAdmin(admin);
//
//		this.userDAO.add(admin);
//
//		this.clear();
//
//		String name = "Administrator";
//
//		List<User> users = this.userDAO.listByName(name, this.requester);
//
//		assertTrue(users.isEmpty());
//
//		verify(this.logger).info("Listing users. Name: " + name);
//	}
//
//	@Test
//	public void removeUmUsuario() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//
//		this.userDAO.add(user);
//
//		this.clear();
//
//		this.userDAO.remove(user);
//
//		this.clear();
//
//		User entity = this.userDAO.findByEmail(user.getEmail());
//
//		assertNull(entity);
//
//		verify(this.logger).info("Removing an user. Email: " + user.getEmail());
//	}
//
//	private void setAdmin(User user) {
//		try {
//			Field adminField = user.getClass().getDeclaredField("admin");
//			adminField.setAccessible(true);
//			adminField.set(user, true);
//			adminField.setAccessible(false);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	private void clear() {
//		this.entityManager.flush();
//		this.entityManager.clear();
//	}
//
//	@AfterEach
//	public void finaliza() {
//		this.entityManager.getTransaction().rollback();
//		this.entityManager.close();
//	}
//
//}
