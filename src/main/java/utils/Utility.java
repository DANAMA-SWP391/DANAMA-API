/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Utility {


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
