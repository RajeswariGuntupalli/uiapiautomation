package com.test.utilities;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A factory for creating JSON objects.
 */
public final class JSONFactory {

    private static JSONObject jsonObjectStored;
    private static HashMap<String, Object> jsonMap = new HashMap<String, Object>();

    /**
     * Instantiates a new JSON factory.
     */
    private JSONFactory() {
    }

    public static void setJsonObjectStored(String jsonString) throws JSONException {
        jsonObjectStored = convertStringToJsonObject(jsonString);
    }

    public static void setJsonObjectStored(JSONObject jsonObject) {
        jsonObjectStored = jsonObject;
    }

    /**
     * Convert string to JSON object.
     *
     * @param jsonString the JSON string
     * @return the JSON object
     * @throws JSONException the JSON exception
     */
    public static JSONObject convertStringToJsonObject(String jsonString) throws JSONException {
        return new JSONObject(jsonString);
    }

    /**
     * Convert string to JSON Array.
     *
     * @param jsonString the JSON string
     * @return the JSON Array
     * @throws JSONException the JSON exception
     */
    public static JSONArray convertStringToJsonArray(String jsonString) throws JSONException {
        return new JSONArray(jsonString);
    }

    /**
     * Parses the JSON as key value pair.
     *
     * @param json the JSON
     * @return the hash map
     * @throws JSONException the JSON exception
     */
    @SuppressWarnings("unchecked")
    public static HashMap<String, Object> parseJsonAsKeyValuePair(JSONObject json) throws JSONException {
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            String val = null;
            JSONObject value = null;
            try {
                value = json.getJSONObject(key);
                jsonMap.put(key, value);
                parseJsonAsKeyValuePair(value);
            } catch (Exception e) {
                val = json.getString(key);
            }

            if (val != null) {
                jsonMap.put(key, val);
            }
        }
        return jsonMap;
    }

    /**
     * Checks if is key exist.
     *
     * @param jsonString the JSON string
     * @param key        the key
     * @return true, if is key exist
     * @throws JSONException the JSON exception
     */
    public static boolean isKeyExist(String jsonString, String key) throws JSONException {
        setJsonObjectStored(jsonString);
        return isKeyExist(key);
    }

    /**
     * Checks if is key exist.
     *
     * @param jsonObject the JSON object
     * @param key        the key
     * @return true, if is key exist
     * @throws JSONException the JSON exception
     */
    public static boolean isKeyExist(JSONObject jsonObject, String key) throws JSONException {
        setJsonObjectStored(jsonObject);
        return isKeyExist(key);
    }

    /**
     * Checks if is key exist.
     *
     * @param key the key
     * @return true, if is key exist
     * @throws JSONException the JSON exception
     */
    private static boolean isKeyExist(String key) throws JSONException {
        jsonMap = parseJsonAsKeyValuePair(jsonObjectStored);
        if (jsonMap.containsKey(key)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the JSON object using key.
     *
     * @param jsonString the JSON string
     * @param key        the key
     * @return the JSON object using key
     * @throws JSONException the JSON exception
     */
    public static JSONObject getJsonObjectUsingKey(String jsonString, String key) throws JSONException {
        setJsonObjectStored(jsonString);
        return getJsonObjectUsingKey(key);
    }

    /**
     * Gets the JSON object using key.
     *
     * @param key the key
     * @return the JSON object using key the JSON object
     * @throws JSONException the JSON exception
     */
    public static JSONObject getJsonObjectUsingKey(JSONObject jsonObject, String key) throws JSONException {
        setJsonObjectStored(jsonObject);
        return getJsonObjectUsingKey(key);
    }

    private static JSONObject getJsonObjectUsingKey(String key) throws JSONException {
        JSONObject value = null;
        boolean keyFound = isKeyExist(key);
        if (keyFound) {
            value = (JSONObject) jsonMap.get(key);
        } else {
        }
        return value;
    }

    /**
     * Gets the json object using JSON array index.
     *
     * @param jsonArr    the json array
     * @param arrayIndex the array index
     * @return the json object with specified index
     * @throws JSONException the JSON exception
     */
    public static JSONObject getJsonObjectUsingIndex(JSONArray jsonArr, int arrayIndex) throws JSONException {
        JSONObject value = null;
        try {
            value = (JSONObject) jsonArr.get(arrayIndex);
        } catch (Exception e) {
        }
        return value;
    }

    /**
     * Gets the JSON array using key.
     *
     * @param jsonObj the JSON object
     * @param key     the key
     * @return the JSON array using key
     * @throws JSONException the JSON exception
     */
    public static JSONArray getJsonArrayUsingKey(JSONObject jsonObj, String key) throws JSONException {
        JSONArray value = null;
        boolean keyFound = isKeyExist(key);
        if (keyFound) {
            value = jsonObj.getJSONArray(key);
        } else {
        }
        return value;
    }

    /**
     * Gets the JSON object from JSON array.
     *
     * @param jsonObj    the JSON object
     * @param key        the key
     * @param arrayIndex the array index
     * @return the JSON object from JSON array
     * @throws JSONException the JSON exception
     */
    public static JSONObject getJsonObjectFromJsonArray(JSONObject jsonObj, String key, int arrayIndex) throws JSONException {
        JSONArray value = null;
        boolean keyFound = isKeyExist(key);
        if (keyFound) {
            value = jsonObj.getJSONArray(key);
        } else {
        }
        return (JSONObject) value.get(arrayIndex);
    }

    /**
     * Gets the value using key.
     *
     * @param jsonString the json string
     * @param key        the key
     * @return the value using key
     * @throws JSONException the JSON exception
     */
    public static String getValueUsingKey(String jsonString, String key) throws JSONException {
        setJsonObjectStored(jsonString);
        return getValueUsingKey(key);
    }

    public static String getValueUsingKey(JSONObject jsonObject, String key) throws JSONException {
        setJsonObjectStored(jsonObject);
        return getValueUsingKey(key);
    }

    private static String getValueUsingKey(String key) throws JSONException {
        String value = null;
        boolean keyFound = isKeyExist(key);
        if (keyFound) {
            value = (String) jsonMap.get(key);
        } else {
        }
        return value;
    }

    public static String getValue(String jsonString, String path) throws JSONException {
        setJsonObjectStored(jsonString);
        return getValue(path);
    }

    private static String getValue(String path) throws JSONException {
        String[] pathNames = path.split("->");
        int size = pathNames.length;
        Object content = jsonObjectStored;
        String result = null;
        String patternString = "(.*?)\\[(.*?)\\]";

        for (int index = 0; index < size; index++) {
            String pathName = pathNames[index];
            if (index == size - 1) {
                result = ((JSONObject) content).getString(pathNames[size - 1]);
            } else if (pathName.matches(patternString)) {
                Pattern p = Pattern.compile(patternString);
                Matcher m = p.matcher(pathName);
                m.matches();
                content = ((JSONObject) content).getJSONArray(m.group(1));
                content = ((JSONArray) content).get(Integer.parseInt(m.group(2)));
            } else {
                content = ((JSONObject) content).getJSONObject(pathName);
            }
        }
        return result;
    }

    public static <T> String convertObjectToJsonString(T object, boolean includeNull) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        if (includeNull) {
            mapper.setSerializationInclusion(Inclusion.ALWAYS);
        } else {
            mapper.setSerializationInclusion(Inclusion.NON_DEFAULT);
        }
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    public static JSONObject removeEmptyNodes(JSONObject jsonObj) {
        try {
            Iterator<String> it = jsonObj.keys();
            while (it.hasNext()) {
                String keyStr = it.next();
                Object keyvalue = jsonObj.get(keyStr);
                if (keyvalue.toString().equals("null")) {
                    jsonObj.remove(keyStr);
                    it = jsonObj.keys();
                } else if (keyvalue instanceof JSONObject) {
                    removeEmptyNodes((JSONObject) keyvalue);
                    if (((JSONObject) keyvalue).length() == 0) {
                        jsonObj.remove(keyStr);
                        it = jsonObj.keys();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }
}
