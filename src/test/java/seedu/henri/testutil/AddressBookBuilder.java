package seedu.henri.testutil;

import seedu.henri.model.Henri;
import seedu.henri.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private Henri henri;

    public AddressBookBuilder() {
        henri = new Henri();
    }

    public AddressBookBuilder(Henri henri) {
        this.henri = henri;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        henri.addPerson(person);
        return this;
    }

    public Henri build() {
        return henri;
    }
}
