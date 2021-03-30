package ch.epfl.imhof.osm;

import ch.epfl.imhof.Attributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class OSMWay extends OSMEntity {

    private final List<OSMNode> nodes;

    public OSMWay(long id, List<OSMNode> nodes, Attributes attributes) {
        super(id, attributes);
        if (nodes.size() < 2) {
            throw new IllegalArgumentException();
        }
        this.nodes = Collections.unmodifiableList(new ArrayList<OSMNode>(nodes));
    }

    public int nodesCount() {
        return nodes.size();
    }

    public List<OSMNode> nodes() {
        return nodes;
    }

    public List<OSMNode> nonRepeatingNodes() {
        if (firstNode().equals(lastNode())) {
            return nodes.subList(0, nodes.size() - 1);
        } else {
            return nodes;
        }
    }

    public OSMNode firstNode() {
        return nodes.get(0);
    }

    public OSMNode lastNode() {
        return nodes.get(nodes.size() - 1);
    }

    public boolean isClosed() {
        return firstNode().equals(lastNode());
    }

    public static final class Builder extends OSMEntity.Builder<OSMWay> {

        private final List<OSMNode> nodes;

        public Builder(long id) {
            super(id);
            nodes = new ArrayList<>();
        }

        public void addNode(OSMNode newNode) {
            nodes.add(newNode);
        }

        @Override
        public boolean isIncomplete() {
            return super.isIncomplete() || (nodes.size() < 2);
        }

        @Override
        public OSMWay build() {
            if (isIncomplete()) {
                throw new IllegalStateException();
            } else {
                return new OSMWay(id, nodes, aBuilder.build());
            }
        }
    }
}
