package pl.com.bottega.factory.demand.forecasting

import spock.lang.Specification

import java.time.LocalDate

class DocumentProcessingSpec extends Specification implements ProductDemandTrait {

    private DemandEvents events = Mock(DemandEvents)

    void setup() {
        builder = new SomethingBiggerWithDemandBuilder(events)
    }

    def "Updated demands should be stored"() {
        given:
        def today = LocalDate.now(builder.clock)
        def demand = demanded(2800L, 0L)
        def document = document(today, 2000L, 3500L)

        when:
        demand.process(document)

        then:
        1 * events.emit(levelChanged([2800L, 2000L], [0L, 3500L]))
    }

    def "Demands for dates not present in system should be stored "() {
        given:
        def today = LocalDate.now(builder.clock)
        def demand = demanded(1000L)
        def document = document(today, 1000L, 3500L, 1000L)

        when:
        demand.process(document)

        then:
        1 * events.emit(levelChanged(notChanged(), [0L, 3500L], [0L, 1000L]))
    }

    def "Document without changes should not generate event"() {
        given:
        def today = LocalDate.now(builder.clock)
        def demand = demanded(2800L, 0L)
        def document = document(today, 2800L, 0L)

        when:
        demand.process(document)

        then:
        0 * events.emit(_ as DemandedLevelsChanged)
    }

    def "Should skip past demands from document"() {
        given:
        def pastDate = LocalDate.now(builder.clock).minusDays(2)
        def demand = demanded(0L, 0L)
        def document = document(pastDate, 2800L, 2800L, 3500L, 1000L)

        when:
        demand.process(document)

        then:
        1 * events.emit(levelChanged([0L, 3500L], [0L, 1000L]))
    }
}