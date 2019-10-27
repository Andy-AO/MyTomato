package app.view;

import app.Main;
import app.control.ScrollPane;

public class StackedPanesController extends Controller {


//--------------------------------------- Field

    private ScrollPane scrollPane;

//--------------------------------------- Getter Setter
//--------------------------------------- Method

    public StackedPanesController() {
    }

    @Override
    public void setMainAndInit(Main main) {
        super.setMainAndInit(main);
        scrollPane.setItems(main.getTomatoTasksMap());
    }

    public ScrollPane createScrollPane() {
        scrollPane = new ScrollPane();
        return scrollPane;
    }


}