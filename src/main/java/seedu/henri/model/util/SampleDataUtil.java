package seedu.henri.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.henri.model.Henri;
import seedu.henri.model.ReadOnlyHenri;
import seedu.henri.model.person.Address;
import seedu.henri.model.person.Email;
import seedu.henri.model.person.GitHubUsername;
import seedu.henri.model.person.Name;
import seedu.henri.model.person.Person;
import seedu.henri.model.person.Phone;
import seedu.henri.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Henri} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person("E1001", new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new GitHubUsername("@alexxx"),
                getTagSet("friends")),
            new Person("E1002", new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new GitHubUsername(
                        "@bernyuuu"),
                getTagSet("colleagues", "friends")),
            new Person("E1003", new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new GitHubUsername("@charolive3"),
                getTagSet("neighbours")),
            new Person("E1004", new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new GitHubUsername(
                        "@lidavid01"),
                getTagSet("family")),
            new Person("E1005", new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new GitHubUsername(
                        "@irfanibrahim23"),
                getTagSet("classmates")),
            new Person("E1006", new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new GitHubUsername("@royb"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyHenri getSampleHenri() {
        Henri sampleAb = new Henri();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
