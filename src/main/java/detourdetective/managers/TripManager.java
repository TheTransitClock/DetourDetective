package detourdetective.managers;

import detourdetective.HibernateUtil;
import detourdetective.entities.Shape;
import detourdetective.entities.Trip;
import detourdetective.entities.VehiclePosition;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
            cr.select(root).where(cb.like(root.get("shape_id"), shape_id));

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
}
