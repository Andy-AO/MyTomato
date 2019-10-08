package app;

import app.model.TomatoTask;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class TomatoTaskDataJson {

    private final String PATH = "res\\json\\tomatoTaskData.json";

    public TomatoTaskDataJson(ObservableList<TomatoTask> tomatoTaskData) {
        this.tomatoTaskData = tomatoTaskData;
    }

    public String getPATH() {
        return PATH;
    }

    public ObservableList<TomatoTask> getTomatoTaskData() {
        return tomatoTaskData;
    }

    public void setTomatoTaskData(ObservableList<TomatoTask> tomatoTaskData) {
        this.tomatoTaskData = tomatoTaskData;
    }

    private ObservableList<TomatoTask> tomatoTaskData;


    public String readString() {
        File jsonFile = new File(PATH);
        System.out.println(jsonFile.getAbsolutePath());
        char[] json = new char[(int) jsonFile.length()];
        try (FileReader fileReader = new FileReader(PATH)) {
            fileReader.read(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            String jsonString = new String(json);
            return jsonString;
        }
    }

    public String write() {
        ArrayList tomatoTaskList = new ArrayList(tomatoTaskData);
        String json = JSON.toJSONString(tomatoTaskList);
        try (FileWriter fileWriter = new FileWriter(PATH)) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return json;
        }
    }

    public void read() {
        this.tomatoTaskData.clear();
        String jsonString = readString();
        ArrayList jsonList = JSON.parseObject(jsonString, ArrayList.class);
        jsonList.forEach(jsonMap -> mapToTask((JSONObject) jsonMap));
    }

    private void mapToTask(JSONObject jsonMap) {
        String name = (String) jsonMap.get("name");
        LocalTime endTime = LocalTime.parse(((String) jsonMap.get("endTime")));
        LocalTime startTime = LocalTime.parse(((String) jsonMap.get("startTime")));
        LocalDate date = LocalDate.parse(((String) jsonMap.get("date")));
        tomatoTaskData.add(new TomatoTask(name, startTime, endTime,date));
    }
}
