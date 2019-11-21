package app.util;

import app.control.OnTopAlert;
import app.model.TomatoTask;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import javafx.scene.control.Alert;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ListJson implements DataManager {

    private final String EMPTY_MAP_STRING = "[]";
    private final File jsonFile;
    private List<TomatoTask> data;

    public List<TomatoTask> getData() {
        return data;
    }

    public ListJson(List<TomatoTask> data, File jsonFile) {
        GL.logger.info("JsonFile:" + jsonFile.getAbsolutePath());
        this.jsonFile = jsonFile;
        this.data = data;
    }

    public String readString() {
        char[] json = new char[(int) jsonFile.length()];
        String jsonString = "";
        try (FileReader fileReader = new FileReader(jsonFile)) {
            fileReader.read(json);
            jsonString = new String(json);
        } catch (FileNotFoundException e) {
            System.err.println("Not Found JSON ,set table empty！");
            jsonString = EMPTY_MAP_STRING;
        } catch (IOException e) {
            GL.logger.warn(getClass().getSimpleName(),e);
        } finally {
            return jsonString;
        }
    }

    public String write() {
        ArrayList tomatoTaskList = new ArrayList(data);
        String json = JSON.toJSONString(tomatoTaskList);
        try (FileWriter fileWriter = new FileWriter(jsonFile)) {
            fileWriter.write(json);
        } catch (IOException e) {
            GL.logger.warn(getClass().getSimpleName(),e);
        } finally {
            return json;
        }
    }

    public void read() {
        this.data.clear();
        String jsonString = readString();
        ArrayList jsonList = null;
        try {
            jsonList = JSON.parseObject(jsonString, ArrayList.class);
            jsonList.forEach(jsonMap -> mapToTask((JSONObject) jsonMap));
        } catch (com.alibaba.fastjson.JSONException e) {
            GL.logger.warn(getClass().getSimpleName(),e);
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
