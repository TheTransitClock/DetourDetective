package detourdetective.algorithm;

import detourdetective.entities.ExportToCSV;
import detourdetective.entities.TripVehicle;
import detourdetective.entities.VehiclePosition;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

import detourdetective.managers.VehiclePositionManager;

public class DetourDetectorTesting extends TestCase {

	private static Logger logger = Logger.getLogger(DetourDetectorTesting.class);
	/*
	Bus that went on a detour.
	 */
	@Test
	public void testDetourDetectionInPlaceWithDefault() throws ParseException {
		String tripBus766 = "JG_A4-Weekday-SDon-084600_B16_414";
		String vehicleId = "766";
		String SDate = "2024032623:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		Date date = sdf.parse(SDate);
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDefaultImpl");

		List<List<VehiclePosition>>  detourDetected = detourDetector.detectDetours(tripBus766, vehicleId, date);

		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);
			for (List<VehiclePosition> vp : detourDetected) {
				logger.info("Off-route Vehicle Position: " + vp);
			}

			// Exporting the results to an Excel file
			try {
				ExportToCSV.exportDetoursToCSV(detourDetected, "Detours1Default.CSV");
			} catch (IOException e) {
				logger.error("Error exporting detours to Excel", e);
			}

		} else {
			logger.info("No detour detected for Vehicle " + vehicleId);
		}
	}
	@Test
	public void testDetourDetectionInPlaceWithDescreteFrechet() {
		String tripBus766 = "JG_A4-Weekday-SDon-084600_B16_414";
		String vehicleId = "766";
		Date date = new Date(2024-3-20);
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDiscreteFrechet");

		List<List<VehiclePosition>>  detourDetected = detourDetector.detectDetours(tripBus766, vehicleId, date);

		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);
			for (List<VehiclePosition> vp : detourDetected) {
				logger.info("Off-route Vehicle Position: " + vp);
			}

			// Exporting the results to an Excel file
			try {
				ExportToCSV.exportDetoursToCSV(detourDetected, "Detours.xlsx");
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
	public void testDetourDetectionNotInPlaceWithDefault()  {
		String tripBus766 = "JG_A4-Weekday-SDon-132500_B43_480";
		String vehicleId = "802";
		Date date = new Date(2024-3-20);
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDefaultImpl");
		List<List<VehiclePosition>>  detourDetected = detourDetector.detectDetours(tripBus766, vehicleId,date);
		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);
			for (List<VehiclePosition> vp : detourDetected) {
				logger.info("Off-route Vehicle Position: " + vp);
			}

			// Exporting the results to an Excel file
			try {
				ExportToCSV.exportDetoursToCSV(detourDetected, "Detours.xlsx");
			} catch (IOException e) {
				logger.error("Error exporting detours to Excel", e);
			}

		} else {
			logger.info("No detour detected for Vehicle " + vehicleId);
		}
	}
	@Test
	public void testDetourDetectionNotInPlaceWithDiscreteFrechet() {
		String tripBus766 = "JG_A4-Weekday-SDon-132500_B43_480";
		String vehicleId = "802";
		Date date = new Date(2024-3-20);
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDiscreteFrechet");
		List<List<VehiclePosition>>  detourDetected = detourDetector.detectDetours(tripBus766, vehicleId,date);
		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);
			for (List<VehiclePosition> vp : detourDetected) {
				logger.info("Off-route Vehicle Position: " + vp);
			}

			// Exporting the results to an Excel file
			try {
				ExportToCSV.exportDetoursToCSV(detourDetected, "Detours.xlsx");
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
	public void testDetourDetectionInPlace2() throws ParseException {
		String tripBus2453 = "UP_A4-Weekday-SDon-036100_X2737_704";
		//String tripBus2453 = "UP_A4-Weekday-SDon-043700_X2737_720";
		//String tripBus2472 = "UP_A4-Weekday-SDon-140000_X2737_733";
		//String vehicleId = "2457";
		String vehicleId = "2453";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("20240319");
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDefaultImpl");
		List<List<VehiclePosition>>  detourDetected = detourDetector.detectDetours(tripBus2453, vehicleId, date);

		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);
			for (List<VehiclePosition> vp : detourDetected) {
				logger.info("Off-route Vehicle Position: " + vp);
			}

			// Exporting the results to an Excel file
			try {
				ExportToCSV.exportDetoursToCSV(detourDetected, "C:/Users/andre/Documents/GSoC2024/DetourDetective/DetourCSV/DetoursX27.CSV");
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
	public void testDetourDetectionNotInPlace2(){
		String tripBus2453NoDetour = "UP_A4-Weekday-SDon-043700_X2737_720";
		String vehicleId = "2457";
		Date date = new Date(2024-3-20);
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDefaultImpl");
		List<List<VehiclePosition>>  detourDetected = detourDetector.detectDetours(tripBus2453NoDetour, vehicleId,date);
		if (detourDetected != null && !detourDetected.isEmpty()) {
			System.out.println("Detour detected for Vehicle " + vehicleId);
			for (List<VehiclePosition> vp : detourDetected) {
				System.out.println("Off-route Vehicle Position: " + vp);
			}
			// Exporting the results to an Excel file
			try {
				ExportToCSV.exportDetoursToCSV(detourDetected, "Detours.CSV");
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("No detour detected for Vehicle " + vehicleId);
		}
	}
	@Test
	public void testDetourDetectionInPlaceWithDefault3() throws ParseException {
		String tripBus766 = "CA_C4-Weekday-SDon-076500_MISC_314";
		String vehicleId = "8223";
		String SDate = "2024072200:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		Date date = sdf.parse(SDate);
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDefaultImpl");

		List<List<VehiclePosition>>  detourDetected = detourDetector.detectDetours(tripBus766, vehicleId, date);

		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);
			for (List<VehiclePosition> vp : detourDetected) {
				logger.info("Off-route Vehicle Position: " + vp);
			}

			// Exporting the results to an Excel file
			try {
				ExportToCSV.exportDetoursToCSV(detourDetected, "Detours3Default.CSV");
			} catch (IOException e) {
				logger.error("Error exporting detours to Excel", e);
			}

		} else {
			logger.info("No detour detected for Vehicle " + vehicleId);
		}
	}
	@Test
	public void testDetourDetectionNotInPlaceWithDefault3() throws ParseException {
		String tripBus766 = "CA_C4-Sunday-144000_MISC_374";
		String vehicleId = "8210";
		String SDate = "2024072100:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		Date date = sdf.parse(SDate);
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDefaultImpl");

		List<List<VehiclePosition>>  detourDetected = detourDetector.detectDetours(tripBus766, vehicleId, date);

		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);
			for (List<VehiclePosition> vp : detourDetected) {
				logger.info("Off-route Vehicle Position: " + vp);
			}

			// Exporting the results to an Excel file
			try {
				ExportToCSV.exportDetoursToCSV(detourDetected, "Detours3Default.CSV");
			} catch (IOException e) {
				logger.error("Error exporting detours to Excel", e);
			}

		} else {
			logger.info("No detour detected for Vehicle " + vehicleId);
		}
	}
	@Test
	public void testDetourDetectionInPlaceWithDefault3TestingToSeeIfItFiltersVPBeforeStartTime() throws ParseException {
		String tripBus766 = "CA_C4-Weekday-SDon-080000_MISC_243";
		String vehicleId = "8713";
		String SDate = "2024072200:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		Date date = sdf.parse(SDate);
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDefaultImpl");

		List<List<VehiclePosition>>  detourDetected = detourDetector.detectDetours(tripBus766, vehicleId, date);

		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);
			for (List<VehiclePosition> vp : detourDetected) {
				logger.info("Off-route Vehicle Position: " + vp);
			}

			// Exporting the results to an Excel file
			try {
				ExportToCSV.exportDetoursToCSV(detourDetected, "Detours3Default.CSV");
			} catch (IOException e) {
				logger.error("Error exporting detours to Excel", e);
			}

		} else {
			logger.info("No detour detected for Vehicle " + vehicleId);
		}
	}
	@Test
	public void testDetourDetectionInPlaceWithDefault3TestingToSeeIfItFiltersVPBeforeStartTimeRouteS76() throws ParseException {
		String tripBus766 = "CA_C4-Weekday-SDon-100500_S7686_141";
		String vehicleId = "7051";
		String SDate = "2024072200:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		Date date = sdf.parse(SDate);
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDefaultImpl");

		List<List<VehiclePosition>>  detourDetected = detourDetector.detectDetours(tripBus766, vehicleId, date);

		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);
			for (List<VehiclePosition> vp : detourDetected) {
				logger.info("Off-route Vehicle Position: " + vp);
			}

			// Exporting the results to an Excel file
			try {
				ExportToCSV.exportDetoursToCSV(detourDetected, "Detours3Default.CSV");
			} catch (IOException e) {
				logger.error("Error exporting detours to Excel", e);
			}

		} else {
			logger.info("No detour detected for Vehicle " + vehicleId);
		}
	}

}
