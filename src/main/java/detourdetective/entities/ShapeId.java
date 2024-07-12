package detourdetective.entities;

public class ShapeId {
	private String shape_id;
	private Integer shape_pt_sequence;

	public String getShape_id() {
		return shape_id;
	}

	public void setShape_id(String shape_id) {
		this.shape_id = shape_id;
	}

	public Integer getShape_pt_sequence() {
		return shape_pt_sequence;
	}

	public void setShape_pt_sequence(Integer shape_pt_sequence) {
		this.shape_pt_sequence = shape_pt_sequence;
	}

	public ShapeId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShapeId(String shape_id, Integer shape_pt_sequence) {
		super();
		this.shape_id = shape_id;
		this.shape_pt_sequence = shape_pt_sequence;
	}

	
}
