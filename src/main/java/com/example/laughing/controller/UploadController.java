package com.example.laughing.controller;

import com.example.laughing.model.TableData;
import com.example.laughing.service.TableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
public class UploadController {

    private final TableService tableService;

    public UploadController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, HttpSession session, Model model) {
        try {
            List<TableData> tableData = tableService.parseCSV(file);
            session.setAttribute("uploadedData", tableData); // Store data in session

            model.addAttribute("tableData", tableData);
            model.addAttribute("showProcessButton", true); // Show process button after upload

        } catch (Exception e) {
            model.addAttribute("message", "Error processing file: " + e.getMessage());
            return "index";
        }

        return "index"; // Stay on upload page after file upload
    }

    @PostMapping("/process")
    public String processData(HttpSession session, Model model) {
        List<TableData> tableData = (List<TableData>) session.getAttribute("uploadedData");

        if (tableData == null || tableData.isEmpty()) {
            model.addAttribute("message", "No data available. Please upload a file first.");
            return "index";
        }

        int alpha = tableService.calculateAlpha(tableData);
        int beta = tableService.calculateBeta(tableData);
        int charlie = tableService.calculateCharlie(tableData);

        model.addAttribute("tableData", tableData);
        model.addAttribute("alpha", alpha);
        model.addAttribute("beta", beta);
        model.addAttribute("charlie", charlie);

        return "result";
    }
}
