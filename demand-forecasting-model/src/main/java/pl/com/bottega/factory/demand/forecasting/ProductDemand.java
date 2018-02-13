package pl.com.bottega.factory.demand.forecasting;

import lombok.AllArgsConstructor;
import pl.com.bottega.factory.demand.forecasting.ReviewRequired.ToReview;
import pl.com.bottega.factory.product.management.RefNoId;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
class ProductDemand {

    private DemandEvents events;
    private Clock clock;

    private RefNoId id;
    private Map<LocalDate, DailyDemand> demands;

    void adjust(AdjustDemand adjustDemand) {
        List<DailyDemand.Result> results =
                adjustDemand.getAdjustments().entrySet().stream()
                        .filter(e -> !e.getKey().isBefore(LocalDate.now(clock)))
                        .map(e -> demand(e.getKey()).adjust(e.getValue()))
                        .collect(Collectors.toList());

        Map<DailyId, DemandedLevelsChanged.Change> changes = results.stream()
                .filter(result -> result.getLevelChange().isPresent())
                .collect(Collectors.toMap(
                        result -> result.getId(),
                        result -> result.getLevelChange().get()
                ));

        if (!changes.isEmpty()) {
            events.emit(new DemandedLevelsChanged(id, changes));
        }
    }

    void process(Document document) {
        List<DailyDemand.Result> results =
                document.getDemands().entrySet().stream()
                        .filter(e -> !e.getKey().isBefore(LocalDate.now(clock)))
                        .map(e -> demand(e.getKey()).update(e.getValue()))
                        .collect(Collectors.toList());

        Map<DailyId, DemandedLevelsChanged.Change> changes = results.stream()
                .filter(result -> result.getLevelChange().isPresent())
                .collect(Collectors.toMap(
                        result -> result.getId(),
                        result -> result.getLevelChange().get()
                ));

        if (!changes.isEmpty()) {
            events.emit(new DemandedLevelsChanged(id, changes));
        }
    }

    void review(ToReview review, ReviewDecision decision) {

    }

    private DailyDemand demand(LocalDate date) {
        return demands.computeIfAbsent(date, key -> new DailyDemand(
                new DailyId(id.getRefNo(), key),
                null, null
        ));
    }
}
