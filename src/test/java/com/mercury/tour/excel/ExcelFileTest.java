package com.mercury.tour.excel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static org.assertj.core.api.Assertions.assertThat;

/**
    Checking that reading text from cell from Excel file is working
 */

@Suite
@IncludeEngines("junit-jupiter")
@SelectClasspathResource("features/")
@ConfigurationParameters(
        {
                @ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.mercury.tour"),
                @ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"),
        }
)
class ExcelFileTest {

    private final Logger log = LoggerFactory.getLogger(ExcelFileTest.class);

    @Test
    @DisplayName("Working with excel file - read text from cell")
    void getData() {
        // Load the file
        ExcelFile excel = new ExcelFile("/TestData.xlsx");

        // Get text from B:2
        String text = excel.getData(0, 1, 1);
        log.info("B:2 = {}", text);

        // Check text
        Assertions.assertEquals("mercury", text, "Cell 1:1 should be mercury");

        // Get text from C:5
        text = excel.getData(0, 4, 2);
        log.info("C:5 = {}", text);

        // Check text
        assertThat(text)
                .as("Cell 4:2 should be abcdA")
                .isEqualTo("abcdA");

    }

    @Test
    void save() {
        // Load the file
        ExcelFile excel = new ExcelFile("/TestData.xlsx");

        // Set text to B:4
        excel.setData(0, 1, 3, "HI");

        // Create new file and save data
        excel.save();
    }
}