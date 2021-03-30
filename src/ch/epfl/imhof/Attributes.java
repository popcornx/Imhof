package ch.epfl.imhof;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Lorenz Rasch
 */
public final class Attributes {

    private final Map<String, String> att;

    public Attributes(Map<String, String> attributes) {
        att = Collections.unmodifiableMap(new HashMap<>(attributes));
    }

    public boolean isEmpty() {
        return att.isEmpty();
    }

    public boolean contains(String key) {
        return att.containsKey(key);
    }

    public String get(String key) {
        return att.get(key);
    }

    public String get(String key, String defaultValue) {
        return att.getOrDefault(key, defaultValue);
    }

    public int get(String key, int defaultValue) {
        try {
            return Integer.parseInt(att.get(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public Attributes keepOnlyKeys(Set<String> keysToKeep) {
        Builder b = new Builder();
        for (String s : keysToKeep) {
            if (contains(s)) {
                b.put(s, get(s));
            }
        }
        return b.build();
    }

    public static final class Builder {
        private Map<String, String> atts = new HashMap<>();

        public void put(String key, String value) {
            atts.put(key, value);
        }

        public Attributes build() {
            return new Attributes(atts);
        }
    }
}
