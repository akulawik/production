package demand.forecasting;

import lombok.Value;

@Value
public class ReviewRequest {
    String refNo;
    Demand previousDocument;
    Demand strongAdjustment;
    Demand currentDocument;
}
