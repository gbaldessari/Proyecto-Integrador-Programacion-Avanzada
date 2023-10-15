package cl.ucn.PIPA.utils;

public class Utils {

    // Radio de la Tierra en metros (aproximado)
    private static final double EARTH_RADIUS = 6371000;
    
    public static double haversine(double startLat, double startLong, double endLat, double endLong) {

        double dLat  = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat   = Math.toRadians(endLat);

        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS/1000 * c;
    }
    private static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
