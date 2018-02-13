package pl.com.bottega.factory.demand.forecasting;

import lombok.Builder;

import java.util.Optional;

class SomethingSmallerWithDemand {

    Result adjust(Adjustment adjustment) {
        return Result.non();
    }

    Result update(Demand documented) {
        return Result.non();
    }

    Demand getLevel() {
        return Demand.nothingDemanded();
    }

    @Builder
    static class Result {
        private final DemandedLevelsChanged.Change levelChange;
        private final ReviewRequired.ToReview toReview;

        static Result non() {
            return new Result(null, null);
        }

        Optional<DemandedLevelsChanged.Change> getLevelChange() {
            return Optional.ofNullable(levelChange);
        }

        Optional<ReviewRequired.ToReview> getToReview() {
            return Optional.ofNullable(toReview);
        }
    }
}
