package com.mercury.tour.excel;

import com.google.common.io.Files;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Objects;

public class ExcelFile implements AutoCloseable {

    final private XSSFWorkbook excel;
    final private boolean autoSave;

    public ExcelFile(String resourceFileName) {
        this(resourceFileName, false);
    }

    /**
     * Creating a constructor of the same class that would load Excel file using the File I/O stream
     */
    public ExcelFile(String resourceFileName, boolean autoSave) {
        this.autoSave = autoSave;
        InputStream inputStream = ExcelFile.class.getResourceAsStream(resourceFileName);

        // NullPointerException check
        Objects.requireNonNull(inputStream, "Cannot find file " + resourceFileName + " in resource directory");

        try {
            excel = new XSSFWorkbook(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Reading Excel file error " + resourceFileName, e);
        }
    }

    /**
     * This method would fetch the data from the corresponding cell of the Excel sheet and return as a String.
     */
    @Step("Get value from Excel cell {row}:{column}")
    public String getData(int sheetIndex, int row, int column) {
        return excel
                .getSheetAt(sheetIndex)
                .getRow(row)
                .getCell(column)
                .getStringCellValue();
    }

    @Step("Set value to Excel cell {row}:{column} = {text}")
    public void setData(int sheetIndex, int row, int column, String text) {
        Row rowObject = excel.getSheetAt(sheetIndex)
                .getRow(row);

        Cell cellObject = rowObject
                .getCell(column);

        if (cellObject == null) cellObject = rowObject.createCell(column);

        cellObject.setCellValue(text);
    }

    @Step("Save Excel to file {file}")
    public File save(File file) {

        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs(); // mk-make directory
                file.createNewFile();
            } catch (IOException error) {
                throw new RuntimeException("Saving Excel to file error " + file, error);
            }
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            excel.write(fileOutputStream);
            try (InputStream fileStream = new FileInputStream(file)) {
                Allure.addAttachment(file.getName(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", fileStream, "xlsx");
            }
        } catch (IOException error) {
            throw new RuntimeException("Saving Excel to file error " + file, error);
        }
        return file;
    }

    public File save() {
        // Timestamp
        long suffix = System.currentTimeMillis();

        // New file with new excel with test results - PASSED or FAILED
        File newExcelFile = new File("./result/test-result-" + suffix + ".xlsx");

        save(newExcelFile);

        return newExcelFile;
    }

    @Override
    public void close() throws Exception {
        if (autoSave) save();
        excel.close();
    }
}