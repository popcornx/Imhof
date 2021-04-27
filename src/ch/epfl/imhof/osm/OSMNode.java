package ch.epfl.imhof.osm;

import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.PointGeo;

public final class OSMNode extends OSMEntity {

    private final PointGeo position;

    public OSMNode(long id, PointGeo position, Attributes attributes) {
        super(id, attributes);
        this.position = position;
    }

    public PointGeo position() {
        return position;
    }

    public static final class Builder extends OSMEntity.Builder<OSMNode> {

        private final PointGeo position;

        public Builder(long id, PointGeo position) {
            super(id);
            this.position = position;
        }

        @Override
        public OSMNode build() throws IllegalStateException{
            if (isIncomplete()) {
                throw new IllegalStateException();
            } else {
                return new OSMNode(this.id, position, aBuilder.build());
            }
        }
    }
}
