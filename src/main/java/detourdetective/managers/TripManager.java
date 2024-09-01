package detourdetective.managers;

import detourdetective.utils.HibernateUtil;
import detourdetective.entities.Shape;
import detourdetective.entities.StopTimes;
import detourdetective.entities.Trip;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TripManager {

	private static final Logger logger = Logger.getLogger(TripManager.class);
	public static List<Point> readShapeLatAndLong(String tripId) {
		
		Trip trip = readtrip(tripId);
		List<Point> latAndLong = new ArrayList<>();
		if(trip!=null)
		{
			List<Shape> shapes = readShape(trip.getShape_id());
	
			GeometryFactory geometryFactory = new GeometryFactory();

            if (shapes != null) {
                for (Shape shape : shapes) {
                    double latitude = shape.getShape_pt_lat();
                    double longitude = shape.getShape_pt_lon();
                    Coordinate coordinate = new Coordinate(longitude, latitude); // Longitude first, then latitude
                    Point point = geometryFactory.createPoint(coordinate);
                    latAndLong.add(point);
                }
            }
        }
		return latAndLong;
	}
    private static List<Shape> readShape(String shape_id) {

    	try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Shape> cr = cb.createQuery(Shape.class);
            Root<Shape> root = cr.from(Shape.class);
            cr.select(root).where(cb.equal(root.get("shape_id"), shape_id));
			cr.orderBy(cb.asc(root.get("shape_pt_sequence")));

            Query<Shape> query = session.createQuery(cr);

            return query.getResultList();
          
        } catch (Exception e) {
			logger.error(e);
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
          
            if(!results.isEmpty()) {
                return results.get(0);
            }else{
                return null;
            }
        } catch (Exception e) {
           
            logger.error(e);
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

				
				return parseTimeWith24HourHandling(arrivalTime);
			}
		}
		return null;
	}
	public static LocalTime tripEndTime(String tripId) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder cb = session.getCriteriaBuilder();

			CriteriaQuery<Tuple> cr = cb.createTupleQuery();
			Root<StopTimes> stopTimesRoot = cr.from(StopTimes.class);
			cr.multiselect(
					stopTimesRoot.get("arrival_time").alias("arrival_time")
			);
			cr.where(
					cb.equal(stopTimesRoot.get("trip_id"), tripId)
			);
			cr.orderBy(cb.desc(stopTimesRoot.get("stop_sequence")));

			Query<Tuple> query = session.createQuery(cr);
			List<Tuple> results = query.getResultList();

			if (!results.isEmpty()) {
				Tuple tuple = results.get(0);
				String arrivalTime = tuple.get("arrival_time", String.class);


				return parseTimeWith24HourHandling(arrivalTime);
			}
		}
		return null;
	}

	public static LocalTime parseTimeWith24HourHandling(String arrivalTime) {
		if (arrivalTime.startsWith("24")) {
			// Adjust the time by converting "24:xx:xx" to "00:xx:xx" and handling the next day.
			arrivalTime = "00" + arrivalTime.substring(2);

			String format = "HH:mm:ss";

			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(format);

			LocalTime dateTime = LocalTime.parse(arrivalTime, timeFormatter);



			return dateTime.plusHours(24);
		} else {
			String format = "HH:mm:ss";

			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(format);
			return LocalTime.parse(arrivalTime, timeFormatter);
		}
	}
	
	   public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	        return dateToConvert.toInstant()
	          .atZone(ZoneId.systemDefault())
	          .toLocalDate();
	    }


}
