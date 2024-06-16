package goa.systems.commons.security;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class SecurityManagerTests {

	@Test
	void test() {

		SecurityManager sm = new SecurityManager();

		assertDoesNotThrow(() -> {
			byte[] salt = sm.getSalt();
			byte[] password1 = sm.getSecurePassword("Password", salt);
			byte[] password2 = sm.getSecurePassword("Password", salt);
			String pwstr1 = sm.toHexString(password1);
			String pwstr2 = sm.toHexString(password2);
			String saltstr = sm.toHexString(salt);
			assertNotNull(saltstr);
			assertEquals(pwstr1, pwstr2);
		});

	}
}
