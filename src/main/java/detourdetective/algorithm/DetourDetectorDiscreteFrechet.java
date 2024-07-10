package detourdetective.algorithm;

import detourdetective.entities.VehiclePosition;

import org.apache.log4j.Logger;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;
import sbahr.DiscreteFrechetDistance;
public class DetourDetectorDiscreteFrechet extends DetourDetectorDefaultImpl implements DetourDetector{
	private static Logger logger = Logger.getLogger(DetourDetectorDiscreteFrechet.class);
	private static final double DETOURTRESHOLD = 100;
	private static final int R=6371*1000;

    @Override
    public boolean detectDetours(List<Point> tripShape, List<VehiclePosition> avlPoints) {
    	
    	List<sbahr.Point> tripPointsConverted=new ArrayList<sbahr.Point>();
    	logger.debug("Trip points.");
    	for (int i = 0; i < tripShape.size(); i++) {
    		tripPointsConverted.add(convertPoint(tripShape.get(i)));
    		logger.debug(tripPointsConverted.get(tripPointsConverted.size()-1));
		}
    	logger.debug("Vehicle points.");
    	List<sbahr.Point> avlPointsConverted=new ArrayList<sbahr.Point>();
    	for (int i = 0; i < avlPoints.size(); i++) {
    		avlPointsConverted.add(convertVehiclePosition(avlPoints.get(i)));
    		logger.debug(avlPointsConverted.get(avlPointsConverted.size()-1));
		}
    	try {
    		DiscreteFrechetDistance discreteFrechetDistance=new DiscreteFrechetDistance();
    		discreteFrechetDistance.setTimeSeriesP(tripPointsConverted);
    		discreteFrechetDistance.setTimeSeriesQ(avlPointsConverted);
			double frechetDistance=discreteFrechetDistance.computeDiscreteFrechet(discreteFrechetDistance.getTimeSeriesP(), discreteFrechetDistance.getTimeSeriesQ());
			logger.info("Frechet distance was computed as " + frechetDistance);
			if(frechetDistance>DETOURTRESHOLD)
				return true;
			else
				return false;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    	return false;
    }
    private sbahr.Point convertVehiclePosition(VehiclePosition vechiclePosition)
    {
    	int dimensions[];
    	// allocating memory to array
    	dimensions = new int[2];  
    	
    	/* convert longitude and latitude to cartesian coordinates */
    	/*
    	 *
			x = R * cos(lat) * cos(lon)
			y = R * cos(lat) * sin(lon)
    	 */
    	
    	dimensions[0]=(int) (R* Math.cos(vechiclePosition.getPosition_latitude()) * Math.cos(vechiclePosition.getPosition_longitude()));
    	dimensions[1]=(int) (R*Math.cos(vechiclePosition.getPosition_latitude()) * Math.sin(vechiclePosition.getPosition_longitude()));
    	
    	sbahr.Point outputPoint= new sbahr.Point(dimensions);
    	
    	return outputPoint;
    }
    private sbahr.Point convertPoint(org.locationtech.jts.geom.Point point){
    	int dimensions[];
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
    	
    	sbahr.Point outputPoint= new sbahr.Point(dimensions);
    	
    	return outputPoint;
    }
    
}
