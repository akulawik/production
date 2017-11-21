package demand.forecasting;

interface DemandRepository {
    SomethingWithDemands get(Object id);

    void save(SomethingWithDemands model);
}
