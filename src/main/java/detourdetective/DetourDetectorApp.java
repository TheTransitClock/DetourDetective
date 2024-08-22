package detourdetective;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


import detourdetective.algorithm.DetourDetector;
import detourdetective.algorithm.DetourDetectorDefaultImpl;
import detourdetective.entities.ExportToCSV;
import detourdetective.entities.TripVehicle;
import detourdetective.entities.VehiclePosition;
import detourdetective.managers.TripManager;
import detourdetective.managers.VehiclePositionManager;
import org.apache.commons.cli.*;

public class DetourDetectorApp {
	public static void main(String[] args) {

		CommandLineParser parser = new DefaultParser();

		// create the Options
		Options options = new Options();
		options.addOption("R", "route", true, "This is the route we want to check..");
		options.addOption("D", "date", true, "The date we want to check this route on..");
		options.addOption("T", "tripId", true, "The trip id we will be checking..");
		options.addOption("V", "vehicleId", true, "The vehicle id we will be checking on the trip id provided.");
		options.addOption("F", "Filename", true, "The filename of where the output will be stored.");
		options.addOption("L", "Directory", true, "The directory where the CSV file will be stored.");
		options.addOption("A", "onRouteThreshold", true, "The number of times the vehicle appears on route when it returns from the detour to say the detour has ended.");
		options.addOption("B", "offRouteThreshold", true, "The number of times the vehicle appears off route to say it is on a detour");
		options.addOption("S", "Distance", true, "The distance a vehicle appears off route to say it is on a detour");

			try {
				CommandLine cmd = parser.parse(options, args);

				if (cmd.hasOption("R") && cmd.hasOption("D") && cmd.hasOption("L")) {
					String routeId = cmd.getOptionValue("R");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");
					Date date = sdf.parse(cmd.getOptionValue("D"));
					// Get trip and vehicle IDs for the route
					List<TripVehicle> tripAndVehicleIds = VehiclePositionManager.getTripIdAndVehicleIdForARoute(routeId);
					if (tripAndVehicleIds == null || tripAndVehicleIds.isEmpty()) {
						System.out.println("No trip and vehicle IDs found for route " + routeId);
						return;
					}

					// Initialize DetourDetector
					DetourDetector detourDetector = new DetourDetectorDefaultImpl();

					// Iterate over each trip and vehicle ID pair
					for (TripVehicle tripAndVehicleId : tripAndVehicleIds) {

						String tripId =  tripAndVehicleId.getTripId();
						String vehicleId = tripAndVehicleId.getVehicleId();

						List<VehiclePosition> vehiclePositions = VehiclePositionManager.readtripVehiclePositionWithDate(tripId,vehicleId, date);
						if (vehiclePositions == null || vehiclePositions.isEmpty()) {
							System.out.println("No vehicle positions found for Trip ID " + tripId + " and Vehicle ID " + vehicleId);
							continue;
						}


						List<List<VehiclePosition>> detours = detourDetector.detectDetours(tripId, vehicleId, date);
						SimpleDateFormat filenameDateFormatter = new SimpleDateFormat("yyyyMMdd");

						LocalTime tripStartTime = TripManager.tripStartTime(tripId);
						String format = "HHmmss";

						DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(format);

						
						// Create a unique filename for each result
						String filename = String.format("%s_%s_%s_%s_%s.CSV",filenameDateFormatter.format(date),timeFormatter.format(tripStartTime), cmd.getOptionValue('R'),tripId, vehicleId);

						// Export the detected detours to an Excel file
						if (detours != null && !detours.isEmpty()) {
							ExportToCSV.exportDetoursToCSV(detours, cmd.getOptionValue("L")+filename);
							System.out.println("Detours exported to " + cmd.getOptionValue("L")+filename);
						} else {
							System.out.println("No detour detected for Trip ID " + tripId + " and Vehicle ID " + vehicleId);
						}
					}
				}
				if (cmd.hasOption("R") && cmd.hasOption("D") && cmd.hasOption("L") && cmd.hasOption("A") && cmd.hasOption("B") && cmd.hasOption("S")) {
					String routeId = cmd.getOptionValue("R");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = sdf.parse(cmd.getOptionValue("D"));
					// Get trip and vehicle IDs for the route
					List<TripVehicle> tripAndVehicleIds = VehiclePositionManager.getTripIdAndVehicleIdForARoute(routeId);
					if (tripAndVehicleIds == null || tripAndVehicleIds.isEmpty()) {
						System.out.println("No trip and vehicle IDs found for route " + routeId);
						return;
					}

					// Initialize DetourDetector
					DetourDetector detourDetector = new DetourDetectorDefaultImpl();

					// Iterate over each trip and vehicle ID pair
					for (TripVehicle tripAndVehicleId : tripAndVehicleIds) {

						String tripId =  tripAndVehicleId.getTripId();
						String vehicleId = tripAndVehicleId.getVehicleId();

						List<VehiclePosition> vehiclePositions = VehiclePositionManager.readtripVehiclePositionWithDate(tripId,vehicleId, date);
						if (vehiclePositions == null || vehiclePositions.isEmpty()) {
							System.out.println("No vehicle positions found for Trip ID " + tripId + " and Vehicle ID " + vehicleId);
							continue;
						}

						int onRouteThreshold = Integer.parseInt(cmd.getOptionValue("A"));
						int offRouteThreshold = Integer.parseInt(cmd.getOptionValue("B"));
						int distance = Integer.parseInt(cmd.getOptionValue("S"));

						List<List<VehiclePosition>> detours = detourDetector.detectDetours(tripId, vehicleId, date,distance,onRouteThreshold,offRouteThreshold);
						SimpleDateFormat filenameDateFormatter = new SimpleDateFormat("yyyyMMdd");

						// Create a unique filename for each result
						String filename = String.format("%s_%s_%s_%s.CSV",filenameDateFormatter.format(date),cmd.getOptionValue('R'),tripId, vehicleId);

						// Export the detected detours to an Excel file
						if (detours != null && !detours.isEmpty()) {
							ExportToCSV.exportDetoursToCSV(detours, cmd.getOptionValue("L")+filename);
							System.out.println("Detours exported to " + cmd.getOptionValue("L")+filename);
						} else {
							System.out.println("No detour detected for Trip ID " + tripId + " and Vehicle ID " + vehicleId);
						}
					}
				}


				if (cmd.hasOption("D")) {
					System.out.println("Date: " + cmd.getOptionValue("D"));
				}

				if (cmd.hasOption("T")) {
					System.out.println("Trip id : " + cmd.getOptionValue("T"));
				}

				if (cmd.hasOption("V")){
					System.out.println("Vehicle: " + cmd.getOptionValue("V"));
				}
				if (cmd.hasOption("F")){
					System.out.println("Filename: " + cmd.getOptionValue("F"));
				}
				if (cmd.hasOption("T") && cmd.hasOption("V") && cmd.hasOption("D") && cmd.hasOption("F") && cmd.hasOption("L")) {
					try {
						String dateString = cmd.getOptionValue("R");
						Date date = new Date(dateString);
						// Initialize DetourDetector and perform detour detection
						DetourDetector detourDetector = new DetourDetectorDefaultImpl();
						List<List<VehiclePosition>> detours = detourDetector.detectDetours(cmd.getOptionValue("T"), cmd.getOptionValue("V"), date);

						// Export the detected detours to an Excel file
						if (detours != null && !detours.isEmpty()) {
							ExportToCSV.exportDetoursToCSV
									(detours, cmd.getOptionValue("F"));
							System.out.println("Detours exported to " + cmd.getOptionValue("F"));
						} else {
							System.out.println("No detour detected for Vehicle " + cmd.getOptionValue("V"));
						}
					} catch (IOException e) {
						System.out.println("IOException occurred: " + e.getMessage());
					}
				}
			} catch (ParseException | IOException e) {
				System.out.println("Unexpected exception: " + e.getMessage());
			} catch (java.text.ParseException e) {
                throw new RuntimeException(e);
            }
    }
    }



