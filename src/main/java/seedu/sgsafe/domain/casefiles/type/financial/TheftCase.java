package seedu.sgsafe.domain.casefiles.type.financial;

import java.util.List;
import java.util.Map;

import seedu.sgsafe.domain.casefiles.type.CaseCategory;
import seedu.sgsafe.domain.casefiles.CaseFormatter;

import java.time.LocalDate;

/**
 * Represents a case involving a theft.
 */
public class TheftCase extends FinancialCase {
    private String stolenObject;

    public TheftCase(String id, String title, LocalDate date, String info, String victim, String officer) {
        super(id, title, date, info, victim, officer);
        this.category = CaseCategory.THEFT;
        this.categoryString = "Theft";
    }

    public String getStolenObject () {
        return stolenObject;
    }

    //@@author shennontay
    @Override
    public String[] getReadCaseDisplay() {
        List<String> displayList = getBaseDisplayLines();

        CaseFormatter.addWrappedFieldForRead(displayList, "Vehicle Type", this.stolenObject);
        CaseFormatter.addWrappedFieldForRead(displayList, "Info", getInfo());

        return displayList.toArray(new String[0]);
    }
    //@@author

    @Override
    public List<String> getValidEditFlags() {
        return List.of("title", "date", "info", "victim", "officer", "stolen-object");
    }

    @Override
    public void update(Map<String, Object> newValues) {
        super.update(newValues);
        if (newValues.containsKey("stolen-object")) {
            this.stolenObject = (String) newValues.get("stolen-object");
        }
    }

    @Override
    public List<String> getAdditionalFields() {
        List<String> additionalFields = super.getAdditionalFields();
        additionalFields.add("stolen-object");
        return additionalFields;
    }

    @Override
    public String toSaveString() {
        return super.toSaveString()
                + "|stolen-object:" + (this.stolenObject == null ? "" : this.stolenObject);
    }
}
