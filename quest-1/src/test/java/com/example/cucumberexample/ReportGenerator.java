package com.example.cucumberexample;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {
    public static void main(String[] args) {
        File reportOutputDirectory = new File("target/cucumber-html-reports");
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("target/cucumber-reports/cucumber.json");

        String projectName = "order-promotion";
        boolean runWith
                = true; // Set to true if you want to see the build number in the report
        String buildNumber = "1"; // Replace with your actual build number

        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        // optional configuration
        
        configuration.setBuildNumber(buildNumber);

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();

        System.out.println("Cucumber HTML report generated at: " + reportOutputDirectory.getAbsolutePath());
    }
}
