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

//    private Sale getMostExpensiveSale() {
//        return Collections.max(
//                reportSource.getSales(), Comparator.comparing(
//                        s-> s.getSaleItems()
//                                .stream()
//                                .flatMapToLong(
//                                        saleItem -> {
//                                            BigDecimal total =
//                                                    saleItem
//                                                            .getItemPrice()
//                                                            .multiply(
//                                                                    saleItem.getItemQuantity()
//                                                            );
//                                            return (LongStream) total;
//                                        }
//                                )
//                        .sum()
//                )
//        );
//    }




//    private String getNameWorstSalesPerson() {
//
//        Map<String, List<Sale>> salesFromPerson = new HashMap<String, List<Sale>>();
//
//        reportSource.getSalesPeople().forEach(
//                person -> {
//                    String personName = person.getName();
//                    salesFromPerson.put(
//                            personName,
//                            reportSource
//                                    .getSales()
//                                    .stream()
//                                    .filter(sale -> {
//                                                return personName.equals(sale.getSalesPersonName());
//                                            }
//                                    )
//                                    .collect(Collectors.toList())
//                    );
//                });
//
//        Map<String, Double> totalValuePerson = new HashMap<String, Double>();
//
//        salesFromPerson.forEach((name, sales) -> {
//                    Double total =
//                            sales.stream().mapToDouble(
//                                    sale -> {
//                                        Double totalSales =
//                                                sale
//                                                        .getSaleItems()
//                                                        .stream()
//                                                        .mapToDouble(
//                                                                saleItem -> {
//                                                                    Double total =
//                                                                            saleItem
//                                                                                    .getItemPrice()
//                                                                                    .multiply(
//                                                                                            saleItem.getItemQuantity()
//                                                                                    )
//                                                                                    .doubleValue();
//                                                                    return total;
//                                                                }
//                                                        ).sum();
//                                        return totalSales;
//                                    }
//                            ).sum();
//                    totalValuePerson.put(name, total);
//                }
//        );
//
//        return Collections.min(totalValuePerson.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
//
//    }

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


//   O conteúdo do arquivo de saída deve resumir os seguintes dados:
//   Quantidade de clientes no arquivo de entrada
//   Quantidade de vendedor no arquivo de entrada
//   ID da venda mais cara
//   O pior vendedor


}
