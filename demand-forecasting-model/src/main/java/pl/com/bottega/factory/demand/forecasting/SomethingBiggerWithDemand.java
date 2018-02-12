package pl.com.bottega.factory.demand.forecasting;

import lombok.AllArgsConstructor;
import pl.com.bottega.factory.demand.forecasting.ReviewRequired.ToReview;

@AllArgsConstructor
class SomethingBiggerWithDemand {

    void adjust(AdjustDemand adjustDemand) {
    }

    void process(Document document) {
    }

    void review(ToReview review, ReviewDecision decision) {
    }
}
