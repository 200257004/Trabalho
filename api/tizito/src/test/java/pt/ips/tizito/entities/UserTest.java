//package pt.ips.tizito.entities;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.hamcrest.Matchers.hasSize;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.fail;
//
//import java.util.Calendar;
//
//import org.apache.commons.lang3.StringUtils;
//import org.junit.jupiter.api.Test;
//import org.junit.platform.runner.JUnitPlatform;
//import org.junit.runner.RunWith;
//
//@RunWith(JUnitPlatform.class)
//public class UserTest {
//
//	@Test
//	public void naoAceitaNomeNulo() {
//		Exception exception = assertThrows(NullPointerException.class, () -> {
//			String name = null;
//
//			new User(name, "fulano@gmail.com", "!10Fulano");
//		});
//		assertThat(exception.getMessage(), equalTo("Name must not be null."));
//	}
//
//	@Test
//	public void naoAceitaNomeComMenosDeDoisCaracteres() {
//		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//			String name = "B";
//
//			new User(name, "fulano@gmail.com", "!10Fulano");
//		});
//		assertThat(exception.getMessage(), equalTo("Name size must be between 2 and 30."));
//	}
//
//	@Test
//	public void aceitaNomeComDoisCaracteres() {
//		try {
//			String name = "Lu";
//
//			User user = new User(name, "fulano@gmail.com", "!10Fulano");
//
//			assertThat(user.getName(), equalTo(name));
//		} catch (RuntimeException e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void aceitaNomeComMaisDeDoisCaracteres() {
//		try {
//			String name = "Gui";
//
//			User user = new User(name, "fulano@gmail.com", "!10Fulano");
//
//			assertThat(user.getName(), equalTo(name));
//		} catch (RuntimeException e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void aceitaNomeComMenosDeTrintaCaracteres() {
//		try {
//			String name = StringUtils.repeat('A', 29);
//
//			User user = new User(name, "fulano@gmail.com", "!10Fulano");
//
//			assertThat(user.getName(), equalTo(name));
//		} catch (RuntimeException e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void aceitaNomeComTrintaCaracteres() {
//		try {
//			String name = StringUtils.repeat('A', 30);
//
//			User user = new User(name, "fulano@gmail.com", "!10Fulano");
//
//			assertThat(user.getName(), equalTo(name));
//		} catch (RuntimeException e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void naoAceitaNomeComMaisDeTrintaCaracteres() {
//		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//			String name = StringUtils.repeat('A', 31);
//
//			new User(name, "fulano@gmail.com", "!10Fulano");
//		});
//		assertThat(exception.getMessage(), equalTo("Name size must be between 2 and 30."));
//	}
//
//	@Test
//	public void naoAceitaNomeMalicioso() {
//		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//			String name = "<script>";
//
//			new User(name, "fulano@gmail.com", "!10Fulano");
//		});
//		assertThat(exception.getMessage(), equalTo("Name must not match \"<script>\"."));
//	}
//
//	@Test
//	public void naoAceitaEmailNulo() {
//		Exception exception = assertThrows(NullPointerException.class, () -> {
//			String email = null;
//
//			new User("Fulano", email, "!10Fulano");
//		});
//		assertThat(exception.getMessage(), equalTo("Email must not be null."));
//	}
//
//	@Test
//	public void aceitaEmailComMenosDeSessentaCaracteres() {
//		try {
//			String email = StringUtils.repeat('f', 43) + "fulano@gmail.com";
//
//			User user = new User("Fulano", email, "!10Fulano");
//
//			assertThat(user.getEmail(), equalTo(email));
//		} catch (RuntimeException e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void aceitaEmailComSessentaCaracteres() {
//		try {
//			String email = StringUtils.repeat('f', 44) + "fulano@gmail.com";
//
//			User user = new User("Fulano", email, "!10Fulano");
//
//			assertThat(user.getEmail(), equalTo(email));
//		} catch (RuntimeException e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void naoAceitaEmailComMaisDeSessentaCaracteres() {
//		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//			String email = StringUtils.repeat('f', 45) + "fulano@gmail.com";
//
//			new User("Fulano", email, "!10Fulano");
//		});
//		assertThat(exception.getMessage(), equalTo("Email size must be between 5 and 60."));
//	}
//
//	@Test
//	public void naoAceitaEmailInvalido() {
//		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//			String email = "www.fulano.com";
//
//			new User("Fulano", email, "!10Fulano");
//		});
//		assertThat(exception.getMessage(), equalTo("Email not a well-formed email address."));
//	}
//
//	@Test
//	public void naoAceitaSenhaNula() {
//		Exception exception = assertThrows(NullPointerException.class, () -> {
//			String password = null;
//
//			new User("Fulano", "fulano@gmail.com", password);
//		});
//		assertThat(exception.getMessage(), equalTo("Password must not be null."));
//	}
//
//	@Test
//	public void naoAceitaSenhaComMenosDeTresCaracteres() {
//		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//			String password = "12";
//
//			new User("Fulano", "fulano@gmail.com", password);
//		});
//		assertThat(exception.getMessage(), equalTo("Password size must be between 3 and 20."));
//	}
//
//	@Test
//	public void aceitaSenhaComTresCaracteres() {
//		try {
//			String password = "123";
//
//			new User("Fulano", "fulano@gmail.com", password);
//		} catch (RuntimeException e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void aceitaSenhaComMaisDeTresCaracteres() {
//		try {
//			String password = "1234";
//
//			new User("Fulano", "fulano@gmail.com", password);
//		} catch (RuntimeException e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void aceitaSenhaComMenosDeVinteCaracteres() {
//		try {
//			String password = StringUtils.repeat('1', 19);
//
//			new User("Fulano", "fulano@gmail.com", password);
//		} catch (RuntimeException e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void aceitaSenhaComVinteCaracteres() {
//		try {
//			String password = StringUtils.repeat('1', 20);
//
//			new User("Fulano", "fulano@gmail.com", password);
//		} catch (RuntimeException e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void naoAceitaSenhaComMaisDeVinteCaracteres() {
//		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//			String password = StringUtils.repeat('1', 21);
//
//			new User("Fulano", "fulano@gmail.com", password);
//		});
//		assertThat(exception.getMessage(), equalTo("Password size must be between 3 and 20."));
//	}
//
//	@Test
//	public void naoAceitaSenhaComEspacos() {
//		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//			String password = "12 3";
//
//			new User("Fulano", "fulano@gmail.com", password);
//		});
//		assertThat(exception.getMessage(), equalTo("Password not a well-formed password."));
//	}
//	
//	@Test
//	public void naoAceitaSenhaComDoisPontos() {
//		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//			String password = "12:3";
//			
//			new User("Fulano", "fulano@gmail.com", password);
//		});
//		assertThat(exception.getMessage(), equalTo("Password not a well-formed password."));
//	}
//
//	@Test
//	public void criaUsuariosComDataDeRegistro() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//
//		Calendar today = Calendar.getInstance();
//
//		Calendar register = user.getRegister();
//		assertThat(register.get(Calendar.MONTH), equalTo(today.get(Calendar.MONTH)));
//		assertThat(register.get(Calendar.DAY_OF_MONTH), equalTo(today.get(Calendar.DAY_OF_MONTH)));
//		assertThat(register.get(Calendar.YEAR), equalTo(today.get(Calendar.YEAR)));
//	}
//
//	@Test
//	public void protegeADataDeRegistro() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//		user.getRegister().add(Calendar.DAY_OF_MONTH, 1);
//
//		Calendar today = Calendar.getInstance();
//
//		Calendar register = user.getRegister();
//		assertThat(register.get(Calendar.DAY_OF_MONTH), equalTo(today.get(Calendar.DAY_OF_MONTH)));
//	}
//
//	@Test
//	public void criaUsuariosInativos() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//
//		assertFalse(user.isActive());
//	}
//
//	@Test
//	public void naoCriaUsuariosAdministradores() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//
//		assertFalse(user.isAdmin());
//	}
//
//	@Test
//	public void criaUsuariosSemAmigos() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//
//		assertTrue(user.getFriends().isEmpty());
//	}
//
//	@Test
//	public void naoAceitaAmigoNulo() {
//		Exception exception = assertThrows(NullPointerException.class, () -> {
//			User friend = null;
//
//			User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//			user.add(friend);
//		});
//		assertThat(exception.getMessage(), equalTo("Friend must not be null."));
//	}
//
//	@Test
//	public void aceitaAmigosNovos() {
//		User friend = new User("Sicrano", "sicrano@gmail.com", "!20Sicrano");
//		friend.setActive(true);
//
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//		user.setActive(true);
//
//		user.add(friend);
//
//		assertTrue(user.contains(friend));
//		assertTrue(friend.contains(user));
//	}
//
//	@Test
//	public void naoAceitaAmigosRepetidos() {
//		User friend = new User("Sicrano", "sicrano@gmail.com", "!20Sicrano");
//		friend.setActive(true);
//
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//		user.add(friend);
//
//		assertThat(user.getFriends(), hasSize(1));
//
//		user.add(friend);
//
//		assertThat(user.getFriends(), hasSize(1));
//	}
//
//	@Test
//	public void naoAceitaAmigoInativo() {
//		User friend = new User("Sicrano", "sicrano@gmail.com", "!20Sicrano");
//
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//		user.add(friend);
//
//		assertTrue(user.getFriends().isEmpty());
//	}
//
//	@Test
//	public void naoAceitaASiProprioComoAmigo() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//		user.setActive(true);
//
//		user.add(user);
//
//		assertTrue(user.getFriends().isEmpty());
//	}
//
//	@Test
//	public void protegeAmigosAceitos() {
//		assertThrows(UnsupportedOperationException.class, () -> {
//			User friend = new User("Sicrano", "sicrano@gmail.com", "!20Sicrano");
//
//			User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//			user.getFriends().add(friend);
//		});
//	}
//
//	@Test
//	public void removeAmigosAceitos() {
//		User friend = new User("Sicrano", "sicrano@gmail.com", "!20Sicrano");
//		friend.setActive(true);
//
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//		user.setActive(true);
//
//		user.add(friend);
//
//		assertTrue(user.contains(friend));
//		assertTrue(friend.contains(user));
//
//		user.remove(friend);
//
//		assertFalse(user.contains(friend));
//		assertFalse(friend.contains(user));
//	}
//
//	@Test
//	public void ignoraAmigosInexistentes() {
//		User friend = new User("Sicrano", "sicrano@gmail.com", "!20Sicrano");
//		friend.setActive(true);
//
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//		user.add(friend);
//
//		assertThat(user.getFriends(), hasSize(1));
//
//		user.remove(new User("Beltrano", "beltrano@gmail.com", "!30Beltrano"));
//
//		assertThat(user.getFriends(), hasSize(1));
//	}
//
//	@Test
//	public void comparaUsuariosComEmailsIguais() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//
//		User comparedUser = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//
//		assertTrue(user.equals(comparedUser));
//	}
//
//	@Test
//	public void comparaUsuariosComReferenciasNulas() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//
//		assertFalse(user.equals(null));
//	}
//
//	@SuppressWarnings("unlikely-arg-type")
//	@Test
//	public void comparaUsuariosComObjetosDeOutrasClasses() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//
//		assertFalse(user.equals(""));
//	}
//
//	@Test
//	public void comparaUsuariosComEmailsDiferentes() {
//		User user = new User("Fulano", "fulano@gmail.com", "!10Fulano");
//
//		User comparedUser = new User("Beltrano", "beltrano@gmail.com", "!30Beltrano");
//
//		assertFalse(user.equals(comparedUser));
//	}
//
//}
