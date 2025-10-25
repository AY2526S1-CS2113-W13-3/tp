package seedu.sgsafe.domain.casefiles.type.traffic;

import java.util.List;
import java.util.Map;

import seedu.sgsafe.domain.casefiles.Case;
import seedu.sgsafe.domain.casefiles.type.CaseType;

public abstract class TrafficCase extends Case {
    private String vehicleType;
    private String vehiclePlate;
    private String roadName;

    public TrafficCase(String id, String title, String date, String info, String victim, String officer) {
        super(id, title, date, info, victim, officer);
        this.type = CaseType.TRAFFIC;
    }
    
    @Override
    public List<String> getValidEditFlags() {
        return List.of("title", "date", "info", "victim", "officer",
                       "vehicleType", "vehiclePlate", "roadName");
    }
    
    @Override
    public void update(Map<String, String> newValues) {
        super.update(newValues);
        if (newValues.containsKey("vehicleType")) {
            this.vehicleType = newValues.get("vehicleType");
        }
        if (newValues.containsKey("vehiclePlate")) {
            this.vehiclePlate = newValues.get("vehiclePlate");
        }
        if (newValues.containsKey("roadName")) {
            this.roadName = newValues.get("roadName");
        }
    }
}
