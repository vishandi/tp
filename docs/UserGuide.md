---
layout: page
title: User Guide
---

UniGenda is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI)
while still having the benefits of a Graphical User Interface (GUI). If you are an NUS student,
and you can type fast, UniGenda can get your contact management tasks done faster than traditional GUI apps.

# Table of Contents
1. [Quick Start](#quick-start)
2. [Features](#features)
   1. [Viewing help](#viewing-help--help)
   2. [Adding a person](#adding-a-person-add)
   3. [Listing all persons](#listing-all-persons--list)
   4. [Editing a person](#editing-a-person--edit)
   5. [Locating persons by name](#locating-persons-by-name-find)
   6. [Deleting a person](#deleting-a-person--delete)
   7. [Viewing a person's schedule](#viewing-a-persons-schedule-viewschedule)
   8. [Viewing persons by tags](#viewing-persons-by-tags-viewgroup)
   9. [Adding a person's schedule](#adding-a-persons-schedule-addevent)
   10. [Editing a person's schedule](#editing-a-persons-schedule-editevent)
   11. [Deleting a person's schedule](#deleting-a-persons-schedule-deleteevent)
   12. [Getting persons who are free](#getting-persons-who-are-free-whoisfree)
   13. [Importing a person's schedule](#importing-a-persons-schedule-importschedule)
   14. [Exporting a person's schedule](#exporting-a-persons-schedule-exportschedule)
   15. [Clearing all entries](#clearing-all-entries--clear)
   16. [Exiting the program](#exiting-the-program--exit)
   17. [Saving the data](#saving-the-data)
   18. [Editing the data file](#editing-the-data-file)
3. [Coming Soon](#coming-soon-v13)
   1. [Viewing Schedule](#viewing-a-persons-schedule-viewschedule)
   2. [Getting common free timing of persons by tag](#getting-common-free-timing-of-persons-by-tag-findcommontiming)
4. [FAQ](#faq)
5. [Command Summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Quick Start

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

## Features

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

* All leading and trailing whitespaces will be trimmed for each input to each prefix.

* All commands are case-insensitive. For example `addevent` or `AdDEvenT` works for `addEvent` commands.

</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a person: `add`
Adds a person to UniGenda without needing complete information about the person.

Format: `add n/NAME p/PHONE_NUMBER [tg/TELEGRAM] [gh/GITHUB] [e/EMAIL] [a/ADDRESS] [t/TAG]...`
* Duplicates of (Name, Phone Number) contacts will be detected; you cannot have two people with the same combination of (Name, Phone Number).
* `NAME` should only contain alphanumeric characters, not blank, and at most 30 characters long.
* `PHONE_NUMBER` should only contain numeric characters, at least 3 digits long, and at most 15 digits long.
* `TELEGRAM` should only contain alphanumeric characters and underscore (_), at least 5 characters long, and not exceeding 40 characters.
* `GITHUB` should only contain alphanumeric characters and hyphen (-), doesn't have multiple consecutive hyphens or begin or end with a hyphen, not blank, and at most 39 characters.
* `ADDRESS` should only contain alphanumeric characters and the following punctuations: **!"#$&'()\*+,-.:;<=>?@**. Its length should not exceed 80 characters.
* `EMAIL` should be a **valid** email with length not exceeding 60 characters.
* **Valid** `EMAIL` are emails that are of the format *local-part@domain* and adhere to the following constraints:
  * The local-part should only contain alphanumeric characters and the following special characteres: **+_.-**. It cannot start or end with any special characters.
  * The domain part is made up of domain labels separated by periods (e.g. domain-label1.domain-label2.com). It must end with a domain label at least 2 characters long, each domain label starts and end with alphanumeric characters, and each domain label consists of only alphanumeric characters, separated only by hyphens, if any.
* `TAG` should only contain alphanumeric characters, cannot contain whitespaces, not blank, and at most 30 characters long.
* Duplicate tags (tags with same tag name) will be ignored and only one will be taken.

Examples:
* add n/John Doe p/98765432
* add n/Betsy Crow t/friend p/1234567 a/Newgate Prison t/Criminal

### Listing all persons : `list`

Shows a list of all persons in UniGenda.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the UniGenda.

Format: `edit INDEX [n/NAME] [p/PHONE] [tg/TELEGRAM] [gh/GITHUB] [e/EMAIL] [a/ADDRESS] [t/TAG]...`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without specifying any tags after it.
* You can also remove telegram, github, email, or address by typing its corresponding prefix without specifying anything after it.
* You cannot edit a person's name and/or phone number such that the person's (Name, Phone Number) combination is the same as another person's (Name, Phone Number) combination in your UniGenda.
* `NAME` should only contain alphanumeric characters, not blank, and at most 30 characters long.
* `PHONE_NUMBER` should only contain numeric characters, at least 3 digits long, and at most 15 digits long.
* `TELEGRAM` should only contain alphanumeric characters and underscore (_), at least 5 characters long, and not exceeding 40 characters.
* `GITHUB` should only contain alphanumeric characters and hyphen (-), doesn't have multiple consecutive hyphens or begin or end with a hyphen, not blank, and at most 39 characters.
* `ADDRESS` should only contain alphanumeric characters and the following punctuations: **!"#$&'()\*+,-.:;<=>?@**. Its length should not exceed 80 characters.
* `EMAIL` should be a **valid** email with length not exceeding 60 characters.
* **Valid** `EMAIL` are emails that are of the format *local-part@domain* and adhere to the following constraints:
    * The local-part should only contain alphanumeric characters and the following special characteres: **+_.-**. It cannot start or end with any special characters.
    * The domain part is made up of domain labels separated by periods (e.g. domain-label1.domain-label2.com). It must end with a domain label at least 2 characters long, each domain label starts and end with alphanumeric characters, and each domain label consists of only alphanumeric characters, separated only by hyphens, if any.
* `TAG` should only contain alphanumeric characters, cannot contain whitespaces, not blank, and at most 30 characters long.
* Duplicate tags (tags with same tag name) will be ignored and only one will be taken.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`.
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`.
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`.
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`.

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from UniGenda.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​.

Examples:
* `list` followed by `delete 2` deletes the 2nd person in UniGenda
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command

### Setting a contact as the user : `setUser`

Sets the specified person as the user and shifts the contact to the top of the list.

Format: `setUser INDEX`

* Sets the person at the specified `INDEX` as the user.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​.

Examples:
* `list` followed by `setUser 2` sets the 2nd person in UniGenda as the user
* `find Betsy` followed by `setUser 1` sets the 1st person in the results of the `find` command as the user

### Viewing a person : `viewSchedule`

Views the specified person's Schedule from UniGenda. You can see the person's upcoming schedule for the following week starting **now**, and the list of the person's schedule.

Format: `viewSchedule INDEX`

* Views the person's schedule at the specified `INDEX`.
* The view will be displayed in the right panel of UniGenda.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​.
* **now** refers to the time that your system is currently in. 
* If you change your system's time, the Upcoming Schedule will **not** update automatically. You need to enter the command once again to view the updated Upcoming Schedule.
* If a certain Event has passed according to your system's time, it will **not** update automatically as well. You need to enter the command once again to view the updated Upcoming Schedule.

Examples:
* `list` followed by `viewSchedule 4` views the 4th person in UniGenda
  ![result for 'view 4'](images/viewResult.png)
* `find Betsy` followed by `viewSchedule 1` views the 1st person in the results of the `find` command

### Viewing persons by tags: `viewGroup`
Shows the names of friends with the same tag.

Format: `viewGroup t/TAG`

* Shows persons sharing the same tag.

Examples:
* `viewGroup t/groupmates`

### Adding a person’s schedule: `addEvent`
Adds an event to the specified indexed contact.

Format: `addEvent INDEX ed/EVENT_DESCRIPTION da/DATE [ti/TIME] [du/DURATION] [r/RECUR_FREQUENCY]`

* INDEX refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, ….
* `EVENT_DESCRIPTION` should not contain **/** and at most 60 characters.
* If TIME is not specified, it will be considered as a full-day event starting from 00:00.
* If TIME is specified but not DURATION, the DURATION will be defaulted to 2 hours.
* If DURATION is specified, TIME also needs to be specified.
* DATE should be a valid DATE in "YYYY-MM-DD" format, and **YYYY must be between 2000-2100**.
* TIME should be in "HH:MM" format.
* DURATION, if provided, should be in one of the following formats (not case-sensitive):

| Value  | Duration          | Example |
|--------|-------------------|---------|
| `HhMm` | H hours M minutes | `3h30m` |
| `Hh`   | H hours           | `2h`    |
| `Mm`   | M minutes         | `20m`   |
| `H`    | H hours           | `3`     |

* DURATION **must be less than 336hours (2 weeks) and at least 1 minute long**
* RECUR_FREQUENCY, if provided, must be one of the following values (not case-sensitive:

| Value            | Frequency |
|------------------|-----------|
| `D`, `DAILY`     | Daily     |
| `W`, `WEEKLY`    | Weekly    |
| `BW`, `BIWEEKLY` | Biweekly  |

* Note that RECUR_FREQUENCY is case-insensitive. For example, `r/dAiLy` will be considered as `r/DAILY`.

Examples:
* `addEvent 3 ed/Open House da/2022-12-20`
* `addEvent 2 ed/CCA Meeting da/2023-11-23 ti/12:00 du/1H30M r/W`
* `addEvent 1 ed/CS2103T Coding da/2023-10-23 ti/12:00 du/1H30M r/Daily`

### Editing a person’s schedule: `editEvent`
Edits the schedule assigned to a person.

Format: `editEvent INDEX EVENT_INDEX [ed/EVENT_DESCRIPTION] [da/DATE] [ti/TIME] [du/DURATION] [r/RECUR_FREQUENCY]`

* Edits an event assigned to a person.
* At least one of the optional fields must be provided.
* `EVENT_DESCRIPTION` should not contain **/** and at most 60 characters.
* DATE should be a valid DATE in "YYYY-MM-DD" format, and **YYYY must be between 2000-2100**.
* TIME should be in "HH:MM" format.
* DURATION should be in one of the following formats, where X and Y are positive integer values representing the hours and minutes respectively(not case-sensitive):

| Format | Duration          | Example |
|--------|-------------------|---------|
| `HhMm` | H hours M minutes | `3h30m` |
| `Hh`   | H hours           | `2h`    |
| `Mm`   | M minutes         | `20m`   |
| `H`    | H hours           | `3`     |

* DURATION **must be less than 336hours (2 weeks) and at least 1 minute long**
* RECUR_FREQUENCY, if provided, must be one of the following values:

| Value            | Frequency |
|------------------|-----------|
| `D`, `DAILY`     | Daily     |
| `W`, `WEEKLY`    | Weekly    |
| `BW`, `BIWEEKLY` | Biweekly  |

* Note that RECUR_FREQUENCY is case-insensitive. For example, `r/dAiLy` will be considered as `r/DAILY`.

Example:
* `editEvent 3 3 da/2022-12-21`
* `editEvent 3 1 ed/CS2103T tutorial da/2022-12-18 ti/14:00 du/2`
* `editEvent 3 1 ed/CS2103T lecture`

### Deleting a person's schedule: `deleteEvent`
Deletes an event from the specified indexed contact.

Format: `deleteEvent INDEX EVENT_INDEX`

* INDEX refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, ….
* EVENT_NUMBER refers to the index of schedules. The schedule_number **must be a positive integer** 1, 2, ….

Example:
* `deleteEvent 3 3`

### Getting persons who are free: `whoIsFree`
Shows a list of persons who are free at specified time and date. You may also choose to additionally filter the list using tags.

Format: `whoIsFree ti/TIME [da/ DATE] [t/TAG]`

* Shows the persons who are free at the time on the date specified.
* TIME is the time at which the user want to find out if the person is free.
* DATE should not be specified if TIME is not specified.
* TIME should be in "HH:MM" format.
* DATE should be in "YYYY-MM-DD" format.

Examples:
* `whoIsFree ti/12:00`
* `whoIsFree ti/14:00 da/2022-02-14`
* `whoIsFree ti/12:00 t/friends`

### Importing a person's schedule: `importSchedule`
Imports a schedule from a file to the person at the specified index.

Format: `importSchedule INDEX pa/FILE_PATH`

* INDEX refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, ….
* The file's data **must be in json format**, but the file itself may have any extension (e.g. .json, .txt etc.).
* The imported `Schedule` **cannot be empty**.
* `FILE_PATH` may be absolute or relative to the folder storing the UniGenda.jar file. More information of absolute and relative file paths can be found [here](https://www.educative.io/edpresso/absolute-vs-relative-path).

Examples:
`importSchedule 1 pa/typicalSchedule.json`

### Exporting a person's schedule: `exportSchedule`
Format: `exportSchedule INDEX`

* Allows user to export schedule of person at specified index.
* INDEX refers to the index number shown in the displayed person list. The index must be a positive integer 1, 2, …
* The exported data will be named `[NAME].json`, where `[NAME]` is the name of the person on index `INDEX`.
* The exported data will be saved in the `data/export/` folder in the folder that you save your `UniGenda.jar`.

Examples:
* `exportSchedule 1`

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

## Command Summary

| Action               | Format, Examples                                                                                                                                                                              |
|----------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**              | `add n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`                     |
| **Clear**            | `clear`                                                                                                                                                                                       |
| **Delete**           | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                                           |
| **Edit**             | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                                                   |
| **SetUser**          | `setUser INDEX`<br> e.g., `setUser 3`                                                                                                                                                         |
| **ViewGroup**        | `viewGroup t/tag`<br>e.g., `viewGroup t/groupmates`                                                                                                                                           |
| **ViewSchedule**     | `viewSchedule INDEX`<br>e.g., `viewSchedule 1`                                                                                                                                                |
| **AddEvent**         | `addEvent INDEX ed/EVENT_DESCRIPTION da/DATE [ti/TIME] [du/DURATION] [r/RECUR_FREQUENCY]` <br> e.g., `1 ed/CS2103T Tutorial da/2022-03-16 ti/10:00 du/1 r/WEEKLY`                             |
| **EditEvent**        | `editEvent INDEX EVENT_INDEX [ed/EVENT_DESCRIPTION] [da/DATE] [ti/TIME] [du/DURATION] [r/RECUR_FREQUENCY]` <br> e.g., `editEvent 3 1 ed/CS2103T tutorial da/18-12-2022 ti/1400 du/2 r/WEEKLY` |
| **DeleteEvent**      | `deleteEvent INDEX EVENT_NUMBER` <br> e.g., `deleteEvent 3 3`                                                                                                                                 |
| **WhoIsFree**        | `whoIsFree ti/TIME [da/DATE]`<br> e.g., `whoIsFree ti/10:00 da/2022-03-14`                                                                                                                    |
| **FindCommonTiming** | `findCommonTiming t/TAG`<br> e.g., `findCommonTiming t/groupmates`                                                                                                                            |
| **ImportSchedule**   | `importSchedule 1 pa/FILE_PATH`<br> e.g., `importSchedule 1 pa/typicalSchedule.json`                                                                                                          |
| **ExportSchedule**   | `exportSchedule INDEX`<br> e.g., `exportSchedule 1`                                                                                                                                           |
| **Find**             | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                                                    |
| **List**             | `list`                                                                                                                                                                                        |
| **Help**             | `help`                                                                                                                                                                                        |
