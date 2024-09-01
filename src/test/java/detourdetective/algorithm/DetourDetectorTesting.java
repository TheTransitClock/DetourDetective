package detourdetective.algorithm;

import detourdetective.entities.Detour;
import detourdetective.utils.ExportToCSV;

import detourdetective.entities.VehiclePosition;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

public class DetourDetectorTesting extends TestCase {

	private static Logger logger = Logger.getLogger(DetourDetectorTesting.class);
	/**
	 * Bus on a detour
	 * @throws ParseException
	 */
	@Test
	public void testDetourDetectionInPlaceWithDefault() throws ParseException {
		String tripBus766 = "JG_A4-Weekday-SDon-084600_B16_414";
		String vehicleId = "766";
		String SDate = "2024032623:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		Date date = sdf.parse(SDate);
		String withTimestamp = "yes";
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDefaultImpl");

		List<Detour>  detoursDetected = detourDetector.detectDetours(tripBus766, vehicleId, date, withTimestamp);

		if (detoursDetected != null && !detoursDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);

			// Exporting the results to an Excel file
			try {
				ExportToCSV.exportDetoursToCSV(detoursDetected, "DetoursB16.CSV");
			} catch (IOException e) {
				logger.error("Error exporting detours to CSV", e);
			}

		} else {
			logger.info("No detour detected for Vehicle " + vehicleId);
		}
	}
	
	/**
	 * Bus on a detour
	 * @throws ParseException
	 */
	/*
	@Test
	public void testDetourDetectionInPlaceWithDescreteFrechet() throws ParseException {
		String tripBus766 = "JG_A4-Weekday-SDon-084600_B16_414";
		String vehicleId = "766";
		String SDate = "2024032623:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		Date date = sdf.parse(SDate);
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDiscreteFrechet");

		List<List<VehiclePosition>>  detourDetected = detourDetector.detectDetours(tripBus766, vehicleId, date);

		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);
			for (List<VehiclePosition> vp : detourDetected) {
				logger.info("Off-route Vehicle Position: " + vp);
			}

			// Exporting the results to an Excel file
			try {
				ExportToCSV.exportDetoursToCSV(detourDetected, "C:/Users/andre/Documents/GSoC2024/DetourDetective/DetourCSV/DetoursB16.CSV");
			} catch (IOException e) {
				logger.error("Error exporting detours to CSV", e);
			}

		} else {
			logger.info("No detour detected for Vehicle " + vehicleId);
		}
	}
	/**
	 * Bus not on a detour
	 * @throws ParseException
	 */
	@Test
	public void testDetourDetectionNotInPlaceWithDefault() throws ParseException {
		String tripBus766 = "JG_A4-Weekday-SDon-132500_B43_480";
		String vehicleId = "802";
		String SDate = "2024032623:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		Date date = sdf.parse(SDate);
		String withTimestamp = "yes";
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDefaultImpl");
		List<Detour>  detourDetected = detourDetector.detectDetours(tripBus766, vehicleId, date, withTimestamp);
		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);
			// Exporting the results to an Excel file
			try {
				ExportToCSV.exportDetoursToCSV(detourDetected, "DetoursB43.CSV");
			} catch (IOException e) {
				logger.error("Error exporting detours to CSV", e);
			}

		} else {
			logger.info("No detour detected for Vehicle " + vehicleId);
		}
	}
	/**
	 * Bus not on detour
	 * @throws ParseException
	 */
	/*@Test
	public void testDetourDetectionNotInPlaceWithDiscreteFrechet() throws ParseException {
		String tripBus766 = "JG_A4-Weekday-SDon-132500_B43_480";
		String vehicleId = "802";
		String SDate = "2024032623:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		Date date = sdf.parse(SDate);
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDiscreteFrechet");
		List<List<VehiclePosition>>  detourDetected = detourDetector.detectDetours(tripBus766, vehicleId,date);
		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);
			for (List<VehiclePosition> vp : detourDetected) {
				logger.info("Off-route Vehicle Position: " + vp);
			}

			// Exporting the results to an Excel file
			try {
				ExportToCSV.exportDetoursToCSV(detourDetected, "C:/Users/andre/Documents/GSoC2024/DetourDetective/DetourCSV/DetoursX27.CSV");
			} catch (IOException e) {
				logger.error("Error exporting detours to CSV", e);
			}

		} else {
			logger.info("No detour detected for Vehicle " + vehicleId);
		}
	}

	 */
	/**
	 * Bus on a detour
	 * @throws ParseException
	 */
	@Test
	public void testDetourDetectionInPlace2() throws ParseException {
		String tripBus2453 = "UP_A4-Weekday-SDon-036100_X2737_704";
		String vehicleId = "2453";
		String SDate = "2024032023:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		Date date = sdf.parse(SDate);
		String withTimestamp = "yes";
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDefaultImpl");
		List<Detour>  detourDetected = detourDetector.detectDetours(tripBus2453, vehicleId, date, withTimestamp);

		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);
			// Exporting the results to an Excel file
			try {
				ExportToCSV.exportDetoursToCSV(detourDetected, "DetoursX27.CSV");
			} catch (IOException e) {
				logger.error("Error exporting detours to CSV", e);
			}

		} else {
			logger.info("No detour detected for Vehicle " + vehicleId);
		}
	}

	/**
	 * Bus on a detour
	 * @throws ParseException
	 */
	@Test
	public void testDetourDetectionInPlaceWithDefault3() throws ParseException {
		String tripBus766 = "CA_C4-Weekday-SDon-076500_MISC_314";
		String vehicleId = "8223";
		String SDate = "2024072200:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		Date date = sdf.parse(SDate);
		String withTimestamp = "yes";
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDefaultImpl");

		List<Detour>  detourDetected = detourDetector.detectDetours(tripBus766, vehicleId, date, withTimestamp);

		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);

			// Exporting the results to an Excel file
			try {
				ExportToCSV.exportDetoursToCSV(detourDetected, "DetoursCA_C4-Weekday-SDon-076500_MISC_314.CSV");
			} catch (IOException e) {
				logger.error("Error exporting detours to Excel", e);
			}

		} else {
			logger.info("No detour detected for Vehicle " + vehicleId);
		}
	}
	/**
	 * Bus not on a detour
	 * @throws ParseException
	 */
	@Test
	public void testDetourDetectionNotInPlaceWithDefault3() throws ParseException {
		String tripBus766 = "CA_C4-Sunday-144000_MISC_374";
		String vehicleId = "8210";
		String SDate = "2024072100:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		Date date = sdf.parse(SDate);
		String withTimestamp = "yes";
		int onRouteCount = 3;
		int offRouteCount =7;
		int distance = 30;
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDefaultImpl");

		List<Detour>  detourDetected = detourDetector.detectDetours(tripBus766, vehicleId, date,withTimestamp,distance,onRouteCount,offRouteCount);

		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);

			// Exporting the results to an Excel file
			try {
				ExportToCSV.exportDetoursToCSV(detourDetected, "DetoursCA_C4-Sunday-144000_MISC_374.CSV");
			} catch (IOException e) {
				logger.error("Error exporting detours to CSV", e);
			}

		} else {
			logger.info("No detour detected for Vehicle " + vehicleId);
		}
	}
	/**
	 * Bus on a detour
	 * @throws ParseException
	 */
	@Test
	public void testDetourDetectionInPlaceWithDefault3TestingToSeeIfItFiltersVPBeforeStartTime() throws ParseException {
		String tripBus766 = "CA_C4-Weekday-SDon-080000_MISC_243";
		String vehicleId = "8705";
		String SDate = "2024072200:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		Date date = sdf.parse(SDate);
		String withTimestamp = "yes";
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDefaultImpl");

		List<Detour>  detourDetected = detourDetector.detectDetours(tripBus766, vehicleId, date, withTimestamp);

		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);

			// Exporting the results to an Excel file
			try {
				ExportToCSV.exportDetoursToCSV(detourDetected, "DetoursCA_C4-Weekday-SDon-080000_MISC_243.CSV");
			} catch (IOException e) {
				logger.error("Error exporting detours to CSV", e);
			}

		} else {
			logger.info("No detour detected for Vehicle " + vehicleId);
		}
	}
	/**
	 * Bus not on a detour
	 * @throws ParseException
	 */
	@Test
	public void testDetourDetectionNotInPlaceWithDefault3TestingToSeeIfItFiltersVPBeforeStartTimeRouteS76() throws ParseException {
		String tripBus766 = "CA_C4-Weekday-SDon-100500_S7686_141";
		String vehicleId = "7051";
		String SDate = "2024072200:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		Date date = sdf.parse(SDate);
		String withTimestamp = "yes";
		DetourDetector detourDetector=DetourDetectorFactory.getInstance("detourdetective.algorithm.DetourDetectorDefaultImpl");

		List<Detour>  detourDetected = detourDetector.detectDetours(tripBus766, vehicleId, date, withTimestamp);

		if (detourDetected != null && !detourDetected.isEmpty()) {
			logger.info("Detour detected for Vehicle " + vehicleId);

			// Exporting the results to an Excel file
			try {
				ExportToCSV.exportDetoursToCSV(detourDetected, "DetoursCA_C4-Weekday-SDon-100500_S7686_141.CSV");
			} catch (IOException e) {
				logger.error("Error exporting detours to CSV", e);
			}

		} else {
			logger.info("No detour detected for Vehicle " + vehicleId);
		}
	}

}
