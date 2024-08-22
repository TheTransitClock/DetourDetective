package detourdetective.managers;

import detourdetective.HibernateUtil;
import detourdetective.entities.Shape;
import detourdetective.entities.StopTimes;
import detourdetective.entities.Trip;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TripManager {
	
	public static List<Shape> readTripShape(String tripId)
	{
		Trip trip = TripManager.readtrip(tripId);
		trip.getShape_id();
		System.out.println(trip);
		return null;
	}
	public static List<Point> readShapeLatAndLong(String tripId) {
		
		Trip trip = readtrip(tripId);
		List<Point> latAndLong = new ArrayList<>();
		if(trip!=null)
		{
			List<Shape> shapes = readShape(trip.getShape_id());
	
			GeometryFactory geometryFactory = new GeometryFactory();
	
			for (Shape shape : shapes) {
				double latitude = shape.getShape_pt_lat();
				double longitude = shape.getShape_pt_lon();
				Coordinate coordinate = new Coordinate(longitude, latitude); // Longitude first, then latitude
				Point point = geometryFactory.createPoint(coordinate);
				latAndLong.add(point);
			}
		}
		return latAndLong;
	}
    private static List<Shape> readShape(String shape_id) {
		// TODO Auto-generated method stub
    	try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Shape> cr = cb.createQuery(Shape.class);
            Root<Shape> root = cr.from(Shape.class);
            cr.select(root).where(cb.equal(root.get("shape_id"), shape_id));
			cr.orderBy(cb.asc(root.get("shape_pt_sequence")));

            Query<Shape> query = session.createQuery(cr);
            List<Shape> results = query.getResultList();
            
            return results;
          
        } catch (Exception e) {
           
            e.printStackTrace();
            return null;
        }
	
	}
	public static Trip readtrip(String tripId) {
       
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
           
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Trip> cr = cb.createQuery(Trip.class);
            Root<Trip> root = cr.from(Trip.class);
            cr.select(root).where(cb.like(root.get("trip_id"), tripId));

            Query<Trip> query = session.createQuery(cr);
            List<Trip> results = query.getResultList();
            // commit transaction
          
            if(results.size() > 0) {
                return results.get(0);
            }else{
                return null;
            }
        } catch (Exception e) {
           
            e.printStackTrace();
            return null;
        }
    }
	
	public static List<Trip> getActiveTrips(String routeId, Date date){
		
		return null;
		
	}
	
	public static List<Trip> getActiveTrips(Date date){
		
		return null;
		
	}

	public static LocalTime tripStartTime(String tripId) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder cb = session.getCriteriaBuilder();

			CriteriaQuery<Tuple> cr = cb.createTupleQuery();
			Root<StopTimes> stopTimesRoot = cr.from(StopTimes.class);
			cr.multiselect(
					stopTimesRoot.get("arrival_time").alias("arrival_time")
			);
			cr.where(
					cb.equal(stopTimesRoot.get("stop_sequence"), 1),
					cb.equal(stopTimesRoot.get("trip_id"), tripId)
			);

			Query<Tuple> query = session.createQuery(cr);
			List<Tuple> results = query.getResultList();

			if(results.size() == 1){
				Tuple tuple = results.get(0);
				String arrivalTime = tuple.get("arrival_time", String.class);
				
				String format = "HH:mm:ss";
								
				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(format);
								
				LocalTime dateTime = LocalTime.parse(arrivalTime, timeFormatter);
				
				return dateTime;
			}
		}
		return null;
	}
	
	   public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	        return dateToConvert.toInstant()
	          .atZone(ZoneId.systemDefault())
	          .toLocalDate();
	    }


}
