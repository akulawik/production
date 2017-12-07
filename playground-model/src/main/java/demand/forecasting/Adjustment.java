package demand.forecasting;

import lombok.Value;

@Value
public class Adjustment {
    Demand level;
    boolean strong;

    public static Adjustment weak(Demand level) {
        return new Adjustment(level, false);
    }

    public static Adjustment strong(Demand level) {
        return new Adjustment(level, true);
    }
}
