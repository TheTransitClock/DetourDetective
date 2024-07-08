package detourdetective.algorithm;

import detourdetective.entities.VehiclePosition;
import detourdetective.managers.TripManager;
import detourdetective.managers.VehiclePositionManager;
import java.text.ParseException;
import java.util.List;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.api.referencing.operation.MathTransform;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.*;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.operation.distance.DistanceOp;

public class DetourDetectorDefaultImpl implements DetourDetector {

	private static final GeometryFactory gf = new GeometryFactory();

	private static final double threshold = 100;

	private static final double countThreshold = 10;
	
	@Override 
	public boolean detectDetours(List<Point> tripShape, List<VehiclePosition> avlPoints)
	{

		// Convert JTS Points to Coordinates
		Coordinate[] polylineCoordinates = new Coordinate[tripShape.size()];
		for (int i = 0; i < tripShape.size(); i++) {
			Point jtsPoint = tripShape.get(i);
			polylineCoordinates[i] = new Coordinate(jtsPoint.getX(), jtsPoint.getY());
		}

		// Create a polyline from the coordinates
		LineString polyline = gf.createLineString(polylineCoordinates);


		// Track consecutive off-route points
		int consecutiveOffRouteCount = 0;

		// Calculate the squared distance for each vehicle position to the polyline and
		// check for detours
		if (avlPoints != null) {
			for (VehiclePosition vehiclePosition : avlPoints) {
				Coordinate vehicleCoordinates = new Coordinate(vehiclePosition.getPosition_longitude(),
						vehiclePosition.getPosition_latitude());
				Point vehiclePoint = gf.createPoint(vehicleCoordinates);

				// Calculate distance
				double distance;

				double squaredDistance = 0;

				distance = this.getDistance(vehiclePoint, polyline);

				System.out.println("The distance is " + distance);

				squaredDistance = distance * distance;

				// Check if the squared distance exceeds the threshold
				if (squaredDistance > threshold) {
					System.out.println("The squared distance is " + squaredDistance);
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

	@Override
	public Point findDetourStart(List<Point> tripShape, List<VehiclePosition> avlPoints) {
		return null;
	}

	@Override
	public Point findDetourEnd(List<Point> tripShape, List<VehiclePosition> avlPoints) {
		return null;
	}

	@Override
	public boolean detectDetours(String tripId, String vehicleId)  {

		// Get the list of JTS Points from the TripManager for the polyline
		List<Point> tripShape = TripManager.readShapeLatAndLong(tripId);

		// Fetch vehicle positions
		List<VehiclePosition> vehiclePositions = VehiclePositionManager.readtripVehiclePosition(tripId, vehicleId);

	 return detectDetours(tripShape, vehiclePositions);
	}

	public double getDistance(Point point, LineString line) {
		double dist = -1.0;
		try {
			String code = "AUTO:42001," + point.getX() + "," + point.getY();
			CoordinateReferenceSystem auto = CRS.decode(code);
			// auto = CRS.decode("epsg:2470");
			MathTransform transform = CRS.findMathTransform(DefaultGeographicCRS.WGS84, auto);
			Geometry g3 = JTS.transform(line, transform);
			Geometry g4 = JTS.transform(point, transform);

			Coordinate[] c = DistanceOp.nearestPoints(g4, g3);

			Coordinate c1 = new Coordinate();
			// System.out.println(c[1].distance(g4.getCoordinate()));
			JTS.transform(c[1], c1, transform.inverse());
			// System.out.println(geometryFactory.createPoint(c1));
			dist = JTS.orthodromicDistance(point.getCoordinate(), c1, DefaultGeographicCRS.WGS84);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dist;
	}

}
