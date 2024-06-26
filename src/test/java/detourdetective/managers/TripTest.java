package detourdetective.managers;

import detourdetective.entities.Shape;
import detourdetective.entities.Trip;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class TripTest extends TestCase {
    @Test
    public void testReadTripShapes(){
        String tripId = "JG_A4-Weekday-SDon-084600_B16_414";
        TripManager.readTripShape(tripId);
        
    }

}