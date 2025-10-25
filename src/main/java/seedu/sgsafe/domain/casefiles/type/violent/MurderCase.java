package seedu.sgsafe.domain.casefiles.type.violent;

import java.util.List;
import java.util.Map;

import seedu.sgsafe.domain.casefiles.type.CaseCategory;

public class MurderCase extends ViolentCase {
    private String weapon;
    private String numberOfVictims;

    public MurderCase(String id, String title, String date, String info, String victim, String officer) {
        super(id, title, date, info, victim, officer);
        this.category = CaseCategory.MURDER;
        this.categoryString = "Murder";
    }

    @Override
    public List<String> getValidEditFlags() {
        return List.of("title", "date", "info", "victim", "officer", "weapon", "numberOfVictims");
    }

    @Override
    public void update(Map<String, String> newValues) {
        super.update(newValues);
        if (newValues.containsKey("weapon")) {
            this.weapon = newValues.get("weapon");
        }
        if (newValues.containsKey("numberOfVictims")) {
            this.numberOfVictims = newValues.get("numberOfVictims");
        }
    }
}
