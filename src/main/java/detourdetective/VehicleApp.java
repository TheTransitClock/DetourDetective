package detourdetective;

import java.time.LocalDate;
import java.util.List;

public class VehicleApp {
    public static void main(String[] args) {
        LocalDate startdate = LocalDate.of(2024,03,27);
        LocalDate endDate = LocalDate.of(2024,03,29);
        List<VehiclePosition> between27And29March = ReadTrip.getTripsBetweenStartDateAndEndDate(startdate,endDate);
        for (VehiclePosition position : between27And29March) {
            System.out.println(position);
        }
    }
}
