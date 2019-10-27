package app.view;

import app.Main;
import app.control.mytomato.StackedPanes;

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
        scrollPane.setItems(main.getTomatoTasksMap());
    }

    public StackedPanes createScrollPane() {
        scrollPane = new StackedPanes();
        return scrollPane;
    }


}