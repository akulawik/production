package demand.forecasting;

import java.util.Optional;

class SomethingWithDemands {

    private final String refNo;
    private Demand documented;
    private Adjustment adjustment;

    private DemandEvents events;

    public SomethingWithDemands(String refNo,
                                Demand documented, Adjustment adjustment,
                                DemandEvents events) {
        this.refNo = refNo;
        this.documented =
                Optional.ofNullable(documented)
                        .orElseGet(Demand::non);
        this.adjustment = adjustment;
        this.events = events;
    }

    void adjust(Adjustment command) {
        Demand previous = getLevel();

        adjustment = command;
        Demand current = getLevel();
        if (!previous.equals(current)) {
            events.emit(
                    new DemandedLevelChanged(refNo, previous, current)
            );
        }
    }

    void update(Demand document) {
        Demand previous = getLevel();

        if (updateRequiresReview(document)) {
            events.emit(new ReviewRequest(refNo,
                    this.documented,
                    this.adjustment.getLevel(),
                    document));
        }

        this.documented = document;
        if (!stronglyAdjusted()) {
            this.adjustment = null;
        }

        Demand current = getLevel();
        if (!previous.equals(current)) {
            events.emit(
                    new DemandedLevelChanged(refNo, previous, current)
            );
        }
    }

    Demand getLevel() {
        if (adjustment != null) {
            return adjustment.getLevel();
        } else {
            return documented;
        }
    }

    private boolean stronglyAdjusted() {
        return adjustment != null && adjustment.isStrong();
    }

    private boolean updateRequiresReview(Demand document) {
        return adjustment.isStrong()
                && this.documented.getLevel() != document.getLevel();
    }
}
