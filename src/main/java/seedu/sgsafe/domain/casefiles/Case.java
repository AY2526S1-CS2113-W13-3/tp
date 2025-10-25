package seedu.sgsafe.domain.casefiles;

import seedu.sgsafe.domain.casefiles.type.CaseType;
import seedu.sgsafe.domain.casefiles.type.CaseCategory;

import java.util.List;
import java.util.Map;

/**
 * Represents a case file in the SGSafe system.
 * Each case contains metadata such as title, date, victim, officer, and status.
 */
public abstract class Case {
    /** The type of case. */
    protected CaseType type;

    /** The category of the case. */
    protected CaseCategory category;

    /** The category name to be printed. */
    protected String categoryString;

    /** The title or summary of the case. */
    private final String id;

    /** The title or summary of the case. */
    private String title;

    /** The date the case was recorded or occurred. */
    private String date;

    /** Additional information or notes about the case. */
    private String info;

    /** The name of the victim involved in the case. */
    private String victim;

    /** The name of the officer assigned to the case. */
    private String officer;

    /** Indicates whether the case is currently open. */
    private boolean isOpen;

    /** Indicates whether a case has been deleted. */
    private boolean isDeleted;

    /**
     * Constructs a {@code Case} object with the specified details.
     * The case is initialized as closed by default.
     *
     * @param id      the case ID in hex form
     * @param title   the title or summary of the case
     * @param date    the date the case was recorded or occurred
     * @param info    additional information or notes about the case
     * @param victim  the name of the victim involved
     * @param officer the name of the officer assigned
     */
    public Case(String id, String title, String date, String info, String victim, String officer) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.info = info;
        this.victim = victim;
        this.officer = officer;
        this.isOpen = true;
        this.isDeleted = false;
    }

    /**
     * Retrieves the title or summary of the case.
     *
     * @return the title of the case
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the date the case was recorded or occurred.
     *
     * @return the date of the case
     */
    public String getDate() {
        return date;
    }

    /**
     * Retrieves additional information about the case.
     *
     * @return the additional information about the case
     */
    public String getInfo() {
        return info;
    }

    /**
     * Retrieves the type of the case.
     *
     * @return the type of the case.
     */
    public CaseType getType() {
        return type;
    }

    /**
     * Retrieves the category of the case.
     *
     * @return the category of the case.
     */
    public CaseCategory getCategory() {
        return category;
    }

    /**
     * Retrieves the name of the victim involved in the case.
     *
     * @return the name of the victim, or null if not specified
     */
    public String getVictim() {
        return victim;
    }

    /**
     * Retrieves the name of the officer assigned to the case.
     *
     * @return the name of the officer, or null if not specified
     */
    public String getOfficer() {
        return officer;
    }

    /**
     * Retrieves the unique ID of the case.
     *
     * @return the case ID
     */
    public String getId() {
        return this.id;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public void setDeleted() {
        this.isDeleted = true;
    }

    /**
     * Returns a formatted summary line representing this case for display purposes.
     * <p>
     * The output includes:
     * <ul>
     *   <li>Status indicator: {@code [O]} for open, {@code [C]} for closed</li>
     *   <li>Category of the case</li>
     *   <li>Case ID: a unique 6-character hexadecimal string</li>
     *   <li>Date and title of the case</li>
     *   <li>Optional victim and officer details, if present</li>
     * </ul>
     * <p>
     * Example output:
     * <pre>
     * [O] #0001a3 2025-10-14 Robbery | Victim: Alice | Officer: Officer Tan
     * [C] #0001a4 2025-10-15 Fraud
     * </pre>
     *
     * @return a display-friendly string summarizing the case
     */
    public String getDisplayLine() {
        String status = this.isOpen ? "[O]" : "[C]";
        String victimLine = (this.victim == null) ? "" : (" | Victim: " + this.victim);
        String officerLine = (this.officer == null) ? "" : (" | Officer: " + this.officer);
        return status + " #" + this.id + " " + "[" + categoryString + "] " +
                this.date + " " + this.title + victimLine + officerLine;
    }

    /**
     * Returns a verbose, multi-line representation of this case for detailed display.
     * <p>
     * The header line uses the format {@code ==== CASE ID 000000 ====}.
     * The {@code info} field is capped at 100 characters; if longer, it is truncated and suffixed with {@code "..."}.
     *
     * @return an array of strings representing the verbose display of the case
     */
    public String[] getMultiLineVerboseDisplay() {
        String header = "======== CASE ID " + this.id + " ========";
        String statusLine = "Status  : " + (this.isOpen ? "Open" : "Closed");
        String truncatedInfo = truncateInfo(this.info);

        return new String[] {
            header,
            statusLine,
            "Title   : " + (title == null ? "" : title),
            "Date    : " + (date == null ? "" : date),
            "Info    : " + truncatedInfo,
            "Victim  : " + (victim == null ? "" : victim),
            "Officer : " + (officer == null ? "" : officer)
        };
    }

    /**
     * Truncates the given info string to a maximum of 100 characters.
     * If the input exceeds the limit, it is shortened and suffixed with {@code "..."}.
     * If the input is {@code null}, an empty string is returned.
     *
     * @param info the original info string
     * @return a truncated version of the info string, capped at 100 characters
     */
    private static String truncateInfo(String info) {
        if (info == null) {
            return "";
        }
        return info.length() > 100 ? info.substring(0, 100) + "..." : info;
    }

    public void setClosed() {
        this.isOpen = false;
    }

    public void setOpen() {
        this.isOpen = true;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    /**
     * Returns the list of valid flags that can be used to edit this case type.
     * The default implementation returns common flags shared by all case types.
     * Subclasses with additional fields should override this method.
     *
     * @return list of valid flag names for editing
     */
    public List<String> getValidEditFlags() {
        // Default flags for all case types
        return List.of("title", "date", "info", "victim", "officer");
    }

    /**
     * Updates the editable fields of {@code Case} instance using the provided map of new values.
     * <p>
     * Each key in {@code newValues} corresponds to a valid editable field (e.g. {@code title}, {@code date},
     * {@code info}, {@code victim}, {@code officer}). Only fields present in the map are updated; all
     * others remain unchanged.
     *
     * @param newValues a map containing field names and their new values
     */
    public void update(Map<String, String> newValues) {
        if (newValues.containsKey("title")) {
            this.title = newValues.get("title");
        }
        if (newValues.containsKey("date")) {
            this.date = newValues.get("date");
        }
        if (newValues.containsKey("info")) {
            this.info = newValues.get("info");
        }
        if (newValues.containsKey("victim")) {
            this.victim = newValues.get("victim");
        }
        if (newValues.containsKey("officer")) {
            this.officer = newValues.get("officer");
        }
    }
}
