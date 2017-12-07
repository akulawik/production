package demand.forecasting

import spock.lang.Specification

class ManagingDemandsSpec extends Specification {

    def builder = new SomethingWithDemandsBuilder()
    def events = Mock(DemandEvents)

    def "Adjusted demands should be stored"() {
        given:
        def demand = demand()
                .demandedLevels(2800)
                .noAdjustments().build()

        when:
        demand.adjust(adjustDemandTo(3500))

        then:
        demand.getLevel() == Demand.of(3500)
        1 * events.emit(levelChanged(2800, 3500))
    }

    def "Adjusted demands should be stored when there is no demand for product"() {
        given:
        def demand = demand()
                .nothingDemanded()
                .noAdjustments().build()

        when:
        demand.adjust(adjustDemandTo(3500))

        then:
        demand.getLevel() == Demand.of(3500)
        1 * events.emit(levelChanged(0, 3500))
    }

    def "In standard case documented demands overrides adjustments"() {
        given:
        def demand = demand()
                .demandedLevels(2800)
                .adjustedTo(3500).build()

        when:
        demand.update(newCallOffDemand(4000))

        then:
        demand.getLevel() == Demand.of(4000)
        1 * events.emit(levelChanged(3500, 4000))
    }

    def "Strong adjustment is kept even after processing of document"() {
        given:
        def demand = demand()
                .demandedLevels(2800)
                .stronglyAdjustedTo(3500).build()

        when:
        demand.update(newCallOffDemand(2800))

        then:
        demand.getLevel() == Demand.of(3500)
        0 * events.emit(_ as DemandedLevelChanged)
    }

    def "Review request after processing of document with unmatching value after strong adjustement"() {
        given:
        def demand = demand()
                .demandedLevels(2800)
                .stronglyAdjustedTo(3500).build()

        when:
        demand.update(newCallOffDemand(5000))

        then:
        demand.getLevel() == Demand.of(3500)
        1 * events.emit(reviewRequest(2800, 3500, 5000))
        0 * events.emit(_ as DemandedLevelChanged)
    }

    def demand() {
        builder.events = events
        builder
    }

    def newCallOffDemand(long level) {
        builder.newCallOffDemand(level)
    }

    def adjustDemandTo(long level) {
        builder.adjustDemandTo(level)
    }

    def levelChanged(long previous, long current) {
        builder.levelChanged(previous, current)
    }

    ReviewRequest reviewRequest(
            long previousDocument,
            long strongAdjustment,
            long currentDocument) {
        builder.reviewRequest(previousDocument, strongAdjustment, currentDocument)
    }
}
