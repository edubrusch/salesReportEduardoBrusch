package br.com.eduardo.report.sales.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ReportDTO {

    private List<Customer> customers;
    private List<SalesPerson> salesPeople;
    private List<Sale> sales;
}
