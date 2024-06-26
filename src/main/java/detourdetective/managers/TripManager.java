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
import org.locationtech.jts.awt.PointShapeFactory.Point;

import java.util.ArrayList;
import java.util.List;

public class TripManager {
	
	public static List<Shape> readTripShape(String tripId)
	{
		Trip trip = TripManager.readtrip(tripId);
		trip.getShape_id();
		System.out.println(trip);
		return null;
	}
	public static List<List<Double>> readShapeLatAndLong(String tripId){
		Trip trip = TripManager.readtrip(tripId);
		trip.getShape_id();
		List<Shape> shapes = trip.getShapes();
		List<List<Double>> latAndLong = new ArrayList();
		  for (Shape shape : shapes) {
	            double latitude = shape.getShape_pt_lat();
	            double longitude = shape.getShape_pt_lon();
	            List<Double> pair = new ArrayList<>();
	            pair.add(latitude);
	            pair.add(longitude);
	            latAndLong.add(pair);
	        }
		return latAndLong;
		
	}
    public static Trip readtrip(String tripId) {
        Transaction transaction = null;
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
