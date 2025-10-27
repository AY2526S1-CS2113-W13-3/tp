package seedu.sgsafe.domain.casefiles.type.traffic;

import java.util.List;
import java.util.Map;

import seedu.sgsafe.domain.casefiles.type.CaseCategory;

import java.time.LocalDate;

public class SpeedingCase extends TrafficCase {
    private Integer speedLimit;
    private Integer exceededSpeed;

    public SpeedingCase(String id, String title, LocalDate date, String info, String victim, String officer) {
        super(id, title, date, info, victim, officer);
        this.category = CaseCategory.SPEEDING;
        this.categoryString = "Speeding";
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public int getExceededSpeed() {
        return exceededSpeed;
    }

    @Override
    public List<String> getValidEditFlags() {
        return List.of("title", "date", "info", "victim", "officer",
                "vehicle-type", "vehicle-plate", "road-name",
                "speed-limit", "exceeded-speed");
    }

    @Override
    public void update(Map<String, Object> newValues) {
        super.update(newValues);
        if (newValues.containsKey("speed-limit") && newValues.get("speed-limit") != null) {
            this.speedLimit = (Integer) newValues.get("speed-limit");
        }
        if (newValues.containsKey("exceeded-speed") && newValues.get("exceeded-speed") != null) {
            this.exceededSpeed = (Integer) newValues.get("exceeded-speed");
        }
    }
}
