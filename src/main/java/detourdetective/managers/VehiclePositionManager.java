package detourdetective.managers;

import detourdetective.HibernateUtil;
import detourdetective.entities.Shape;
import detourdetective.entities.Trip;
import detourdetective.entities.TripVehicle;
import detourdetective.entities.VehiclePosition;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VehiclePositionManager {
    public static List<VehiclePosition> readtripVehiclePosition(String tripId, String vehicleId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<VehiclePosition> cr = cb.createQuery(VehiclePosition.class);
            Root<VehiclePosition> root = cr.from(VehiclePosition.class);

            // Constructing the query predicate
            Predicate tripIdPredicate = cb.like(root.get("trip_id"), tripId);
            Predicate vehicleIdPredicate = cb.like(root.get("vehicle_id"), vehicleId);
            Predicate combinedPredicate = cb.and(tripIdPredicate, vehicleIdPredicate);
            

            cr.select(root).where(combinedPredicate);
            cr.orderBy(cb.asc(root.get("timestamp")));

            Query<VehiclePosition> query = session.createQuery(cr);
            List<VehiclePosition> results = query.getResultList();

            // commit transaction
            transaction.commit();
            return results;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }
    public static List<VehiclePosition> getTripVehiclePositionsByDate(String tripId, LocalDate startDate) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<VehiclePosition> cr = cb.createQuery(VehiclePosition.class);
            Root<VehiclePosition> root = cr.from(VehiclePosition.class);
            Predicate tripIdPredicate = cb.like(root.get("trip_id"), tripId);
            Predicate startDatePredicate = cb.equal(root.get("trip_start_date"), startDate);

            Predicate combinedPredicate = cb.and(tripIdPredicate, startDatePredicate);

            cr.select(root).where(combinedPredicate);

            Query<VehiclePosition> query = session.createQuery(cr);
            List<VehiclePosition> results = query.getResultList();
            // commit transaction
            transaction.commit();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            return null;
        }
    }
    public static Set<TripVehicle> tripsByDate(LocalDate date){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<VehiclePosition> cr = cb.createQuery(VehiclePosition.class);
            Root<VehiclePosition> root = cr.from(VehiclePosition.class);
            cr.select(root).where(cb.equal(root.get("timestamp"), date));

            Query<VehiclePosition> query = session.createQuery(cr);
            List<VehiclePosition> results = query.getResultList();
            cr.orderBy(cb.asc(root.get("timestamp")));

            Set<TripVehicle> tripSet = new HashSet<>();

 
    		for (VehiclePosition position : results) {
    			tripSet.add(new TripVehicle(position));
    		}
    		return tripSet;

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
    public static Set<String> getTripIdForARoute(String routeId) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<VehiclePosition> cr = cb.createQuery(VehiclePosition.class);
            Root<VehiclePosition> root = cr.from(VehiclePosition.class);
            cr.select(root).where(cb.like(root.get("route_id"), routeId));

            Query<VehiclePosition> query = session.createQuery(cr);
            List<VehiclePosition> results = query.getResultList();

            // Prepare the set to hold unique trip and vehicle IDs
            Set<String> tripandvehicleId = new HashSet<>();

            // Iterate over the results and add the IDs to the set
            for (VehiclePosition vehiclePosition : results) {
                String tripId = vehiclePosition.getTrip_id();
                String vehicleId= vehiclePosition.getVehicle_id();
                tripandvehicleId.add(tripId+ "--:" + vehicleId);
            }
            return tripandvehicleId;
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
   
   



}
