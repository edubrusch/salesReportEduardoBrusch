package br.com.eduardo.report.sales.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@Builder
public class SalesInputFile {

    private File salesFile;
    private String separator;
}
