package demand.forecasting;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.Clock;
import java.time.LocalDate;

@AllArgsConstructor
class SomethingWithDemands {

    private Clock clock;
    private Events events;

    interface Events {
        void emit(DemandChanged event);
    }

    void adjust(AdjustDemand adjustment) {
        // We can change only Demands for today and future.
        if (LocalDate.now(clock).isAfter(adjustment.getDate())) {
            // TODO ADK bizz what if user try adjust historical data
        }

        // Adjust demand should be possible even
        // if there was no call-off document for that product.
        // In standard case future call-off documents should be stronger (overrides) adjustment,
        // but if customer warn us about opposite case import of call-off document should not remove previous adjustments.

        // Data from call-off document should be preserved (DON’T OVERRIDE THEM).
        // New demand is stored for further reference

        // emit domain event demand changed
        events.emit(new DemandChanged());
    }

    void update(Object somethingFromDocument) {
        // some important domain rules
    }

    Object getLevel() {
        return null; // we have some currently demanded level
    }

    @Value
    static class DemandChanged {
    }
}
