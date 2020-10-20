package br.com.eduardo.report.sales.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class SaleItem {

    private long itemID;
    private BigDecimal itemQuantity;
    private BigDecimal itemPrice;

    //Item ID-Item Quantity-Item Price

}
