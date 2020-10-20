package br.com.eduardo.report.sales.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Customer {

    @Setter(AccessLevel.NONE)
    private final long code = 002;

    private String CNPJ;
    private String name;
    private String businessArea;

//    002çCNPJçNameçBusiness Area

}
