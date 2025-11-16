package Pages;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import io.qameta.allure.Step;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {

    @Step("قراءة بيانات الإكسل من الملف: {filePath} والشيت: {sheetName}")
    public static List<String[]> readExcel(String filePath, String sheetName) {
        List<String[]> data = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                System.err.println("Sheet not found: " + sheetName);
                return data;
            }

            // ابدأ من الصف 1 لتخطي الـ Header
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell mobileCell = row.getCell(0);
                Cell passwordCell = row.getCell(1);

                String mobile = getCellStringValue(mobileCell);
                String password = getCellStringValue(passwordCell);

                data.add(new String[]{mobile, password});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    private static String getCellStringValue(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double value = cell.getNumericCellValue();
                    if (value == (long) value) {
                        return String.valueOf((long) value);
                    } else {
                        return String.valueOf(value);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
