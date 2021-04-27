package ch.epfl.imhof;

import java.util.*;

public final class Graph<N> {
    private final Map<N, Set<N>> neighbors;

    public Graph(Map<N, Set<N>> neighbors) {
        Map<N, Set<N>> map = new HashMap<>();
        for (Map.Entry<N, Set<N>> entry : neighbors.entrySet()) {
            map.put(entry.getKey(), Collections.unmodifiableSet(new HashSet<>(entry.getValue())));
        }
        this.neighbors = Collections.unmodifiableMap(new HashMap<>(map));
    }

    public Set<N> nodes() {
        return neighbors.keySet();
    }

    public Set<N> neighborsOf(N node) throws IllegalArgumentException {
        if (!neighbors.containsKey(node)) {
            throw new IllegalArgumentException();
        }
        return neighbors.get(node);
    }

    public static class Builder<N> {
        private final Map<N, Set<N>> neighbors = new HashMap<>();

        public void addNode(N n) {
            neighbors.putIfAbsent(n, new HashSet<>());
        }

        public void addEdge(N n1, N n2) throws IllegalArgumentException {
            if (!neighbors.containsKey(n1) || !neighbors.containsKey(n2)) {
                throw new IllegalArgumentException();
            }
            neighbors.get(n1).add(n2);
            neighbors.get(n2).add(n1);
        }

        public Graph<N> build() {
            return new Graph<>(neighbors);
        }
    }
}
