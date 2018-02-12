package pl.com.bottega.factory.demand.forecasting;

import lombok.Value;

@Value
class Adjustment {

    Demand demand;
    boolean strong;

    static Adjustment strong(Demand demand) {
        return new Adjustment(demand, true);
    }

    static Adjustment weak(Demand demand) {
        return new Adjustment(demand, false);
    }

    static boolean isStrong(Adjustment adjustment) {
        return adjustment != null && adjustment.strong;
    }
}
