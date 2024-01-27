import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResidentTest {
    private static final String FIRST_NAME_BOB = "Bob";
    private static final String FIRST_NAME_JANE = "Jane";
    private static final String LAST_NAME_SMITH = "Smith";
    private static final String LAST_NAME_DOE = "Doe";
    private static final Address ADDRESS_ONE = new Address("123 Gofer St", "Seattle", "WA");
    private static final Address ADDRESS_TWO = new Address("234 Gofer St", "Seattle", "WA");
    private static final int AGE_25 = 25;
    private static final int AGE_45 = 45;
    private static final String BOB_DOE_ONE_25_STRING = "Bob Doe, 123 Gofer St, Seattle, WA, 25";

    @Test
    void getAddress() {
        Resident resident = new Resident(FIRST_NAME_BOB, LAST_NAME_SMITH, ADDRESS_ONE, AGE_25);

        assertEquals(resident.getAddress(), ADDRESS_ONE);
    }

    @Test
    void getAge() {
        Resident resident = new Resident(FIRST_NAME_BOB, LAST_NAME_SMITH, ADDRESS_ONE, AGE_25);

        assertEquals(resident.getAge(), AGE_25);
    }

    @Test
    void compareTo() {
        Resident bob_doe_one_25 = new Resident(FIRST_NAME_BOB, LAST_NAME_DOE, ADDRESS_ONE, AGE_25);
        Resident jane_doe_one_25 = new Resident(FIRST_NAME_JANE, LAST_NAME_DOE, ADDRESS_ONE, AGE_25);
        Resident bob_smith_one_25 = new Resident(FIRST_NAME_BOB, LAST_NAME_SMITH, ADDRESS_ONE, AGE_25);
        Resident bob_doe_two_25 = new Resident(FIRST_NAME_BOB, LAST_NAME_DOE, ADDRESS_TWO, AGE_25);
        Resident bob_doe_one_45 = new Resident(FIRST_NAME_BOB, LAST_NAME_DOE, ADDRESS_ONE, AGE_45);

        assertEquals(0, bob_doe_one_25.compareTo(bob_doe_one_25));
        assertTrue(bob_doe_one_25.compareTo(jane_doe_one_25) < 0);
        assertTrue(bob_doe_one_25.compareTo(bob_smith_one_25) < 0);
        assertTrue(bob_doe_one_25.compareTo(bob_doe_two_25) < 0);
        assertTrue(bob_doe_one_25.compareTo(bob_doe_one_45) < 0);

        assertTrue(jane_doe_one_25.compareTo(bob_smith_one_25) < 0);
        assertTrue(bob_doe_two_25.compareTo(bob_smith_one_25) < 0);
        assertTrue(bob_doe_one_45.compareTo(bob_smith_one_25) < 0);
    }

    @Test
    void testEquals() {
        Resident bob_doe_one_25 = new Resident(FIRST_NAME_BOB, LAST_NAME_DOE, ADDRESS_ONE, AGE_25);
        Resident bob_doe_one_25_2 = new Resident(FIRST_NAME_BOB, LAST_NAME_DOE, ADDRESS_ONE, AGE_25);
        Resident bob_doe_one_25_3 = new Resident(FIRST_NAME_BOB, LAST_NAME_DOE, ADDRESS_ONE, AGE_25);
        Resident jane_doe_one_25 = new Resident(FIRST_NAME_JANE, LAST_NAME_DOE, ADDRESS_ONE, AGE_25);
        Resident bob_smith_one_25 = new Resident(FIRST_NAME_BOB, LAST_NAME_SMITH, ADDRESS_ONE, AGE_25);
        Resident bob_doe_two_25 = new Resident(FIRST_NAME_BOB, LAST_NAME_DOE, ADDRESS_TWO, AGE_25);
        Resident bob_doe_one_45 = new Resident(FIRST_NAME_BOB, LAST_NAME_DOE, ADDRESS_ONE, AGE_45);

        assertAll(
                () -> assertEquals(bob_doe_one_25, bob_doe_one_25),
                () -> assertEquals(bob_doe_one_25, bob_doe_one_25_2),
                () -> assertEquals(bob_doe_one_25_2, bob_doe_one_25),
                () -> assertEquals(bob_doe_one_25_2, bob_doe_one_25_3),
                () -> assertEquals(bob_doe_one_25, bob_doe_one_25_3),
                () -> assertNotEquals(bob_doe_one_25, null),
                () -> assertNotEquals(bob_doe_one_25, jane_doe_one_25),
                () -> assertNotEquals(bob_doe_one_25, bob_smith_one_25),
                () -> assertNotEquals(bob_doe_one_25, bob_doe_two_25),
                () -> assertNotEquals(bob_doe_one_25, bob_doe_one_45)
        );
    }

    @Test
    void testHashCode() {
        Resident bob_doe_one_25 = new Resident(FIRST_NAME_BOB, LAST_NAME_DOE, ADDRESS_ONE, AGE_25);
        Resident bob_doe_one_25_2 = new Resident(FIRST_NAME_BOB, LAST_NAME_DOE, ADDRESS_ONE, AGE_25);
        Resident jane_doe_one_25 = new Resident(FIRST_NAME_JANE, LAST_NAME_DOE, ADDRESS_ONE, AGE_25);
        Resident bob_smith_one_25 = new Resident(FIRST_NAME_BOB, LAST_NAME_SMITH, ADDRESS_ONE, AGE_25);
        Resident bob_doe_two_25 = new Resident(FIRST_NAME_BOB, LAST_NAME_DOE, ADDRESS_TWO, AGE_25);
        Resident bob_doe_one_45 = new Resident(FIRST_NAME_BOB, LAST_NAME_DOE, ADDRESS_ONE, AGE_45);

        assertAll("Equal hashCodes for equal Residents",
                () -> assertEquals(bob_doe_one_25.hashCode(), bob_doe_one_25.hashCode()),
                () -> assertEquals(bob_doe_one_25.hashCode(), bob_doe_one_25_2.hashCode())
        );

        assertAll("Different hashCodes for non-equal Residents",
                () -> assertNotEquals(bob_doe_one_25.hashCode(), jane_doe_one_25.hashCode()),
                () -> assertNotEquals(bob_doe_one_25.hashCode(), bob_smith_one_25.hashCode()),
                () -> assertNotEquals(bob_doe_one_25.hashCode(), bob_doe_two_25.hashCode()),
                () -> assertNotEquals(bob_doe_one_25.hashCode(), bob_doe_one_45.hashCode())
        );
    }

    @Test
    void testToString() {
        Resident bob_doe_one_25 = new Resident(FIRST_NAME_BOB, LAST_NAME_DOE, ADDRESS_ONE, AGE_25);

        assertEquals(BOB_DOE_ONE_25_STRING, bob_doe_one_25.toString());
    }
}