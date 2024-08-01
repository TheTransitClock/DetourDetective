package detourdetective.algorithm;

import detourdetective.entities.TripVehicle;
import detourdetective.entities.VehiclePosition;
import junit.framework.TestCase;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import detourdetective.managers.VehiclePositionManager;

public class DetourDetectorTesting extends TestCase {

	private static Logger logger = Logger.getLogger(DetourDetectorTesting.class);
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
	public void testDetourDetectionInPlaceWithDefault() throws ParseException, java.text.ParseException {
		String tripBus766 = "JG_A4-Weekday-SDon-084600_B16_414";
		String vehicleId = "766";
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDefaultImpl");

		List<List<VehiclePosition>>  detourDetected = detourDetector.detectDetours(tripBus766, vehicleId);

		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);
			for (List<VehiclePosition> vp : detourDetected) {
				logger.info("Off-route Vehicle Position: " + vp);
			}

			// Exporting the results to an Excel file
			try {
				((DetourDetectorDefaultImpl) detourDetector).exportDetoursToExcel(detourDetected, "Detours1Default.xlsx");
			} catch (IOException e) {
				logger.error("Error exporting detours to Excel", e);
			}

		} else {
			logger.info("No detour detected for Vehicle " + vehicleId);
		}
	}
	@Test
	public void testDetourDetectionInPlaceWithDescreteFrechet() throws ParseException, java.text.ParseException {
		String tripBus766 = "JG_A4-Weekday-SDon-084600_B16_414";
		String vehicleId = "766";
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDiscreteFrechet");

		List<List<VehiclePosition>>  detourDetected = detourDetector.detectDetours(tripBus766, vehicleId);

		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);
			for (List<VehiclePosition> vp : detourDetected) {
				logger.info("Off-route Vehicle Position: " + vp);
			}

			// Exporting the results to an Excel file
			try {
				((DetourDetectorDefaultImpl) detourDetector).exportDetoursToExcel(detourDetected, "Detours.xlsx");
			} catch (IOException e) {
				logger.error("Error exporting detours to Excel", e);
			}

		} else {
			logger.info("No detour detected for Vehicle " + vehicleId);
		}
	}
	/*
	Bus that didn't on a detour.
	 */
	@Test
	public void testDetourDetectionNotInPlaceWithDefault() throws ParseException, java.text.ParseException {
		String tripBus766 = "JG_A4-Weekday-SDon-132500_B43_480";
		String vehicleId = "802";
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDefaultImpl");
		List<List<VehiclePosition>>  detourDetected = detourDetector.detectDetours(tripBus766, vehicleId);
		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);
			for (List<VehiclePosition> vp : detourDetected) {
				logger.info("Off-route Vehicle Position: " + vp);
			}

			// Exporting the results to an Excel file
			try {
				((DetourDetectorDefaultImpl) detourDetector).exportDetoursToExcel(detourDetected, "Detours.xlsx");
			} catch (IOException e) {
				logger.error("Error exporting detours to Excel", e);
			}

		} else {
			logger.info("No detour detected for Vehicle " + vehicleId);
		}
	}
	@Test
	public void testDetourDetectionNotInPlaceWithDiscreteFrechet() throws ParseException, java.text.ParseException {
		String tripBus766 = "JG_A4-Weekday-SDon-132500_B43_480";
		String vehicleId = "802";
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDiscreteFrechet");
		List<List<VehiclePosition>>  detourDetected = detourDetector.detectDetours(tripBus766, vehicleId);
		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);
			for (List<VehiclePosition> vp : detourDetected) {
				logger.info("Off-route Vehicle Position: " + vp);
			}

			// Exporting the results to an Excel file
			try {
				((DetourDetectorDefaultImpl) detourDetector).exportDetoursToExcel(detourDetected, "Detours.xlsx");
			} catch (IOException e) {
				logger.error("Error exporting detours to Excel", e);
			}

		} else {
			logger.info("No detour detected for Vehicle " + vehicleId);
		}
	}
	/*
	Bus that went on a detour.
	 */
	@Test
	public void testDetourDetectionInPlace2() throws ParseException, java.text.ParseException {
		String tripBus2453 = "UP_A4-Weekday-SDon-036100_X2737_704";
		//String tripBus2453 = "UP_A4-Weekday-SDon-043700_X2737_720";
		//String tripBus2472 = "UP_A4-Weekday-SDon-140000_X2737_733";
		//String vehicleId = "2457";
		String vehicleId = "2453";
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDefaultImpl");
		List<List<VehiclePosition>>  detourDetected = detourDetector.detectDetours(tripBus2453, vehicleId);

		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);
			for (List<VehiclePosition> vp : detourDetected) {
				logger.info("Off-route Vehicle Position: " + vp);
			}

			// Exporting the results to an Excel file
			try {
				((DetourDetectorDefaultImpl) detourDetector).exportDetoursToExcel(detourDetected, "Detours.xlsx");
			} catch (IOException e) {
				logger.error("Error exporting detours to Excel", e);
			}

		} else {
			logger.info("No detour detected for Vehicle " + vehicleId);
		}
	}
	/*
	Bus that didn't on a detour.
	 */
	@Test
	public void testDetourDetectionNotInPlace2() throws ParseException, java.text.ParseException {
		String tripBus2453NoDetour = "UP_A4-Weekday-SDon-043700_X2737_720";
		String vehicleId = "2457";
		
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDefaultImpl");
		List<List<VehiclePosition>>  detourDetected = detourDetector.detectDetours(tripBus2453NoDetour, vehicleId);
		if (detourDetected != null && !detourDetected.isEmpty()) {
			System.out.println("Detour detected for Vehicle " + vehicleId);
			for (List<VehiclePosition> vp : detourDetected) {
				System.out.println("Off-route Vehicle Position: " + vp);
			}
			// Exporting the results to an Excel file
			try {
				((DetourDetectorDefaultImpl) detourDetector).exportDetoursToExcel(detourDetected, "Detours.xlsx");
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("No detour detected for Vehicle " + vehicleId);
		}
	}
	@Test
	public void testForDetoursWithGivenDateUsingDefaultAlgorithm() throws ParseException, java.text.ParseException {
		LocalDate date = LocalDate.of(2024, 3, 27);

		Set<TripVehicle> tripSet = VehiclePositionManager.tripsByDate(date);

		for (TripVehicle vehicle : tripSet) {
			System.out.println(vehicle);
		}
	}

}
