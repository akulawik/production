package pl.com.bottega.factory.demand.forecasting

import java.time.LocalDate

import static pl.com.bottega.factory.demand.forecasting.ReviewRequired.ToReview

class SomethingBiggerWithDemandBuilder {

    def demand(long ... levels) {
        // todo
        this
    }

    def adjusted(Map<LocalDate, Long> adjustments) {
        // todo
        this
    }

    def stronglyAdjusted(Map<LocalDate, Long> adjustments) {
        // todo
        this
    }

    def build() {
        // todo
    }

    def document(LocalDate date, long ... levels) {
        // todo
        new Document(created, refNo, results)
    }

    def adjustDemand(Map<LocalDate, Long> adjustments) {
        // todo
    }

    def levelChanged(List<Long>... changes) {
        // todo
    }

    ReviewRequired reviewRequest(ToReview... reviews) {
        // todo
    }

    ToReview review(LocalDate date,
                    long previousDocumented,
                    long strongAdjustment,
                    long newDocumented) {
        // todo
    }
}
