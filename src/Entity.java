import java.util.ArrayList;
import java.util.List;

public class Entity {
    private Global G;
    private final String id;
    private Entity parent;
    private EFlagSet flagSet;
    private Description description;
    private Navigation navigation;
    private int weight;
    private int capacity;
    private List<Entity> contains;
    private Method method;

    /**
     * For builder only
     */
    public Entity(Global g, String id, Entity parent, EFlagSet flagSet, Description description, Navigation navigation, int weight, int capacity, List<Entity> contains, Method method) {
        this.G = g;
        this.id = id;
        this.parent = parent;
        this.flagSet = flagSet;
        this.description = description;
        this.navigation = navigation;
        this.weight = weight;
        this.capacity = capacity;
        this.contains = contains;
        this.method = method;
    }

    public static class Builder {

        public Builder(Global g, String id) {
            this.G = g;
            this.id = id;
        }

        public Entity build() {
            Entity out = new Entity(G, id, parent, flagSet != null ? flagSet : new EFlagSet(), description, navigation, weight, capacity, contains == null ? new ArrayList<>() : contains, method);
            if(out.getParent() != null) {
                out.parent.contains.add(out);
            }
            return out;
        }

        private Global G;
        private String id;
        private Entity parent;
        private EFlagSet flagSet;
        private Description description;
        private Navigation navigation;
        private int weight;
        private int capacity;
        private List<Entity> contains;
        private Method method;

        public Builder setParent(Entity parent) {
            this.parent = parent;
            return this;
        }

        public Builder setFlagSet(EFlagSet flagSet) {
            this.flagSet = flagSet;
            return this;
        }

        public Builder setDescription(Description description) {
            this.description = description;
            return this;
        }

        public Builder setNavigation(Navigation navigation) {
            this.navigation = navigation;
            return this;
        }

        public Builder setWeight(int weight) {
            this.weight = weight;
            return this;
        }

        public Builder setCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder putLocalGlobals(List<Entity> contains) {
            this.contains = new ArrayList<>(contains);
            return this;
        }

        public Builder setMethod(Method method) {
            this.method = method;
            return this;
        }
    }

    public boolean methodNotNull(Global g) {
        if(this.getMethod() != null) {
            return this.getMethod().apply(g);
        }
        return false;
    }

    public List<Entity> getAncestry() {
        List<Entity> out = new ArrayList<>();
        Entity iter = this.getParent();
        while(null != iter) {
            out.add(iter);
            iter = iter.getParent();
        }
        return out;
    }

    public List<Entity> getDescendants() {
        List<Entity> out = new ArrayList<Entity>();
        for(Entity e : this.contains) { //TODO make this allow for global presences (air, "you" etc)
            out.add(e);
            out.addAll(e.getDescendants());
        }
        return out;
    }

    public List<Entity> getDescendantsWithSelf() {
        List<Entity> out = this.getDescendants();
        out.add(this);
        return out;
    }

    protected Description describe() {
        return this.description;
    }

    protected boolean addChild(Entity e) {
        if(this.contains.contains(e)) {
            return false;
        }
        this.contains.add(e);
        return true;
    }

    protected boolean move(Entity to) {
        if(null == to) {
            return false;
        }
        this.getParent().removeChild(this);
        this.parent = to;
        this.parent.addChild(this);
        return true;
    }

    protected boolean removeChild(Entity child) {
        return this.contains.remove(child);
    }

    public Method getMethod() {
        return this.method;
    }

    protected Navigation getNavigation() {
        return this.navigation;
    }

    protected String getId() {
        return this.id;
    }

    /**
     * Adds the specified entity to this Entity's contains list, but without setting that Entity's parent to this (unsafe).
     * Use for LOCAL-GLOBALs.
     * @param entity the entity.
     */
    protected void insert(Entity entity) {
        this.contains.add(entity);
    }

    //TODO maybe instead of "fixing" getDescendants leave as is and add a "getAccessible"? need to figure out w/ parser

    public boolean in(Entity e) {
        return this.getAncestry().contains(e);
    }

    public Entity getParent() {
        return this.parent;
    }

    public EFlagSet getFlags() {
        return this.flagSet;
    }

    /**
     * Determines whether this Entity matches one of the supplied Entities.
     * @param e any number of Entity objects
     * @return {@code true} if this entity is one of the supplied Entities.
     */
    public boolean is(Entity... e) {
        for(Entity e1 : e) {
            if(this.equals(e1)) {
                return true;
            }
        }
        return false;

    }

    public boolean has(EFlag flag) {
        return this.flagSet.has(flag);
    }

    public boolean hasAny(EFlag... flags) {
        for(EFlag f : flags) {
            if(flagSet.has(f)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAll(EFlag... flags) {
        for(EFlag f : flags) {
            if(!flagSet.has(f)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Entity " + this.description.getShort());
        out.append("\n in " + (null == this.getParent() ? "REMOVED" : this.getParent().description.getShort())
                + (null == this.getParent() || null == this.getParent().getParent() ? "" : " in " + this.getParent().getParent().description.getShort()
                + (this.getParent().getParent().getParent() != null ? "... (+" + this.getParent().getParent().getAncestry().size() + ")" : "")));
        out.append("\n of (");
            for(EFlag e : EFlag.values()) {
                if(this.getFlags().has(e)) {
                    out.append(e.name() + " ");
                }
            }
            if(out.charAt(out.length() - 1) == ' ') {
                out.deleteCharAt(out.length() - 1);
            }
            out.append(")");
        out.append("\n has (");
            for(Entity e : this.getDescendants()) {
                out.append(e.description.getShort() + ", ");
            }
            if(out.substring(out.length() - 2).equals(", ")) {
                out.delete(out.length() - 2, out.length());
            }
            out.append(")");
        return out.toString();
    }
}
