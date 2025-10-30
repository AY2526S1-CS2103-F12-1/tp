package seedu.henri.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.henri.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.henri.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.henri.logic.parser.CliSyntax.PREFIX_GITHUB;
import static seedu.henri.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.henri.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.henri.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.henri.commons.util.CollectionUtil;
import seedu.henri.commons.util.ToStringBuilder;
import seedu.henri.logic.Messages;
import seedu.henri.logic.commands.exceptions.CommandException;
import seedu.henri.model.Model;
import seedu.henri.model.person.Address;
import seedu.henri.model.person.Email;
import seedu.henri.model.person.GitHubUsername;
import seedu.henri.model.person.Name;
import seedu.henri.model.person.Person;
import seedu.henri.model.person.Phone;
import seedu.henri.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the employee ID. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: EMPLOYEE_ID (must be in format Exxxx) "
            + "[" + PREFIX_NAME + " NAME] "
            + "[" + PREFIX_PHONE + " PHONE] "
            + "[" + PREFIX_EMAIL + " EMAIL] "
            + "[" + PREFIX_ADDRESS + " ADDRESS] "
            + "[" + PREFIX_GITHUB + " GITHUB_USERNAME]\n"
            + "Example: " + COMMAND_WORD + " E1234 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person with employee ID %1$s not found.";

    private final String employeeId;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param employeeId of the person to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(String employeeId, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(employeeId);
        requireNonNull(editPersonDescriptor);

        this.employeeId = employeeId;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToEdit = lastShownList.stream()
                .filter(person -> person.id().toString().equals(employeeId))
                .findFirst()
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, employeeId)));

        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.name());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.phone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.email());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.address());
        GitHubUsername updatedGitHubUsername =
                editPersonDescriptor.getGitHubUsername().orElse(personToEdit.gitHubUsername());
        Set<Tag> updatedTags = personToEdit.tags();

        return new Person(personToEdit.id(), updatedName, updatedPhone, updatedEmail,
                updatedAddress, updatedGitHubUsername, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return employeeId.equals(otherEditCommand.employeeId)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("employeeId", employeeId)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private GitHubUsername gitHubUsername;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setGitHubUsername(toCopy.gitHubUsername);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, gitHubUsername, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setGitHubUsername(GitHubUsername gitHubUsername) {
            this.gitHubUsername = gitHubUsername;
        }

        public Optional<GitHubUsername> getGitHubUsername() {
            return Optional.ofNullable(gitHubUsername);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(gitHubUsername, otherEditPersonDescriptor.gitHubUsername)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("gitHubUsername", gitHubUsername)
                    .add("tags", tags)
                    .toString();
        }
    }
}
