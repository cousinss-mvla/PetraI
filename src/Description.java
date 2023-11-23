import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Description data-holding class for an Entity's short, long, first, and method descriptors. These descriptors do NOT reference or necessitate {@link Word}s, but are used for player-facing text output only.
 */
public class Description {
    private Entity owner;
    //The Entity's "name" or phrasal descriptor. Rooms should follow Upper Case and objects, unless special, should follow lower case.
    //example: "sword", "Great Hall"
    private String dShort;
    //The Entity's "long" or flowery descriptor.
    //This descriptor is used in all non-casual description cases in place of dShort - that is,
    //aside from references such as "You cannot eat the sword" where "the sword" is dShort,
    //in specific descriptors dLong will replace "There is a sword here."
    //Note that (in contrast to dFirst), dLong is a "general" flowery descriptor - that is, no context can be assumed,
    //including the state of the Entity or its location. "the ground" is assumed to be the general default location of an object
    //described using dLong.
    //ex: "There is a long, silver blade with minimal scratches on the ground."
    private String dLong;
    //The Entity's "first", most flowery descriptor.
    //This descriptor is used upon first view of a Room, or when describing any object without {@link EFlag#TOUCH}.
    //As such, all default-state context can be assumed for purposes of description: a sword ITEM Entity, which can through
    //the course of the game be moved from place to place for the purposes of dLong, can be assured to be in its original
    //state for the purposes of dFirst - thus, while dLong may describe the sword as, generally, "on the ground",
    //dFirst may instead describe it as being perched carefully against the wall, without worry that this will later
    //become untrue.
    //ex: (Room) "The great hall is lit softly by candles hanging in the air. It is mostly empty, aside from a single silver blade resting carefully against the wall."
    //ex: (ITEM Entity) "The blade is ornate but not fragile, and shines silver as it rests lightly against the wall."
    private String dFirst;

    private List<Word> names;
    private List<Word> adjectives;

    //The Entity's method descriptor. See {@link EntityDescribeMethod}. Can be null if no object descriptor is provided (for Rooms, this means the object must have a dLong).
    private EntityDescribeMethod dMethod;

    private Description(String dShort, String dLong, String dFirst, EntityDescribeMethod dMethod, List<Word> names, List<Word> adjectives) {
        this.dShort = dShort;
        this.dLong = dLong;
        this.dFirst = dFirst;
        this.dMethod = dMethod;
        this.names = names;
        this.adjectives = adjectives;
    }

    public static class Builder {
        public Builder() {}
        public Description build() {
            return new Description(dShort, dLong, dFirst, dMethod, names, adjectives);
        }
        private String dShort;
        private String dLong;
        private String dFirst;
        private EntityDescribeMethod dMethod;
        private List<Word> names;
        private List<Word> adjectives;

        public Builder setShort(String dShort) {
            this.dShort = dShort;
            return this;
        }

        public Builder setLong(String dLong) {
            this.dLong = dLong;
            return this;
        }

        public Builder setFirst(String dFirst) {
            this.dFirst = dFirst;
            return this;
        }

        public Builder setMethod(EntityDescribeMethod dMethod) {
            this.dMethod = dMethod;
            return this;
        }

        public Builder setNames(List<Word> names) {
            this.names = names;
            return this;
        }

        public Builder setAdjectives(List<Word> adjectives) {
            this.adjectives = adjectives;
            return this;
        }
    }

    public Description setShort(String dShort) {
        this.dShort = dShort;
        return this;
    }

    public Description setLong(String dLong) {
        this.dLong = dLong;
        return this;
    }

    public Description setFirst(String dFirst) {
        this.dFirst = dFirst;
        return this;
    }

    public Description setMethod(EntityDescribeMethod dMethod) {
        this.dMethod = dMethod;
        return this;
    }

    public Description addAdjectives(Word... adjectives) {
        if(this.adjectives == null) {
            this.adjectives = new ArrayList<Word>();
        }
        for(Word w : adjectives) {
            if(!this.adjectives.contains(w)) {
                this.adjectives.add(w);
            }
        }
        return this;
    }

    public boolean removeAdjective(Word adjective) {
        return this.adjectives.remove(adjective);
    }

    public boolean hasAdjectives(Word... adjectives) {
        if(this.adjectives == null) {
            return false;
        }
        for(Word w : adjectives) {
            if(!this.adjectives.contains(w)) {
                return false;
            }
        }
        return true;
    }

    public String the() {
        return (!owner.has(EFlag.NART) ? "the " : "") + this.getShort();
    }

    public String The() {
        return (!owner.has(EFlag.NART) ? "The " : "") + this.getShort();
    }

    public String theLowerCase() {
        return (!owner.has(EFlag.NART) ? "the " : "") + (owner.has(EFlag.NUNC) ? this.getShort() : this.getShort().toLowerCase());
    }

    public String a() {
        return (!owner.has(EFlag.NART) ? (!owner.has(EFlag.BVOW) ? "a " : "an ") : "") + this.getShort();
    }

    public String A() {
        return (!owner.has(EFlag.NART) ? (!owner.has(EFlag.BVOW) ? "A " : "An ") : "") + this.getShort();
    }

    public void setOwner(Entity e) {
        this.owner = e;
    }

    public String getShort() {
        return this.dShort;
    }

    public String getLong() {
        return this.dLong;
    }

    public String getFirst() {
        return this.dFirst;
    }

    public EntityDescribeMethod getMethod() {
        return this.dMethod;
    }
}
