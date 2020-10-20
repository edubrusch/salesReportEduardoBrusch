package br.com.eduardo.report.sales.service;

import br.com.eduardo.report.sales.model.Report;
import br.com.eduardo.report.sales.model.ReportDTO;
import br.com.eduardo.report.sales.model.Sale;

import java.util.*;
import java.util.stream.Collectors;

public class ReportDataProcess {

    private ReportDTO reportSource;

    public Report processReport(ReportDTO reportSource) {

        this.reportSource = reportSource;
        Sale best = getMostExpensiveSale();

        long customerAmount = reportSource.getCustomers().size();
        long salesPeopleAmount = reportSource.getSalesPeople().size();
        String worstSeller = getNameWorstSalesPerson();
        long expensiveSaleID = best.getSaleID();

        return Report
                .builder()
                .customerAmount(String.valueOf(customerAmount))
                .salesPersonAmount(String.valueOf(salesPeopleAmount))
                .expensivePurchaseID(String.valueOf(expensiveSaleID))
                .worstSalesPerson(worstSeller)
                .build();
    }

    private Sale getMostExpensiveSale() {
        return Collections.max(
                reportSource.getSales(), Comparator.comparing(
                        sale -> {
                            return totalSaleValue(sale);
                        }
                )
        );
    }

    private String getNameWorstSalesPerson(){

        Map<String, List<Sale>> salesFromPerson = new HashMap<String, List<Sale>>();

        reportSource.getSalesPeople().forEach(
                person -> {
                    String personName = person.getName();
                    List<Sale> personSales =
                            reportSource
                                    .getSales()
                                    .stream()
                                    .filter(sale -> {
                                                return personName.equals(sale.getSalesPersonName());
                                            }
                                    )
                                    .collect(Collectors.toList());
                    salesFromPerson.put(
                            personName,personSales
                    );
                });

        Map<String, Double> totalValuePerson = new HashMap<String, Double>();

        salesFromPerson.forEach((name, sales) -> {
                    Double total =
                            sales.stream().mapToDouble(
                                    sale -> {
                                        return totalSaleValue(sale);
                                    }
                            ).sum();
                    totalValuePerson.put(name, total);
                }
        );

        return Collections.min(
                totalValuePerson.entrySet(),
                Comparator.comparingDouble(Map.Entry::getValue)
        )
        .getKey();

    }

    private Double totalSaleValue(Sale sale) {
        Double totalSale =
                sale
                .getSaleItems()
                .stream()
                .mapToDouble(
                    saleItem -> {
                        Double totalItems =
                            saleItem
                            .getItemPrice().multiply(
                                    saleItem.getItemQuantity()
                            )
                            .doubleValue();
                        return totalItems;
                    }
                ).sum();
        return totalSale;
    }

}
