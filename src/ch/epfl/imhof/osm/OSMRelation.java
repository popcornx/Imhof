package ch.epfl.imhof.osm;

import ch.epfl.imhof.Attributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class OSMRelation extends OSMEntity {

    private final List<Member> members;

    public OSMRelation(long id, List<Member> members, Attributes attributes) {
        super(id, attributes);
        this.members = Collections.unmodifiableList(new ArrayList<>(members));
    }

    public List<Member> members() {
        return members;
    }

    public static final class Member {

        private final Type type;
        private final String role;
        private final OSMEntity member;

        public Member(Type type, String role, OSMEntity member) {
            this.type = type;
            this.role = role;
            this.member = member;
        }

        public Type type() {
            return type;
        }

        public String role() {
            return role;
        }

        public OSMEntity member() {
            return member;
        }

        public enum Type {
            NODE, WAY, RELATION
        }
    }

    public static final class Builder extends OSMEntity.Builder<OSMRelation> {

        private final List<Member> members;

        public Builder(long id) {
            super(id);
            members = new ArrayList<>();
        }

        public void addMember(Member.Type type, String role, OSMEntity newMember) {
            members.add(new Member(type, role, newMember));
        }

        @Override
        public OSMRelation build() {
            if (isIncomplete()) {
                throw new IllegalStateException();
            } else {
                return new OSMRelation(id, members, aBuilder.build());
            }
        }
    }
}
