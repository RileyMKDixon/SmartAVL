package ece492.smartavl.data;

public class VehicleData {

    // status code definitions (defined by us)
    public static final int STATUS_DISCONNECTED = 0x00;
    public static final int STATUS_CONNECTED = 0x01;
    public static final int CHECK_ENGINE_OFF = 0x60;
    public static final int CHECK_ENGINE_ON = 0x61;

    public static final int BLUETOOTH_NOT_INITIALIZED = -1;
    public static final int BLUETOOTH_SETUP_COMPLETE = 0;
    public static final int BLUETOOTH_NOT_SUPPORTED = 1;
    public static final int BLUETOOTH_NOT_ENABLED = 2;
    public static final int BLUETOOTH_NO_PAIRED_DEVICES = 3;
    public static final int BLUETOOTH_CONNECTION_FAILED = 4;
    public static final int BLUETOOTH_CONNECTION_DISCONNECTED = 5;
    public static final int BLUETOOTH_SETUP_TIMEOUT = 6;

    private static int bluetoothStatus = BLUETOOTH_NOT_INITIALIZED;

    private static String vehicleVIN = "Unknown";

    private static int vehicleSpeed = 0;
    private static int vehicleRPM = 0;
    private static int vehicleRunTimeSinceEngineStart = 0;

    private static double vehicleLatitude = 53.527570;
    private static double vehicleLongitude = -113.529453;

    private static int vehicleConnectionStatus = STATUS_DISCONNECTED;
    private static int vehicleCheckEngineStatus = CHECK_ENGINE_OFF;


    public static void setToDefaults() {
        vehicleVIN = "Unknown";
        vehicleSpeed = 0;
        vehicleRPM = 0;
        vehicleRunTimeSinceEngineStart = 0;
        vehicleLatitude = 53.527570;
        vehicleLongitude = -113.529453;
        vehicleConnectionStatus = STATUS_DISCONNECTED;
        vehicleCheckEngineStatus = CHECK_ENGINE_OFF;
    }

    /**
     * Returns the status of the bluetooth connection/setup process
     * @return
     */
    public static int getBluetoothStatus() {
        return bluetoothStatus;
    }

    /**
     * Sets the status of the bluetooth connection/setup process
     * @param status
     */
    public static void setBluetoothStatus(int status) {
        bluetoothStatus = status;
        if (bluetoothStatus == BLUETOOTH_SETUP_COMPLETE){
            // if bluetooth is set up, set displayed status to connected
            setConnectionStatus(STATUS_CONNECTED);
        }else{
            // if bluetooth is no longer set up, reset VehicleData to defaults
            setToDefaults();
        }
    }

    /**
     * Returns the VIN of the vehicle
     * @return
     */
    public static String getVIN() {
        return vehicleVIN;
    }

    /**
     * Sets the VIN of the vehicle
     * @param vin
     */
    public static void setMake(String vin) {
        vehicleVIN = vin;
    }

    /**
     * Returns the current vehicle speed in km/h
     * @return
     */
    public static int getSpeed() {
        return vehicleSpeed;
    }

    /**
     * Sets the current vehicle speed in km/h
     * @param speed
     */
    public static void setSpeed(int speed) {
        vehicleSpeed = speed;
    }

    /**
     * Returns the current vehicle RPM
     * @return
     */
    public static int getRPM() {
        return vehicleRPM;
    }

    /**
     * Sets the current vehicle RPM
     * @param rpm
     */
    public static void setRPM(int rpm) {
        vehicleRPM = rpm;
    }

    /**
     * Returns the time since engine start in seconds
     * @return
     */
    public static int getRunTimeSinceEngineStart() {
        return vehicleRunTimeSinceEngineStart;
    }

    /**
     * Sets the current time since engine start in seconds
     * @param runTimeSinceEngineStart
     */
    public static void setRunTimeSinceEngineStart(int runTimeSinceEngineStart) {
        vehicleRunTimeSinceEngineStart = runTimeSinceEngineStart;
    }

    /**
     * Returns the current vehicle latitude
     * @return
     */
    public static double getLatitude() {
        return vehicleLatitude;
    }

    /**
     * Sets the current vehicle latitude
     * @param latitude
     */
    public static void setLatitude(double latitude) {
        vehicleLatitude = latitude;
    }

    /**
     * Returns the current vehicle longitude
     * @return
     */
    public static double getLongitude() {
        return vehicleLongitude;
    }

    /**
     * Sets the current vehicle longitude
     * @param longitude
     */
    public static void setLongitude(double longitude) {
        vehicleLongitude = longitude;
    }

    /**
     * Sets the connection status with the vehicle
     * @param status
     */
    public static void setConnectionStatus(int status) {
        vehicleConnectionStatus = status;
    }

    /**
     * Returns the connection status with the vehicle
     * @return
     */
    public static int getConnectionStatus() {
        return vehicleConnectionStatus;
    }

    /**
     * Returns true if the vehicle status is connected
     * @return
     */
    public static boolean isConnected() {
        return (vehicleConnectionStatus == STATUS_CONNECTED);
    }

    /**
     * Sets the status of the vehicle check engine light
     * @param status
     */
    public static void setCheckEngineStatus(int status) {
        vehicleCheckEngineStatus = status;
    }

    /**
     * Returns the status of the vehicle check engine light
     * @return
     */
    public static int getCheckEngineStatus() {
        return vehicleCheckEngineStatus;
    }

    /**
     * Returns true if the vehicle check engine light is on
     * @return
     */
    public static boolean isCheckEngineOn() {
        return (vehicleCheckEngineStatus == CHECK_ENGINE_ON);
    }

}
