package detourdetective.managers;

import detourdetective.HibernateUtil;
import detourdetective.entities.Shape;
import detourdetective.entities.Trip;
import detourdetective.entities.VehiclePosition;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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



}
