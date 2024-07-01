package detourdetective.algorithm;

import detourdetective.entities.VehiclePosition;
import detourdetective.managers.TripManager;
import detourdetective.managers.VehiclePositionManager;
import junit.framework.TestCase;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static detourdetective.algorithm.DetourDetector.detectDetours;

public class DistanceToPoylineTest extends TestCase {
	@Test
	public void testDistanceBetweenPointAndPolyline() throws ParseException {
		// create a geometry factory
		GeometryFactory gf = new GeometryFactory();
		// create a point
		Coordinate pointCoord = new Coordinate(10.0, 20.0);
		Point point = gf.createPoint(pointCoord);

		// create a polyline
		Coordinate[] polylineCoords = new Coordinate[]{
				new Coordinate(0.0, 0.0),
				new Coordinate(10.0, 10.0),
				new Coordinate(20.0, 20.0)
		};
		LineString polyline = gf.createLineString(polylineCoords);

		// calculate the distance from the point to the polyline
		double distance = polyline.distance(point);

		System.out.println("Distance: " + distance);

	}
	/*
	Bus that went on a detour.
	 */
	@Test
	public void testDetourDetectionInPlace() throws ParseException, java.text.ParseException {
		String tripBus766 = "JG_A4-Weekday-SDon-084600_B16_414";
		String vehicleId = "766";

		boolean detourDetected = detectDetours(tripBus766, vehicleId);
		if (detourDetected) {
			System.out.println("Detour detected for Vehicle " + vehicleId);
		} else {
			System.out.println("No detour detected for Vehicle " + vehicleId);
		}
	}
	/*
	Bus that didn't on a detour.
	 */
	@Test
	public void testDetourDetectionNotInPlace() throws ParseException, java.text.ParseException {
		String tripBus766 = "JG_A4-Weekday-SDon-132500_B43_480";
		String vehicleId = "766";

		boolean detourDetected = detectDetours(tripBus766, vehicleId);
		if (detourDetected) {
			System.out.println("Detour detected for Vehicle " + vehicleId);
		} else {
			System.out.println("No detour detected for Vehicle " + vehicleId);
		}
	}

}
