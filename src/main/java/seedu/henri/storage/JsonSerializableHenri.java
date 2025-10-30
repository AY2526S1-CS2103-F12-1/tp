package seedu.henri.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.henri.commons.exceptions.IllegalValueException;
import seedu.henri.model.Henri;
import seedu.henri.model.ReadOnlyHenri;
import seedu.henri.model.audit.AuditLogEntry;
import seedu.henri.model.person.Person;

/**
 * An Immutable Henri that is serializable to JSON format.
 */
@JsonRootName(value = "henri")
class JsonSerializableHenri {

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    @JsonProperty("auditLog")
    private final List<JsonAdaptedAuditLogEntry> auditLogEntries = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableHenri} with the given persons.
     */
    @JsonCreator
    public JsonSerializableHenri(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                 @JsonProperty("auditLog") List<JsonAdaptedAuditLogEntry> auditLogEntries) {
        this.persons.addAll(persons);
        if (auditLogEntries != null) {
            this.auditLogEntries.addAll(auditLogEntries);
        }
    }

    /**
     * Converts a given {@code ReadOnlyHenri} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableHenri}.
     */
    public JsonSerializableHenri(ReadOnlyHenri source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).toList());
        auditLogEntries.addAll(source.getAuditLog().getEntries().stream()
                .map(JsonAdaptedAuditLogEntry::new).toList());
    }

    /**
     * Converts this Henri into the model's {@code Henri} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Henri toModelType() throws IllegalValueException {
        Henri henri = new Henri();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            henri.addPerson(person);
        }
        for (JsonAdaptedAuditLogEntry jsonAdaptedEntry : auditLogEntries) {
            AuditLogEntry entry = jsonAdaptedEntry.toModelType();
            henri.getAuditLog().addEntry(entry.getAction(), entry.getDetails(), entry.getTimestamp());
        }
        return henri;
    }

}
