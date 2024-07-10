package detourdetective.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

import java.io.Serializable;

@Entity(name = "shapes")
@IdClass(ShapeId.class)
public class Shape implements Serializable {
    @Id
    private String shape_id;
    @Id
    private Integer shape_pt_sequence;
    
    private Double shape_pt_lat;
    private Double shape_pt_lon;
    
    private Double shape_dist_traveled;
    private static final long serialVersionUID = 4321L;

    public String getShape_id() {
        return shape_id;
    }

    public void setShape_id(String shape_id) {
        this.shape_id = shape_id;
    }

    public Double getShape_pt_lat() {
        return shape_pt_lat;
    }

    public void setShape_pt_lat(Double shape_pt_lat) {
        this.shape_pt_lat = shape_pt_lat;
    }

    public Double getShape_pt_lon() {
        return shape_pt_lon;
    }

    public void setShape_pt_lon(Double shape_pt_lon) {
        this.shape_pt_lon = shape_pt_lon;
    }

    public Integer getShape_pt_sequence() {
        return shape_pt_sequence;
    }

    public void setShape_pt_sequence(Integer shape_pt_sequence) {
        this.shape_pt_sequence = shape_pt_sequence;
    }

    public Double getShape_dist_traveled() {
        return shape_dist_traveled;
    }

    public void setShape_dist_traveled(Double shape_dist_traveled) {
        this.shape_dist_traveled = shape_dist_traveled;
    }

    @Override
    public String toString() {
        return "EntityShapes{" +
                "shape_id='" + shape_id + '\'' +
                ", shape_pt_lat=" + shape_pt_lat +
                ", shape_pt_lon=" + shape_pt_lon +
                ", shape_pt_sequence=" + shape_pt_sequence +
                ", shape_dist_traveled=" + shape_dist_traveled +
                '}';
    }
}
