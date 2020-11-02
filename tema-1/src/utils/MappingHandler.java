package utils;

import model.Location;
import model.Transition;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MappingHandler {

    public static Location jsonObjToLocation(JSONObject jsonObject) throws Exception {

        Location location = new Location();
        try {
            location.setId(jsonObject.get("id").toString());
            location.setTokens(Integer.parseInt(jsonObject.get("tokens").toString()));

            JSONArray jsonArray = (JSONArray) jsonObject.get("input");
            List<String> ioList = new ArrayList<String>();
            Iterator<String> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                ioList.add(iterator.next());
            }
            location.setInput(ioList);

            jsonArray = (JSONArray) jsonObject.get("output");
            ioList = new ArrayList<String>();
            iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                ioList.add(iterator.next());
            }
            location.setOutput(ioList);
        } catch (Exception e) {
            throw new Exception("JSON ERROR. Location parameters");
        }
        return location;
    }

    public static Transition jsonObjToTransition(JSONObject jsonObject) throws Exception {

        Transition transition = new Transition();
        try {
            transition.setId(jsonObject.get("id").toString());
            transition.setMinTime(Integer.parseInt(jsonObject.get("minTime").toString()));
            transition.setMaxTime(Integer.parseInt(jsonObject.get("maxTime").toString()));

            JSONArray jsonArray = (JSONArray) jsonObject.get("input");
            List<String> ioList = new ArrayList<String>();
            Iterator<String> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                ioList.add(iterator.next());
            }
            transition.setInput(ioList);

            jsonArray = (JSONArray) jsonObject.get("output");
            ioList = new ArrayList<String>();
            iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                ioList.add(iterator.next());
            }
            transition.setOutput(ioList);
        }  catch (Exception e) {
            throw new Exception("JSON ERROR. Transition parameters");
        }
        return transition;
    }
}
