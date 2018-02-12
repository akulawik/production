package pl.com.bottega.factory.demand.forecasting;

import lombok.AllArgsConstructor;
import pl.com.bottega.factory.demand.forecasting.ReviewRequired.ToReview;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Map;

@AllArgsConstructor
class SomethingBiggerWithDemand {

    private DemandEvents events;
    private Clock clock;

    private AdjustDemand field;

    void adjust(AdjustDemand adjustDemand) {
        //  We can change only Demands for today and future.
        for (Map.Entry<LocalDate, Adjustment> entry : field.getAdjustments().entrySet()) {
            if (!entry.getKey().isBefore(LocalDate.now(clock))) {

            }
        }
        //  New demand is stored for further reference
        field = adjustDemand;

        events.emit(new DemandedLevelsChanged(null, null));
        //  Adjust demand at day to amount, delivered.
        //  Data from call-off document should be preserved.
        //  Adjust demand should be possible even
        //  if there was no document for that product.
        //  In standard case future call-off documents should override adjustment,
        //  but if customer warn us about opposite case
        //  import of document should not remove previous adjustments.
        //          Logistician note should be kept with adjustment.

    }

    void process(Document document) {

    }

    void review(ToReview review, ReviewDecision decision) {

    }
}
