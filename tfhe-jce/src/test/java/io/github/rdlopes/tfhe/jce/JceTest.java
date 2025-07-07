package io.github.rdlopes.tfhe.jce;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic test class for tfhe-jce module.
 */
public class JceTest {

    @Test
    public void testBasicAssertion() {
        assertTrue(true, "This should always pass");
        assertEquals(2, 1 + 1, "Basic math should work");
    }
}
