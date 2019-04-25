package ninja.cyplay.com.playretail_api.model.events;

/**
 * Created by damien on 18/03/16.
 */
public class FormPageSelectEvent {

    private int position;

    public FormPageSelectEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
