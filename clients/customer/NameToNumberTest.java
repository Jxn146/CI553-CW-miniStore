package clients.customer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NameToNumberTest {

	@Test
    void testCaseInsensitiveLookup() {
        NameToNumber nameToNumber = new NameToNumber();
        assertEquals("0001", nameToNumber.getNumberByName(nameToNumber, "TV"), "Case-insensitive match failed for 'TV'");
        assertEquals("0001", nameToNumber.getNumberByName(nameToNumber, "tv"), "Case-insensitive match failed for 'tv'");
        assertEquals("0001", nameToNumber.getNumberByName(nameToNumber, "Tv"), "Case-insensitive match failed for 'Tv'");
    }

    @Test
    void testInvalidNameLookup() {
        NameToNumber nameToNumber = new NameToNumber();
        assertNull(nameToNumber.getNumberByName(nameToNumber, "Invalid"), "Invalid name should return null");
    }

    @Test
    void testDefaultValues() {
        NameToNumber nameToNumber = new NameToNumber();
        assertTrue(nameToNumber.containsKey("0001"), "Default key '0001' is missing");
        assertEquals("TV", nameToNumber.get("0001"), "Default value for key '0001' is incorrect");
    }
}
