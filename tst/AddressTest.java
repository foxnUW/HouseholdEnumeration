import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    private static final String STREET_STD = "123 Gofer St";
    private static final String STREET_APT_STD = "123 Gofer St Apt. 101";
    private static final String CITY_STD = "Seattle";
    private static final String STATE_STD = "WA";
    private static final String STREET_NONSTD_LOWER = " 123 gofer st., ";
    private static final String STREET_NONSTD_UPPER = " 123 GOFER ST., ";
    private static final String APT_NONSTD = " Apt. 101 ";
    private static final String CITY_NONSTD_LOWER = " seattle, ";
    private static final String CITY_NONSTD_UPPER = " SEATTLE, ";
    private static final String STATE_NONSTD_LOWER = "wa.";
    private static final String STATE_NONSTD_INITIAL = "Wa.";
    private static final String STREET_OTHER_STD = "234 Gofer St";
    private static final String STREET_STD_APT_OTHER_STD = "123 Gofer St Apt. 123";
    private static final String CITY_OTHER_STD = "Tacoma";
    private static final String STATE_OTHER_STD = "FL";
    private static final String STD_NO_APT_STRING = STREET_STD + ", " + CITY_STD + ", " + STATE_STD;
    private static final String STD_APT_STRING = STREET_APT_STD + ", " + CITY_STD + ", " + STATE_STD;

    @Test
    void streetOnlyConstructor() {
        Address addy = new Address(STREET_STD, CITY_STD, STATE_STD);
        assertNotNull(addy);
    }

    @Test
    void nonstandardInputRepresentations() {
        Address withoutApt_std = new Address(STREET_STD, CITY_STD, STATE_STD);
        Address withApt_std = new Address(STREET_APT_STD, CITY_STD, STATE_STD);
        Address withoutApt_nonstdLower = new Address(STREET_NONSTD_LOWER, CITY_NONSTD_LOWER, STATE_NONSTD_LOWER);
        Address withoutApt_nonstdUpper = new Address(STREET_NONSTD_UPPER, CITY_NONSTD_UPPER, STATE_NONSTD_INITIAL);
        Address withApt_nonstdLower = new Address(STREET_NONSTD_LOWER + APT_NONSTD, CITY_NONSTD_LOWER,
                STATE_NONSTD_LOWER);
        Address withAtp_nonstdUpper = new Address(STREET_NONSTD_UPPER + APT_NONSTD, CITY_NONSTD_UPPER,
                STATE_NONSTD_INITIAL);

        assertAll(
                () -> assertEquals(withoutApt_std, withoutApt_nonstdLower),
                () -> assertEquals(withoutApt_std, withoutApt_nonstdUpper),
                () -> assertEquals(withApt_std, withApt_nonstdLower),
                () -> assertEquals(withApt_std, withAtp_nonstdUpper)
        );
    }

    @Test
    void equals() {
        Address stdWithoutApt1 = new Address(STREET_STD, CITY_STD, STATE_STD);
        Address stdWithoutApt2 = new Address(STREET_STD, CITY_STD, STATE_STD);
        Address copyStdWithoutApt2 = new Address(stdWithoutApt2);
        Address stdWithDifferentStreet = new Address(STREET_OTHER_STD, CITY_STD, STATE_STD);
        Address stdWithDifferentCity = new Address(STREET_STD, CITY_OTHER_STD, STATE_STD);
        Address stdWithDifferentState = new Address(STREET_STD, CITY_STD, STATE_OTHER_STD);
        Address stdWithApt1 = new Address(STREET_APT_STD, CITY_STD, STATE_STD);
        Address stdWithApt2 = new Address(STREET_APT_STD, CITY_STD, STATE_STD);
        Address stdWithDifferentApt = new Address(STREET_STD_APT_OTHER_STD, CITY_STD, STATE_STD);

        assertAll("Reflexivity",
                () -> assertEquals(stdWithApt1, stdWithApt1),
                () -> assertEquals(stdWithoutApt1, stdWithoutApt1),
                () -> assertNotEquals(null, stdWithApt1),
                () -> assertNotEquals(null, stdWithoutApt1)
        );

        assertAll("Symmetry",
                () -> assertEquals(stdWithoutApt1, stdWithoutApt2),
                () -> assertEquals(stdWithoutApt1.equals(stdWithoutApt2), stdWithoutApt2.equals(stdWithoutApt1)),
                () -> assertNotEquals(stdWithoutApt1, stdWithApt1),
                () -> assertEquals(copyStdWithoutApt2, stdWithoutApt2),
                () -> assertNotEquals(stdWithDifferentStreet, stdWithoutApt1),
                () -> assertNotEquals(stdWithDifferentCity, stdWithoutApt1),
                () -> assertNotEquals(stdWithDifferentState, stdWithoutApt1),
                () -> assertEquals(stdWithApt1, stdWithApt2),
                () -> assertEquals(stdWithApt2.equals(stdWithApt1), stdWithApt1.equals(stdWithApt2)),
                () -> assertNotEquals(stdWithDifferentApt, stdWithApt1)
        );

        assertAll("Transitivity",
                () -> assertEquals(stdWithoutApt1, copyStdWithoutApt2)
        );
    }

    @Test
    void testHashCode() {
        Address stdWithoutApt1 = new Address(STREET_STD, CITY_STD, STATE_STD);
        Address stdWithoutApt2 = new Address(STREET_STD, CITY_STD, STATE_STD);
        Address stdWithDifferentStreet = new Address(STREET_OTHER_STD, CITY_STD, STATE_STD);
        Address stdWithDifferentCity = new Address(STREET_STD, CITY_OTHER_STD, STATE_STD);
        Address stdWithDifferentState = new Address(STREET_STD, CITY_STD, STATE_OTHER_STD);
        Address stdWithApt1 = new Address(STREET_APT_STD, CITY_STD, STATE_STD);
        Address stdWithApt2 = new Address(STREET_APT_STD, CITY_STD, STATE_STD);
        Address stdWithDifferentApt = new Address(STREET_STD_APT_OTHER_STD, CITY_STD, STATE_STD);

        assertAll("Equivalent hashCodes for equal addresses",
                () -> assertEquals(stdWithoutApt1.hashCode(), stdWithoutApt2.hashCode()),
                () -> assertEquals(stdWithoutApt1.hashCode(), stdWithoutApt1.hashCode()),
                () -> assertEquals(stdWithApt1.hashCode(), stdWithApt1.hashCode()),
                () -> assertEquals(stdWithApt1.hashCode(), stdWithApt2.hashCode())
        );

        assertAll("Different hashCodes for non-equal addresses",
                () -> assertNotEquals(stdWithoutApt1.hashCode(), stdWithApt1.hashCode()),
                () -> assertNotEquals(stdWithoutApt1.hashCode(), stdWithDifferentStreet.hashCode()),
                () -> assertNotEquals(stdWithoutApt1.hashCode(), stdWithDifferentCity.hashCode()),
                () -> assertNotEquals(stdWithoutApt1.hashCode(), stdWithDifferentState.hashCode()),
                () -> assertNotEquals(stdWithApt1.hashCode(), stdWithDifferentApt.hashCode())
        );
    }

    @Test
    void testToString() {
        Address stdWithoutApt1 = new Address(STREET_STD, CITY_STD, STATE_STD);
        Address stdWithApt1 = new Address(STREET_APT_STD, CITY_STD, STATE_STD);

        assertEquals(STD_APT_STRING, stdWithApt1.toString());
        assertEquals(STD_NO_APT_STRING, stdWithoutApt1.toString());
    }

    @Test
    void testCompareTo() {
        Address stdWithoutApt1 = new Address(STREET_STD, CITY_STD, STATE_STD);
        Address stdWithoutApt2 = new Address(STREET_STD, CITY_STD, STATE_STD);
        Address stdWithDifferentStreet = new Address(STREET_OTHER_STD, CITY_STD, STATE_STD);
        Address stdWithDifferentCity = new Address(STREET_STD, CITY_OTHER_STD, STATE_STD);
        Address stdWithDifferentState = new Address(STREET_STD, CITY_STD, STATE_OTHER_STD);
        Address stdWithApt1 = new Address(STREET_APT_STD, CITY_STD, STATE_STD);
        Address stdWithApt2 = new Address(STREET_APT_STD, CITY_STD, STATE_STD);
        Address stdWithDifferentApt = new Address(STREET_STD_APT_OTHER_STD, CITY_STD, STATE_STD);

        assertAll(
                () -> assertTrue(stdWithoutApt1.compareTo(stdWithoutApt1) == 0),
                () -> assertTrue(stdWithoutApt1.compareTo(stdWithoutApt2) == 0),
                () -> assertTrue(stdWithoutApt1.compareTo(stdWithDifferentStreet) < 0),
                () -> assertTrue(stdWithoutApt1.compareTo(stdWithDifferentCity) < 0),
                () -> assertTrue(stdWithoutApt1.compareTo(stdWithDifferentState) > 0),
                () -> assertTrue(stdWithoutApt1.compareTo(stdWithApt1) < 0),
                () -> assertTrue(stdWithApt1.compareTo(stdWithApt2) == 0),
                () -> assertTrue(stdWithApt1.compareTo(stdWithDifferentApt) < 0)
        );
    }
}