package pl.com.bottega.factory.demand.forecasting

import java.time.LocalDate

import static pl.com.bottega.factory.demand.forecasting.ReviewRequired.ToReview

trait ProductDemandTrait {

    SomethingBiggerWithDemandBuilder builder

    ProductDemand demanded(Long... levels) {
        builder.demand(levels).build()
    }

    SomethingBiggerWithDemandBuilder demand(Long... levels) {
        builder.demand(levels)
    }

    Document document(LocalDate date, Long... levels) {
        builder.document(date, levels)
    }

    AdjustDemand adjustments(Map<LocalDate, Long> map) {
        builder.adjustDemand(map)
    }

    DemandedLevelsChanged levelChanged(List<Long>... changes) {
        builder.levelChanged(changes)
    }

    List<Long> notChanged() {
        []
    }

    ReviewRequired reviewRequest(ToReview... reviews) {
        builder.reviewRequest(reviews)
    }

    ToReview review(
            LocalDate date,
            long previousDocumented,
            long strongAdjustment,
            long newDocumented) {
        return builder.review(date, previousDocumented, strongAdjustment, newDocumented)
    }
}