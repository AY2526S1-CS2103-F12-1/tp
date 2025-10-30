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
import seedu.henri.testutil.HenriBuilder;
import seedu.henri.testutil.TypicalPersons;

public class JsonSerializableHenriTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableHenriTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsHenri.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonHenri.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonHenri.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableHenri dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableHenri.class).get();
        Henri henriFromFile = dataFromFile.toModelType();
        Henri typicalPersonsHenri = TypicalPersons.getTypicalHenri();
        assertEquals(henriFromFile, typicalPersonsHenri);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableHenri dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableHenri.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsDuplicateException() throws Exception {
        JsonSerializableHenri dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableHenri.class).get();
        assertThrows(DuplicatePersonException.class, "Operation would result in duplicate persons",
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_validAuditLog_success() throws Exception {
        Henri original = TypicalPersons.getTypicalHenri();
        original.addAuditEntry("ADD", "Added person: Alice");
        original.addAuditEntry("EDIT", "Edited person: Bob");

        JsonSerializableHenri jsonBook = new JsonSerializableHenri(original);
        Henri converted = jsonBook.toModelType();
        AuditLog auditLog = converted.getAuditLog();

        assertEquals(2, auditLog.getEntries().size());
        assertEquals("ADD", auditLog.getEntries().get(0).getAction());
        assertEquals("Added person: Alice", auditLog.getEntries().get(0).getDetails());
    }

    @Test
    public void toModelType_emptyAuditLog_success() throws Exception {
        Henri original = TypicalPersons.getTypicalHenri();
        JsonSerializableHenri jsonBook = new JsonSerializableHenri(original);
        Henri converted = jsonBook.toModelType();
        AuditLog auditLog = converted.getAuditLog();

        assertNotNull(auditLog);
        assertTrue(auditLog.getEntries().isEmpty());
    }

    @Test
    public void toModelType_auditLogPersistence_success() throws Exception {
        Henri original = new HenriBuilder().withPerson(ALICE).withPerson(BENSON).build();
        original.addAuditEntry("TEST", "Test action");
        original.addAuditEntry("DELETE", "Deleted person");

        JsonSerializableHenri jsonBook = new JsonSerializableHenri(original);
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

        JsonSerializableHenri jsonBook = new JsonSerializableHenri(original);
        Henri converted = jsonBook.toModelType();

        assertEquals(3, converted.getAuditLog().getEntries().size());
        assertEquals("FIRST", converted.getAuditLog().getEntries().get(0).getAction());
        assertEquals("SECOND", converted.getAuditLog().getEntries().get(1).getAction());
        assertEquals("THIRD", converted.getAuditLog().getEntries().get(2).getAction());
    }
}
