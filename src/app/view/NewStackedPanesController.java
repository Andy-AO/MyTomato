package app.view;

import app.Main;
import app.control.mytomato.NewStackedPanes;
import app.control.mytomato.StackedPanes;
import app.model.TomatoTask;
import app.util.GL;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.time.LocalDate;

public class NewStackedPanesController extends Controller {


//--------------------------------------- Field

    private NewStackedPanes newStackedPanes;

//--------------------------------------- Getter Setter
//--------------------------------------- Method

    public NewStackedPanesController() {

    }

    @Override
    public void setMainAndInit(Main main) {
        super.setMainAndInit(main);
        newStackedPanes.setItemsMap(main.getTomatoTasksMap());
        writeWhenItemsChange();
        newStackedPanes.setOnKeyPressed(this::handleTableDeleteKey);
        newStackedPanes.setStackedPanesController(this);
    }

    private void handleTableDeleteKey(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        String keyName = keyCode.getName();
        switch (keyName) {
            case "Delete":
                main.getMainLayoutController().handleDeleteButton();
                break;
            case "R":
                main.getMainLayoutController().showCellHeight();
                break;
            default:
                break;
        }

    }

    private void writeWhenItemsChange() {
        newStackedPanes.getItemsMap().addListener(new MapChangeListener<LocalDate, ObservableList<TomatoTask>>() {
            @Override
            public void onChanged(Change<? extends LocalDate, ? extends ObservableList<TomatoTask>> change) {
                main.getTomatoTaskDataMapJson().write();
            }
        });
        newStackedPanes.titledPaneItemsChangeProperty().addListener((observable, oldChange, newChange) -> {
            main.getTomatoTaskDataMapJson().write();
        });
    }

    public NewStackedPanes createScrollPane() {
        newStackedPanes = new NewStackedPanes();
        return newStackedPanes;
    }


}