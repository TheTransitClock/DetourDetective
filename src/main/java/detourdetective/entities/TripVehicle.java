package detourdetective.entities;

import java.util.Objects;

public class TripVehicle {
	
	private String tripId;
	private String vehicleId;
	public TripVehicle(String tripId, String vehicleId) {
		super();
		this.tripId = tripId;
		this.vehicleId = vehicleId;
	}
	public TripVehicle(VehiclePosition position) {
		this.tripId = position.getTrip_id();
		this.vehicleId = position.getVehicle_id();
	}
	public String getTripId() {
		return tripId;
	}
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(tripId, vehicleId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TripVehicle other = (TripVehicle) obj;
		return Objects.equals(tripId, other.tripId) && Objects.equals(vehicleId, other.vehicleId);
	}
	@Override
	public String toString() {
		return "TripVehicle [tripId=" + tripId + ", vehicleId=" + vehicleId + "]";
	}
	

}
