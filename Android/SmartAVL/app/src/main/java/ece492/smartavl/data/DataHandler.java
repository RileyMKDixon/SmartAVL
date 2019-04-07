package ece492.smartavl.data;

import org.json.JSONException;
import org.json.JSONObject;

public class DataHandler {

    public static void handleIncomingData(String data) {

        //PIDs
        final String CHECK_ENGINE_PID = "1";
        final String RPM_PID = "12";
        final String SPEED_PID = "13";
        final String RUNTIME_PID = "31";
        final String LATITUDE_PID = "lat";
        final String LONGITUDE_PID = "long";
        final String VIN_PID = "vin";

        JSONObject obj;

        try {
            obj = new JSONObject(data);
        }catch (Throwable t){
            t.printStackTrace();
            VehicleLog.log(data);
            return;
        }

        if (obj.has(CHECK_ENGINE_PID)){
            try{
                int checkEngine = obj.getInt(CHECK_ENGINE_PID);
                if (checkEngine == 0){
                    VehicleData.setCheckEngineStatus(VehicleData.CHECK_ENGINE_OFF);
                    VehicleLog.log("Check Engine Light: OFF");
                }else if (checkEngine == 1){
                    VehicleData.setCheckEngineStatus(VehicleData.CHECK_ENGINE_ON);
                    VehicleLog.log("Check Engine Light: ON");
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
        }

        if (obj.has(RPM_PID)){
            try{
                int rpm = obj.getInt(RPM_PID);
                VehicleData.setRPM(rpm);
                VehicleLog.log("RPM: " + rpm);
            }catch(JSONException e){
                e.printStackTrace();
            }
        }

        if (obj.has(SPEED_PID)){
            try{
                int speed = obj.getInt(SPEED_PID);
                VehicleData.setSpeed(speed);
                VehicleLog.log("Speed: " + speed + " km/h");
            }catch(JSONException e){
                e.printStackTrace();
            }
        }

        if (obj.has(RUNTIME_PID)){
            try{
                int runtime = obj.getInt(RUNTIME_PID);
                VehicleData.setRunTimeSinceEngineStart(runtime);
                VehicleLog.log("Runtime: " + runtime + " sec");
            }catch(JSONException e){
                e.printStackTrace();
            }
        }

        if (obj.has(LATITUDE_PID)){
            try{
                double latitude = obj.getDouble(LATITUDE_PID);
                VehicleData.setLatitude(latitude);
                VehicleLog.log("Latitude: " + latitude);
            }catch(JSONException e){
                e.printStackTrace();
            }
        }

        if (obj.has(LONGITUDE_PID)){
            try{
                double longitude = obj.getDouble(LONGITUDE_PID);
                VehicleData.setLongitude(longitude);
                VehicleLog.log("Longitude: " + longitude);
            }catch(JSONException e){
                e.printStackTrace();
            }
        }

        if (obj.has(VIN_PID)){
            try{
                String vin = obj.getString(VIN_PID);
                VehicleData.setVIN(vin);
                VehicleLog.log("VIN: " + vin);
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
}
