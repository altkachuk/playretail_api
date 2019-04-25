package ninja.cyplay.com.apilibrary.models.business;

public enum FilterType {

    CHECKBOX("checkbox_regular");

    private final String text;
    FilterType(final String text) {this.text = text;}
    @Override
    public String toString() { return text;}
}
