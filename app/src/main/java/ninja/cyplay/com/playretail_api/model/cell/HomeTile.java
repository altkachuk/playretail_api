package ninja.cyplay.com.playretail_api.model.cell;

import java.io.Serializable;

/**
 * Created by damien on 01/07/15.
 */
public class HomeTile implements Serializable {

    private String title;
    private int drawableUri;
    private ETileAction action;

    public HomeTile(String title, int drawableUri, ETileAction action) {
        this.title = title;
        this.drawableUri = drawableUri;
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawableUri() {
        return drawableUri;
    }

    public void setDrawableUri(int drawableUri) {
        this.drawableUri = drawableUri;
    }

    public ETileAction getAction() {
        return action;
    }

    public void setAction(ETileAction action) {
        this.action = action;
    }

    public enum ETileAction {
        CLIENTS,
        SCAN,
        TWEETS,
        CATALOG,
        CHISTORY,
        INSHOP
    }

}
