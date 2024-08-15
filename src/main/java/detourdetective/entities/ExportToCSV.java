package detourdetective.entities;

import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;



public class ExportToCSV {
    private static Logger logger = Logger.getLogger(ExportToCSV.class);

    public static void exportDetoursToCSV(List<List<VehiclePosition>> detours, String fileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Optional: Write header row
            writer.println("Longitude,Latitude,Timestamp");

            int rowCount = 0;
            for (List<VehiclePosition> detour : detours) {
                for (VehiclePosition vp : detour) {
                    writer.printf("%f,%f,%d\n", vp.getPosition_longitude(), vp.getPosition_latitude(), vp.getTimestamp().getTime());

                    // Debugging output
                    logger.info("Added VehiclePosition " + vp + " to row: " + (rowCount + 1));
                    rowCount++;
                }
            }
        }
    }
}
