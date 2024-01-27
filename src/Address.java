/**
 * Represents an address (street address, city, and state) for the US.
 *
 * @author Nicholas Fox
 * @version 1.0
 */
public class Address implements Comparable<Address> {
    /**
     * Street portion of the street address (required).
     *
     * <p>Initial caps, with 1st, 2nd, 3rd, etc. for numbering.
     * No periods following abbreviations (for example, Ave not Ave.).
     */
    private final String street;
    /**
     * Apartment portion of the street address (optional).
     *
     * <p>Set to <code>null</code> if not present.
     */
    private final String apartment;
    /**
     * City, in initial caps.
     */
    private final String city;
    /**
     * State, by 2-letter code in all caps.
     */
    private final String state;

    /**
     * Creates an address without an apartment or unit number.
     *
     * @param street the street portion of the address
     * @param city   the city portion of the address
     * @param state  the state portion of the address
     */
    public Address(String street, String city, String state) {
        String normalizedStreet = street;
        String normalizedApartment = null;
        String normalizedCity = city;
        String normalizedState = state;

        if (normalizedStreet.contains("Apt.")) {
            int aptIdx = normalizedStreet.indexOf("Apt.");
            normalizedApartment = normalizedStreet.substring(aptIdx);
            normalizedStreet = normalizedStreet.substring(0, aptIdx);
        }

        normalizedStreet = toStandardizedFormat(normalizedStreet);
        if (normalizedApartment != null) {
            normalizedApartment = normalizedApartment.trim();
        }
        normalizedCity = toStandardizedFormat(normalizedCity);
        normalizedState = normalizedState.toUpperCase().replaceAll("\\.", "");

        this.street = normalizedStreet;
        this.apartment = normalizedApartment;
        this.city = normalizedCity;
        this.state = normalizedState;
    }

    /**
     * Creates a copy of the given <code>address</code>.
     *
     * @param address the address to copy
     */
    public Address(Address address) {
        this.street = address.street;
        this.apartment = address.apartment;
        this.city = address.city;
        this.state = address.state;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param other the object to be compared
     * @return <code>true</code> iff <code>other</code> is an Address with matching street, apartment, city, and state
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Address)) {
            return false;
        }

        Address that = (Address) other;

        return this.compareTo(that) == 0;
    }

    /**
     * Returns a hash code value for the address.
     *
     * @return a hash code for the address
     */
    @Override
    public int hashCode() {
        int res = this.state.hashCode() + 11;
        res += 31 * this.city.hashCode() + 13;
        res += 31 * this.street.hashCode() + 17;
        res += this.apartment == null ? 23 : 31 * this.apartment.hashCode() + 23;
        return res;
    }

    /**
     * Returns a string representation of the address.
     *
     * <p>For an address without an apartment: <code>&lt;street&gt;, &lt;city&gt;, &lt;state&gt;</code>.
     * For an address with an apartment: <code>&lt;street&gt; &lt;apartment&gt;, &lt;city&gt;, &lt;state&gt;</code>.
     *
     * @return a string representation of the address
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(this.street);
        if (this.apartment != null) {
            res.append(" ").append(this.apartment);
        }
        res.append(", ").append(this.city)
                .append(", ").append(this.state);
        return res.toString();
    }

    /**
     * Compares this address with the specified address for order.
     *
     * <p>Returns a negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>Ordering is lexicographically by state, then by city, then by street, then by apartment (if present). An
     * address without an apartment is considered less than one with an apartment, all else being equal.
     *
     * @param other the address to be compared.
     * @return a negative integer, zero, or a positive integer as this address
     * is less than, equal to, or greater than the specified address.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Address other) {
        if (!this.state.equals(other.state)) {
            return this.state.compareTo(other.state);
        }
        if (!this.city.equals(other.city)) {
            return this.city.compareTo(other.city);
        }
        if (!this.street.equals(other.street)) {
            return this.street.compareTo(other.street);
        }
        if (this.apartment == null && other.apartment != null) {
            return -1;
        }
        if (this.apartment == null) {
            return 0;
        }
        return this.apartment.compareTo(other.apartment);
    }

    /**
     * Returns a string meeting the standardized form.
     *
     * <p>Standardized form for this class is initial caps, no punctuation, no trailing or leading white space
     *
     * @param s the string to standardize
     * @return the standardized string
     */
    private String toStandardizedFormat(String s) {
        String res = s.trim();
        res = res.replaceAll("\\.", "");
        res = res.replaceAll(",", "");

        String[] words = res.split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].trim();
            String firstLetter = words[i].substring(0, 1).toUpperCase();
            String remaining = words[i].substring(1).toLowerCase();
            words[i] = firstLetter + remaining;
        }
        return String.join(" ", words);
    }
}
