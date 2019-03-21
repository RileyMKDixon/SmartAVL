package ece492.smartavl.data;

public class VehicleData {

    // status code definitions (defined by us)
    public static final int STATUS_DISCONNECTED = 0x00;
    public static final int STATUS_CONNECTED = 0x01;
    public static final int IGNITION_OFF = 0x10;
    public static final int IGNITION_ON = 0x11;
    public static final int LOCK_UNLOCKED = 0x20;
    public static final int LOCK_LOCKED = 0x21;
    public static final int BATTERY_LOW = 0x30;
    public static final int BATTERY_GOOD = 0x31;
    public static final int HEADLIGHTS_OFF = 0x40;
    public static final int HEADLIGHTS_ON = 0x41;
    public static final int HIGH_BEAMS_OFF = 0x50;
    public static final int HIGH_BEAMS_ON = 0x51;
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

    private static String vehicleMake = "Unknown";
    private static String vehicleModel = "Unknown";

    private static int vehicleYear = 1900;

    private static int vehicleSpeed = 0;
    private static int vehicleRPM = 0;
    private static int vehicleFuelPercentage = 100;

    private static double vehicleLatitude = 53.527570;
    private static double vehicleLongitude = -113.529453;

    private static int vehicleConnectionStatus = STATUS_DISCONNECTED;
    private static int vehicleIgnitionStatus = IGNITION_OFF;
    private static int vehicleLockStatus = LOCK_UNLOCKED;
    private static int vehicleBatteryStatus = BATTERY_GOOD;
    private static int vehicleHeadlightStatus = HEADLIGHTS_OFF;
    private static int vehicleHighBeamStatus = HIGH_BEAMS_OFF;
    private static int vehicleCheckEngineStatus = CHECK_ENGINE_OFF;


    public static void setToDefaults() {
        vehicleMake = "Unknown";
        vehicleModel = "Unknown";
        vehicleYear = 1900;
        vehicleSpeed = 0;
        vehicleRPM = 0;
        vehicleFuelPercentage = 100;
        vehicleLatitude = 53.527570;
        vehicleLongitude = -113.529453;
        vehicleConnectionStatus = STATUS_DISCONNECTED;
        vehicleIgnitionStatus = IGNITION_OFF;
        vehicleLockStatus = LOCK_UNLOCKED;
        vehicleBatteryStatus = BATTERY_GOOD;
        vehicleHeadlightStatus = HEADLIGHTS_OFF;
        vehicleHighBeamStatus = HIGH_BEAMS_OFF;
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
    }

    /**
     * Returns the make of the vehicle
     * @return
     */
    public static String getMake() {
        return vehicleMake;
    }

    /**
     * Sets the make of the vehicle
     * @param make
     */
    public static void setMake(String make) {
        vehicleMake = make;
    }

    /**
     * Returns the model of the vehicle
     * @return
     */
    public static String getModel() {
        return vehicleModel;
    }

    /**
     * Sets the model of the vehicle
     * @param model
     */
    public static void setModel(String model) {
        vehicleModel = model;
    }

    /**
     * Returns the year of the vehicle
     * @return
     */
    public static int getYear() {
        return vehicleYear;
    }

    /**
     * Sets the year of the vehicle
     * @param year
     */
    public static void setYear(int year) {
        vehicleYear = year;
    }

    /**
     * Returns the current vehicle speed
     * @return
     */
    public static int getSpeed() {
        return vehicleSpeed;
    }

    /**
     * Sets the current vehicle speed
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
     * Returns the current vehicle fuel percentage
     * @return
     */
    public static int getFuelPercentage() {
        return vehicleFuelPercentage;
    }

    /**
     * Sets the current vehicle fuel percentage
     * @param fuelPercentage
     */
    public static void setFuelPercentage(int fuelPercentage) {
        if (fuelPercentage < 0) {
            fuelPercentage = 0;
        }else if (fuelPercentage > 100){
            fuelPercentage = 100;
        }
        vehicleFuelPercentage = fuelPercentage;
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
     * Sets the status of the vehicle ignition
     * @param status
     */
    public static void setIgnitionStatus(int status) {
        vehicleIgnitionStatus = status;
    }

    /**
     * Returns the vehicle ignition status
     * @return
     */
    public static int getIgnitionStatus() {
        return vehicleIgnitionStatus;
    }

    /**
     * Returns true if vehicle ignition is on
     * @return
     */
    public static boolean isIgnitionOn() {
        return (vehicleIgnitionStatus == IGNITION_ON);
    }

    /**
     * Sets the status of the vehicle door locks
     * @param status
     */
    public static void setLockStatus(int status) {
        vehicleLockStatus = status;
    }

    /**
     * Returns the status of the vehicle door locks
     * @return
     */
    public static int getLockStatus() {
        return vehicleLockStatus;
    }

    /**
     * Returns true if vehicle is locked
     * @return
     */
    public static boolean isLocked() {
        return (vehicleLockStatus == LOCK_LOCKED);
    }

    /**
     * Sets the status of the vehicle battery condition
     * @param status
     */
    public static void setBatteryStatus(int status) {
        vehicleBatteryStatus = status;
    }

    /**
     * Returns the status of the vehicle battery condition
     * @return
     */
    public static int getBatteryStatus() {
        return vehicleBatteryStatus;
    }

    /**
     * Returns true if vehicle battery status is good
     * @return
     */
    public static boolean isBatteryGood() {
        return (vehicleBatteryStatus == BATTERY_GOOD);
    }

    /**
     * Sets the status of the vehicle headlights
     * @param status
     */
    public static void setHeadlightStatus(int status) {
        vehicleHeadlightStatus = status;
    }

    /**
     * Returns the status of the vehicle headlights
     * @return
     */
    public static int getHeadlightStatus() {
        return vehicleHeadlightStatus;
    }

    /**
     * Returns true if vehicle headlights are on
     * @return
     */
    public static boolean isHeadlightsOn() {
        return (vehicleHeadlightStatus == HEADLIGHTS_ON);
    }

    /**
     * Sets the status of the vehicle high beams
     * @param status
     */
    public static void setHighBeamStatus(int status) {
        vehicleHighBeamStatus = status;
    }

    /**
     * Returns the status of the vehicle high beams
     * @return
     */
    public static int getHighBeamStatus() {
        return vehicleHighBeamStatus;
    }

    /**
     * Returns true if vehicle headlights are on
     * @return
     */
    public static boolean isHighBeamsOn() {
        return (vehicleHighBeamStatus == HIGH_BEAMS_ON);
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
