package detourdetective;

import java.util.List;

public class VehicleApp {
    public static void main(String[] args) {
        List<VehiclePosition> bus776Trip1 = ReadTrip.readtrip();
        for (VehiclePosition position : bus776Trip1) {
            System.out.println(position);
        }
    }
}
