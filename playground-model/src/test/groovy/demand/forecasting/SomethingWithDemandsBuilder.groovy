package demand.forecasting

import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class SomethingWithDemandsBuilder {

    Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault())
    DemandEvents events

    String refNo = "3009000"
    LocalDate date = LocalDate.now(clock)
    Adjustment adjustment
    Demand documented

    def build() {
        new SomethingWithDemands(refNo, documented, adjustment, events)
    }

    SomethingWithDemandsBuilder reset() {
        nothingDemanded()
        noAdjustments()
    }

    SomethingWithDemandsBuilder nothingDemanded() {
        documented = null
        this
    }

    SomethingWithDemandsBuilder noAdjustments() {
        this.adjustment = null
        this
    }

    SomethingWithDemandsBuilder demandedLevels(long level) {
        documented = new Demand(level)
        this
    }

    SomethingWithDemandsBuilder adjustedTo(long level) {
        this.adjustment = Adjustment.weak(Demand.of(level))
        this
    }

    SomethingWithDemandsBuilder stronglyAdjustedTo(long level) {
        this.adjustment = Adjustment.strong(Demand.of(level))
        this
    }

    def newCallOffDemand(long level) {
        Demand.of(level)
    }

    def adjustDemandTo(long level) {
        Adjustment.weak(Demand.of(level))
    }

    def levelChanged(long previous, long current) {
        new DemandedLevelChanged(refNo, Demand.of(previous), Demand.of(current))
    }

    def reviewRequest(
            long previousDocument,
            long strongAdjustment,
            long currentDocument) {
        new ReviewRequest(
                refNo,
                Demand.of(previousDocument),
                Demand.of(strongAdjustment),
                Demand.of(currentDocument)
        )
    }
}
