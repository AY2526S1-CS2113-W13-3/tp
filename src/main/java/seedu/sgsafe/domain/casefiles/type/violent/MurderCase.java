package seedu.sgsafe.domain.casefiles.type.violent;

import seedu.sgsafe.domain.casefiles.type.CaseCategory;

public class MurderCase extends ViolentCase {
    private String weapon;
    private int numberOfVictims;

    public MurderCase(String id, String title, String date, String info, String victim, String officer) {
        super(id, title, date, info, victim, officer);
        this.category = CaseCategory.MURDER;
        this.categoryString = "Murder";
    }
}
