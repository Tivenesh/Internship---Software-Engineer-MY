package com.example.laughing.service;

import com.example.laughing.model.TableData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class TableService {

    public List<TableData> parseCSV(MultipartFile file) throws Exception {
        List<TableData> tableDataList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            CSVParser csvParser = CSVParser.parse(reader, CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build());

            for (CSVRecord record : csvParser) {
                if (record.size() >= 2) { // Ensure there are at least 2 columns
                    String column1 = record.get(0);
                    int column2;
                    try {
                        column2 = Integer.parseInt(record.get(1));
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid number: " + record.get(1));
                        continue;
                    }
                    tableDataList.add(new TableData(column1, column2));
                }
            }
        }
        return tableDataList;
    }

    public int calculateAlpha(List<TableData> tableDataList) {
        return (tableDataList.size() > 20) ? tableDataList.get(5).getColumn2() + tableDataList.get(20).getColumn2() : 0;
    }

    public int calculateBeta(List<TableData> tableDataList) {
        return (tableDataList.size() > 15 && tableDataList.size() > 7) ?
                tableDataList.get(15).getColumn2() / tableDataList.get(7).getColumn2() : 0;
    }

    public int calculateCharlie(List<TableData> tableDataList) {
        return (tableDataList.size() > 13 && tableDataList.size() > 12) ?
                tableDataList.get(13).getColumn2() * tableDataList.get(12).getColumn2() : 0;
    }
}
