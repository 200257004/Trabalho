//package pt.ips.tizito.entities;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.junit.jupiter.api.Assertions.assertFalse;
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
//public class IdeaTest {
//
//	@Test
//	public void naoAceitaDescricaoNula() {
//		Exception exception = assertThrows(NullPointerException.class, () -> {
//			String description = null;
//
//			new Idea(description);
//		});
//		assertThat(exception.getMessage(), equalTo("Description must not be null."));
//	}
//
//	@Test
//	public void naoAceitaDescricaoComMenosDeDoisCaracteres() {
//		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//			String description = "A";
//
//			new Idea(description);
//		});
//		assertThat(exception.getMessage(), equalTo("Description size must be between 2 and 15."));
//	}
//
//	@Test
//	public void aceitaDescricaoComDoisCaracteres() {
//		try {
//			String description = "P�";
//
//			Idea idea = new Idea(description);
//
//			assertThat(idea.getDescription(), equalTo(description));
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
//			Idea idea = new Idea(description);
//
//			assertThat(idea.getDescription(), equalTo(description));
//		} catch (RuntimeException e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void aceitaDescricaoComMenosDeQuinzeCaracteres() {
//		try {
//			String description = StringUtils.repeat('A', 14);
//
//			Idea idea = new Idea(description);
//
//			assertThat(idea.getDescription(), equalTo(description));
//		} catch (RuntimeException e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void aceitaDescricaoComQuinzeCaracteres() {
//		try {
//			String description = StringUtils.repeat('A', 15);
//
//			Idea idea = new Idea(description);
//
//			assertThat(idea.getDescription(), equalTo(description));
//		} catch (RuntimeException e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void naoAceitaDescricaoComMaisDeQuinzeCaracteres() {
//		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//			String description = StringUtils.repeat('A', 16);
//
//			new Idea(description);
//		});
//		assertThat(exception.getMessage(), equalTo("Description size must be between 2 and 15."));
//	}
//
//	@Test
//	public void comparaIdeiasComDescricaoECategoriaIguais() {
//		Category category = new Category("Combinar um(a) ...");
//
//		Idea idea = new Idea("Almo�o");
//		idea.setCategory(category);
//
//		Idea comparedIdea = new Idea("Almo�o");
//		comparedIdea.setCategory(category);
//
//		assertTrue(idea.equals(comparedIdea));
//	}
//
//	@Test
//	public void comparaIdeiasComReferenciasNulas() {
//		Idea idea = new Idea("Almo�o");
//
//		assertFalse(idea.equals(null));
//	}
//
//	@SuppressWarnings("unlikely-arg-type")
//	@Test
//	public void comparaIdeiasComObjetosDeOutrasClasses() {
//		Idea idea = new Idea("Almo�o");
//
//		assertFalse(idea.equals(BigDecimal.TEN));
//	}
//
//	@Test
//	public void comparaIdeiasComDescricoesDiferentes() {
//		Category category = new Category("Combinar um(a) ...");
//
//		Idea idea = new Idea("Almo�o");
//		idea.setCategory(category);
//
//		Idea comparedIdea = new Idea("Caminhada");
//		comparedIdea.setCategory(category);
//
//		assertFalse(idea.equals(comparedIdea));
//	}
//
//	@Test
//	public void comparaIdeiasComCategoriasDiferentes() {
//		Category category01 = new Category("Combinar um(a) ...");
//
//		Idea idea = new Idea("Caminhada");
//		idea.setCategory(category01);
//
//		Category category02 = new Category("Fazer um(a) ...");
//
//		Idea comparedIdea = new Idea("Caminhada");
//		comparedIdea.setCategory(category02);
//
//		assertFalse(idea.equals(comparedIdea));
//	}
//
//	@Test
//	public void comparaIdeiasComDescricaoECategoriaDiferentes() {
//		Category category01 = new Category("Combinar um(a) ...");
//
//		Idea idea = new Idea("Caminhada");
//		idea.setCategory(category01);
//
//		Category category02 = new Category("Andar de ...");
//
//		Idea comparedIdea = new Idea("Motocicleta");
//		comparedIdea.setCategory(category02);
//
//		assertFalse(idea.equals(comparedIdea));
//	}
//
//}
