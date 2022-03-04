---
layout: page
title: User Guide
---

UniGenda is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI)
while still having the benefits of a Graphical User Interface (GUI). If you are an NUS student,
and you can type fast, UniGenda can get your contact management tasks done faster than traditional GUI apps.


* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `UniGenda.jar` from [here](https://github.com/AY2122S2-CS2103T-W09-1/tp/releases)*.

1. Copy the file to the folder you want to use as the _home folder_ for your UniGenda.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`list`** : Lists all contacts.

   * **`add`**`n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to UniGenda.

   * **`delete`**`3` : Deletes the 3rd contact shown in the current list.

   * **`addEvent`**`1 ed/CS2103T Tutorial da/2022-03-16 ti/10:00 du/1` : Adds a CS2103T Tutorial event to the first contact.

   * **`exit`** : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

\* Will be released soon! Stay tuned!

--------------------------------------------------------------------------------------------------------------------

## New/Modified Features (Coming in V1.2)

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a person: `add`
Adds a person to UniGenda without needing complete information about the person.

Format: `add n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [ig/INSTAGRAM_ID] [tele/TELEGRAM_HANDLE] [t/TAG]`
* Duplicates of (Name, Phone Number) contacts will be detected; you cannot have two people with the same combination of (Name, Phone Number).

Examples:
* add n/John Doe p/98765432
* add n/Betsy Crow t/friend p/1234567 a/Newgate Prison t/Criminal

### Listing all persons : `list`

Shows a list of all persons in UniGenda.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the UniGenda.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from UniGenda.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in UniGenda.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Deleting a tag: `deleteTag`
Deletes a tag that a contact has.

Format: `deleteTag INDEX t/TAGTOBEDELETED`
* Edits the person’s tag at the specified INDEX. The INDEX refers to the index number shown in the displayed person list. The index must be a positive integer 1, 2, …
* TAGTOBEDELETED is case-insensitive. Eg. criminal will match Criminal
* Only fully-named-tag will be matched. Eg. crim will not be matched with Criminal
* TAGTOBEDELETED must be one of the tags that the person at index INDEX has.

Examples:
* `deleteTag 2 t/Criminal`

### Adding a person’s schedule: `addEvent`
Adds an event to the specified indexed contact.

Format: `addEvent INDEX ed/EVENT_DESCRIPTION da/DATE [ti/TIME] [du/DURATION] [t/TAG]`
* INDEX refers to the index number shown in the displayed person list. The index must be a positive integer 1, 2, …
* If TIME is not specified, it will be considered as a full-day event.
* If DURATION is specified, TIME also needs to be specified.
* If TIME is specified but not DURATION, the DURATION will be defaulted to 2 hours.
* DURATION should be in one of the following formats, where X and Y are integer values representing the hours and minutes respectively(not case-sensitive):
  * XHYM
  * XHY
  * XH
  * X


Example:
* `addSchedule 3 da/20-12-2022 ti/1000 du/2`

### Editing a person’s schedule: `editEvent`
Edits the schedule assigned to a person.

Format: `editEvent INDEX EVENT_INDEX [ed/EVENT_DESCRIPTION] [da/DATE] [ti/TIME] [du/DURATION] [t/TAG]`
* Edits an event assigned to a person.
* At least one of the optional fields must be provided

Example:
* `editEvent 3 3 da/21-12-2022`
* `editEvent 3 1 ed/CS2103T tutorial da/18-12-2022 ti/1400 du/2`
* `editEvent 3 1 ed/CS2103T lecture`

### Deleting a person's schedule: `deleteEvent`
Deletes an event from the specified indexed contact.

Format: `deleteEvent INDEX EVENT_NUMBER`
* INDEX refers to the index number shown in the displayed person list. The index must be a positive integer 1, 2, …
* EVENT_NUMBER refers to the index of schedules. The schedule_number must be a positive integer 1, 2, …

Example:
* `deleteEvent 3 3`

### Viewing a person’s schedule: `viewSchedule`
Shows the schedule of a specified person.

Format: `viewSchedule INDEX`
* Shows the schedule of a specific person at INDEX
* The index refers to the index number shown in the displayed person list.
* The index must be positive. Eg. 1, 2, 3…

Example:
* `viewSchedule 5`

### Get friends who are free: `freeSchedule`
Retrieves information of friends who are free at the specified time or date.

Format: `freeSchedule ti/TIME [da/ DATE]`
* Shows the friends who are free at the time specified today
* Shows the friends who are free at the time on the date specified
* TIME is the time at which the user want to find out if the person is free
* TIME should be specified in 24h format
* DATE should not be specified if TIME is not specified

Examples:
* `freeSchedule ti/ 1200`
* `freeSchedule ti/ 1400 da/14-02-2021`

### Get team’s common free timing: `freeGroupSchedule`
Gets the common timing of a group of friends is free (by tag)

Format: `freeGroupSchedule t/TAG`
* Show the overlapping timings that a group of friends with the same tags are free

### View contacts by tags: `viewGroup`
Shows the names of friends with the same tag

Format: `viewGroup t/TAG`
* Shows contacts sharing the same tag

### Clearing all entries : `clear`

Clears all entries from UniGenda.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

UniGenda data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

UniGenda data are saved as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, UniGenda
will discard all data and start with an empty data file at the next run.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous UniGenda home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**AddEvent** | `addEvent INDEX ed/EVENT_DESCRIPTION da/DATE [ti/TIME] [du/DURATION] [t/TAG]` <br> e.g., `1 ed/CS2103T Tutorial da/2022-03-16 ti/10:00 du/1`
**EditEvent** | `editEvent INDEX EVENT_INDEX [ed/EVENT_DESCRIPTION] [da/DATE] [ti/TIME] [du/DURATION] [t/TAG]` <br> e.g., * `editEvent 3 1 ed/CS2103T tutorial da/18-12-2022 ti/1400 du/2`
**DeleteEvent** | `deleteEvent INDEX EVENT_NUMBER` <br> e.g., `deleteEvent 3 3`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
