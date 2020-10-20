package br.com.eduardo.report.sales.service;

import br.com.eduardo.report.sales.model.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class InputDataProcessService {

    public ReportDTO processSalesInputFile(SalesInputFile inputFile) {

        ArrayList<String> lines = new ArrayList<String>();

        try {
            Stream<String> stream = Files.lines(inputFile.getSalesFile().toPath());
            stream.forEach(lines::add);

        } catch (IOException e) {
            throw new RuntimeException("Please verify the correct file path or if the contents are null", e);
        }

        return categorizeFile(lines);
    }

    private ReportDTO categorizeFile(ArrayList<String> lines) {

        List<SalesPerson> salesPeople = new ArrayList<SalesPerson>();
        List<Customer> customers = new ArrayList<Customer>();
        List<Sale> sales = new ArrayList<Sale>();

        String separator = "ç";

        for (String line : lines) {
            String[] lineItems = line.split(separator);
            if(lineItems.length > 4) {
                throw new RuntimeException("Please verify the sales file. line %s doesn't seem to be valid.");
            }
            int code = Integer.parseInt(lineItems[0]);

            switch (code) {
                case 001: {
                    salesPeople.add(
                            SalesPerson.builder()
                                    .CPF(lineItems[1])
                                    .Name(lineItems[2])
                                    .Salary(new BigDecimal(lineItems[3]))
                                    .build()
                    );
                    break;
                }
                case 002: {
                    customers.add(
                            Customer.builder()
                                    .CNPJ(lineItems[1])
                                    .name(lineItems[2])
                                    .businessArea(lineItems[3])
                                    .build()
                    );
                    break;
                }

                case 003: {
                    String[] saleItems = lineItems[2]
                            .replace("[", "")
                            .replace("]","")
                            .split(",");
                    List<SaleItem> saleItemsResult = new ArrayList<SaleItem>();

                    for (String item : saleItems) {
                        String[] itemDetails = item.split("-");

                        SaleItem currentSaleItem =
                        SaleItem.builder()
                                .itemID(Long.parseLong(itemDetails[0]))
                                .itemPrice(new BigDecimal(itemDetails[1]))
                                .itemQuantity(new BigDecimal(itemDetails[2]))
                                .build();
                        saleItemsResult.add(currentSaleItem);
                    }

                    sales.add(
                            Sale.builder()
                                    .saleID(Long.parseLong(lineItems[1]))
                                    .saleItems(saleItemsResult)
                                    .SalesPersonName(lineItems[1])
                                    .build()
                    );
                    break;
                }
                default:
                    throw new RuntimeException(
                            "Please verify the sales file. File codes must be either 001, 002 or 003."
                    );

            }
        }

        return ReportDTO.builder()
                .customers(customers)
                .sales(sales)
                .salesPeople(salesPeople)
                .build();
    }
}
