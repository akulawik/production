package pl.com.bottega.factory.demand.forecasting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.Optional;

@AllArgsConstructor
class DailyDemand {

    private DailyId id;
    private Demand documented;
    private Adjustment adjustment;

    Result adjust(Adjustment adjustment) {
        //  Adjust demand at day to amount, delivered.
        Demand previous = getLevel();
        this.adjustment = adjustment;
        Demand current = getLevel();

        if (previous.equals(current)) {
            return Result.non(id);
        }
        return Result.builder()
                .id(id)
                .levelChange(new DemandedLevelsChanged.Change(previous, current))
                .build();
    }

    Result update(Demand documented) {
        Demand previous = getLevel();
        this.documented = documented;

        if (!Adjustment.isStrong(this.adjustment)) {
            this.adjustment = null;
        }
        Demand current = getLevel();

        if (previous.equals(current)) {
            return Result.non(id);
        }

        return Result.builder()
                .id(id)
                .levelChange(new DemandedLevelsChanged.Change(previous, current))
                .build();
    }

    Demand getLevel() {
        if (adjustment != null) {
            return adjustment.getDemand();
        } else if (documented != null) {
            return documented;
        }
        return Demand.nothingDemanded();
    }

    @Builder
    @Value
    static class Result {
        private final DailyId id;
        private final DemandedLevelsChanged.Change levelChange;
        private final ReviewRequired.ToReview toReview;

        static Result non(DailyId id) {
            return new Result(id, null, null);
        }

        Optional<DemandedLevelsChanged.Change> getLevelChange() {
            return Optional.ofNullable(levelChange);
        }

        Optional<ReviewRequired.ToReview> getToReview() {
            return Optional.ofNullable(toReview);
        }
    }
}
