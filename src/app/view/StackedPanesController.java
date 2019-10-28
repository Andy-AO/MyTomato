package app.view;

import app.Main;
import app.control.mytomato.StackedPanes;
import app.model.TomatoTask;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class StackedPanesController extends Controller {


//--------------------------------------- Field

    private StackedPanes scrollPane;

//--------------------------------------- Getter Setter
//--------------------------------------- Method

    public StackedPanesController() {
    }

    @Override
    public void setMainAndInit(Main main) {
        super.setMainAndInit(main);
        scrollPane.setItemsMap(main.getTomatoTasksMap());
        writeWhenItemsChange();
    }

    private void writeWhenItemsChange() {
        scrollPane.getItemsMap().addListener(new MapChangeListener<LocalDate, ObservableList<TomatoTask>>() {
            @Override
            public void onChanged(Change<? extends LocalDate, ? extends ObservableList<TomatoTask>> change) {
                main.getTomatoTaskDataMapJson().write();
            }
        });
    }

    public StackedPanes createScrollPane() {
        scrollPane = new StackedPanes();
        return scrollPane;
    }


}