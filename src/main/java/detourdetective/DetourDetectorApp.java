package detourdetective;


import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import detourdetective.algorithm.DetourDetector;
import detourdetective.algorithm.DetourDetectorDefaultImpl;
import detourdetective.entities.ExportToExcel;
import detourdetective.entities.VehiclePosition;
import org.apache.commons.cli.*;

public class DetourDetectorApp {
	public static void main(String[] args) {

		CommandLineParser parser = new DefaultParser();

		Scanner keyboard = new Scanner(System.in);

		// create the Options
		Options options = new Options();
		options.addOption("R", "route", false, "This is the route we want to check..");
		options.addOption("D", "date", false, "The date we want to check this route on..");
		options.addOption("T", "detour", true, "Arguments: tripId vehicleId fileName");

			try {
				CommandLine cmd = parser.parse(options, args);

				if (cmd.hasOption("R")) {
					System.out.println("Route: " + cmd.getOptionValue("R"));
				} else {
					System.out.println("Route not provided.");
				}

				if (cmd.hasOption("D")) {
					System.out.println("Date: " + cmd.getOptionValue("D"));
				} else {
					System.out.println("Date not provided.");
				}

				if (cmd.hasOption("T")) {
					String tripId;
					String vehicleId;
					String fileName;

					System.out.println("Enter trip id:");
					tripId = keyboard.next();
					System.out.println("Enter vehicle id:");
					vehicleId = keyboard.next();
					System.out.println("Enter filename:");
					fileName = keyboard.next();


					System.out.println("Trip ID: " + tripId);
					System.out.println("Vehicle ID: " + vehicleId);
					System.out.println("File: " + fileName);

					try {
						// Initialize DetourDetector and perform detour detection
						DetourDetector detourDetector = new DetourDetectorDefaultImpl();
						List<List<VehiclePosition>> detours = detourDetector.detectDetours(tripId, vehicleId);

						// Export the detected detours to an Excel file
						if (detours != null && !detours.isEmpty()) {
							ExportToExcel.exportDetoursToExcel(detours, fileName);
							System.out.println("Detours exported to " + fileName);
						} else {
							System.out.println("No detour detected for Vehicle " + vehicleId);
						}
					} catch (IOException e) {
						System.out.println("IOException occurred: " + e.getMessage());
					}
				} else {
					System.out.println("Detour option not provided.");
				}
			} catch (ParseException e) {
				System.out.println("Unexpected exception: " + e.getMessage());
			}
		}
    }



