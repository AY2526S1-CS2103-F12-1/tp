---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# Henri User Guide

Henri is a **desktop app for managing personal details of employees in tech companies, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). It helps HR personnel accelerate company processes via improved readability and contact organisation. If you can type fast, Henri can get your contact management tasks done faster than traditional GUI apps.

### Target User Profile
This product is designed for HR administrators from tech companies with many dynamic cross-functional project teams, who frequently manage contact information of developers from various departments and prefer using the keyboard over using a mouse.

<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103-F12-1/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your Henri installation.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar henri.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add -name John Doe -hp 98765432 -em johnd@example.com -addr John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete E1003` : Deletes the contact with the ID of "E1003" as shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add -name NAME`, `NAME` is a parameter which can be used as `add -name John Doe`.

* Items in square brackets are optional.<br>
  e.g `-name NAME [-gh GITHUB_USERNAME]` can be used as `-name John Doe -gh @johndoe123` or as `-name John Doe`.

* Command words are **case-sensitive**.

* Parameters can be in any order.<br>
  e.g. if the command specifies `-name NAME -hp PHONE_NUMBER`, `-hp PHONE_NUMBER -name NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit`, `audit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing Help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a Person: `add`

Adds a person to the address book.

Format: `add -name NAME -hp PHONE_NUMBER -em EMAIL -addr ADDRESS [-gh GITHUB_USERNAME]`

#### Required Parameters
1. `-name` - name of the person being added to the address book
   - Should only contain alphanumeric characters with a single space between words
   - Accepted special characters: hyphen (-), apostrophe ('), slash (/), period (.), comma (,), and at symbol (@)
   - Should be 1-50 characters long
2. `-hp`   - numerical phone number of the person
   - Should only contain digits
   - Should be 3-12 digits long
3. `-em`   - email address in the format 'user@domain'
   - user
     - Can only contain alphanumeric characters and the following special characters: `+`, `_`, `.`, `-`
     - Must start and end with an alphanumeric character
   - domain
     - Can contain one or more subdomains separated by periods (.)
     - Each subdomain can only contain alphanumeric characters and hyphens (`-`)
     - Each subdomain must start and end with an alphanumeric character
     - Must end with a top-level domain of minimum 2 characters
   - User and domain parts are separated by a single at symbol (`@`)
4. `-addr` - the address of the person in string format
   - Should be 1-150 characters long

#### Optional Parameters
1. `-gh` - the GitHub username string associated with the person
   - Should start with an at symbol (`@`)
   - Can only contain alphanumeric characters or hyphens (`-`)
   - Cannot begin or end with a hyphen (`-`)
   - Cannot have consecutive hyphens (`--`)
   - Should be 3-39 characters long (excluding the at symbol)

Examples:
* `add -name John Doe -hp 98765432 -em johnd@example.com -addr John street, block 123, #01-01`
* `add -name Betsy Crowe -em betsycrowe@example.com -addr Newgate Prison -hp 1234567`
* `add -name Mike Oxlong -hp 96767676 -em mikeo@exammple.com -addr 67 Downing St -gh @mikeoxlong`

<box type="tip" seamless>
**Tip:** To add tags to a person, use the [`tag`](#tag) command.
</box>

Note:
* Duplicate names are not allowed in the address book. If a person with the same name (case-insensitive) already exists, the command will fail with an error message.
* If by coincidence there are two persons with the same name, consider manually aliasing their names.

### Listing all Persons : `list`

Shows a list of all persons in the address book.

Format: `list`

Note:
* By default, the list is sorted by the order of contact entry into henri, _NOT_ by person ID.

### Editing a Person : `edit`

Edits an existing person in the address book.

Format: `edit EMPLOYEE_ID [-name NAME] [-hp PHONE] [-em EMAIL] [-addr ADDRESS] [-gh GITHUB_USERNAME]`

* Edits the person with the specified `EMPLOYEE_ID`. The employee ID starts with 'E" and is displayed next to the name in the displayed person list.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

#### Possible Parameters
1. `-name` - name of the person being added to the address book
    - Should only contain alphanumeric characters with a single space between words
    - Accepted special characters: hyphen (-), apostrophe ('), slash (/), period (.), comma (,), and at symbol (@)
    - Should be 1-50 characters long
2. `-hp`   - numerical phone number of the person
    - Should only contain digits
    - Should be 3-12 digits long
3. `-em`   - email address in the format 'user@domain'
    - user
        - Can only contain alphanumeric characters and the following special characters: `+`, `_`, `.`, `-`
        - Must start and end with an alphanumeric character
    - domain
        - Can contain one or more subdomains separated by periods (.)
        - Each subdomain can only contain alphanumeric characters and hyphens (`-`)
        - Each subdomain must start and end with an alphanumeric character
        - Must end with a top-level domain of minimum 2 characters
    - User and domain parts are separated by a single at symbol (`@`)
4. `-addr` - the address of the person in string format
    - Should be 1-150 characters long
5. `-gh` - the GitHub username string associated with the person
    - Should start with an at symbol (`@`)
    - Can only contain alphanumeric characters or hyphens (`-`)
    - Cannot begin or end with a hyphen (`-`)
    - Cannot have consecutive hyphens (`--`)
    - Should be 3-39 characters long (excluding the at symbol)

<box type="tip" seamless>

**Tip:** To remove the GitHub username of a person, use `-gh` with no username after the `-gh` prefix.
</box>

Examples:
*  `edit E1001 -hp 91234567 -em johndoe@example.com` Edits the phone number and email address of the person with the ID of "E1001" to be `91234567` and `johndoe@example.com` respectively.
*  `edit E1002 -name Betsy Crower` Edits the name of the employee with id "E1002" to be `Betsy Crower`.

Note:

* If the person list is filtered using the `view` command, employee IDs which are not in the current view will not be accepted. Consider using the `list` command to display all persons first.

### Adding a Tag: `tag`
Adds one or more tags to an existing person in the address book without removing existing tags.

Format: `tag EMPLOYEE_ID TAG [MORE_TAGS]…`

* Adds tags to the person identified by their `EMPLOYEE_ID`. 
* The employee ID must start with 'E' (e.g., E1001, E2050). 
* At least one tag must be provided. 
* Tags are added cumulatively - existing tags are preserved. 
* Multiple tags can be added at once by separating them with spaces. 
* Tags are case-insensitive - `Friends` and `friends` are treated as the same tag. 
* Tags must be alphanumeric and can contain hyphens between words (e.g., part-time, team-lead). 
* Tags must be between 1-20 characters in length. 
* Tags cannot contain whitespaces. 
* If you attempt to add duplicate tags (including case variations), only the new tags will be added and a warning will be shown.

Examples:
* `tag E1001 friends` Adds the tag "friends" to the employee with ID E1001.
* `tag E2050 colleagues mentor` Adds both "colleagues" and "mentor" tags to employee E2050.
* `tag E1001 team-lead part-time` Adds hyphenated tags to employee E1001.
* `tag E2050 Friends colleagues` If employee E2050 already has a "friends" tag (in any case variation), only "colleagues" will be added and a warning will show that "Friends" is a duplicate.

Note: 

* Tags are case-insensitive. Adding Boardgames to a person who already has boardgames will be detected as a duplicate and ignored.
* If the person list is filtered using the `view` command, employee IDs which are not in the current view will not be accepted. Consider using the `list` command to display all persons first.


Warning: 
* If all tags you attempt to add are duplicates, the command will return a message indicating no new tags were added.


### Removing a Tag: `untag`

Removes one or more tags from an existing person in the address book.

Format: `untag EMPLOYEE_ID TAG [MORE_TAGS]…`

* Removes tags from the person identified by their `EMPLOYEE_ID`. 
* The employee ID must start with 'E' (e.g., E1001, E2050). 
* At least one tag must be provided. 
* Tags are case-insensitive - `Friends` and `friends` are treated as the same tag. 
* Multiple tags can be removed at once by separating them with spaces. 
* The command will show which tags were successfully removed. 
* If some tags don't exist on the person, a warning will be shown indicating which tags were not found, but valid tags will still be removed. 
* If all specified tags don't exist on the person, an error will be shown and no tags will be removed.

Examples:
* `untag E1001 friends` Removes the tag "friends" from employee E1001.
* `untag E2050 colleagues mentor` Removes both "colleagues" and "mentor" tags from employee E2050.
* `untag E1001 team-lead` Removes the hyphenated tag "team-lead" from employee E1001.
* `untag E2050 Friends` Removes the tag "friends" (case-insensitive) from employee E2050, even if it was originally added as "friends".

Note: 
* Tag removal is case-insensitive. Removing Boardgames from a person who has boardgames will successfully remove that tag.

Warning: 
* If none of the specified tags exist on the person, the command will fail with an error message listing the non-existent tags. However, if at least one tag exists, valid tags will be removed and a warning will show which tags were not found.

### Creating a Team: `create-team`
Adds a team to the address book. All team names are automatically set to Upper Case format. 

![create-team](images/createTeam.png)

Format: `create-team TEAM_NAME TEAM_LEADER_ID`

* The team name must be an alphanumeric string containing 1 to 40 characters.
* The team name must not contain spaces.
* The team name is case-insensitive. e.g. `DevTeam` and `devteam` are considered the same team name.

Example:
* `create-team Systems E1001` creates a team named `Systems` with the person having employee ID `E1001` as the team leader.

Note:

* If the person list is filtered using the `view` command, employee IDs which are not in the current view will not be accepted. Consider using the `list` command to display all persons first.

### Adding Members to a Team: `add-to-team`
Adds a peron to an existing team in the address book.

![add-to-team](images/addToTeam.png)

Format: `add-to-team TEAM_ID MEMBER_ID`

* The team ID must start with a **capital** "T" followed by 4 digits. e.g. `T0001`, `T0234`.
* The member ID must start with a **capital** "E" followed by 4 digits. e.g. `E0001`, `E0234`.

Example:
* `add-to-team T0001 E1002` adds the person with employee ID `E1002` to the team with team ID `T0001`.

Exceptions:
* If the team ID or member ID does not exist, the command will fail with an error message.

### Removing Members from a Team: `remove-from-team`
Remove a person from an existing team in the address book.

![remove-from-team](images/removeFromTeam.png)

Format: `remove-from-team TEAM_ID MEMBER_ID`

* The team ID must start with a capital "T" followed by 4 digits. e.g. `T0001`, `T0234`.
* The member ID must start with a capital "E" followed by 4 digits. e.g. `E0001`, `E0234`.

Example:
* `remove-from-team T0001 E1002` removes the person with employee ID `E1002` from the team with team ID `T0001`.

Exceptions:
* If the member to be removed is the team leader, the command will fail with an error message.
* If the member to be removed is not part of the team, the command will fail with an error message.
* If the team ID or member ID does not exist, the command will fail with an error message.

Note:

* If the person list is filtered using the `view` command, employee IDs which are not in the current view will not be accepted. Consider using the `list` command to display all persons first.

### Set a Team as a Subteam of Another Team: `set-subteam`

Sets an already created team as a subteam of another already created parent team.
The parent team must not already contain the subteam as a subteam (directly or indirectly) to avoid cycles.
The subteam must also not already contain the parent team as a subteam (directly or indirectly) to avoid cycles.

![set-subteam](images/setSubteam.png)

Format: `set-subteam PARENT_TEAM_ID SUBTEAM_ID`

Exceptions:
- `No team with ID <TEAM_ID> found`:
  - This error message is shown when either the parent team ID or the subteam ID does not exist in the address book.
- `Team <TEAM_ID_A> cannot be a subteam of team <TEAM_ID_B>`:
  - If team TEAM_ID_A already exists as a subteam of another team.
  - If TEAM_ID_A is the same as TEAM_ID_B. 
  - If the team TEAM_ID_A exists as a parent team (directly or indirectly) of team TEAM_ID_B.
  - If the team TEAM_ID_B exists as a subteam (directly or indirectly) of team TEAM_ID_A.

### Deleting a Team: `delete-team`
Deletes an existing team from the address book.

![delete-team](images/deleteTeam.png)

Format: `delete-team TEAM_ID`

Example:
* `delete-team T0001` deletes the team with team ID `T0001`.

Exceptions:
* If the team ID does not exist, the command will fail with an error message.
* If the team to be deleted has subteams, the command will fail with an error message.

Format: `delete-team TEAM_ID`

### Locating Persons by Name: `view`

Finds persons whose names contain any of the given keywords.

![view](images/viewCommand.png)

Format: `view KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Partial matches are allowed. e.g. `ann` will match `Annabel` and `Joanna`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* Ordering of result is based on keyword. 
  * sorts filtered persons by:
    1) Number of matched keywords (more matches first)
    2) Closeness of match (exact > word > substring)
    3) Order of keyword appearance in the input
    4) Last by name in case-insensitive alphabetical order


Examples:
* `view John` returns `john` and `John Doe`
* `view alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a Person : `delete`

Deletes the specified person from the address book.

Format: `delete E1002`

* Deletes the person at the specified `EMPLOYEE_ID`.
* The index **must be a valid employee** in Henri.

Examples:
* `delete E1002` deletes the employee with id "E1002" in the address book.

Note:

* If the person list is filtered using the `view` command, employee IDs which are not in the current view will not be accepted. Consider using the `list` command to display all persons first.

### Setting a Person's Salary: `set-salary`

Sets the salary of the specified person in the address book.

Format: `set-salary EMPLOYEE_ID SALARY`

* Sets the salary of the person with the specified `EMPLOYEE_ID` to the specified `SALARY`.
* The employee ID starts with "E" and is displayed next to the name in the displayed person list.
* The salary must be a non-negative number representing the **monthly salary in dollars**.
* Take note that nagative zero is considered a valid input and it will be treated as zero.
* The salary may contain decimal places, but it will be rounded to two decimal places.

Examples:
* `set-salary E1001 1000` sets the salary of the person with the ID of "E1001" to *$1000.00 / month*.
* `set-salary E2050 3550.61` sets the salary of the person with the ID of "E2050" to *$3550.61 / month*.
* `set-salary E1001 4000.247` sets the salary of the person with the ID of "E1001" to *$4000.25 / month*.

### Sorting Persons: `sort`

Sorts the persons in the address book by the specified fields.

Format: `sort -FIELD [-MORE_FIELDS]`

* Sorts the persons in the address book by the specified fields.
* The fields are case-sensitive and must be from the following:
  * `name` - sorts by name in alphabetical order
  * `hp` - sorts by phone number in numerical order
  * `em` - sorts by email address in alphabetical order
  * `addr` - sorts by address in alphabetical order
  * `salary` - sorts by salary in numerical order
  * `gh` - sorts by GitHub username in alphabetical order
  * `id` - sorts by employee ID in numerical order
* The fields are case-insensitive.
* The fields can be specified multiple times, although this should not be necessary for any use case.
* The fields can be specified in any order. The order of the fields determines their priority in comparing two persons.
* The default sorting is by name in dictionary alphabetical order.
* The persons are always sorted in ascending order. Note that capital letters are considered "smaller" than their lowercase counterparts.

Examples:
* `sort -name -salary` sorts the persons in the address book by name in alphabetical order, then by salary in numerical order.
* `sort -salary -gh` sorts the persons in the address book by salary in numerical order, then by GitHub username in alphabetical order.
* `sort` sorts the persons in the address book by name in alphabetical order.

### Clearing All Entries : `clear`

Clears all entries from the address book.

Format: `clear`

Note:

* This command clears both the persons and teams from the address book.
* When the address book is cleared, the employee ID counter resets, but the team ID counter does not reset.

### Importing Contacts from the Data File : `import`

Imports the contact data of people from a specific JSON file from the data folder into the main address book.

Format: `import FILENAME.json`

* The file must be located inside the 'data' folder 
* The command must include the '.json' file extension 
* This command only imports employee contacts, with the assumption that the JSON file contains an array of employee objects in the correct format as it appears in the main henri.json
* JSON format from the file must be valid 
* Duplicate contacts with the same employee ID will not be imported

Example:
* `import oldContacts.json` imports non-duplicate contacts from 'data/oldContacts.json' into the address book.

Exceptions:
* If any imported contacts already exist in the henri app (same names), it will be skipped and the skipped IDs will be displayed with the command result.
* If there are any specific errors in the data file "An error was found in the JSON data you are trying to import: " followed by the specific error message will be displayed and no data will be imported.

Note:
* The import command is only meant to import the data of the contacts of people, not teams. Teams must be created manually after importing contacts.
* Ensure that the name and ID fields of the imported contacts do not conflict with existing contacts in the address book to avoid duplicates.
* For people without a GitHub username, the `gitHubUsername` field can be an empty string.
* For people without any tags, the `tags` array can be empty.

JSON Format Example
- Please ensure that the JSON file closely follows the structure below for successful import:
```
{
  "persons" : [ {
    "id" : "E1001",
    "name" : "Alex Yeoh",
    "phone" : "87438807",
    "email" : "alexyeoh@example.com",
    "address" : "Blk 30 Geylang Street 29, #06-40",
    "gitHubUsername" : "@alexxx",
    "salary" : "1000.0",
    "tags" : [ "friends" ]
  }, {
    "id" : "E1002",
    "name" : "Bernice Yu",
    "phone" : "99272758",
    "email" : "berniceyu@example.com",
    "address" : "Blk 30 Lorong 3 Serangoon Gardens, #07-18",
    "gitHubUsername" : "",
    "salary" : "2000.0",
    "tags" : [ ]
  } ]
}
```

### Viewing of the Audit Logs : `audit`

Displays a log of all past actions that have modified the address book data.

Format: `audit`

* Shows a chronological list of all commands that have changed the address book state.
* Each entry includes:
  * The action type (e.g., ADD, DELETE, EDIT, CLEAR)
  * Details of what was changed 
  * Timestamp of when the action occurred
* Read-only commands (like list, view, help, exit) are not logged. 
* If no actions have been performed, displays "No audit log entries found."

Examples:
* `audit` displays all logged actions in chronological order.

Note: 
* The audit log persists between sessions and survives application restarts. Only commands that modify data are recorded to keep the log meaningful and concise.

### Exiting the Program : `exit`

Exits the program.

Format: `exit`

### Saving the Data

Henri data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the Data File

Henri data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, Henri will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause Henri to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Henri home folder.

--------------------------------------------------------------------------------------------------------------------

## Known Issues / Limitations

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.
3. **`add-to-team` command might be long** we acknowledge that the command word might be long however for clarity reasons we decided to keep as such.
4. **`remove-from-team` command might be long** we acknowledge that the command word might be long however for clarity reasons we decided to keep as such.
5. **`add` command allows for duplicate phone number, email address, home address and GitHub username** as it is possible that multiple employees in the company share the same phone number (department phone number), home address (living together), email address (shared email address by department), GitHub username (shared GitHub account for open source projects). Hence, we allow duplicate entries for these fields.
6. **`create-team` command does not check for duplicate team names** as it is possible that multiple teams in the company share the same name (e.g. multiple "DevTeam" in different departments). Hence, we allow duplicate team names.
7. **`import` command only supports adding of employees** currently which does not include teams and subteams.
8. **Subteams are not exported in the data file** currently, only teams and their members are exported.

--------------------------------------------------------------------------------------------------------------------
## Future Enhancements
1. **Customisable command words to fit user preferences**
2. **`import` command enhancements to support team import**
3. **`import` command enhancements to have more input validation**
4. **Standardise phone number format for duplicate detection**
5. **Allow storage of subteam relationships in data file**


## Command Summary

| Action               | Format, Examples                                                                                                                                                                                  |
|----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**              | `add -name NAME -hp PHONE_NUMBER -em EMAIL -addr ADDRESS [-gh GITHUB_USERNAME]` <br> e.g., `add -name James Ho -hp 22224444 -em jamesho@example.com -addr 123, Clementi Rd, 1234665 -gh @jamesho` |
| **Add to Team**      | `add-to-team TEAM_ID MEMBER_ID`<br> e.g., `add-to-team T0001 E1002`                                                                                                                               |
| **Audit**            | `audit`                                                                                                                                                                                           |
| **Clear**            | `clear`                                                                                                                                                                                           |
| **Create Team**      | `create-team TEAM_NAME TEAM_LEADER_ID`<br> e.g., `create-team DevTeam E1001`                                                                                                                      |
| **Delete**           | `delete EMPLOYEE_ID`<br> e.g., `delete E1003`                                                                                                                                                     |
| **Delete Team**      | `delete-team TEAM_ID`<br> e.g., `delete-team T0001`                                                                                                                                               |
| **Edit**             | `edit EMPLOYEE_ID [-name NAME] [-hp PHONE] [-em EMAIL] [-addr ADDRESS] [-gh GITHUB_USERNAME]`<br> e.g., `edit E1001 -hp 91234567 -em johndoe@example.com`                                         |
| **Exit**             | `exit`                                                                                                                                                                                            |
| **Help**             | `help`                                                                                                                                                                                            |
| **Import**           | `import FILENAME.json`<br> e.g., `import oldContacts.json`                                                                                                                                        |
| **List**             | `list`                                                                                                                                                                                            |
| **Remove from Team** | `remove-from-team TEAM_ID MEMBER_ID`<br> e.g., `remove-from-team T0001 E1002`                                                                                                                     |
| **Set Salary**       | `set-salary EMPLOYEE_ID SALARY`<br> e.g., `set-salary E1001 3000.50`                                                                                                                              |
| **Set Subteam**      | `set-subteam PARENT_TEAM_ID SUBTEAM_ID`<br> e.g., `set-subteam T0001 T0002`                                                                                                                       |
| **Sort**             | `sort -FIELD [-MORE_FIELDS]`<br> e.g., `sort -name -salary`                                                                                                                                       |
| **Tag**              | `tag EMPLOYEE_ID TAG [MORE_TAGS]…`<br> e.g., `tag E1003 cs2103-f12`                                                                                                                               |
| **Untag**            | `untag EMPLOYEE_ID TAG [MORE_TAGS]…` <br> e.g., `untag E1003 ay2425`                                                                                                                              |
| **View**             | `view KEYWORD [MORE_KEYWORDS]`<br> e.g., `view James Jake`                                                                                                                                        |
