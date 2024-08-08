package detourdetective;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import detourdetective.algorithm.DetourDetector;
import detourdetective.algorithm.DetourDetectorDefaultImpl;
import detourdetective.entities.ExportToCSV;
import detourdetective.entities.VehiclePosition;
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

			try {
				CommandLine cmd = parser.parse(options, args);

				if (cmd.hasOption("R")) {
					String routeId = cmd.getOptionValue("R");

					// Get trip and vehicle IDs for the route
					Set<String> tripAndVehicleIds = VehiclePositionManager.getTripIdForARoute(routeId);
					if (tripAndVehicleIds == null || tripAndVehicleIds.isEmpty()) {
						System.out.println("No trip and vehicle IDs found for route " + routeId);
						return;
					}

					// Initialize DetourDetector
					DetourDetector detourDetector = new DetourDetectorDefaultImpl();

					// Iterate over each trip and vehicle ID pair
					for (String tripAndVehicleId : tripAndVehicleIds) {
						String[] ids = tripAndVehicleId.split("--:");
						if (ids.length != 2) {
							System.out.println("Invalid format for trip and vehicle ID: " + tripAndVehicleId);
							continue;
						}
						String tripId = ids[0];
						String vehicleId = ids[1];

						List<VehiclePosition> vehiclePositions = VehiclePositionManager.readtripVehiclePosition(tripId,vehicleId);
						if (vehiclePositions == null || vehiclePositions.isEmpty()) {
							System.out.println("No vehicle positions found for Trip ID " + tripId + " and Vehicle ID " + vehicleId);
							continue;
						}

						Date tripDate = vehiclePositions.get(0).getTrip_start_date();

						List<List<VehiclePosition>> detours = detourDetector.detectDetours(tripId, vehicleId);

						// Create a unique filename for each result
						String filename = String.format("%s_%s_%s_%s.CSV",tripDate,cmd.getOptionValue('R'),tripId, vehicleId);

						// Export the detected detours to an Excel file
						if (detours != null && !detours.isEmpty()) {
							ExportToCSV.exportDetoursToCSV(detours, filename);
							System.out.println("Detours exported to " + filename);
						} else {
							System.out.println("No detour detected for Trip ID " + tripId + " and Vehicle ID " + vehicleId);
						}
					}
				} else {
					System.out.println("Route not provided.");
				}


				if (cmd.hasOption("D")) {
					System.out.println("Date: " + cmd.getOptionValue("D"));
				} else {
					System.out.println("Date not provided.");
				}

				if (cmd.hasOption("T")) {
					System.out.println("Trip id : " + cmd.getOptionValue("T"));
				} else {
					System.out.println("Detour option not provided.");
				}

				if (cmd.hasOption("V")){
					System.out.println("Vehicle: " + cmd.getOptionValue("V"));
				} else {
					System.out.println("Vehicle not provided.");
				}
				if (cmd.hasOption("F")){
					System.out.println("Filename: " + cmd.getOptionValue("F"));
				} else {
					System.out.println("Filename not provided.");
				}
				if (cmd.hasOption("T") && cmd.hasOption("V") && cmd.hasOption("F")) {
					try {
						// Initialize DetourDetector and perform detour detection
						DetourDetector detourDetector = new DetourDetectorDefaultImpl();
						List<List<VehiclePosition>> detours = detourDetector.detectDetours(cmd.getOptionValue("T"), cmd.getOptionValue("V"));

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
			}
		}
    }



