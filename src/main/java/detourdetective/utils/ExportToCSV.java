package detourdetective.utils;

import detourdetective.entities.Detour;
import detourdetective.entities.VehiclePosition;
import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;



public class ExportToCSV {
    private static final Logger logger = Logger.getLogger(ExportToCSV.class);

    public static void exportDetoursToCSV(List<Detour> detours, String fileName) throws IOException {
        logger.info("File:" +fileName);
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Optional: Write header row
            writer.println("Detour #,Trip Id,Longitude,Latitude,Vehicle Id,Time");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd HH:mm:ss");

            int rowCount = 1;
            for (Detour detour : detours) {
                for (VehiclePosition vp : detour.getVehiclePositions()) {
                    writer.printf("%d,%s,%f,%f,%s,%s\n",rowCount,vp.getTrip_id(), vp.getPosition_longitude(), vp.getPosition_latitude(),vp.getVehicle_id(), formatter.format( vp.getTimestamp()));

                   
                    // Debugging output
                    logger.debug("Added VehiclePosition " + vp + " to row: " + (rowCount));

                }
                rowCount++;
            }
        }
    }
}
