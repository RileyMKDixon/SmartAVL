package ece492.smartavl.data;

public class DataHandler {

    public static void handleIncomingData(String data) {

        // Log raw data, for now, TODO: format this data for readable printing to the log
        VehicleLog.log(data);

        // TODO, interpret incoming data string and update appropriate values in VehicleData to match
        // eg.:
        //VehicleData.setSpeed(...);


    }
}
