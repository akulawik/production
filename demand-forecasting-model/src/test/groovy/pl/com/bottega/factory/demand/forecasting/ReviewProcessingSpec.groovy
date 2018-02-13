package pl.com.bottega.factory.demand.forecasting

import spock.lang.Specification

import java.time.LocalDate

import static pl.com.bottega.factory.demand.forecasting.ReviewDecision.*

class ReviewProcessingSpec extends Specification implements ProductDemandTrait {

    def events = Mock(DemandEvents)

    void setup() {
        builder = new SomethingBiggerWithDemandBuilder(events)
    }

    def "Review requested"() {
        given:
        def today = LocalDate.now(builder.clock)
        def tomorrow = today.plusDays(1)
        def demand = demand(0L, 0L)
                .stronglyAdjusted((tomorrow): 3500L)
                .build()

        when:
        demand.process(document(today, 0L, 2800L))

        then:
        1 * events.emit(reviewRequest(review(tomorrow, 0L, 3500L, 2800L)))
    }

    def "decision to 'ignore'"() {
        given:
        def today = LocalDate.now(builder.clock)
        def tomorrow = today.plusDays(1)
        def demand = demand(0L, 2800L)
                .stronglyAdjusted((tomorrow): 3500L)
                .build()

        when:
        demand.review(review(tomorrow, 0L, 3500L, 2800L), IGNORE)

        then:
        0 * events.emit(_ as DemandedLevelsChanged)
    }

    def "decision to 'pick new'"() {
        given:
        def today = LocalDate.now(builder.clock)
        def tomorrow = today.plusDays(1)
        def demand = demand(0L, 2800L)
                .stronglyAdjusted((tomorrow): 3500L)
                .build()

        when:
        demand.review(review(tomorrow, 0L, 3500L, 2800L), PICK_NEW)

        then:
        1 * events.emit(levelChanged([], [3500L, 2800L]))
    }

    def "decision to 'pick previous'"() {
        given:
        def today = LocalDate.now(builder.clock)
        def tomorrow = today.plusDays(1)
        def demand = demand(0L, 2800L)
                .stronglyAdjusted((tomorrow): 3500L)
                .build()

        when:
        demand.review(review(tomorrow, 0L, 3500L, 2800L), PICK_PREVIOUS)

        then:
        1 * events.emit(levelChanged([], [3500L, 0L]))
    }

    def "decision to 'make adjustment weak'"() {
        given:
        def today = LocalDate.now(builder.clock)
        def tomorrow = today.plusDays(1)
        def demand = demand(0L, 2800L)
                .stronglyAdjusted((tomorrow): 3500L)
                .build()

        when:
        demand.review(review(tomorrow, 0L, 3500L, 2800L), MAKE_ADJUSTMENT_WEAK)

        then:
        0 * events.emit(_ as DemandedLevelsChanged)
    }
}
