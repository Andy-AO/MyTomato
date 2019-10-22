package app.view;

import app.Main;

public abstract class  Controller {
    public Main main;
    public void setMainAndInit(Main main) {
        this.main = main;
    }
}
