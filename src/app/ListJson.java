package app;

import app.model.TomatoTask;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ListJson implements Json {

    private final String EMPTY_MAP_STRING = "[]";
    private String jsonPath;
    private List<TomatoTask> data;

    public String getJsonPath() {
        return jsonPath;
    }

    public List<TomatoTask> getData() {
        return data;
    }

    public ListJson(List<TomatoTask> data, String jsonPath) {
        this.jsonPath = jsonPath;
        this.data = data;
    }

    public String readString() {
        File jsonFile = new File(jsonPath);
        System.out.println(jsonFile.getAbsolutePath());
        char[] json = new char[(int) jsonFile.length()];
        String jsonString = "";
        try (FileReader fileReader = new FileReader(jsonPath)) {
            fileReader.read(json);
            jsonString = new String(json);
        } catch (FileNotFoundException e) {
            System.err.println("Not Found JSON ,set table empty！");
            jsonString = EMPTY_MAP_STRING;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return jsonString;
        }
    }

    @Override
    public String write() {
        ArrayList tomatoTaskList = new ArrayList(data);
        String json = JSON.toJSONString(tomatoTaskList);
        try (FileWriter fileWriter = new FileWriter(jsonPath)) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return json;
        }
    }

    @Override
    public void read() {
        this.data.clear();
        String jsonString = readString();
        ArrayList jsonList = null;
        try {
            jsonList = JSON.parseObject(jsonString, ArrayList.class);
            jsonList.forEach(jsonMap -> mapToTask((JSONObject) jsonMap));
        } catch (com.alibaba.fastjson.JSONException ex) {
            ex.printStackTrace();
            Alert alert = new OnTopAlert(Alert.AlertType.WARNING, "JSON file parse exception.");
            alert.showAndWait();
            System.exit(1);
        }
    }

    private void mapToTask(JSONObject jsonMap) {
        String name = (String) jsonMap.get("name");
        LocalTime endTime = LocalTime.parse(((String) jsonMap.get("endTime")));
        LocalTime startTime = LocalTime.parse(((String) jsonMap.get("startTime")));
        LocalDate date = LocalDate.parse(((String) jsonMap.get("date")));
        data.add(new TomatoTask(name, startTime, endTime, date));
    }
}
