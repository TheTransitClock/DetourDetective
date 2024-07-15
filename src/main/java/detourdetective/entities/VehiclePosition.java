package detourdetective.entities;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity(name = "vehicle_positions")
public class VehiclePosition {
    @Id
    private int oid;
    private String trip_id;
    private String route_id;
    private String trip_start_time;
    private String vehicle_id;
    private String vehicle_label;
    private String vehicle_license_plate;
    private Double position_latitude;
    private Double position_longitude;
    private Double position_bearing;
    private Double position_speed;
    private String occupancy_status;

    private LocalDate timestamp;

    private LocalDate trip_start_date;

    public VehiclePosition(){

    }

    public VehiclePosition(int oid, String trip_id, String route_id, String trip_start_time, String vehicle_id, String vehicle_label, String vehicle_license_plate, Double position_latitude, Double position_longitude, Double position_bearing, Double position_speed, String occupancy_status, LocalDate timestamp, LocalDate trip_start_date) {
        this.oid = oid;
        this.trip_id = trip_id;
        this.route_id = route_id;
        this.trip_start_time = trip_start_time;
        this.vehicle_id = vehicle_id;
        this.vehicle_label = vehicle_label;
        this.vehicle_license_plate = vehicle_license_plate;
        this.position_latitude = position_latitude;
        this.position_longitude = position_longitude;
        this.position_bearing = position_bearing;
        this.position_speed = position_speed;
        this.occupancy_status = occupancy_status;
        this.timestamp = timestamp;
        this.trip_start_date = trip_start_date;
    }

    public VehiclePosition(String trip_id, String route_id) {
        this.trip_id = trip_id;
        this.route_id = route_id;

    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getTrip_start_time() {
        return trip_start_time;
    }

    public void setTrip_start_time(String trip_start_time) {
        this.trip_start_time = trip_start_time;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getVehicle_label() {
        return vehicle_label;
    }

    public void setVehicle_label(String vehicle_label) {
        this.vehicle_label = vehicle_label;
    }

    public String getVehicle_license_plate() {
        return vehicle_license_plate;
    }

    public void setVehicle_license_plate(String vehicle_license_plate) {
        this.vehicle_license_plate = vehicle_license_plate;
    }

    public Double getPosition_latitude() {
        return position_latitude;
    }

    public void setPosition_latitude(Double position_latitude) {
        this.position_latitude = position_latitude;
    }

    public Double getPosition_longitude() {
        return position_longitude;
    }

    public void setPosition_longitude(Double position_longitude) {
        this.position_longitude = position_longitude;
    }

    public Double getPosition_bearing() {
        return position_bearing;
    }

    public void setPosition_bearing(Double position_bearing) {
        this.position_bearing = position_bearing;
    }

    public Double getPosition_speed() {
        return position_speed;
    }

    public void setPosition_speed(Double position_speed) {
        this.position_speed = position_speed;
    }

    public String getOccupancy_status() {
        return occupancy_status;
    }

    public void setOccupancy_status(String occupancy_status) {
        this.occupancy_status = occupancy_status;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDate getTrip_start_date() {
        return trip_start_date;
    }

    public void setTrip_start_date(LocalDate trip_start_date) {
        this.trip_start_date = trip_start_date;
    }

    @Override
    public String toString() {
        return "VehiclePosition{" +
                "oid=" + oid +
                ", trip_id='" + trip_id + '\'' +
                ", route_id='" + route_id + '\'' +
                ", trip_start_time='" + trip_start_time + '\'' +
                ", vehicle_id='" + vehicle_id + '\'' +
                ", vehicle_label='" + vehicle_label + '\'' +
                ", vehicle_license_plate='" + vehicle_license_plate + '\'' +
                ", position_latitude=" + position_latitude +
                ", position_longitude=" + position_longitude +
                ", position_bearing=" + position_bearing +
                ", position_speed=" + position_speed +
                ", occupancy_status='" + occupancy_status + '\'' +
                ", timestamp=" + timestamp +
                ", trip_start_date=" + trip_start_date +
                '}';
    }
}
