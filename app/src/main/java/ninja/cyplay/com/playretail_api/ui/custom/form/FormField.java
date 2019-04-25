package ninja.cyplay.com.playretail_api.ui.custom.form;

public interface FormField {
    public boolean runValidation();
    public void setValue(String val);
    public String getValue();
}