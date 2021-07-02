//package pt.ips.tizito.entities;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.hamcrest.Matchers.hasSize;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.fail;
//
//import java.math.BigDecimal;
//
//import org.apache.commons.lang3.StringUtils;
//import org.junit.jupiter.api.Test;
//import org.junit.platform.runner.JUnitPlatform;
//import org.junit.runner.RunWith;
//
//@RunWith(JUnitPlatform.class)
//public class CategoryTest {
//
//	@Test
//	public void naoAceitaDescricaoNula() {
//		Exception exception = assertThrows(NullPointerException.class, () -> {
//			String description = null;
//
//			new Category(description);
//		});
//		assertThat(exception.getMessage(), equalTo("Description must not be null."));
//	}
//
//	@Test
//	public void naoAceitaDescricaoComMenosDeDoisCaracteres() {
//		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//			String description = "A";
//
//			new Category(description);
//		});
//		assertThat(exception.getMessage(), equalTo("Description size must be between 2 and 20."));
//	}
//
//	@Test
//	public void aceitaDescricaoComDoisCaracteres() {
//		try {
//			String description = "P�";
//
//			Category category = new Category(description);
//
//			assertThat(category.getDescription(), equalTo(description));
//		} catch (RuntimeException e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void aceitaDescricaoComMaisDeDoisCaracteres() {
//		try {
//			String description = "Mix";
//
//			Category category = new Category(description);
//
//			assertThat(category.getDescription(), equalTo(description));
//		} catch (RuntimeException e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void aceitaDescricaoComMenosDeVinteCaracteres() {
//		try {
//			String description = StringUtils.repeat('A', 19);
//
//			Category category = new Category(description);
//
//			assertThat(category.getDescription(), equalTo(description));
//		} catch (RuntimeException e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void aceitaDescricaoComVinteCaracteres() {
//		try {
//			String description = StringUtils.repeat('A', 20);
//
//			Category category = new Category(description);
//
//			assertThat(category.getDescription(), equalTo(description));
//		} catch (RuntimeException e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void naoAceitaDescricaoComMaisDeVinteCaracteres() {
//		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//			String description = StringUtils.repeat('A', 21);
//
//			new Category(description);
//		});
//		assertThat(exception.getMessage(), equalTo("Description size must be between 2 and 20."));
//	}
//
//	@Test
//	public void criaCategoriasSemIdeas() {
//		Category category = new Category("Jogar ...");
//
//		assertTrue(category.getIdeas().isEmpty());
//	}
//
//	@Test
//	public void naoAceitaIdeiaNula() {
//		Exception exception = assertThrows(NullPointerException.class, () -> {
//			Idea idea = null;
//
//			Category category = new Category("Jogar ...");
//			category.add(idea);
//		});
//		assertThat(exception.getMessage(), equalTo("Idea must not be null."));
//	}
//
//	@Test
//	public void aceitaIdeiasNovas() {
//		Idea idea = new Idea("Boliche");
//
//		Category category = new Category("Jogar ...");
//		category.add(idea);
//
//		assertTrue(category.contains(idea));
//		assertThat(idea.getCategory(), equalTo(category));
//	}
//
//	@Test
//	public void naoAceitaIdeiasRepetidas() {
//		Idea idea = new Idea("Bilhar");
//
//		Category category = new Category("Jogar ...");
//		category.add(idea);
//
//		assertThat(category.getIdeas(), hasSize(1));
//
//		category.add(idea);
//
//		assertThat(category.getIdeas(), hasSize(1));
//	}
//
//	@Test
//	public void protegeIdeiasAceitas() {
//		assertThrows(UnsupportedOperationException.class, () -> {
//			Idea idea = new Idea("Cartas");
//
//			Category category = new Category("Jogar ...");
//			category.getIdeas().add(idea);
//		});
//	}
//
//	@Test
//	public void removeIdeiasAceitas() {
//		Idea idea = new Idea("V�lei");
//
//		Category category = new Category("Jogar ...");
//		category.add(idea);
//
//		assertTrue(category.contains(idea));
//		assertThat(idea.getCategory(), equalTo(category));
//
//		category.remove(idea);
//
//		assertFalse(category.contains(idea));
//		assertNull(idea.getCategory());
//	}
//
//	@Test
//	public void ignoraIdeiasInexistentes() {
//		Idea idea = new Idea("Boliche");
//
//		Category category = new Category("Jogar ...");
//		category.add(idea);
//
//		assertThat(category.getIdeas(), hasSize(1));
//
//		category.remove(null);
//
//		assertThat(category.getIdeas(), hasSize(1));
//	}
//
//	@Test
//	public void comparaCategoriasComDescricoesIguais() {
//		Category category = new Category("Ir à(o) ...");
//
//		Category comparedCategory = new Category("Ir à(o) ...");
//
//		assertTrue(category.equals(comparedCategory));
//	}
//
//	@Test
//	public void comparaCategoriasComReferenciasNulas() {
//		Category category = new Category("Ir à(o) ...");
//
//		assertFalse(category.equals(null));
//	}
//
//	@SuppressWarnings("unlikely-arg-type")
//	@Test
//	public void comparaCategoriasComObjetosDeOutrasClasses() {
//		Category category = new Category("Ir à(o) ...");
//
//		assertFalse(category.equals(BigDecimal.TEN));
//	}
//
//	@Test
//	public void comparaCategoriasComDescricoesDiferentes() {
//		Category category = new Category("Ir à(o) ...");
//
//		Category comparedCategory = new Category("Andar de ...");
//
//		assertFalse(category.equals(comparedCategory));
//	}
//
//}
