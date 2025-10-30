# SGSafe User Guide

SGSafe is a *Command Line Interface (CLI) based case management system* that is designed specifically for law
enforcement agencies in Singapore to manage, track and process cases. Built with the diverse needs of police personnel
in mind, this application allows frontline officers to efficiently manage and process cases from creation to closure. By
providing an easy-to-use yet efficient interface, SGSafe transforms traditional case management processes into an
organised digital workflow that enhances operational efficiency for the public sector.

## Table of Contents

- [Quick Start](#quick-start)
- [Case Categories](#case-categories)
- [Features](#features)
    - [Adding a case: `add`](#adding-a-case-add)
    - [Listing cases: `list`](#listing-cases-list)
    - [Closing a case: `close`](#closing-a-case-close)
    - [Opening a case: `open`](#opening-a-case-open)
    - [Updating a case: `edit`](#editing-a-case-edit)
    - [Deleting a case: `delete`](#deleting-a-case-delete)
    - [Exiting the program: `bye`](#exiting-the-program-bye)
    - [Settings: `setting`](#settings-setting)
    - [File storage](#file-storage)
- [FAQ](#faq)
- [Command Summary](#command-summary)
- [Coming Soon](#coming-soon)

## Quick Start

1. Ensure that you have Java 17 installed on your computer.
2. Download the latest version of `SGSafe` from
   the [releases page](https://github.com/AY2526S1-CS2113-W13-3/tp/releases).
3. Copy the file to the folder you want to use as the home folder for SGSafe.
4. Open a command terminal, navigate to the folder containing the JAR file, and run `java -jar SGSafe.jar` to start the
   application.
5. Type commands in the command line and press Enter to execute them. Refer to the [Command Summary](#command-summary)
   section
   below for a quick overview of available commands.

---

## Case Categories

> **Notes:**
>
> * The category tag for the add command should be from the list below. If the category is not in the list, type '
    others' as the category.
> * Some categories have additional tags (that allow more types of information) attributed to them, which can be edited
    using the `edit` feature.
    Additional tags, if any, are indicated in brackets. Please note that you cannot use the `add` command to set these
    additional tags; they can only be modified using the `edit` command.
> * Some of these additional tags are not shown in standard list command and can only be viewed using the `read` command or in
    verbose mode of the `list` command.
> * For more information on how to add and edit categories,
    > refer to [`add`](#adding-a-case-add) and [`edit`](#editing-a-case-edit)

**`CATEGORY` tags:**

* Burglary
* Scam
* Theft (Stolen object)
* Arson
* Property
* Vandalism
* Rape
* Voyeurism
* Accident (Vehicle type, Vehicle plate, Road name)
* Speeding (Vehicle type, Vehicle plate, Road name, Speed limit, Exceeded speed)
* Assault
* Murder (Weapon, Number of victims)
* Robbery
* Others (Custom category)

---

## Features

> **Notes about the command format:**
>
> * Words in `UPPER_CASE` are the parameters to be supplied by the user.\
    >   e.g., in `add --title TITLE`, `TITLE` is a parameter which can be used as `add --title Murder at Yishun`.
>
> * Parameters with command line flags can be in any order.\
    >   e.g., if the command specifies `--title TITLE --date DATE`, `--date DATE --title TITLE` is also acceptable.
>
> * Parameters that are not preceded by command line flags must appear in the order specified.\
    >   e.g., if the command specifies `close INDEX` INDEX must come after close.
>
> * Optional parameters are enclosed in square brackets.\
    >   e.g., `--title TITLE [--victim VICTIM]` means the victim parameter is optional.
> * The escape character for `--` is `\--`. 
> For example: `add --category murder --title hello\--world --info hello --date 05/02/2022` will result in the title of be case being `hello--world`
> * You cannot use the character | in your input as it is used in the save file format.
---

### Adding a case: `add`

Adds a new case to the case management system.

**Format:** `add --category CATEGORY --title TITLE --date DATE --info INFO [--victim VICTIM] [--officer OFFICER]`

* `CATEGORY`: The category of the case
* `TITLE`: The title or summary of the case
* `DATE`: The date the case was recorded or occurred
* `INFO`: Additional information or notes about the case
* `VICTIM`: (Optional) The name(s) of the victim involved
* `OFFICER`: (Optional) The name(s) of the officer assigned

> ℹ️ Note: The above are stored as strings (except date). No special formatting is required for those inputs.\
> ℹ️ Note: For accepted CATEGORY tags, refer to [Case Categories](#case-categories).\
> ℹ️ Note: Date is stored as a Java LocalDate. The default input format is `dd/MM/yyyy`. You may wish to change it using
> the settings command below.\
> ⚠️ Warning: A maximum of 5000 characters is allowed for all the fields.

**Examples:**

* `add --category Theft --title Theft case --date 15/10/2024 --info Stolen wallet --victim John Doe --officer Officer Smith`

* `add --category Burglary --info Burglary at 123 Main St --date 20/02/2024 --title Burglary case`

---

### Listing Cases: `list`

Displays all cases in the system, with optional filters and formatting modes.

#### **Format:** `list --status <open|closed|all> --mode <summary|verbose>`

#### Flags

- `--status` (optional): Filters cases by their status.
    - `open`: Show only open cases.
    - `closed`: Show only closed cases.
    - `all`: Show all cases (default).
- `--mode` (optional): Controls the level of detail in the output.
    - `summary`: One-line display per case.
    - `verbose`: Multi-line display with labeled fields.

#### Summary Mode Output

Each case is shown in a single line with:

- Status: `[Open]` or `[Closed]`
- Category (e.g., `Theft`, `Scam`)
- Case ID (6-character hexadecimal)
- Date
- Title

Example:

```
You currently have 3 cases in total
---
Note: Only very basic case details are shown here.
For more in depth information about the case (e.g. Info, Victim, Officer)
run: list --mode verbose
---
STATUS   CATEGORY         ID     DATE       TITLE
[Open]   Theft            0001a3 14/10/2025 Robbery
[Closed] Scam             0001a4 15/10/2025 Fraud
[Closed] Traffic accident 0001a5 15/10/2025 Fraud
```

#### Verbose Mode Output

Each case is shown in multiple lines with:

- Status
- Category (e.g., `Theft`, `Scam`)
- Case ID
- Date
- Title
- Info
- The time the case was created
- The last updated time of the case
- Victim (if available)
- Officer (if available)

Example:

```
You currently have 1 case in total
---
Note: Only basic case details (e.g. Title) are shown here and is truncated if too long.
For full case information (e.g. case-specific details like murder weapon),
use the read command
To see how to use the read command, run: help read
To use the read command, run: read <caseID>
---
======== CASE ID 000000 ========
Status     : Open
Category   : Murder
Title      : TITLE
Date       : 22/04/2023
Info       : Masked suspect entered victim's bedroom
Created at : 2025-10-27T18:28:25.170780500
Updated at : 2025-10-27T18:28:25.170780500
Victim     : Jane Doe
Officer    : Officer John Lee
```

> Note:
> - Deleted cases are excluded from all listings.
> - More specific case information (e.g. murder weapon) can only be accessed from read. This is an intentional design
    choice as we do not want to clutter the list command with unnecessary details.


---

### Closing a case: `close`

Marks a case as closed.

**Format:** `close ID`

* Closes the case with the specified `ID`.
* The id refers to the id of the case itself.
* The id **must be exactly 6 hexadecimal digits** 000001, 000fab, 00beef, … and the case must exist.

**Examples:**

* `close 000003` closes the case with the id 000003 in the list.

---

### Opening a case: `open`

Marks a case as open.

**Format:** `open ID`

* Reopens the case with the specified `ID`.
* The id refers to the id of the case itself.
* The id **must be exactly 6 hexadecimal digits** 000001, 000fab, 00beef, … and the case must exist.

**Examples:**

* `open 000003` opens the case with the id 000003 in the list.

---

### Editing a case: `edit`

Updates the details of an existing case.

**Format:** `edit ID [--title TITLE] [--date DATE] [--info INFO] [--victim VICTIM] [--officer OFFICER] ...`

* The id **must be exactly 6 hexadecimal digits** 000001, 000fab, 00beef, … and the case must exist.
* Editing the case requires and one or more valid flags and their new values.
* If no flags are provided, the valid editable fields for that case type will be shown instead.
* `...` above refers to additional tags that may be available for certain categories. For more information on these additional
  tags, refer to [Case Categories](#case-categories).

**Examples:**

1. `edit 000001` : displays a list of all editable fields for the case.

Example output:
 ```
  Case found: [Open]   Traffic accident 000001 05/06/2018 Car accident

  Fields that can be edited: --title, --date, --info, --victim, --officer, --vehicle-type, --vehicle-plate, --road-name
```

2. `edit 000001 --title Drunk Driving --road-name Adam Road --date 03/04/2025` : edits the title, road name, and date of the case

Example output:
 ```
  Case edited:
  [Open]   Traffic accident 000001 03/04/2025 Drunk Driving
```

> ℹ️ Note: Not all edited fields will show up in the return message after a successful edit. To view the updated case in full detail, use the `read` command.\
> ℹ️ Note: A closed case cannot be edited. To edit the case, reopen the case using [`open`](#opening-a-case-open) command.\
> ℹ️ Note: The above are stored as strings (except date). No special formatting is required for those inputs.\
> ℹ️ Note: Date is stored as a Java LocalDate. The default input format is `dd/MM/yyyy`. You may wish to change it using
> the settings command below.\
> ⚠️ Warning: A maximum of 5000 characters is allowed for all the fields.

---

### Deleting a case: `delete`

**Format:** `delete ID`

* Deletes the case with the specified ID.
* The id refers to the id of the case itself.
* The id **must be exactly 6 hexadecimal digits** 000001, 000fab, 00beef, … and the case must exist.

**Examples:**

* `delete 00012a` deletes the case with the id 00012a in the list.

---

### Exiting the program: `bye`

Exits the program.

**Format:** `bye`

---

### Settings: `setting`

This is a function to perform user-defined setting for the program. User can set the date input format and output
format.

**Format:** `setting --type TYPE --value VALUE`
> ℹ️ Note: Type can only be `dateinput` representing the input format and `dateoutput` representing the format where the
> date will be printed and `timestampotuput` representing the format where the date time (for updatedAt and createdAt)
> will be printed when the user uses verbose printing or read command.\
> ⚠️ Warning: The value must be a valid date format, according to Java's DateFormatter. Stray characters that are not
> date and time-related will flag as an error.
> For more information, please refer
> to [Java DateTimeFormatter](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/format/DateTimeFormatter.html).
> ⚠️ Warning: Note that month is capitalised in the date-time-formatter. `MM` represents month while `mm` represents
> minutes.
> The default input and output format is `dd/MM/yyyy` and the default timestamp output format is `dd/MM/yyyy HH:mm:ss`.
> ⚠️ Warning: If the input format has repeated characters (e.g., `dd-MM-yyyy-dd`), the user is expected to key in the
> same day for both `dd`.

**Examples:**

* `setting --type dateinput --value dd-MM-yyyy` means that all inputs for date must follow dd-MM-yyyy format to be
  considered valid.
* `setting --type dateoutput --value dd/MM/yyyy` means that all output for date will be printed in dd/MM/yyyy format.
* `setting --type datetimeoutput --value dd/MM/yyyy HH:mm:ss` means that all output for timestamp will be
  printed in dd/MM/yyyy HH:mm:ss format.

---

### Viewing the help menu: `help`

Displays a list of all available commands along with their descriptions and usage examples.

**Format:** `help`

* Shows a complete help menu of supported commands.
* Use this if you're unsure how to use a command or want a refresher.
* Can be run anytime

---

### Reading a case: `read`

Displays the full details of a specific case, including any category-specific fields. Fields that are not filled by the user will be shown as empty.

**Format:** `read ID`

* The id **must be exactly 6 hexadecimal digits** 000001, 000fab, 00beef, … and the case must exist.

**Example:**

Input: `read 000001`

Example output:
```
  Title             : Murder at Yishun
  Case ID           : 000000
  Status            : Closed
  Category          : Murder
  Date              : 23/06/2020
  Victim            : Lim Ying
  Officer           : Tony
  Created at        : 2025-10-30T16:37:05
  Updated at        : 2025-10-30T16:37:06
  Weapon            : 
  Number of Victims : 
  Info              : Yishun Ring Road
```
---

### File storage

This is a feature that saves your case info to `data.txt` in the folder that you run the program in.
There is no specific command that will execute this function,
but it will automatically run every time you run any command.

#### Save file modification (only for advanced users)

_Beware that you may corrupt your data if you modify any data incorrectly, do this at your own risk!_

The data in the save file is stored in this format:

> `key1:value1|key2:value2|key3:value3|...`

each key corresponds to a field, and each line corresponds to a case. An empty value means that the field has not been
initialised.

Fields that you SHOULD NOT EDIT:

1. id
2. category
3. created-at

You cannot add new cases through editing the file, do not add new lines to the save file.

The date field must be stored in the format dd/MM/yyyy (day/month/year).

The modified-at field must be stored in the format dd/MM/yyyy hh:mm:ss (day/month/year hour:minute:second).

***Examples***

- To change the victim in a case from `alice` to `bob`, modify the line in `data.txt`
  from `...|victim:alice|...` to `...|victim:bob|...`.
- To change the date in a case from fifth of november 2024 (05/11/2025) to second of january 2025 (02/01/2025),
  modify the line corresponding to the case that you want to edit in `data.txt` from `...|date:05/11/2025|...`
  to `...|date:02/01/2025|...`.

---

## FAQ

**Q**: How do I transfer my data to another computer?
**A**: You can copy the `data.txt` file from the current working directory of the source computer to the directory
containing the .jar file of the destination computer.

**Q**: Is it recommended that I edit the `data.txt` file directly?
**A**: It is not recommended as you may corrupt your data if you modify any data incorrectly. You should only edit the
file if you are an advanced user and know what you are doing.

**Q**: What happens if a case is marked as closed?
**A**: Closed cases are still visible in the list but are marked with `[C]` instead of `[O]`.

**Q**: Can I edit a closed case?
**A**: No, closed cases cannot be edited. You will need to reopen the case using the `open` command before you can edit
it.

**Q**: What date formats are supported?
**A**: By default, SGSafe supports the `dd/MM/yyyy` format for date input and output. You can change the date input
and output formats using the `setting` command. The supported formats are based on Java's DateTimeFormatter.

**Q**: Can I edit the data.txt file as the program is running?
**A**: No, please do not edit or delete the data.txt file while the program is running as it may lead to data corruption.

---

## Command Summary

| Action          | Format                                                                                                | Example                                                                                                                    |
|-----------------|-------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------|
| **Add case**    | `add --category CATEGORY --title TITLE --date DATE --info INFO [--victim VICTIM] [--officer OFFICER]` | `add --category Theft --title Theft case --date 2024-01-15 --info Stolen wallet --victim John Doe --officer Officer Smith` |
| **List cases**  | `list`                                                                                                | `list`                                                                                                                     |
| **Close case**  | `close ID`                                                                                            | `close 000003`                                                                                                             |
| **Open case**   | `open ID`                                                                                             | `open 000003`                                                                                                              |
| **Edit case**   | `edit ID [--title TITLE] [--date DATE] [--info INFO] [--victim VICTIM] [--officer OFFICER]`           | `edit 000001 --victim Jane Smith --officer Officer Lee`                                                                    |
| **Delete case** | `delete ID`                                                                                           | `delete 00beef`                                                                                                            |
| **Read Case**   | `read ID`                                                                                             | `read 000001`                                                                                                              |
| **Setting**     | `setting --type TYPE --value VALUE`                                                                   | `setting --type dateinput --value dd-MM-yyyy`                                                                              |
| **Exit**        | `bye`                                                                                                 | `bye`                                                                                                                      |
| **Help**        | `help`                                                                                                | `help`                                                                                                                     |
