package pl.com.bottega.factory.demand.forecasting;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class SomethingSmallerWithDemandBuilder {

    private Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    private String refNo = "3009000";
    private LocalDate date = LocalDate.now(clock);
    private Demand documented;
    private Adjustment adjustment;

    public SomethingSmallerWithDemandBuilder(Clock clock, String refNo, LocalDate date) {
        this.clock = clock;
        this.refNo = refNo;
        this.date = date;
    }

    public SomethingSmallerWithDemandBuilder() {
    }

    public SomethingSmallerWithDemand build() {
        // TODO correct constructor parameters
        return new SomethingSmallerWithDemand();
    }

    public SomethingSmallerWithDemandBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public SomethingSmallerWithDemandBuilder nothingDemanded() {
        documented = null;
        return this;
    }

    public SomethingSmallerWithDemandBuilder noAdjustments() {
        adjustment = null;
        return this;
    }

    public SomethingSmallerWithDemandBuilder demandedLevels(long level) {
        documented = Demand.of(level);
        return this;
    }

    public SomethingSmallerWithDemandBuilder demandedLevels(Demand level) {
        documented = level;
        return this;
    }

    public SomethingSmallerWithDemandBuilder adjustedTo(long level) {
        adjustment = new Adjustment(Demand.of(level), false);
        return this;
    }

    public SomethingSmallerWithDemandBuilder stronglyAdjustedTo(long level) {
        adjustment = new Adjustment(Demand.of(level), true);
        return this;
    }

    public Demand newCallOffDemand(long level) {
        return Demand.of(level);
    }

    public Adjustment adjustDemandTo(long level) {
        return new Adjustment(Demand.of(level), false);
    }

    public DemandedLevelsChanged.Change levelChanged(long previous, long current) {
        return new DemandedLevelsChanged.Change(Demand.of(previous), Demand.of(current));
    }

    public ReviewRequired.ToReview reviewRequest(long previousDocumented, long adjustment, long newDocumented) {
        return new ReviewRequired.ToReview(new DailyId(refNo, date), Demand.of(previousDocumented), Demand.of(adjustment), Demand.of(newDocumented));
    }

    public Demand getDocumented() {
        return documented;
    }

    public Adjustment getAdjustment() {
        return adjustment;
    }
}
