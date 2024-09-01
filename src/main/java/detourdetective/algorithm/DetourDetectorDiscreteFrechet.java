package detourdetective.algorithm;

import detourdetective.entities.Detour;
import detourdetective.entities.VehiclePosition;

import org.apache.log4j.Logger;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;
import sbahr.DiscreteFrechetDistance;
public class DetourDetectorDiscreteFrechet extends DetourDetectorDefaultImpl implements DetourDetector{
	private static final Logger logger = Logger.getLogger(DetourDetectorDiscreteFrechet.class);
	private static final double DETOUR_THRESHOLD = 50;
	private static final int R=6371*1000;
	private static final double countThreshold = 10;

    @Override
    public List<Detour>  detectDetours(String tripId, String vehicleId, List<Point> tripShape, List<VehiclePosition> avlPoints) {
		int consecutiveOffRouteCount = 0;
		List<Detour>  detours = new ArrayList<Detour>();
    	List<sbahr.Point> tripPointsConverted=new ArrayList<sbahr.Point>();
    	logger.debug("Trip points.");
        for (Point point : tripShape) {
            tripPointsConverted.add(convertPoint(point));
            logger.debug(tripPointsConverted.get(tripPointsConverted.size() - 1));
        }
    	logger.debug("Vehicle points.");
    	List<sbahr.Point> avlPointsConverted=new ArrayList<sbahr.Point>();
        for (VehiclePosition avlPoint : avlPoints) {
            avlPointsConverted.add(convertVehiclePosition(avlPoint));
            logger.debug(avlPointsConverted.get(avlPointsConverted.size() - 1));
        }
		// Ensure the lists are not empty before proceeding
		if (tripPointsConverted.isEmpty() || avlPointsConverted.isEmpty()) {
			logger.error("Trip shape or AVL points list is empty.");
			return detours; // Return empty list as no detour detected
		}
    	try {
    		DiscreteFrechetDistance discreteFrechetDistance=new DiscreteFrechetDistance();
    		discreteFrechetDistance.setTimeSeriesP(tripPointsConverted);
    		discreteFrechetDistance.setTimeSeriesQ(avlPointsConverted);
			double frechetDistance=discreteFrechetDistance.computeDiscreteFrechet(discreteFrechetDistance.getTimeSeriesP(), discreteFrechetDistance.getTimeSeriesQ());
			logger.info("Frechet distance was computed as " + frechetDistance);
			if(frechetDistance> DETOUR_THRESHOLD)
				detours.add(new Detour(tripId, vehicleId, avlPoints,tripShape));
			return detours;

		} catch (Exception e) {
			logger.error("Error computing Frechet distance", e);
		}
		return detours ; // No detour detected if list is empty
	}
    private sbahr.Point convertVehiclePosition(VehiclePosition vehiclePosition)
    {
    	int[] dimensions;
    	// allocating memory to array
    	dimensions = new int[2];  
    	
    	/* convert longitude and latitude to cartesian coordinates */
    	/*
    	 *
			x = R * cos(lat) * cos(lon)
			y = R * cos(lat) * sin(lon)
    	 */
    	
    	dimensions[0]=(int) (R* Math.cos(vehiclePosition.getPosition_latitude()) * Math.cos(vehiclePosition.getPosition_longitude()));
    	dimensions[1]=(int) (R*Math.cos(vehiclePosition.getPosition_latitude()) * Math.sin(vehiclePosition.getPosition_longitude()));

        return new sbahr.Point(dimensions);
    }
    private sbahr.Point convertPoint(org.locationtech.jts.geom.Point point){
    	int[] dimensions;
    	// allocating memory to array
    	dimensions = new int[2];  
    	
    	/* convert longitude and latitude to cartesian coordinates */
    	/*
    	 *
			x = R * cos(lat) * cos(lon)
			y = R * cos(lat) * sin(lon)
    	 */
    	
    	dimensions[0]=(int)(R* Math.cos(point.getX()) *  Math.cos(point.getY()));
    	dimensions[1]=(int) (R* Math.cos(point.getX()) *  Math.sin(point.getY()));

        return new sbahr.Point(dimensions);
    }
    
}
