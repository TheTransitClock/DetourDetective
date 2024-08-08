package detourdetective;

import detourdetective.entities.Shape;
import detourdetective.entities.VehiclePosition;
import junit.framework.TestCase;
import detourdetective.managers.VehiclePositionManager;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class VehiclePositionTest extends TestCase {
    @Test
    public void test_getTripVehiclePositions(){
        LocalDate startDate = LocalDate.of(2024,3,26);
        String tripId = "JG_A4-Weekday-SDon-084600_B16_414";
        List<VehiclePosition> positions = VehiclePositionManager.getTripVehiclePositionsByDate(tripId,startDate);
        for (VehiclePosition position : positions) {
            System.out.println(position);
        }

    }
    @Test
    public void test_getReadTrips(){
        List<VehiclePosition> trips = VehiclePositionManager.readtripVehiclePosition("JG_A4-Weekday-SDon-084600_B16_414","766");
        for (VehiclePosition position : trips) {
            System.out.println(position);
        }
    }
    @Test
    public void test_getTripAndVehicleId(){
        Set<String> tripAndVehcileId = VehiclePositionManager.getTripIdForARoute("B16");
        for (String ids : tripAndVehcileId) {
            System.out.println(ids);
        }
    }
    




}