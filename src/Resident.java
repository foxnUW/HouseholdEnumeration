import java.lang.Comparable;

/**
 * Represents a resident residing at an address.
 *
 * <p>All residents will have a name, first and last, an address, and an age.
 *
 * @author Nicholas Fox
 * @version 1.0
 */
public class Resident implements Comparable<Resident> {
    /**
     * First name of resident.
     */
    private final String firstName;
    /**
     * Last name of resident.
     */
    private final String lastName;
    /**
     * Address of resident.
     */
    private final Address address;
    /**
     * Age of resident.
     */
    private final int age;

    /**
     * Creates a resident.
     *
     * @param firstName the first name of the resident
     * @param lastName  the last name of the resident
     * @param address   the address of the resident
     * @param age       the age of the resident
     */
    public Resident(String firstName, String lastName, Address address, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = new Address(address);
        this.age = age;
    }

    /**
     * Returns the resident's address.
     *
     * @return the address of the resident
     */
    public Address getAddress() {
        return new Address(this.address);
    }

    /**
     * Returns the resident's age.
     *
     * @return the age of the resident
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Compares this resident with the specified resident for order.
     *
     * <p>Returns a negative integer, zero, or a positive integer as this resident is less than, equal to, or greater
     * than the specified resident.
     *
     * <p>Residents are ordered by last name, then first name, then address, then age.
     *
     * @param other the resident to be compared
     * @return a negative integer, zero, or a positive integer as this resident is less than, equal to, or greater than
     * the specified resident
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *        from being compared to this object.
     */
    @Override
    public int compareTo(Resident other) {
        if (this.lastName.compareTo(other.lastName) != 0) {
            return this.lastName.compareTo(other.lastName);
        }
        if (this.firstName.compareTo(other.firstName) != 0) {
            return this.firstName.compareTo(other.firstName);
        }
        if (!this.address.equals(other.address)) {
            return this.address.toString().compareTo(other.address.toString());
        }
        return this.age - other.age;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param other the object to be compared
     * @return <code>true</code> iff <code>other</code> is a Resident and matches the names, age, and address
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Resident)) {
            return false;
        }

        Resident that = (Resident) other;
        return this.compareTo(that) == 0;
    }

    /**
     * Returns a hash code value for the resident.
     *
     * @return a hash code for the resident
     */
    @Override
    public int hashCode() {
        int res = this.lastName.hashCode() + 11;
        res += 31 * this.firstName.hashCode() + 13;
        res += 31 * this.age + 17;
        res += 31 * this.address.hashCode() + 23;
        return res;
    }

    /**
     * Returns a string representation of the resident.
     *
     * <p>The string representation is: &lt;first name&gt; &lt;last name&gt;, &lt;address&gt;, &lt;age&gt;
     *
     * @return a string representation of the resident
     */
    @Override
    public String toString() {
        return this.firstName +
                " " + this.lastName +
                ", " + this.address +
                ", " + this.age;
    }
}
