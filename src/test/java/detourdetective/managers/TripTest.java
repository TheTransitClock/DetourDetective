package detourdetective.managers;

import detourdetective.entities.Shape;
import detourdetective.entities.Trip;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TripTest extends TestCase {
   @Test
    public void testReadTripShapes(){
        String tripId = "JG_A4-Weekday-SDon-084600_B16_414";
        TripManager.readTripShape(tripId);
        
    }
    
    @Test
    public void testReadLatAndLong(){
        String tripId = "JG_A4-Weekday-SDon-084600_B16_414";
        System.out.println(TripManager.readShapeLatAndLong(tripId));
        
        
    }
    @Test
    public void testTripsByDate(){
        LocalDate date = LocalDate.of(2024,03,27);
        System.out.println(VehiclePositionManager.tripsByDate(date));


    }
    

}