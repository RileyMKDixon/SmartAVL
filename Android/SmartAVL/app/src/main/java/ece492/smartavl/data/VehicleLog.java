package ece492.smartavl.data;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class VehicleLog {

    private static ArrayList<String> logEntries = new ArrayList<>();

    /**
     * Appends the content to the log preceded with current timestamp
     * @param content
     */
    public static void log(String content) {
        LocalDateTime date = LocalDateTime.now();
        int hour = date.getHour();
        int minute = date.getMinute();
        int second = date.getSecond();
        String timestamp = String.format("[%02d:%02d:%02d] ", hour, minute, second);
        String logString = timestamp + content;
        logEntries.add(logString);
    }

    /**
     * Appends the raw content to the log (without timestamp)
     * @param rawContent
     */
    public static void logRaw(String rawContent) {
        logEntries.add(rawContent);
    }

    /**
     * Clears all entries from the log
     */
    public static void clearLog() {
        logEntries.clear();
    }

    /**
     * Returns the log as an ArrayList of String objects
     * @return
     */
    public static ArrayList<String> getLogAsList() {
        return logEntries;
    }

    /**
     * Returns the log as a single formatted String
     * @return
     */
    public static String getLogAsString() {
        if (logEntries.size() < 1){
            return "";
        }
        return String.join("\n", logEntries);
    }
}
