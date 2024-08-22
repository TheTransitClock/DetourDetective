package detourdetective.managers;

import detourdetective.HibernateUtil;
import detourdetective.entities.*;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

public class VehiclePositionManager {
    public static List<VehiclePosition> readtripVehiclePosition(String tripId, String vehicleId) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

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

            return results;
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
    public static List<VehiclePosition> readtripVehiclePositionWithDate(String tripId, String vehicleId, Date date) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<VehiclePosition> cr = cb.createQuery(VehiclePosition.class);
            Root<VehiclePosition> root = cr.from(VehiclePosition.class);

            // Constructing the query predicate
            Predicate tripIdPredicate = cb.like(root.get("trip_id"), tripId);
            Predicate vehicleIdPredicate = cb.like(root.get("vehicle_id"), vehicleId);
            Predicate tripDatePredicate = cb.equal(root.get("trip_start_date"),date);
            Predicate combinedPredicate = cb.and(tripIdPredicate, vehicleIdPredicate, tripDatePredicate);


            cr.select(root).where(combinedPredicate);
            cr.orderBy(cb.asc(root.get("timestamp")));

            Query<VehiclePosition> query = session.createQuery(cr);
            List<VehiclePosition> results = query.getResultList();

            StopTimes firstStopTime = TripManager.tripStartTime(tripId);


            List<VehiclePosition> filteredVehiclePositions = new ArrayList<>();
            for (VehiclePosition vp : results) {
                if (vp.getTimestamp().toString().compareTo(firstStopTime.getArrival_time()) >= 0) {
                    filteredVehiclePositions.add(vp);
                }
            }


            return filteredVehiclePositions;
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    public static LocalTime secondsFromMidnight(Timestamp time){
        LocalTime now = LocalTime.now(ZoneId.systemDefault());
        now.toSecondOfDay();

        return null;
    }



    public static List<VehiclePosition> getTripVehiclePositionsByDate(String tripId, LocalDate startDate) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

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
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Set<TripVehicle> tripsByDate(LocalDate date){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<VehiclePosition> cr = cb.createQuery(VehiclePosition.class);
            Root<VehiclePosition> root = cr.from(VehiclePosition.class);
            cr.select(root).where(cb.equal(root.get("timestamp"), date));
            cr.orderBy(cb.asc(root.get("timestamp")));

            Query<VehiclePosition> query = session.createQuery(cr);
            List<VehiclePosition> results = query.getResultList();


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
    public static List<TripVehicle> getTripIdAndVehicleIdForARoute(String routeId) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Tuple> cr = cb.createTupleQuery();  // Use Tuple to select specific fields
            Root<VehiclePosition> root = cr.from(VehiclePosition.class);

            // Select distinct trip_id and vehicle_id
            cr.select(cb.tuple(root.get("trip_id"), root.get("vehicle_id")))
                    .distinct(true)  // Ensure distinct combinations
                    .where(cb.equal(root.get("route_id"), routeId));

            // Execute the query
            Query<Tuple> query = session.createQuery(cr);
            List<Tuple> results = query.getResultList();

            // Convert the results to a list of TripVehicle objects
            List<TripVehicle> tripAndVehicleId = new ArrayList<>();
            for (Tuple result : results) {
                String tripId = result.get(0, String.class);
                String vehicleId = result.get(1, String.class);
                tripAndVehicleId.add(new TripVehicle(tripId, vehicleId));
            }

            return tripAndVehicleId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    }

