package seedu.sgsafe.domain.casefiles.type.financial;

import java.util.List;
import java.util.Map;

import seedu.sgsafe.domain.casefiles.type.CaseCategory;

import java.time.LocalDate;

public class TheftCase extends FinancialCase {
    private String stolenObject;

    public TheftCase(String id, String title, LocalDate date, String info, String victim, String officer) {
        super(id, title, date, info, victim, officer);
        this.category = CaseCategory.THEFT;
        this.categoryString = "Theft";
    }

    @Override
    public List<String> getValidEditFlags() {
        return List.of("title", "date", "info", "victim", "officer", "stolen-object");
    }

    @Override
    public void update(Map<String, String> newValues) {
        super.update(newValues);
        if (newValues.containsKey("stolen-object")) {
            this.stolenObject = newValues.get("stolenObject");
        }
    }
}
