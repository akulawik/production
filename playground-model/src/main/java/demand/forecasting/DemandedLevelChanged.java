package demand.forecasting;

import lombok.Value;

@Value
public class DemandedLevelChanged {
    String refNo;
    Demand previous;
    Demand current;
}
