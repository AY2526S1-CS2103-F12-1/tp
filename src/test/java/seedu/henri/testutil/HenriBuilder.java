package seedu.henri.testutil;

import seedu.henri.model.Henri;
import seedu.henri.model.person.Person;

/**
 * A utility class to help with building Henri objects.
 * Example usage: <br>
 *     {@code Henri ab = new HenriBuilder().withPerson("John", "Doe").build();}
 */
public class HenriBuilder {

    private Henri henri;

    public HenriBuilder() {
        henri = new Henri();
    }

    public HenriBuilder(Henri henri) {
        this.henri = henri;
    }

    /**
     * Adds a new {@code Person} to the {@code Henri} that we are building.
     */
    public HenriBuilder withPerson(Person person) {
        henri.addPerson(person);
        return this;
    }

    public Henri build() {
        return henri;
    }
}
