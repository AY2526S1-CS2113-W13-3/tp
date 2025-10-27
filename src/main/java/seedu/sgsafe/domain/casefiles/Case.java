package seedu.sgsafe.domain.casefiles;

import seedu.sgsafe.domain.casefiles.type.CaseType;
import seedu.sgsafe.domain.casefiles.type.CaseCategory;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

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

    /** Metadata timestamp for auditing of when the case is created. */
    private final LocalDateTime createdAt;

    /** Metadata timestamp for auditing of when the case is updated. */
    private LocalDateTime updatedAt;

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
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
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
        this.updatedAt = LocalDateTime.now();
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
        String status = this.isOpen ? "[Open]" : "[Closed]";
        return String.format("%-8s %-9s %-6s %-10s %s", status, categoryString, this.id, this.date, this.title);
    }

    /**
     * Constructs a detailed, multi-line string representation of this case for display purposes.
     * <p>
     * The output begins with a header line in the format {@code ==== CASE ID 000000 ====}, followed by
     * key-value lines for each non-null field. Each value is truncated to 100 characters and suffixed
     * with {@code "..."} if it exceeds that length. Optional fields such as {@code victim} and {@code officer}
     * are only included if they are non-null.
     * <p>
     * This method avoids stacking function calls and delegates conditional formatting and addition
     * to a helper method for clarity and maintainability.
     *
     * @return an array of strings representing the verbose, multi-line display of the case
     */
    public String[] getMultiLineVerboseDisplay() {
        List<String> lines = new ArrayList<>();

        lines.add(formatCaseIDHeader());
        lines.add(formatStatus());

        addFormattedLine(lines, "Category", categoryString);
        addFormattedLine(lines, "Title", title);
        addFormattedLine(lines, "Date", date);
        addFormattedLine(lines, "Info", info);
        addFormattedLine(lines, "Created at", createdAt.toString());
        addFormattedLine(lines, "Updated at", updatedAt.toString());
        addFormattedLine(lines, "Victim", victim);
        addFormattedLine(lines, "Officer", officer);

        return lines.toArray(new String[0]);
    }


    /**
     * Constructs the header line for the verbose display.
     * Format: {@code "======== CASE ID 000000 ========"}
     *
     * @return the formatted header string
     */
    private String formatCaseIDHeader() {
        return "======== CASE ID " + this.id + " ========";
    }

    /**
     * Constructs the status line indicating whether the case is open or closed.
     * Format: {@code "Status  : Open"} or {@code "Status  : Closed"}
     *
     * @return the formatted status string
     */
    private String formatStatus() {
        return "Status  : " + (this.isOpen ? "Open" : "Closed");
    }

    /**
     * Formats a labeled line with truncated content.
     * If the value is {@code null}, an empty string is used.
     * Format: {@code "Label   : value"}
     *
     * @param label the label to display (e.g., "Title", "Date")
     * @param value the value to display, which will be truncated
     * @return the formatted line
     */
    private String formatLine(String label, String value) {
        return label + "  : " + truncate(value);
    }

    private void addFormattedLine(List<String> lines, String label, String value) {
        if (value != null) {
            String formatted = formatLine(label, value);
            lines.add(formatted);
        }
    }

    /**
     * Truncates the input string to a maximum of 100 characters.
     * If the input is {@code null}, returns an empty string.
     * If the input exceeds 100 characters, appends {@code "..."}.
     *
     * @param input the string to truncate
     * @return the truncated string
     */
    private String truncate(String input) {
        return input.length() <= 100 ? input : input.substring(0, 100) + "...";
    }

    public void setClosed() {
        this.isOpen = false;
        updatedAt = LocalDateTime.now();
    }

    public void setOpen() {
        this.isOpen = true;
        updatedAt = LocalDateTime.now();
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
        this.updatedAt = LocalDateTime.now();
    }
}
