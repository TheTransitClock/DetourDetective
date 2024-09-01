package detourdetective.algorithm;

/*
 * This file is part of detourdective.org
 * 
 * Transitime.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (GPL) as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * detourdective.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with detourdective.org .  If not, see <http://www.gnu.org/licenses/>.
 */



import java.util.Date;
import java.util.List;

import detourdetective.entities.Detour;
import detourdetective.entities.VehiclePosition;
import org.locationtech.jts.geom.*;

/**
 * Defines the interface for detecting detours. To create detour detection using
 * an alternate method simply implement this interface and configure
 * DetourDetectorFactory to instantiate the new class when a
 * DetourDetector is needed.
 *
 * @Author Andrew Cunningham
 *
 */
public interface DetourDetector {
    /**
     * Detects detours for the given trip. This interface might need to be
     * changed in the future to return more detailed detour information.
     * 
     * @param tripId the ID of the trip
     * @param vehicleId the ID of the vehicle
     * @return true if a detour is detected, false otherwise
     *
     */

    public List<Detour>  detectDetours(String tripId, String vehicleId, Date date, String withTimestamp, int distanceSquaredThreshold, int onCountThreshold, int offCountThreshold);

    public List<Detour>  detectDetours(List<Point> tripShape, List<VehiclePosition> avlPoints);

    public Point findDetourStart(List<Point> tripShape, List<VehiclePosition> avlPoints);

    public Point findDetourEnd(List<Point> tripShape, List<VehiclePosition> avlPoints);

    public List<Detour> detectDetours(String tripId, String vehicleId, Date date, String withTimestamp);
}
