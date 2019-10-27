package app.control.mytomato;

import app.model.TomatoTask;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import java.time.LocalDate;

public class StackedPanes extends app.control.StackedPanes {

//--------------------------------------- Field

    protected ObservableMap<LocalDate, ObservableList<TomatoTask>> items = null;
//--------------------------------------- GS

//--------------------------------------- Method

    public void setItems(ObservableMap<LocalDate, ObservableList<TomatoTask>> items) {
        this.items = items;
        this.items.forEach((localDate,list)->{
            TitledPane titledPane = new TitledPane(localDate);
            titledPane.setItems(list);
            stackedTitledPanes.getChildren().add(titledPane);
        });
    }


}
