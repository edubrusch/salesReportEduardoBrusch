package br.com.eduardo.report.sales.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class Report {

    private String fileName;
    private String customerAmount;
    private String salesPersonAmount;
    private String expensivePurchaseID;
    private String worstSalesPerson;

    public Map<String, Object> toMap() {

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("fileName", fileName);
        parameters.put("customerAmount",customerAmount);
        parameters.put("salesPersonAmount",salesPersonAmount);
        parameters.put("expensivePurchaseID",expensivePurchaseID);
        parameters.put("worstSalesPerson",worstSalesPerson);
        return parameters;
    }
}
