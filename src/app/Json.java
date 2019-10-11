package app;

import app.model.TomatoTask;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Json {

    private  String jsonPath ;

    public String getJsonPath() {
        return jsonPath;
    }

    public List<TomatoTask> getTomatoTaskData() {
        return tomatoTaskData;
    }

    public Json(List<TomatoTask> tomatoTaskData , String jsonPath) {
        this.jsonPath = jsonPath;
        this.tomatoTaskData = tomatoTaskData;
    }


    private List<TomatoTask> tomatoTaskData;


    public String readString() {
        File jsonFile = new File(jsonPath);
        System.out.println(jsonFile.getAbsolutePath());
        char[] json = new char[(int) jsonFile.length()];
        String jsonString = "";
        try (FileReader fileReader = new FileReader(jsonPath)) {
            fileReader.read(json);
             jsonString = new String(json);
        } catch (FileNotFoundException e) {
            System.out.println("Not Found JSON ,set table empty！");
             jsonString = new String("[]");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return jsonString;
        }
    }

    public String write() {
        ArrayList tomatoTaskList = new ArrayList(tomatoTaskData);
        String json = JSON.toJSONString(tomatoTaskList);
        try (FileWriter fileWriter = new FileWriter(jsonPath)) {
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
