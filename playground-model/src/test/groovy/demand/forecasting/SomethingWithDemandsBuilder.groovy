package demand.forecasting

import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class SomethingWithDemandsBuilder {

    Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault())
    SomethingWithDemands.Events events

    String refNo = "3009000"
    LocalDate date = LocalDate.now(clock)

    SomethingWithDemands build() {
        new SomethingWithDemands(clock, events)
    }

    SomethingWithDemandsBuilder reset() {
        nothingDemanded()
        noAdjustments()
    }

    SomethingWithDemandsBuilder nothingDemanded() {
        // ??
        this
    }

    SomethingWithDemandsBuilder noAdjustments() {
        // ??
        this
    }

    SomethingWithDemandsBuilder demandedLevels(long level) {
        // ??
        this
    }

    SomethingWithDemandsBuilder adjustedTo(long level) {
        // ??
        this
    }

    SomethingWithDemandsBuilder stronglyAdjustedTo(long level) {
        // ??
        this
    }

    def newCallOffDemand(long level) {
        // ??
    }

    def adjustDemandTo(long level) {
        // ??
    }

    SomethingWithDemands.DemandChanged levelChanged(long previous, long current) {
        new SomethingWithDemands.DemandChanged(
                // ??
        )
    }
}
