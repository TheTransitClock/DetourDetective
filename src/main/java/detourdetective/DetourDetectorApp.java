package detourdetective;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


import detourdetective.algorithm.DetourDetector;
import detourdetective.algorithm.DetourDetectorDefaultImpl;
import detourdetective.entities.Detour;
import detourdetective.utils.ExportToCSV;
import detourdetective.entities.TripVehicle;
import detourdetective.entities.VehiclePosition;
import detourdetective.managers.TripManager;
import detourdetective.managers.VehiclePositionManager;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

public class DetourDetectorApp {

	private static Logger logger = Logger.getLogger(DetourDetectorApp.class);
	public static void main(String[] args) {

		CommandLineParser parser = new DefaultParser();

		// create the Options
		Options options = new Options();
		options.addOption("R", "route", true, "This is the route we want to check..");
		options.addOption("D", "date", true, "The date we want to check this route on..");
		options.addOption("T", "tripId", true, "The trip id we will be checking..");
		options.addOption("V", "vehicleId", true, "The vehicle id we will be checking on the trip id provided.");
		options.addOption("F", "filename", true, "The filename of where the output will be stored.");
		options.addOption("L", "directory", true, "The directory where the CSV file will be stored.");
		options.addOption("A", "onRouteThreshold", true, "The number of times the vehicle appears on route when it returns from the detour to say the detour has ended.");
		options.addOption("B", "offRouteThreshold", true, "The number of times the vehicle appears off route to say it is on a detour");
		options.addOption("S", "distance", true, "The distance a vehicle appears off route to say it is on a detour");
		options.addOption("W", "withTimestamp", true, "If you just want the vehicle GPS points from the Start of the first stop time to the last stop time. Answering Yes for you do want it or No if you dont want it.");

			try {
				CommandLine cmd = parser.parse(options, args);

				if (cmd.hasOption("R") && cmd.hasOption("D") && cmd.hasOption("L") && cmd.hasOption("W")) {
					String routeId = cmd.getOptionValue("R");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");
					Date date = sdf.parse(cmd.getOptionValue("D"));
					String withTimestamp = cmd.getOptionValue("W");
					// Get trip and vehicle IDs for the route
					List<TripVehicle> tripAndVehicleIds = VehiclePositionManager.getTripIdAndVehicleIdForARoute(routeId);
					if (tripAndVehicleIds == null || tripAndVehicleIds.isEmpty()) {
						logger.info("No trip and vehicle IDs found for route " + routeId);
						return;
					}

					// Initialize DetourDetector
					DetourDetector detourDetector = new DetourDetectorDefaultImpl();

					// Iterate over each trip and vehicle ID pair
					for (TripVehicle tripAndVehicleId : tripAndVehicleIds) {

						String tripId =  tripAndVehicleId.getTripId();
						String vehicleId = tripAndVehicleId.getVehicleId();

						List<VehiclePosition> vehiclePositions = VehiclePositionManager.readtripVehiclePositionWithDate(tripId,vehicleId, date, withTimestamp);
						if (vehiclePositions == null || vehiclePositions.isEmpty()) {
							logger.info("No vehicle positions found for Trip ID " + tripId + " and Vehicle ID " + vehicleId);
							continue;
						}


						List<Detour> detours = detourDetector.detectDetours(tripId, vehicleId, date, withTimestamp);
						SimpleDateFormat filenameDateFormatter = new SimpleDateFormat("yyyyMMdd");

						LocalTime tripStartTime = TripManager.tripStartTime(tripId);
						String format = "HHmmss";

						DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(format);

						
						// Create a unique filename for each result
						String filename = String.format("%s_%s_%s_%s_%s.CSV",filenameDateFormatter.format(date),timeFormatter.format(tripStartTime), cmd.getOptionValue('R'),tripId, vehicleId);

						// Export the detected detours to an Excel file
						if (detours != null && !detours.isEmpty()) {
							ExportToCSV.exportDetoursToCSV(detours, cmd.getOptionValue("L")+filename);
							logger.info("Detours exported to " + cmd.getOptionValue("L")+filename);
						} else {
							logger.info("No detour detected for Trip ID " + tripId + " and Vehicle ID " + vehicleId);
						}
					}
				}
				if (cmd.hasOption("R") && cmd.hasOption("D") && cmd.hasOption("L") && cmd.hasOption("A") && cmd.hasOption("B") && cmd.hasOption("S") && cmd.hasOption("W")) {
					String routeId = cmd.getOptionValue("R");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = sdf.parse(cmd.getOptionValue("D"));
					String withTimestamp = cmd.getOptionValue("W");
					// Get trip and vehicle IDs for the route
					List<TripVehicle> tripAndVehicleIds = VehiclePositionManager.getTripIdAndVehicleIdForARoute(routeId);
					if (tripAndVehicleIds == null || tripAndVehicleIds.isEmpty()) {
						logger.info("No trip and vehicle IDs found for route " + routeId);
						return;
					}

					// Initialize DetourDetector
					DetourDetector detourDetector = new DetourDetectorDefaultImpl();

					// Iterate over each trip and vehicle ID pair
					for (TripVehicle tripAndVehicleId : tripAndVehicleIds) {

						String tripId =  tripAndVehicleId.getTripId();
						String vehicleId = tripAndVehicleId.getVehicleId();

						List<VehiclePosition> vehiclePositions = VehiclePositionManager.readtripVehiclePositionWithDate(tripId,vehicleId, date, withTimestamp);
						if (vehiclePositions == null || vehiclePositions.isEmpty()) {
							logger.info("No vehicle positions found for Trip ID " + tripId + " and Vehicle ID " + vehicleId);
							continue;
						}

						int onRouteThreshold = Integer.parseInt(cmd.getOptionValue("A"));
						int offRouteThreshold = Integer.parseInt(cmd.getOptionValue("B"));
						int distance = Integer.parseInt(cmd.getOptionValue("S"));

						if(tripId.equals("CA_C4-Weekday-SDon-115000_MISC_364")) {
							logger.debug("Test");
						}

							List<Detour> detours = detourDetector.detectDetours(tripId, vehicleId, date, withTimestamp, distance, onRouteThreshold, offRouteThreshold);

						SimpleDateFormat filenameDateFormatter = new SimpleDateFormat("yyyyMMdd");

						// Create a unique filename for each result
						String filename = String.format("%s_%s_%s_%s.CSV",filenameDateFormatter.format(date),cmd.getOptionValue('R'),tripId, vehicleId);

						// Export the detected detours to an Excel file
						if (detours != null && !detours.isEmpty()) {
							ExportToCSV.exportDetoursToCSV(detours, cmd.getOptionValue("L")+filename);
							logger.info("Detours exported to " + cmd.getOptionValue("L")+filename);
						} else {
							logger.info("No detour detected for Trip ID " + tripId + " and Vehicle ID " + vehicleId);
						}
					}
				}


				if (cmd.hasOption("D")) {
					logger.info("Date: " + cmd.getOptionValue("D"));
				}

				if (cmd.hasOption("T")) {
					logger.info("Trip id : " + cmd.getOptionValue("T"));
				}

				if (cmd.hasOption("V")){
					logger.info("Vehicle: " + cmd.getOptionValue("V"));
				}
				if (cmd.hasOption("F")){
					logger.info("Filename: " + cmd.getOptionValue("F"));
				}
				if (cmd.hasOption("T") && cmd.hasOption("V") && cmd.hasOption("D") && cmd.hasOption("F") && cmd.hasOption("L")) {
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");
						Date date = sdf.parse(cmd.getOptionValue("D"));
						String withTimestamp = cmd.getOptionValue("W");
						// Initialize DetourDetector and perform detour detection
						DetourDetector detourDetector = new DetourDetectorDefaultImpl();
						List<Detour> detours = detourDetector.detectDetours(cmd.getOptionValue("T"), cmd.getOptionValue("V"), date, withTimestamp);

						// Export the detected detours to an Excel file
						if (detours != null && !detours.isEmpty()) {
							ExportToCSV.exportDetoursToCSV
									(detours, cmd.getOptionValue("F"));
							logger.info("Detours exported to " + cmd.getOptionValue("F"));
						} else {
							logger.info("No detour detected for Vehicle " + cmd.getOptionValue("V"));
						}
					} catch (IOException e) {
						logger.info("IOException occurred: " + e.getMessage());
					}
				}
				if (cmd.hasOption("T") && cmd.hasOption("V") && cmd.hasOption("D") && cmd.hasOption("F") && cmd.hasOption("L") && cmd.hasOption("A") && cmd.hasOption("B") && cmd.hasOption("S") && cmd.hasOption("W")) {
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");
						Date date = sdf.parse(cmd.getOptionValue("D"));
						String withTimestamp = cmd.getOptionValue("W");
						int distance = Integer.parseInt(cmd.getOptionValue("S"));
						int onRouteThreshold = Integer.parseInt(cmd.getOptionValue("A"));
						int offRouteThreshold = Integer.parseInt(cmd.getOptionValue("B"));
						// Initialize DetourDetector and perform detour detection
						DetourDetector detourDetector = new DetourDetectorDefaultImpl();
						List<Detour> detours = detourDetector.detectDetours(cmd.getOptionValue("T"), cmd.getOptionValue("V"), date,withTimestamp, distance,onRouteThreshold,offRouteThreshold);

						// Export the detected detours to an Excel file
						if (detours != null && !detours.isEmpty()) {
							ExportToCSV.exportDetoursToCSV
									(detours, cmd.getOptionValue("F"));
							logger.info("Detours exported to " + cmd.getOptionValue("F"));
						} else {
							logger.info("No detour detected for Vehicle " + cmd.getOptionValue("V"));
						}
					} catch (IOException e) {
						logger.info("IOException occurred: " + e.getMessage());
					}
				}
			} catch (ParseException | IOException e) {
				logger.info("Unexpected exception: " + e.getMessage());
			} catch (java.text.ParseException e) {
                throw new RuntimeException(e);
            }
    }

    }



