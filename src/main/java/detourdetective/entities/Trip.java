package detourdetective.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity(name = "trips")
public class Trip implements Serializable {
    @Id
    private String trip_id;
    private String route_id;
    private String service_id;
    private Integer direction_id;
    private String block_id;
    private String trip_type;
    private String trip_headsign;
    private String trip_short_name;
    private Integer bikes_allowed;
    private Integer wheelchair_accessible;
    public String getShape_id() {
		return shape_id;
	}

	public void setShape_id(String shape_id) {
		this.shape_id = shape_id;
	}

	private String shape_id;
    @OneToMany(fetch = FetchType.EAGER, targetEntity=Shape.class)
    @JoinColumn(name = "shape_id", referencedColumnName = "shape_id")
    private List<Shape> shapes;

    private static final long serialVersionUID = 1234L;

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

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public Integer getDirection_id() {
        return direction_id;
    }

    public void setDirection_id(Integer direction_id) {
        this.direction_id = direction_id;
    }

    public String getBlock_id() {
        return block_id;
    }

    public void setBlock_id(String block_id) {
        this.block_id = block_id;
    }

    public String getTrip_type() {
        return trip_type;
    }

    public void setTrip_type(String trip_type) {
        this.trip_type = trip_type;
    }

    public String getTrip_headsign() {
        return trip_headsign;
    }

    public void setTrip_headsign(String trip_headsign) {
        this.trip_headsign = trip_headsign;
    }

    public String getTrip_short_name() {
        return trip_short_name;
    }

    public void setTrip_short_name(String trip_short_name) {
        this.trip_short_name = trip_short_name;
    }

    public Integer getBikes_allowed() {
        return bikes_allowed;
    }

    public void setBikes_allowed(Integer bikes_allowed) {
        this.bikes_allowed = bikes_allowed;
    }

    public Integer getWheelchair_accessible() {
        return wheelchair_accessible;
    }

    public void setWheelchair_accessible(Integer wheelchair_accessible) {
        this.wheelchair_accessible = wheelchair_accessible;
    }

   

    @Override
	public String toString() {
		return "Trip [trip_id=" + trip_id + ", route_id=" + route_id + ", service_id=" + service_id + ", direction_id="
				+ direction_id + ", block_id=" + block_id + ", trip_type=" + trip_type + ", trip_headsign="
				+ trip_headsign + ", trip_short_name=" + trip_short_name + ", bikes_allowed=" + bikes_allowed
				+ ", wheelchair_accessible=" + wheelchair_accessible + ", shape_id=" + shape_id + ", shapes=" + shapes
				+ "]";
	}

	public List<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }
}

