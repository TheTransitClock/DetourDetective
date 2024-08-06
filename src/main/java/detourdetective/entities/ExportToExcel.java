package detourdetective.entities;

import detourdetective.algorithm.DetourDetectorDefaultImpl;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;



public class ExportToExcel {
    private static Logger logger = Logger.getLogger(ExportToExcel.class);
    public static void exportDetoursToExcel(List<List<VehiclePosition>> detours, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Detours");

        int rowCount = 0;
        for (List<VehiclePosition> detour : detours) {
            for (VehiclePosition vp : detour) {
                Row row = sheet.createRow(rowCount++);
                createCell(row, 0, vp.getPosition_longitude());
                createCell(row, 1, vp.getPosition_latitude());
                // Add more cells if VehiclePosition has more attributes

                // Debugging output
                logger.info("Added VehiclePosition " +vp + " to row: " + rowCount );
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            workbook.write(outputStream);
        } finally {
            workbook.close();
        }
    }

    private static void createCell(Row row, int column, double value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
    }
}
