package app.control.mytomato;

import app.model.TomatoTask;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Node;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StackedPanes extends app.control.StackedPanes {

//--------------------------------------- Field

    protected ObservableMap<LocalDate, ObservableList<TomatoTask>> itemsMap = null;
    protected ObservableList<ObservableList<TomatoTask>> itemsList = FXCollections.observableArrayList();
    private Comparator<? super Node> comparatorTitledPane = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            TitledPane t1 = (TitledPane) o1;
            TitledPane t2 = (TitledPane) o2;
            int result = t1.getLOCAL_DATE().compareTo(t2.getLOCAL_DATE());
            return result;
        }
    };


    private Comparator<ObservableList<TomatoTask>> comparatorTomatoTaskList = new Comparator<ObservableList<TomatoTask>>() {
        @Override
        public int compare(ObservableList<TomatoTask> o1, ObservableList<TomatoTask> o2) {
            boolean o1IsEmpty = o1.isEmpty();
            boolean o2IsEmpty = o2.isEmpty();
            if ((o1IsEmpty) && (o2IsEmpty)) {
                return 0;
            } else if (o1IsEmpty) {
                return -1;
            } else if (o2IsEmpty) {
                return 1;
            }
            return (o1.get(0).getDate().compareTo(o2.get(0).getDate()));
        }
    };

//--------------------------------------- GS

//--------------------------------------- Method

    public void setItemsMap(ObservableMap<LocalDate, ObservableList<TomatoTask>> itemsMap) {
        this.itemsMap = itemsMap;
        convertItemsMapToSortedList();
        showItemsList();
        itemsMap.addListener(new MapChangeListener<LocalDate, ObservableList<TomatoTask>>() {
            @Override
            public void onChanged(Change<? extends LocalDate, ? extends ObservableList<TomatoTask>> change) {
                ObservableList<TomatoTask> addList = change.getValueAdded();
                if (!addList.isEmpty()) {
                    addTitledPane(addList);
                }
            }
        });
    }

    private void addTitledPane(ObservableList<TomatoTask> addList) {
        if (!addList.isEmpty()) {

            TitledPane titledPane = new TitledPane(addList.get(0).getDate());
            titledPane.setItems(addList);

            stackedTitledPanes.getChildren().add(titledPane);

            List list = new ArrayList(stackedTitledPanes.getChildren());

            list.sort(comparatorTitledPane);
            Collections.reverse(list);
            stackedTitledPanes.getChildren().clear();
            stackedTitledPanes.getChildren().addAll(list);
        }
    }
/*
    private void addTitledPane(ObservableList<TomatoTask> addList) {
        if (!addList.isEmpty()) {

            TitledPane titledPane = new TitledPane(addList.get(0).getDate());
            titledPane.setItems(addList);

            stackedTitledPanes.getChildren().add(titledPane);
            stackedTitledPanes.getChildren().sort(comparatorTitledPane);
            Collections.reverse(stackedTitledPanes.getChildren());
        }
    }
*/

    private void showItemsList() {
        this.itemsList.forEach((list) -> {
            if (!list.isEmpty()) {
                TitledPane titledPane = new TitledPane(list.get(0).getDate());
                titledPane.setItems(list);
                stackedTitledPanes.getChildren().add(titledPane);
            }
        });
    }


    private void convertItemsMapToSortedList() {
        this.itemsMap.forEach((localDate, list) -> {
            itemsList.add(list);
            itemsList.sort(comparatorTomatoTaskList);
        });
        Collections.reverse(itemsList);
    }


    public void addItems(TomatoTask... tomatoTasks) {
        for (int i = 0; i < tomatoTasks.length; i++) {
            addItem(tomatoTasks[i]);
        }
    }

    private void addItem(TomatoTask tomatoTask) {
        ObservableList<TomatoTask> list = itemsMap.get(tomatoTask.getDate());
        if (list == null) {
            ObservableList<TomatoTask> newList = FXCollections.observableArrayList(tomatoTask);
            itemsMap.put(tomatoTask.getDate(), newList);
        } else {
            list.add(tomatoTask);
        }
    }
}
