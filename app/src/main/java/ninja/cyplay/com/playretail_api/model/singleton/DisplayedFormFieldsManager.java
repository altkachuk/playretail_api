package ninja.cyplay.com.playretail_api.model.singleton;

import java.util.ArrayList;
import java.util.List;

import ninja.cyplay.com.playretail_api.ui.custom.form.FormSubViewLayout;

/**
 * Created by damien on 14/01/16.
 */
public class DisplayedFormFieldsManager {

    private static DisplayedFormFieldsManager INSTANCE = null;
    private List<FormSubViewLayout> displayedLayoutFields;

    private DisplayedFormFieldsManager() {}

    public static DisplayedFormFieldsManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new DisplayedFormFieldsManager();
        return INSTANCE;
    }

    public void reset() {
        if (displayedLayoutFields != null)
            displayedLayoutFields.clear();
    }

    public void addField(FormSubViewLayout fieldLayout) {
        if (displayedLayoutFields == null)
            displayedLayoutFields = new ArrayList<>();
        displayedLayoutFields.add(fieldLayout);
    }

    public Boolean runFieldChecks() {
        Boolean valid = true;
        if (displayedLayoutFields != null)
            for (int i = 0 ; i < displayedLayoutFields.size() ; i++) {
                FormSubViewLayout subViewLayout = displayedLayoutFields.get(i);
                if (!subViewLayout.runValidation())
                    valid = false;
            }
        return valid;
    }

    public List<FormSubViewLayout> getDisplayedLayoutFields() {
        return displayedLayoutFields;
    }
}
