package app.control.mytomato;

import app.model.TomatoTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;

public class StackedPanes extends app.control.StackedPanes {

//--------------------------------------- Field

    protected ObservableMap<LocalDate, ObservableList<TomatoTask>> itemsMap = null;
    protected ObservableList<ObservableList<TomatoTask>> itemsList = FXCollections.observableArrayList();
//--------------------------------------- GS

//--------------------------------------- Method

    public void setItemsMap(ObservableMap<LocalDate, ObservableList<TomatoTask>> itemsMap) {
        this.itemsMap = itemsMap;
        convertItemsMapToSortedList();
        showItemsList();
    }

    private void showItemsList() {
        this.itemsList.forEach((list)->{
            if (!list.isEmpty()) {
                TitledPane titledPane = new TitledPane(list.get(0).getDate());
                titledPane.setItems(list);
                stackedTitledPanes.getChildren().add(titledPane);
            }
        });
    }

    private void convertItemsMapToSortedList() {
        this.itemsMap.forEach((localDate, list)->{
            itemsList.add(list);
            itemsList.sort(new Comparator<ObservableList<TomatoTask>>() {
                @Override
                public int compare(ObservableList<TomatoTask> o1, ObservableList<TomatoTask> o2) {
                    boolean o1IsEmpty = o1.isEmpty();
                    boolean o2IsEmpty = o2.isEmpty();
                    if((o1IsEmpty)&&(o2IsEmpty)){
                        return 0;
                    }
                    else if  (o1IsEmpty){
                        return -1;
                    }
                    else if  (o2IsEmpty){
                        return 1;
                    }
                    return (o1.get(0).getDate().compareTo(o2.get(0).getDate()));
                }
            });
        });
        Collections.reverse(itemsList);
    }


    public void addItems(TomatoTask ... tomatoTasks) {
        for (int i = 0; i < tomatoTasks.length; i++) {
            addItem(tomatoTasks[i]);
        }
    }

    private void addItem(TomatoTask tomatoTask) {
        ObservableList<TomatoTask> list = itemsMap.get(tomatoTask.getDate());
        if (list == null) {
            FXCollections.observableArrayList(tomatoTask);
        }
        else {
            list.add(tomatoTask);
        }
    }
}
