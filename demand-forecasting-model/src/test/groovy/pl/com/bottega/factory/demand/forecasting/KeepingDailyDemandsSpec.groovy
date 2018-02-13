package pl.com.bottega.factory.demand.forecasting

import spock.lang.Specification

class KeepingDailyDemandsSpec extends Specification {

    def builder = new SomethingSmallerWithDemandBuilder()

    def "Adjusted demands should be stored"() {
        given:
        def demand = demand()
                .demandedLevels(2800)
                .noAdjustments().build()

        when:
        def res = demand.adjust(adjustDemandTo(3500))

        then:
        demand.getLevel() == Demand.of(3500)
        res == levelChanged(2800, 3500)
    }

    def "Adjusted demands should be stored when there is no demand for product"() {
        given:
        def demand = demand()
                .nothingDemanded()
                .noAdjustments().build()

        when:
        def res = demand.adjust(adjustDemandTo(3500))

        then:
        demand.getLevel() == Demand.of(3500)
        res == levelChanged(0, 3500)
    }

    def "In standard case documented demands overrides adjustments"() {
        given:
        def demand = demand()
                .demandedLevels(2800)
                .adjustedTo(3500).build()

        when:
        def res = demand.update(newCallOffDemand(4000))

        then:
        demand.getLevel() == Demand.of(4000)
        res == levelChanged(3500, 4000)
    }

    def "Strong adjustment is kept even after processing of document"() {
        given:
        def demand = demand()
                .demandedLevels(2800)
                .stronglyAdjustedTo(3500).build()

        when:
        def res = demand.update(newCallOffDemand(2800))

        then:
        demand.getLevel() == Demand.of(3500)
        res == noChange()
    }

    def "Document update ignored by strong adjustment should rise warning"() {
        given:
        def demand = demand()
                .demandedLevels(2800)
                .stronglyAdjustedTo(3500).build()

        when:
        def res = demand.update(newCallOffDemand(5000))

        then:
        demand.getLevel() == Demand.of(3500)
        res == reviewRequest(2800, 3500, 5000)
    }

    SomethingSmallerWithDemandBuilder demand() {
        builder
    }

    Demand newCallOffDemand(long level) {
        builder.newCallOffDemand(level)
    }

    Adjustment adjustDemandTo(long level) {
        builder.adjustDemandTo(level)
    }

    DailyDemand.Result noChange() {
        DailyDemand.Result.non(builder.dailyId())
    }

    DailyDemand.Result levelChanged(long previous, long current) {
        DailyDemand.Result.builder()
                .id(builder.dailyId())
                .levelChange(builder.levelChanged(previous, current))
                .build()
    }

    DailyDemand.Result reviewRequest(long previousDocumented, long adjustment, long newDocumented) {
        DailyDemand.Result.builder()
                .id(builder.dailyId())
                .toReview(builder.reviewRequest(previousDocumented, adjustment, newDocumented))
                .build()
    }
}
