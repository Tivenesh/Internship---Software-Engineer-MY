package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TableController {

    @GetMapping("/")
    public String showTables(Model model) {
        // Table 1 Data
        List<TableEntry> table1 = List.of(
            new TableEntry("A1", 41), new TableEntry("A2", 18), new TableEntry("A3", 21),
            new TableEntry("A4", 63), new TableEntry("A5", 2), new TableEntry("A6", 53),
            new TableEntry("A7", 5), new TableEntry("A8", 57), new TableEntry("A9", 60),
            new TableEntry("A10", 93), new TableEntry("A11", 28), new TableEntry("A12", 3),
            new TableEntry("A13", 90), new TableEntry("A14", 39), new TableEntry("A15", 80),
            new TableEntry("A16", 88), new TableEntry("A17", 49), new TableEntry("A18", 60),
            new TableEntry("A19", 26), new TableEntry("A20", 28)
        );

        // Extract required values
        int A5 = getValue(table1, "A5");
        int A20 = getValue(table1, "A20");
        int A15 = getValue(table1, "A15");
        int A7 = getValue(table1, "A7");
        int A13 = getValue(table1, "A13");
        int A12 = getValue(table1, "A12");

        // Table 2 Computation
        List<TableEntry> table2 = new ArrayList<>();
        table2.add(new TableEntry("Alpha", A5 + A20));
        table2.add(new TableEntry("Beta", A15 / A7));
        table2.add(new TableEntry("Charlie", A13 * A12));

        // Pass tables to HTML
        model.addAttribute("table1", table1);
        model.addAttribute("table2", table2);
        return "index";
    }

    private int getValue(List<TableEntry> table, String index) {
        return table.stream().filter(e -> e.getIndex().equals(index)).findFirst().orElse(new TableEntry("", 0)).getValue();
    }

    public static class TableEntry {
        private String index;
        private int value;

        public TableEntry(String index, int value) {
            this.index = index;
            this.value = value;
        }

        public String getIndex() {
            return index;
        }

        public int getValue() {
            return value;
        }
    }
}
