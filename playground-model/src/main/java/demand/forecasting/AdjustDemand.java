package demand.forecasting;

import lombok.Value;

import java.time.LocalDate;

@Value
public class AdjustDemand {
    String refNo;
    LocalDate date;
    long level;
    boolean strong;
}
