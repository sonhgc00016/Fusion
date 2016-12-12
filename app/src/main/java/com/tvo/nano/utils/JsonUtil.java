package com.tvo.nano.utils;

import android.util.Log;

import com.tvo.nano.fusioncosmetics.Constants;

import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {

    /**
     * Convert array list from json string
     *
     * @param <T>        the generic type
     * @param json
     * @param item_class
     * @return the array list
     */
    public static <T> ArrayList<T> convertArrayListFromJsonString(String json, Class<T> item_class) {
        if (json == null) return null;

        ArrayList<T> ret = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, item_class);
            ret = mapper.readValue(json, type);
        } catch (JsonParseException e) {
            e.printStackTrace();
            return null;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return ret;
    }


    /**
     * Convert a object from json object
     *
     * @param <T>        the generic type
     * @param json
     * @param returnType
     * @return the object
     */
    public static <T> T convertObjectFromJsonObject(JSONObject json, Class<T> returnType, String rootNode) {
        T ret = null;
        if (json != null) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            try {
                try {
                    ret = mapper.readValue(String.valueOf(json.getJSONObject(rootNode)), returnType);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (JsonParseException e) {
                e.printStackTrace();
                return null;
            } catch (JsonMappingException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return ret;
    }

}
