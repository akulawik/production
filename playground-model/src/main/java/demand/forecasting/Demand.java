package demand.forecasting;

import lombok.Value;

@Value
public class Demand {
    long level;

    public static Demand of(long level) {
        return new Demand(level);
    }

    public static Demand non() {
        return new Demand(0);
    }
}
