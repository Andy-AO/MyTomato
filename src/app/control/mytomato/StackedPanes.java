package app.control.mytomato;

import app.model.TomatoTask;
import app.view.StackedPanesController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.*;
import javafx.scene.Node;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StackedPanes extends app.control.StackedPanes {

    private GridPane focusedGridPane;

    public StackedPanes() {
        super();
        vBox.getStyleClass().add("v-box");
    }

    //--------------------------------------- Field+
    StackedPanesController stackedPanesController;

    public StackedPanesController getStackedPanesController() {
        return stackedPanesController;
    }

    public void setStackedPanesController(StackedPanesController stackedPanesController) {
        this.stackedPanesController = stackedPanesController;
    }

    private SimpleObjectProperty<ListChangeListener.Change<? extends TomatoTask>> titledPaneItemsChange = new SimpleObjectProperty();

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


    private Comparator<ObservableList<TomatoTask>> comparatorTomatoTaskList = (o1, o2) -> {
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
    };

//--------------------------------------- GS


    public ListChangeListener.Change<? extends TomatoTask> getTitledPaneItemsChange() {
        return titledPaneItemsChange.get();
    }

    public SimpleObjectProperty<ListChangeListener.Change<? extends TomatoTask>> titledPaneItemsChangeProperty() {
        return titledPaneItemsChange;
    }

    public void setTitledPaneItemsChange(ListChangeListener.Change<? extends TomatoTask> titledPaneItemsChange) {
        this.titledPaneItemsChange.set(titledPaneItemsChange);
    }

    public ObservableMap<LocalDate, ObservableList<TomatoTask>> getItemsMap() {
        return itemsMap;
    }

//--------------------------------------- Method

    public void setItemsMap(ObservableMap<LocalDate, ObservableList<TomatoTask>> itemsMap) {
        this.itemsMap = itemsMap;
        convertItemsMapToSortedList();
        showItemsList();
        itemsMap.addListener((MapChangeListener<LocalDate, ObservableList<TomatoTask>>) change -> {
            ObservableList<TomatoTask> addList = change.getValueAdded();
            if (!addList.isEmpty()) {
                addTitledPane(addList);
            }
        });
    }

    private void addTitledPane(ObservableList<TomatoTask> addList) {
        if (!addList.isEmpty()) {

            TitledPane titledPane = creatTitledPane(addList);

            List list = new ArrayList(vBox.getChildren());
            list.add(titledPane);
            list.sort(comparatorTitledPane);
            Collections.reverse(list);
            vBox.getChildren().clear();
            vBox.getChildren().addAll(list);

        }
    }


    public void removeTitledPane(TitledPane titledPane) {
        vBox.getChildren().remove(titledPane);
    }


    private void showItemsList() {
        this.itemsList.forEach((list) -> {
            if (!list.isEmpty()) {
                TitledPane titledPane = creatTitledPane(list);
                vBox.getChildren().add(titledPane);
            }
        });
    }

    private TitledPane creatTitledPane(ObservableList<TomatoTask> list) {
        TitledPane titledPane = new TitledPane(list.get(0).getDate());
        titledPane.setItems(list);
        list.addListener((ListChangeListener<TomatoTask>) change -> {

            if (change.next()) {
                List removedItems = change.getRemoved();
                List addedSubList = change.getAddedSubList();
                boolean sortAble = (!addedSubList.isEmpty()) | (!removedItems.isEmpty());
                if(sortAble){
                }
                change.reset();
            }
            if (list.isEmpty()) {
                removeTitledPane(titledPane);
            }

            setTitledPaneItemsChange(change);

        });


        return titledPane;
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
    public void addItems(List<TomatoTask> items) {
        for (int i = 0; i < items.size(); i++) {
            addItem(items.get(i));
        }
    }

    private void addItem(TomatoTask tomatoTask) {
        ObservableList<TomatoTask> list = itemsMap.get(tomatoTask.getDate());
        if (list == null||list.isEmpty()) {
            ObservableList<TomatoTask> newList = FXCollections.observableArrayList(tomatoTask);
            itemsMap.put(tomatoTask.getDate(), newList);
        } else {
            list.add(tomatoTask);
        }
    }

    public GridPane getFocusedGridPane() {
        return focusedGridPane;
    }

    public void setEditingGridPane(GridPane focusedGridPane) {
        this.focusedGridPane = focusedGridPane;
    }
}
