package ninja.cyplay.com.apilibrary.models.business;

public enum FilterSpec {

    COLOR("color"),
    SIZE("size");

    private final String text;
    FilterSpec(final String text) {this.text = text;}
    @Override
    public String toString() { return text;}
}
