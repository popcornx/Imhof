package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class OSMMap {
    private final List<OSMWay> ways;
    private final List<OSMRelation> relations;

    public OSMMap(Collection<OSMWay> ways, Collection<OSMRelation> relations) {
        this.ways = Collections.unmodifiableList(new ArrayList<>(ways));
        this.relations = Collections.unmodifiableList(new ArrayList<>(relations));
    }

    public List<OSMWay> ways() {
        return ways;
    }

    public List<OSMRelation> relations() {
        return relations;
    }

    public final static class Builder {

        private final List<OSMNode> nodes = new ArrayList<>();
        private final List<OSMWay> ways = new ArrayList<>();
        private final List<OSMRelation> relations = new ArrayList<>();

        public void addNode(OSMNode newNode) {
            nodes.add(newNode);
        }

        public OSMNode nodeForId(long id) {
            for (OSMNode node : nodes) {
                if (node.id() == id) {
                    return node;
                }
            }
            return null;
        }

        public void addWay(OSMWay newWay) throws NullPointerException {
            if (newWay == null) {
                throw new NullPointerException();
            }
            ways.add(newWay);
        }

        public OSMWay wayForId(long id) {
            for (OSMWay way : ways) {
                if (way.id() == id) {
                    return way;
                }
            }
            return null;
        }

        public void addRelation(OSMRelation newRelation) throws NullPointerException {
            if (newRelation == null) {
                throw new NullPointerException();
            }
            relations.add(newRelation);
        }

        public OSMRelation relationForId(long id) {
            for (OSMRelation relation : relations) {
                if (relation.id() == id) {
                    return relation;
                }
            }
            return null;
        }

        public OSMMap build() {
            return new OSMMap(ways, relations);
        }
    }
}
