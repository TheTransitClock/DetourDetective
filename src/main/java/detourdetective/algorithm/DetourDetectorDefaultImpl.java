package detourdetective.algorithm;

import detourdetective.entities.VehiclePosition;
import detourdetective.managers.TripManager;
import detourdetective.managers.VehiclePositionManager;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.api.referencing.operation.MathTransform;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.*;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.operation.distance.DistanceOp;
import java.text.SimpleDateFormat;

public class DetourDetectorDefaultImpl implements DetourDetector {

	private static Logger logger = Logger.getLogger(DetourDetectorDefaultImpl.class);

	private static final GeometryFactory gf = new GeometryFactory();

	private static final double threshold = 400;

	private static final double onRouteThreshold = 5;

	private static final double countThreshold = 5;

	private static boolean detourDetected = false;

	public List<List<VehiclePosition>> detectDetours(List<Point> tripShape, List<VehiclePosition> avlPoints) {
		// Convert JTS Points to Coordinates
		Coordinate[] polylineCoordinates = new Coordinate[tripShape.size()];
		for (int i = 0; i < tripShape.size(); i++) {
			Point jtsPoint = tripShape.get(i);
			polylineCoordinates[i] = new Coordinate(jtsPoint.getX(), jtsPoint.getY());
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd hh:mm:ss");
		
		// Create a polyline from the coordinates
		LineString polyline = gf.createLineString(polylineCoordinates);

		// Track consecutive off-route points
		int consecutiveOffRouteCount = 0;
		int consecutiveOnRouteCount = 0;

		int position_counter=0;
		List<List<VehiclePosition>> detours = new ArrayList<>();
		List<VehiclePosition> potentialOffRoutePoints = new ArrayList<>();
		List<VehiclePosition> offRoutePoints = new ArrayList<>();

		// Calculate the squared distance for each vehicle position to the polyline and check for detours
		if (avlPoints != null) {
			for (VehiclePosition vehiclePosition : avlPoints) {
				Coordinate vehicleCoordinates = new Coordinate(vehiclePosition.getPosition_longitude(), vehiclePosition.getPosition_latitude());
				Point vehiclePoint = gf.createPoint(vehicleCoordinates);

				// Calculate distance
				double distance = this.getDistance(vehiclePoint, polyline);
				double squaredDistance = distance * distance;

				logger.debug("The distance is " + distance);

				logger.info("Vehicle position :"+position_counter+ "," + vehicleCoordinates.getX()+","+vehicleCoordinates.getY()+", Timestamp:"+ formatter.format( vehiclePosition.getTimestamp()));
				
				position_counter++;

				if (squaredDistance > threshold ) {
					consecutiveOffRouteCount++;
					consecutiveOnRouteCount = 0;
				}
				if (squaredDistance < threshold ) {
					consecutiveOffRouteCount=0;
					consecutiveOnRouteCount ++;
				}
				
				if(consecutiveOffRouteCount > 0 && consecutiveOffRouteCount<=countThreshold)
				{
					potentialOffRoutePoints.add(vehiclePosition);
				}
				
				if(consecutiveOffRouteCount==countThreshold)
				{
					logger.info("Start of detour detected.");
					detourDetected=true;
					offRoutePoints.addAll(potentialOffRoutePoints);						
				}
				
				if(consecutiveOffRouteCount > countThreshold)
				{
					offRoutePoints.add(vehiclePosition);
				}
				
				if(consecutiveOnRouteCount > 0 && consecutiveOnRouteCount < onRouteThreshold)
				{					
					offRoutePoints.add(vehiclePosition);
				}
				
				if(consecutiveOnRouteCount > onRouteThreshold)
				{										
					if(offRoutePoints.size() >= countThreshold)
					{
						logger.info("End of detour detected.");
						detourDetected=false;
						detours.add(new ArrayList<>(offRoutePoints));
					}
					
					offRoutePoints.clear();
					potentialOffRoutePoints.clear();
				}
							
				logger.info("Number of points off route is : "+consecutiveOffRouteCount);
				logger.info("Number of points on route is : "+consecutiveOnRouteCount);
				logger.info("DetourDetected : "+detourDetected);
			}
		}

		// If detour is still ongoing at the end of the points, add it to the list
		if (detourDetected && !offRoutePoints.isEmpty()) {
			detours.add(new ArrayList<>(offRoutePoints));
		}

		if (!detours.isEmpty()) {
			logger.info("Detour in place for vehicle.");
			return detours; // Return the detour points
		}

		logger.info("No detour detected.");
		return null; // No detour detected
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
	public List<List<VehiclePosition>>  detectDetours(String tripId, String vehicleId)  {

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

	@Override
	public List<List<VehiclePosition>> detectDetours(String tripId, String vehicleId, int countTreshold,
			int onRouteTreshold, int distanceConsideredOffRoute) {
		// TODO Auto-generated method stub
		return null;
	}


}
