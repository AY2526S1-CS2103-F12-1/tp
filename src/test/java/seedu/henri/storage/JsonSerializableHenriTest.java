package seedu.henri.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.henri.testutil.Assert.assertThrows;
import static seedu.henri.testutil.TypicalPersons.ALICE;
import static seedu.henri.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.henri.commons.exceptions.IllegalValueException;
import seedu.henri.commons.util.JsonUtil;
import seedu.henri.model.Henri;
import seedu.henri.model.audit.AuditLog;
import seedu.henri.model.person.exceptions.DuplicatePersonException;
import seedu.henri.testutil.AddressBookBuilder;
import seedu.henri.testutil.TypicalPersons;

public class JsonSerializableHenriTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        Henri henriFromFile = dataFromFile.toModelType();
        Henri typicalPersonsHenri = TypicalPersons.getTypicalAddressBook();
        assertEquals(henriFromFile, typicalPersonsHenri);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsDuplicateException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(DuplicatePersonException.class, "Operation would result in duplicate persons",
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_validAuditLog_success() throws Exception {
        Henri original = TypicalPersons.getTypicalAddressBook();
        original.addAuditEntry("ADD", "Added person: Alice");
        original.addAuditEntry("EDIT", "Edited person: Bob");

        JsonSerializableAddressBook jsonBook = new JsonSerializableAddressBook(original);
        Henri converted = jsonBook.toModelType();
        AuditLog auditLog = converted.getAuditLog();

        assertEquals(2, auditLog.getEntries().size());
        assertEquals("ADD", auditLog.getEntries().get(0).getAction());
        assertEquals("Added person: Alice", auditLog.getEntries().get(0).getDetails());
    }

    @Test
    public void toModelType_emptyAuditLog_success() throws Exception {
        Henri original = TypicalPersons.getTypicalAddressBook();
        JsonSerializableAddressBook jsonBook = new JsonSerializableAddressBook(original);
        Henri converted = jsonBook.toModelType();
        AuditLog auditLog = converted.getAuditLog();

        assertNotNull(auditLog);
        assertTrue(auditLog.getEntries().isEmpty());
    }

    @Test
    public void toModelType_auditLogPersistence_success() throws Exception {
        Henri original = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        original.addAuditEntry("TEST", "Test action");
        original.addAuditEntry("DELETE", "Deleted person");

        JsonSerializableAddressBook jsonBook = new JsonSerializableAddressBook(original);
        Henri converted = jsonBook.toModelType();

        assertEquals(2, converted.getAuditLog().getEntries().size());
        assertEquals("TEST", converted.getAuditLog().getEntries().get(0).getAction());
        assertEquals("Test action", converted.getAuditLog().getEntries().get(0).getDetails());
    }

    @Test
    public void toModelType_multipleAuditEntries_correctOrder() throws Exception {
        Henri original = new Henri();
        original.addAuditEntry("FIRST", "First entry");
        original.addAuditEntry("SECOND", "Second entry");
        original.addAuditEntry("THIRD", "Third entry");

        JsonSerializableAddressBook jsonBook = new JsonSerializableAddressBook(original);
        Henri converted = jsonBook.toModelType();

        assertEquals(3, converted.getAuditLog().getEntries().size());
        assertEquals("FIRST", converted.getAuditLog().getEntries().get(0).getAction());
        assertEquals("SECOND", converted.getAuditLog().getEntries().get(1).getAction());
        assertEquals("THIRD", converted.getAuditLog().getEntries().get(2).getAction());
    }
}
