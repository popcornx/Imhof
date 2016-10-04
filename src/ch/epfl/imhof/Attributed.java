package ch.epfl.imhof;

/**
 * @author Lorenz Rasch
 */
public final class Attributed<T> {

    private final T value;
    private final Attributes atts;


    public Attributed(T value, Attributes atts) {
        this.value = value;
        this.atts = atts;
    }

    public T value() {
        return value;
    }

    public Attributes attributes() {
        return atts;
    }

    public boolean hasAttribute(String attributeName){
        return attributes().contains(attributeName);
    }

    public String attributeValue(String attributeName){
        return attributes().get(attributeName);
    }

    public String attributeValue(String attributeName, String defaultValue){
        return attributes().get(attributeName, defaultValue);
    }

    public int attributeValue(String attributeName, int defaultValue){
        return attributes().get(attributeName, defaultValue);
    }
}
