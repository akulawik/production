package demand.forecasting;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LogisticService {

    private final DemandRepository repository;

    //@Transactional
    public void adjust(AdjustDemand adjustment) {
        SomethingWithDemands demand = repository.get(null);
        demand.adjust(adjustment); // -> DemandChanged
        repository.save(demand);
    }

}
