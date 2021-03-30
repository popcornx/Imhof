package ch.epfl.imhof.osm;

import ch.epfl.imhof.Attributes;

/**
 * @author Lorenz Rasch
 */
public abstract class OSMEntity {
    private final long id;
    private final Attributes attributes;

    protected OSMEntity(long id, Attributes attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    public long id() {
        return this.id;
    }

    public Attributes attributes() {
        return this.attributes;
    }

    public boolean hasAttribute(String key) {
        return attributes.contains(key);
    }

    public String attributeValue(String key) {
        return attributes.get(key, null);
    }

    public abstract static class Builder<T> {
        protected final long id;
        private boolean incomplete = false;
        protected Attributes.Builder aBuilder = new Attributes.Builder();

        protected Builder(long id) {
            this.id = id;
        }

        public void setAttribute(String key, String value) {
            aBuilder.put(key, value);
        }

        public void setIncomplete() {
            incomplete = true;
        }

        public boolean isIncomplete() {
            return incomplete;
        }

        public abstract T build();

    }
}
