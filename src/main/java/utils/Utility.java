/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Utility {

    public static class TimeSerializer implements JsonSerializer<Time> {
        private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

        @Override
        public JsonElement serialize(Time src, Type typeOfSrc, JsonSerializationContext context) {
            // Format the time to "HH:mm:ss" before serializing
            return new JsonPrimitive(TIME_FORMAT.format(src));
        }
    }
    public static class TimeDeserializer implements JsonDeserializer<Time> {

        @Override
        public Time deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String timeString = json.getAsString();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                Date date = sdf.parse(timeString);
                return new Time(date.getTime());
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }
    }
}
