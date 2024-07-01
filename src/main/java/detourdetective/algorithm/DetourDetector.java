package detourdetective.algorithm;

import detourdetective.entities.VehiclePosition;
import detourdetective.managers.TripManager;
import detourdetective.managers.VehiclePositionManager;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import java.text.ParseException;
import java.util.List;

public class DetourDetector {

    private static final GeometryFactory gf = new GeometryFactory();

    private static final double threshold = 100;

    private static final double countThreshold = 10;
    
    public static final String PLUGIN_ID = "org.locationtech.udig.tutorials.distancetool";

    public static boolean detectDetours(String tripId, String vehicleId) throws ParseException {
        // Get the list of JTS Points from the TripManager for the polyline
        List<Point> jtsPoints = TripManager.readShapeLatAndLong(tripId);

        // Convert JTS Points to Coordinates
        Coordinate[] polylineCoordinates = new Coordinate[jtsPoints.size()];
        for (int i = 0; i < jtsPoints.size(); i++) {
            Point jtsPoint = jtsPoints.get(i);
            polylineCoordinates[i] = new Coordinate(jtsPoint.getX(), jtsPoint.getY());
        }

        // Create a polyline from the coordinates
        LineString polyline = gf.createLineString(polylineCoordinates);

        // Fetch vehicle positions
        List<VehiclePosition> vehiclePositions = VehiclePositionManager.readtripVehiclePosition(tripId, vehicleId);

        // Track consecutive off-route points
        int consecutiveOffRouteCount = 0;

        // Calculate the squared distance for each vehicle position to the polyline and check for detours
        if (vehiclePositions != null) {
            double totalSquaredDistance = 0.0;
            for (VehiclePosition vehiclePosition : vehiclePositions) {
                Coordinate vehicleCoordinates = new Coordinate(vehiclePosition.getPosition_longitude(), vehiclePosition.getPosition_latitude());
                Point vehiclePoint = gf.createPoint(vehicleCoordinates);

                // Calculate distance
                double distance = polyline.distance(vehiclePoint);
                System.out.println("Distance "+distance);
                double squaredDistance = distance * distance;
                totalSquaredDistance=totalSquaredDistance+squaredDistance;
                System.out.println("This is squared distance "+squaredDistance);
                System.out.println("This is total squared distance "+totalSquaredDistance);


                // Check if the squared distance exceeds the threshold
                if (squaredDistance > threshold) {
                    consecutiveOffRouteCount++;
                    if (consecutiveOffRouteCount > countThreshold) {
                        return true; // Detour detected
                    }
                } else {
                    consecutiveOffRouteCount = 0; // Reset count
                }
            }
        }

        return false; // No detour detected
    }


}





