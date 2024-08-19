package detourdetective.entities;

import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;



public class ExportToCSV {
    private static Logger logger = Logger.getLogger(ExportToCSV.class);

    public static void exportDetoursToCSV(List<List<VehiclePosition>> detours, String fileName) throws IOException {
        logger.info("File:" +fileName);
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Optional: Write header row
            writer.println("Longitude,Latitude,Timestamp");
            
            
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd hh:mm:ss");
            
            

            int rowCount = 0;
            for (List<VehiclePosition> detour : detours) {
                for (VehiclePosition vp : detour) {
                    writer.printf("%f,%f,%s\n", vp.getPosition_longitude(), vp.getPosition_latitude(), formatter.format( vp.getTimestamp()));

                   
                    // Debugging output
                    logger.debug("Added VehiclePosition " + vp + " to row: " + (rowCount + 1));
                    rowCount++;
                }
            }
        }
    }
}
