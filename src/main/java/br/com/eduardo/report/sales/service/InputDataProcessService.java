package br.com.eduardo.report.sales.service;

import br.com.eduardo.report.sales.model.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class InputDataProcessService {

    public ReportDTO processSalesInputFile(File inputFile) {

        ArrayList<String> lines = new ArrayList<String>();
        try {
            Stream<String> stream = Files.lines(inputFile.toPath());
            stream.forEach(lines::add);

        } catch (IOException e) {
            throw new RuntimeException("Please verify the correct file path or if the contents are null", e);
        }
        ReportDTO result =  categorizeFile(lines);
        result.setFileName(inputFile.getName());

        return result;
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
                    salesPeople.add(fillSalesPeople(lineItems));
                    break;
                }
                case 002: {
                    customers.add(fillCustomer(lineItems));
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
                        saleItemsResult.add(fillSaleDetails(itemDetails));
                    }
                    sales.add(fillSale(lineItems, saleItemsResult));
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

    private SalesPerson fillSalesPeople(String[] lineItems) {
        return SalesPerson.builder()
                .CPF(lineItems[1])
                .Name(lineItems[2])
                .Salary(new BigDecimal(lineItems[3]))
                .build();
    }

    private Customer fillCustomer(String[] lineItems) {
        return Customer.builder()
                .CNPJ(lineItems[1])
                .name(lineItems[2])
                .businessArea(lineItems[3])
                .build();
    }

    private Sale fillSale(String[] lineItems, List<SaleItem> saleItemsResult) {
        return Sale.builder()
                .saleID(Long.parseLong(lineItems[1]))
                .saleItems(saleItemsResult)
                .SalesPersonName(lineItems[3])
                .build();
    }

    private SaleItem fillSaleDetails(String[] itemDetails) {
        return SaleItem.builder()
                .itemID(Long.parseLong(itemDetails[0]))
                .itemQuantity(new BigDecimal(itemDetails[1]))
                .itemPrice(new BigDecimal(itemDetails[2]))
                .build();
    }
}
