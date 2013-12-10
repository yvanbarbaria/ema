/**
 *
 * @author Manu, Yvan
 * Listener qui permet de suivre le changement de position des coordonnées GPS
 */
package dirigeablecore;

import com.phidgets.GPSPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.GPSPositionChangeEvent;
import com.phidgets.event.GPSPositionChangeListener;

public class GPositionChangeListener implements GPSPositionChangeListener {

    public GPositionChangeListener() {
        //Constructeur vide pour le moment à voir si dans le futur si nous avons besoin d'attributs
        // Permet d'instancier le listener et d'ajouter l'évenement positionChanged
    }

    // Evenement qui permet de suivre le changement de position GPS
    public void gpsPositionChanged(GPSPositionChangeEvent gpsPositionChangeEvent) {

        GPSPhidget gps = (GPSPhidget) gpsPositionChangeEvent.getSource();

        try {
            System.out.println(gps.toString()
                    + ", Velocity: " + gps.getVelocity() + "km/h"
                    + ", Heading: " + gps.getHeading() + " degrees"
                    +"\n" +", Latitude: " + gps.getLatitude() + "°"
                    + ", Altitude: " + gps.getAltitude() + "m"
                    + ", Longitude: " + gps.getLongitude() + "°" +"\n");
            // On récupère les coordonnées GPS courante
            DirigeableCore.lastLatitude = gps.getLatitude();
            DirigeableCore.lastLongitude = gps.getLongitude();
            DirigeableCore.lastAltitude = gps.getAltitude();

        } catch (PhidgetException ex) {
            System.out.println("\n--->Error: " + ex.getDescription());
        }
    }
}
