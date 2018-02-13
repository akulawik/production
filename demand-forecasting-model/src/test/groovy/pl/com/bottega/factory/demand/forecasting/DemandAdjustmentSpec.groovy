package pl.com.bottega.factory.demand.forecasting

import spock.lang.Specification

import java.time.LocalDate

class DemandAdjustmentSpec extends Specification implements ProductDemandTrait {

    def events = Mock(DemandEvents)

    void setup() {
        builder = new SomethingBiggerWithDemandBuilder(events)
    }

    def "Adjusted demands should be stored"() {
        given:
        def today = LocalDate.now(builder.clock)
        def demand = demanded(2800L, 0L)
        def adjustments = adjustments([(today): 1000L])

        when:
        demand.adjust(adjustments)

        then:
        1 * events.emit(levelChanged([2800L, 1000L]))
    }

    def "Adjustment of future demands is possible"() {
        given:
        def today = LocalDate.now(builder.clock)
        def demand = demanded(2800L)
        def adjustments = adjustments([(today.plusDays(1)): 1000L])

        when:
        demand.adjust(adjustments)

        then:
        1 * events.emit(levelChanged(notChanged(), [0L, 1000L]))
    }

    def "Adjustment without changes should not generate event"() {
        given:
        def today = LocalDate.now(builder.clock)
        def demand = demanded(2800L, 1000L)
        def adjustments = adjustments([(today): 2800L, (today.plusDays(1)): 1000L])

        when:
        demand.adjust(adjustments)

        then:
        0 * events.emit(_ as DemandedLevelsChanged)
    }

    def "Should skip past demands adjustments"() {
        given:
        def pastDate = LocalDate.now(builder.clock).minusDays(2)
        def demand = demanded(2800L, 0L)
        def adjustments = adjustments([(pastDate): 1000L])

        when:
        demand.adjust(adjustments)

        then:
        0 * events.emit(_ as DemandedLevelsChanged)
    }
}