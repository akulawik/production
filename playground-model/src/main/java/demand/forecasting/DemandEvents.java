package demand.forecasting;

public interface DemandEvents {
    void emit(DemandedLevelChanged event);
    void emit(ReviewRequest event);
}
