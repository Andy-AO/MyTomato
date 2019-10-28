package app.control.mytomato;

import app.model.TomatoTask;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.*;
import javafx.scene.Node;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StackedPanes extends app.control.StackedPanes {

    //--------------------------------------- Field
    private SimpleObjectProperty<ListChangeListener.Change<? extends TomatoTask>> titledPaneChange = new SimpleObjectProperty();

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

    public ListChangeListener.Change<? extends TomatoTask> getTitledPaneChange() {
        return titledPaneChange.get();
    }

    public SimpleObjectProperty<ListChangeListener.Change<? extends TomatoTask>> titledPaneChangeProperty() {
        return titledPaneChange;
    }

    public void setTitledPaneChange(ListChangeListener.Change<? extends TomatoTask> titledPaneChange) {
        this.titledPaneChange.set(titledPaneChange);
    }

    public ObservableMap<LocalDate, ObservableList<TomatoTask>> getItemsMap() {
        return itemsMap;
    }

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

            vBox.getChildren().add(titledPane);

            List list = new ArrayList(vBox.getChildren());

            list.sort(comparatorTitledPane);
            Collections.reverse(list);
            vBox.getChildren().clear();
            vBox.getChildren().addAll(list);
        }
    }


    public void removeTitledPane(TitledPane titledPane) {
        vBox.getChildren().remove(titledPane);
    }

/*
    private void addTitledPane(ObservableList<TomatoTask> addList) {
        if (!addList.isEmpty()) {

            TitledPane titledPane = new TitledPane(addList.get(0).getDate());
            titledPane.setItems(addList);

            vBox.getChildren().add(titledPane);
            vBox.getChildren().sort(comparatorTitledPane);
            Collections.reverse(vBox.getChildren());
        }
    }
*/

    private void showItemsList() {
        this.itemsList.forEach((list) -> {
            if (!list.isEmpty()) {
                TitledPane titledPane = new TitledPane(list.get(0).getDate());
                titledPane.setItems(list);

                list.addListener(new ListChangeListener<TomatoTask>() {
                    @Override
                    public void onChanged(Change<? extends TomatoTask> c) {
                        if (list.isEmpty()) {
                            removeTitledPane(titledPane);
                        }
                        setTitledPaneChange(c);
                    }
                });
                vBox.getChildren().add(titledPane);
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
