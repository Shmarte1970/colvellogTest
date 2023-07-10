package es.zarca.covellog.interfaces.web.app.controller;

import javax.faces.event.ActionListener;

/**
 *
 * @author francisco
 */
public class ActionData {
    private ActionListener listener;
    private String onComplete;
    private String update;

    public ActionData(ActionListener listener) {
        this.listener = listener;
    }

    public ActionData(ActionListener actionListener, String onComplete, String update) {
        this.listener = actionListener;
        this.onComplete = onComplete;
        this.update = update;
    }

    public ActionListener getListener() {
        return listener;
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
    }

    public String getOnComplete() {
        return onComplete;
    }

    public void setOnComplete(String onComplete) {
        this.onComplete = onComplete;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

}