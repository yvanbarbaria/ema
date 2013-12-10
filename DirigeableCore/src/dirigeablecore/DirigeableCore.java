
/**
 *
 * @author Manu, Yvan
 */

package dirigeablecore;

import com.phidgets.GPSPhidget;
import com.phidgets.PhidgetException;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;

public class DirigeableCore implements java.io.Serializable {

    public static String dirigeableId = ""; //On prévoit le cas où plus tard le client dise "Oui mais si je veux pouvoir controler plusieur dirigeables..."	
    public static double lastAltitude = 0;
    public static double lastLongitude = 0;
    public static double lastLatitude = 0;
    public static double lastAmbiantTemperature = 0; //Facultatif
    public static double lastMeasuredTemperature = 0; //Si le capteur est un peu plus loin, au bout d'un cable...
    public static GPositionChangeListener positionListener;

    public static void main(String[] args) throws IOException, PhidgetException {
        //Initialize Dirigeable Id with mac adress
        System.out.println("Initialisation de l'id du Dirireable...");
        NetworkInterface network = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        byte[] mac = network.getHardwareAddress();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        dirigeableId = sb.toString();
        System.out.println("Initialisation faite avec la valeur : " + dirigeableId);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 10);
        server.createContext("/", new dirigeablecore.HttpServer());
        server.setExecutor(null); // creates a default executor
        System.out.println("Lancement du serveur Http...");
        server.start();
        System.out.println("Serveur Http lancé");

        //GPS
        final GPSPhidget gps = new GPSPhidget();
        // On lance l'ouverture du gps pour activer les événements
        gps.openAny();
        System.out.println("Récupération des données GPS... \n");
        
        // On instancie le listener 
        positionListener = new GPositionChangeListener();
        // On attache le listener au gps
        gps.addGPSPositionChangeListener(positionListener);


        //Temperature sensor
        //TODO
    }
}