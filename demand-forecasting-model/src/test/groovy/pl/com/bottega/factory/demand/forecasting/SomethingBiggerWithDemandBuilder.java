package pl.com.bottega.factory.demand.forecasting;

import pl.com.bottega.factory.product.management.RefNoId;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class SomethingBiggerWithDemandBuilder {

    private String refNo = "3009000";
    public Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    private DemandEvents events;
    private Map<LocalDate, SomethingSmallerWithDemandBuilder> demands = new HashMap<>();

    public SomethingBiggerWithDemandBuilder(DemandEvents events) {
        this.events = events;
    }

    public SomethingBiggerWithDemand build() {
        Map<LocalDate, Demand> documented = demands.entrySet().stream()
                .filter(e -> e.getValue().getDocumented() != null)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().getDocumented()
                ));
        Map<LocalDate, Adjustment> adjustements = demands.entrySet().stream()
                .filter(e -> e.getValue().getAdjustment() != null)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().getAdjustment()
                ));
        // TODO correct constructor parameters
        return new SomethingBiggerWithDemand(events, clock, null);
    }

    public SomethingBiggerWithDemandBuilder demand(Long... levels) {
        LocalDate date = LocalDate.now(clock);
        for (long level : levels) {
            demands(date).demandedLevels(level);
            date = date.plusDays(1);
        }

        return this;
    }

    public SomethingBiggerWithDemandBuilder adjusted(Map<LocalDate, Long> adjustments) {
        adjustments.forEach((date, level) ->
                demands(date).adjustedTo((long) level)
        );
        return this;
    }

    public SomethingBiggerWithDemandBuilder stronglyAdjusted(Map<LocalDate, Long> adjustments) {
        adjustments.forEach((date, level) ->
                demands(date).stronglyAdjustedTo((long) level)
        );
        return this;
    }

    public Document document(LocalDate date, Long... levels) {
        Instant created = date.atTime(OffsetTime.of(8, 0, 0, 0, ZoneOffset.UTC)).toInstant();
        SortedMap<LocalDate, Demand> results = new TreeMap<>();
        for (Long level : levels) {
            results.put(date, Demand.of(level));
            date = date.plusDays(1);
        }

        return new Document(created, refNo, results);
    }

    public AdjustDemand adjustDemand(Map<LocalDate, Long> adjustments) {
        return new AdjustDemand(refNo, adjustments.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> Adjustment.weak(Demand.of(e.getValue()))
                )));
    }

    public DemandedLevelsChanged levelChanged(List<Long>... changes) {
        LocalDate date = LocalDate.now(clock);
        Map<DailyId, DemandedLevelsChanged.Change> results = new HashMap<>();
        for (List<Long> change : changes) {
            if (change.size() == 2) {
                results.put(new DailyId(refNo, date), new DemandedLevelsChanged.Change(Demand.of(change.get(0)), Demand.of(change.get(1))));
            } else if (!change.isEmpty()) throw new IllegalArgumentException();
            date = date.plusDays(1);
        }

        return new DemandedLevelsChanged(new RefNoId(refNo), results);
    }

    public ReviewRequired reviewRequest(ReviewRequired.ToReview... reviews) {
        return new ReviewRequired(new RefNoId(refNo), Arrays.asList(reviews));
    }

    public ReviewRequired.ToReview review(LocalDate date, long previousDocumented, long strongAdjustment, long newDocumented) {
        return new ReviewRequired.ToReview(new DailyId(refNo, date), Demand.of(previousDocumented), Demand.of(strongAdjustment), Demand.of(newDocumented));
    }

    private SomethingSmallerWithDemandBuilder demands(LocalDate date) {
        return demands.computeIfAbsent(date, key -> new SomethingSmallerWithDemandBuilder(clock, refNo, key));
    }

}
