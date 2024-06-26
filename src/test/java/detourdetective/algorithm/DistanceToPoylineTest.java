package detourdetective.algorithm;

import junit.framework.TestCase;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.junit.jupiter.api.Test;
public class DistanceToPoylineTest extends TestCase{
	

	    @Test
	    public void testDistanceBetweenPointAndPolyline() throws ParseException {
	    	// create a geometry factory
	    	GeometryFactory gf = new GeometryFactory();
	    	// create a point
	    	Coordinate pointCoord = new Coordinate(10.0, 20.0);
	    	Point point = gf.createPoint(pointCoord);

	    	// create a polyline
	    	Coordinate[] polylineCoords = new Coordinate[] {
	    	    new Coordinate(0.0, 0.0),
	    	    new Coordinate(10.0, 10.0),
	    	    new Coordinate(20.0, 20.0)
	    	};
	    	LineString polyline = gf.createLineString(polylineCoords);

	    	// calculate the distance from the point to the polyline
	    	double distance = polyline.distance(point);

	    	System.out.println("Distance: " + distance);
	    	
	    }
	}

