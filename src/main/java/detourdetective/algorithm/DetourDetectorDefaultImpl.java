package detourdetective.algorithm;

import detourdetective.entities.VehiclePosition;
import detourdetective.managers.TripManager;
import detourdetective.managers.VehiclePositionManager;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;

import java.util.Date;

import java.util.List;

import org.apache.log4j.Logger;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.api.referencing.operation.MathTransform;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.GeodeticCalculator;
import org.geotools.referencing.crs.*;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.operation.distance.DistanceOp;

import java.awt.geom.Point2D;
import java.text.SimpleDateFormat;

public class DetourDetectorDefaultImpl implements DetourDetector {

	private static Logger logger = Logger.getLogger(DetourDetectorDefaultImpl.class);

	private static final GeometryFactory gf = new GeometryFactory();

	private double distanceSquaredThreshold = 400;

	private double onRouteThreshold = 3;

	private double offRouteThreshold = 5;

	private static boolean detourDetected = false;

	public List<List<VehiclePosition>> detectDetours(List<Point> tripShape, List<VehiclePosition> avlPoints,
			int distanceSquaredThreshold, int onRouteThreshold, int countThreshold) {
		// Convert JTS Points to Coordinates
		Coordinate[] polylineCoordinates = new Coordinate[tripShape.size()];
		for (int i = 0; i < tripShape.size(); i++) {
			Point jtsPoint = tripShape.get(i);
			polylineCoordinates[i] = new Coordinate(jtsPoint.getX(), jtsPoint.getY());
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhh:mm:ss");

		// Create a polyline from the coordinates
		LineString polyline = gf.createLineString(polylineCoordinates);

		// Track consecutive off-route points
		int consecutiveOffRouteCount = 0;
		int consecutiveOnRouteCount = 0;

		Timestamp detourStart = null;
		Timestamp detourEnd = null;
		Timestamp savedDetourStart = null;
		long detourDurationInMillis = 0;

		int position_counter = 0;
		List<List<VehiclePosition>> detours = new ArrayList<>();
		List<VehiclePosition> potentialOffRoutePoints = new ArrayList<>();
		List<VehiclePosition> offRoutePoints = new ArrayList<>();

		// Calculate the squared distance for each vehicle position to the polyline and
		// check for detours
		if (avlPoints != null) {
			for (VehiclePosition vehiclePosition : avlPoints) {
				Coordinate vehicleCoordinates = new Coordinate(vehiclePosition.getPosition_longitude(),
						vehiclePosition.getPosition_latitude());
				Point vehiclePoint = gf.createPoint(vehicleCoordinates);

				// Calculate distance
				//double distance = this.getDistance(vehiclePoint, polyline);
				float distance = getDistance(vehiclePosition,tripShape); 
				
				double squaredDistance = distance * distance;

				logger.debug("The distance is " + distance);

				logger.info("Vehicle position :" + position_counter + "," + vehicleCoordinates.getX() + ","
						+ vehicleCoordinates.getY() + ", Timestamp:"
						+ formatter.format(vehiclePosition.getTimestamp()));
								
				position_counter++;

				// Check if the squared distance exceeds the threshold
				if (squaredDistance > distanceSquaredThreshold) {

					logger.debug("The squared distance is " + squaredDistance + " which is greater than threshold.");

					consecutiveOffRouteCount++;
					consecutiveOnRouteCount = 0;

					if(detourStart == null) {
						detourStart = vehiclePosition.getTimestamp();
					}
				}

				if (squaredDistance < distanceSquaredThreshold) {
					consecutiveOffRouteCount = 0;
					consecutiveOnRouteCount++;
					savedDetourStart = detourStart;
					detourStart = null;
				}

				if (consecutiveOffRouteCount > 0 && consecutiveOffRouteCount <= countThreshold) {
					potentialOffRoutePoints.add(vehiclePosition);
				}

				if (consecutiveOffRouteCount == countThreshold) {
					logger.info("Start of detour detected.");
					detourDetected = true;
					offRoutePoints.addAll(potentialOffRoutePoints);
					savedDetourStart = detourStart;
				}

				if (consecutiveOffRouteCount > countThreshold) {
					offRoutePoints.add(vehiclePosition);
				}

				if (consecutiveOnRouteCount > 0 && consecutiveOnRouteCount < onRouteThreshold) {
					offRoutePoints.add(vehiclePosition);
				}

				if (consecutiveOnRouteCount > onRouteThreshold) {
					if (offRoutePoints.size() >= countThreshold) {
						logger.info("End of detour detected.");
						detourDetected = false;
						detourEnd = vehiclePosition.getTimestamp();
						if (detourStart != null && detourEnd != null) {
							Duration detourDuration = Duration.between(detourStart.toInstant(),detourEnd.toInstant());
							detourDurationInMillis = detourDuration.toMillis();
							logger.info("Detour Duration in Milliseconds: " + detourDurationInMillis);
						}
						detours.add(offRoutePoints);
					}
					offRoutePoints=new ArrayList<>();
					potentialOffRoutePoints=new ArrayList<>();
				}

				logger.info("Number of points off route is : " + consecutiveOffRouteCount);
				logger.info("Number of points on route is : " + consecutiveOnRouteCount);
				logger.info("Distance : " + distance);
				logger.info("DetourDetected : " + detourDetected);
			}
		}

		// If detour is still ongoing at the end of the points, add it to the list
		if (!offRoutePoints.isEmpty() && detourDetected) {
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
	public List<List<VehiclePosition>> detectDetours(String tripId, String vehicleId, Date date) {

		// Get the list of JTS Points from the TripManager for the polyline
		List<Point> tripShape = TripManager.readShapeLatAndLong(tripId);

		// Fetch vehicle positions
		List<VehiclePosition> vehiclePositions = VehiclePositionManager.readtripVehiclePositionWithDate(tripId,
				vehicleId, date);

		return detectDetours(tripShape, vehiclePositions);
	}

	@Override
	public List<List<VehiclePosition>> detectDetours(String tripId, String vehicleId, Date date, int distance,
			int onCountThreshold, int offCountThreshold) {
		// Get the list of JTS Points from the TripManager for the polyline
		List<Point> tripShape = TripManager.readShapeLatAndLong(tripId);

		// Fetch vehicle positions
		List<VehiclePosition> vehiclePositions = VehiclePositionManager.readtripVehiclePositionWithDate(tripId,
				vehicleId, date);

		this.distanceSquaredThreshold = distance * distance;
		this.onRouteThreshold = onCountThreshold;
		this.offRouteThreshold = offCountThreshold;

		return detectDetours(tripShape, vehiclePositions);
	}

	@Override
	public List<List<VehiclePosition>> detectDetours(List<Point> tripShape, List<VehiclePosition> avlPoints) {
		return detectDetours(tripShape, avlPoints, (int) distanceSquaredThreshold, (int) onRouteThreshold,
				(int) offRouteThreshold);
	}

	public double getDistance(Point point, LineString line) {
		double dist = -1.0;

		try {
			Coordinate[] lineCoordinates = line.getCoordinates();
			double mindistance = -1.0;
			
			Coordinate closestPointToLine = null;
						
			for (int i = 0; i < lineCoordinates.length; i++) {

				double distance = Point2D.distance(lineCoordinates[i].getX(), lineCoordinates[i].getY(), point.getX(),
						point.getY());
				if (mindistance < 0 || distance < mindistance) {
					mindistance = distance;
					closestPointToLine = lineCoordinates[i];
				}
			}

			Coordinate[] pointCoordinates = point.getCoordinates();
			
			logger.info("Closest point on line route:"+closestPointToLine.toString());
			mindistance = -1.0;

			Coordinate closestPointToPoint = null;

			for (int i = 0; i < pointCoordinates.length; i++) {

				double distance = Point2D.distance(pointCoordinates[i].getX(), pointCoordinates[i].getY(), point.getX(),
						point.getY());
				if (mindistance < 0 || distance < mindistance) {
					mindistance = distance;
					closestPointToPoint = pointCoordinates[i];
				}
			}
			logger.info("Closest point on point:"+closestPointToPoint.toString());
			
			GeodeticCalculator gc = new GeodeticCalculator(DefaultGeographicCRS.WGS84);
			gc.setStartingPosition(JTS.toDirectPosition(closestPointToLine, DefaultGeographicCRS.WGS84));
			gc.setDestinationPosition(JTS.toDirectPosition(closestPointToPoint, DefaultGeographicCRS.WGS84));			
			double distance = gc.getOrthodromicDistance();
			dist = distance;
		} catch (Exception Ex) {
			logger.info(Ex.toString());
		}
		return dist;

	}

	private float getDistance(VehiclePosition vehiclePosition, List<Point> tripShape) {
		// TODO Auto-generated method stub
		float mindistance = -1;
		Point closestPoint=null;
		
		for(int i=0;i<tripShape.size();i++)
		{
			 float distance = distFrom(vehiclePosition.getPosition_latitude(),vehiclePosition.getPosition_longitude(),   tripShape.get(i).getY() , tripShape.get(i).getX());
			 if(mindistance ==-1 || distance < mindistance)
			 {
				 mindistance=distance;
				 closestPoint=tripShape.get(i);
			 }
		}
		logger.info(vehiclePosition);
		logger.info(closestPoint);
		return mindistance;
	}
		
	public float distFrom(double lat1, double lng1, double lat2, double lng2) {
	    double earthRadius = 6371000; //meters
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    float dist = (float) (earthRadius * c);

	    return dist;
	    }
}
