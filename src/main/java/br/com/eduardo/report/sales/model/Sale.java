package br.com.eduardo.report.sales.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Builder
public class Sale {

    @Setter(AccessLevel.NONE)
    private final long code = 003;

    private long saleID;
    private List<SaleItem> saleItems;
    private String SalesPersonName;


//    003çSale IDç[]çSalesman name
}
