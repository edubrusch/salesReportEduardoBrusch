package br.com.eduardo.report.sales.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class SalesPerson {

    @Setter(AccessLevel.NONE)
    private final long code = 001;

    private String CPF;
    private String Name;
    private BigDecimal Salary;

//    001çCPFçNameçSalary

}
