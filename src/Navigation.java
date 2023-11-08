import java.util.EnumMap;
import java.util.function.Function;

/**
 * Much like {@link Description}, the Navigator is a data-child for an Entity's (almost always a Room's) locational "neighbors" -
 * other Rooms which can be accessed as the Adventurer moves.
 */
public class Navigation {
    private final Global G;
    private EnumMap<Direction, Function<Global, Entity>> map;

    public Navigation(Global G) {
        this.G = G;
        this.map = new EnumMap<Direction, Function<Global, Entity>>(Direction.class);
    }

    public Navigation put(Function<Global, Entity> method, Direction... direction) {
        for(Direction d : direction) {
            this.map.put(d, method);
        }
        return this;
    }

    /**
     * Applies the Direction's movement-attempt function, and returns the new ROOM Entity.
     * @param direction the direction.
     * @return one of the following:
     * The new ROOM Entity if the Adventurer can move that way (UEXIT or CEXIT pass)
     * {@code null} if the Adventurer cannot move that way and the caller should handle the print-message ("you cannot go that way"). (EXIT absent)
     * or the caller room Entity if the Adventurer cannot move that way, but the print-message has already been handled. (FEXIT general, NEXIT, CEXIT fail)
     */
    public Entity move(Direction direction) {
        if(!map.containsKey(direction)) {
            return null;
        }
        try {
            return map.get(direction).apply(G);
        } catch (Exception e) {
            return null;
        }
    }

    //TODO do I need a map.get ? cases where implementation needs to get() but not simply use using move()?
}
