package detourdetective;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class ReadTrip {
    public static List<VehiclePosition> readtrip() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<VehiclePosition> cr = cb.createQuery(VehiclePosition.class);
            Root<VehiclePosition> root = cr.from(VehiclePosition.class);
            cr.select(root).where(cb.like(root.get("trip_id"), "JG_A4-Weekday-SDon-084600_B16_414"));

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
    public static List<VehiclePosition> getTripVehiclePositions() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<VehiclePosition> cr = cb.createQuery(VehiclePosition.class);
            Root<VehiclePosition> root = cr.from(VehiclePosition.class);
            Predicate tripIdPredicate = cb.like(root.get("trip_id"), "JG_A4-Weekday-SDon-084600_B16_414");
            Predicate startDatePredicate = cb.like(root.get("trip_start_date"), "2024-03-26");

            Predicate combinedPredicate = cb.and(tripIdPredicate, startDatePredicate);

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
    public static List<VehiclePosition> getTripsBetweenStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<VehiclePosition> cr = cb.createQuery(VehiclePosition.class);
            Root<VehiclePosition> root = cr.from(VehiclePosition.class);

            cr.select(root).where(cb.between(root.get("trip_start_date"),startDate,endDate));

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



}
