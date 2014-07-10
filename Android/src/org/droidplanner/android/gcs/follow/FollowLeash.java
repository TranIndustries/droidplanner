package org.droidplanner.android.gcs.follow;

import org.droidplanner.core.drone.Drone;
import org.droidplanner.core.helpers.coordinates.Coord2D;
import org.droidplanner.core.helpers.geoTools.GeoTools;
import org.droidplanner.core.helpers.units.Length;

import android.location.Location;

public class FollowLeash extends FollowType {

	public FollowLeash(Drone drone, Length radius, double mIN_TIME_MS) {
		super(drone, radius, mIN_TIME_MS);
	}

	@Override
	public void processNewLocation(Location location) {
		Coord2D gcsCoord = new Coord2D(location.getLatitude(),
				location.getLongitude());
		if (GeoTools.getDistance(gcsCoord, drone.GPS.getPosition())
				.valueInMeters() > radius.valueInMeters()) {
			double headingGCStoDrone = GeoTools.getHeadingFromCoordinates(
					gcsCoord, drone.GPS.getPosition());
			Coord2D goCoord = GeoTools.newCoordFromBearingAndDistance(gcsCoord,
					headingGCStoDrone, radius.valueInMeters());
			drone.guidedPoint.newGuidedCoord(goCoord);
		}
	}

}
