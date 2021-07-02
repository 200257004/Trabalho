//package pt.ips.tizito.daos;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.hasItems;
//import static org.hamcrest.Matchers.hasSize;
//import static org.junit.Assert.assertNull;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//
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
//import pt.ips.tizito.entities.Category;
//import pt.ips.tizito.entities.Idea;
//
//@RunWith(JUnitPlatform.class)
//public class CategoryDAOTest {
//
//	private EntityManager entityManager;
//
//	private Logger logger;
//
//	private CategoryDAO categoryDAO;
//
//	@BeforeEach
//	public void inicializa() {
//		this.entityManager = new EntityManagerProducer().createEntityManager();
//
//		this.logger = mock(Logger.class);
//
//		this.categoryDAO = new CategoryDAO(this.entityManager, this.logger);
//
//		this.entityManager.getTransaction().begin();
//	}
//
//	@Test
//	public void adicionaUmaCategoriaSemIdeias() {
//		Category category = new Category("Andar de ...");
//
//		this.categoryDAO.add(category);
//
//		assertNotNull(category.getId());
//		assertTrue(this.entityManager.contains(category));
//
//		verify(this.logger).info("Persisting a new category. Description: Andar de ...");
//	}
//
//	@Test
//	public void adicionaUmaCategoriaComIdeias() {
//		Idea idea = new Idea("Bicicleta");
//
//		Category category = new Category("Andar de ...");
//		category.add(idea);
//
//		this.categoryDAO.add(category);
//
//		assertNotNull(category.getId());
//		assertTrue(this.entityManager.contains(category));
//
//		assertNotNull(idea.getId());
//		assertTrue(this.entityManager.contains(idea));
//
//		verify(this.logger).info("Persisting a new category. Description: Andar de ...");
//	}
//
//	@Test
//	public void alteraUmaCategoriaSemIdeias() {
//		Category category = new Category("Andar de ...");
//
//		this.categoryDAO.add(category);
//
//		this.clear();
//
//		category.setDescription("Jogar ...");
//
//		this.categoryDAO.update(category);
//
//		this.clear();
//
//		Category entity = this.categoryDAO.findById(category.getId());
//
//		assertTrue(entity.equals(category));
//
//		verify(this.logger).info("Updating a category. Id: " + category.getId());
//	}
//
//	@Test
//	public void alteraUmaCategoriaAdicionandoIdeias() {
//		Category category = new Category("Andar de ...");
//
//		this.categoryDAO.add(category);
//
//		this.clear();
//
//		Idea idea = new Idea("Bicicleta");
//
//		category.add(idea);
//
//		this.categoryDAO.update(category);
//
//		this.clear();
//
//		Category entity = this.categoryDAO.findById(category.getId());
//
//		assertTrue(entity.equals(category));
//		assertThat(entity.getIdeas(), hasSize(1));
//		assertThat(entity.getIdeas(), hasItems(idea));
//
//		verify(this.logger).info("Updating a category. Id: " + category.getId());
//	}
//
//	@Test
//	public void alteraUmaCategoriaRemovendoIdeias() {
//		Idea idea = new Idea("Bicicleta");
//
//		Category category = new Category("Andar de ...");
//		category.add(idea);
//
//		this.categoryDAO.add(category);
//
//		this.clear();
//
//		category.remove(idea);
//
//		this.categoryDAO.update(category);
//
//		this.clear();
//
//		Category entity = this.categoryDAO.findById(category.getId());
//
//		assertTrue(entity.equals(category));
//		assertTrue(entity.getIdeas().isEmpty());
//
//		verify(this.logger).info("Updating a category. Id: " + category.getId());
//	}
//
//	@Test
//	public void alteraIdeiasDeUmaCategoria() {
//		Idea idea = new Idea("Bicicleta");
//
//		Category category = new Category("Andar de ...");
//		category.add(idea);
//
//		this.categoryDAO.add(category);
//
//		this.clear();
//
//		idea.setDescription("Patins");
//
//		this.categoryDAO.update(category);
//
//		this.clear();
//
//		Category entity = this.categoryDAO.findById(category.getId());
//
//		assertTrue(entity.equals(category));
//		assertThat(entity.getIdeas(), hasSize(1));
//		assertThat(entity.getIdeas(), hasItems(idea));
//
//		verify(this.logger).info("Updating a category. Id: " + category.getId());
//	}
//
//	@Test
//	public void buscaUmaCategoriaPorId() {
//		Category category = new Category("Jogar ...");
//
//		this.categoryDAO.add(category);
//
//		this.clear();
//
//		Category entity = this.categoryDAO.findById(category.getId());
//
//		assertTrue(entity.equals(category));
//
//		verify(this.logger).info("Finding a category. Id: " + category.getId());
//	}
//
//	@Test
//	public void naoBuscaUmaCategoriaInexistente() {
//		Long id = Long.MAX_VALUE;
//
//		Category entity = this.categoryDAO.findById(id);
//
//		assertNull(entity);
//
//		verify(this.logger).info("Finding a category. Id: " + id);
//	}
//
//	@Test
//	public void listaCategorias() {
//		Category play = new Category("Jogar ...");
//
//		this.categoryDAO.add(play);
//
//		Category go = new Category("Ir à(o) ...");
//
//		this.categoryDAO.add(go);
//
//		this.clear();
//
//		List<Category> list = this.categoryDAO.list();
//
//		assertThat(list, hasSize(2));
//		assertThat(list, hasItems(go, play));
//
//		verify(this.logger).info("Listing categories.");
//	}
//
//	@Test
//	public void removeUmaCategoriaSemIdeias() {
//		Category category = new Category("Andar de ...");
//
//		this.categoryDAO.add(category);
//
//		this.clear();
//
//		this.categoryDAO.remove(category);
//
//		this.clear();
//
//		Category entity = this.categoryDAO.findById(category.getId());
//
//		assertNull(entity);
//
//		verify(this.logger).info("Removing a category. Id: " + category.getId());
//	}
//
//	@Test
//	public void removeUmaCategoriaComIdeias() {
//		Idea idea = new Idea("Trilha");
//
//		Category category = new Category("Combinar um(a) ...");
//		category.add(idea);
//
//		this.categoryDAO.add(category);
//
//		this.clear();
//
//		this.categoryDAO.remove(category);
//
//		this.clear();
//
//		Category entity = this.categoryDAO.findById(category.getId());
//
//		assertNull(entity);
//
//		verify(this.logger).info("Removing a category. Id: " + category.getId());
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
