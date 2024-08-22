package detourdetective.entities;

import jakarta.persistence.*;

@Entity(name = "stop_times")
public class StopTimes {
    @Id
    private String trip_id;
    @Id
    private String stop_id;
    @Id
    private Integer stop_sequence;
    private String arrival_time;
    private String departure_time;
    private String stop_headsign;
    private Integer pickup_type;
    private Integer drop_off_type;
    private double shape_dist_travelled;
    private Integer timepoint;

    public StopTimes(String trip_id, String stop_id, Integer stop_sequence, String arrival_time, String departure_time, String stop_headsign, Integer pickup_type, Integer drop_off_type, double shape_dist_travelled, Integer timepoint) {
        this.trip_id = trip_id;
        this.stop_id = stop_id;
        this.stop_sequence = stop_sequence;
        this.arrival_time = arrival_time;
        this.departure_time = departure_time;
        this.stop_headsign = stop_headsign;
        this.pickup_type = pickup_type;
        this.drop_off_type = drop_off_type;
        this.shape_dist_travelled = shape_dist_travelled;
        this.timepoint = timepoint;
    }

    public StopTimes() {

    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getStop_id() {
        return stop_id;
    }

    public void setStop_id(String stop_id) {
        this.stop_id = stop_id;
    }

    public Integer getStop_sequence() {
        return stop_sequence;
    }

    public void setStop_sequence(Integer stop_sequence) {
        this.stop_sequence = stop_sequence;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getStop_headsign() {
        return stop_headsign;
    }

    public void setStop_headsign(String stop_headsign) {
        this.stop_headsign = stop_headsign;
    }

    public Integer getPickup_type() {
        return pickup_type;
    }

    public void setPickup_type(Integer pickup_type) {
        this.pickup_type = pickup_type;
    }

    public Integer getDrop_off_type() {
        return drop_off_type;
    }

    public void setDrop_off_type(Integer drop_off_type) {
        this.drop_off_type = drop_off_type;
    }

    public double getShape_dist_travelled() {
        return shape_dist_travelled;
    }

    public void setShape_dist_travelled(double shape_dist_travelled) {
        this.shape_dist_travelled = shape_dist_travelled;
    }

    public Integer getTimepoint() {
        return timepoint;
    }

    public void setTimepoint(Integer timepoint) {
        this.timepoint = timepoint;
    }

    @Override
    public String toString() {
        return "StopTimes{" +
                "trip_id='" + trip_id + '\'' +
                ", stop_id='" + stop_id + '\'' +
                ", stop_sequence=" + stop_sequence +
                ", arrival_time='" + arrival_time + '\'' +
                ", departure_time='" + departure_time + '\'' +
                ", stop_headsign='" + stop_headsign + '\'' +
                ", pickup_type=" + pickup_type +
                ", drop_off_type=" + drop_off_type +
                ", shape_dist_travelled=" + shape_dist_travelled +
                ", timepoint=" + timepoint +
                '}';
    }
}
