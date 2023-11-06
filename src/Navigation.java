import java.util.EnumMap;
import java.util.concurrent.Callable;
/**
 * Much like {@link Description}, the Navigator is a data-child for an Entity's (almost always a Room's) locational "neighbors" -
 * other Rooms which can be accessed as the Adventurer moves.
 */
public class Navigation {
    private EnumMap<Direction, Callable<Entity>> map;

    public Navigation() {
        this.map = new EnumMap<Direction, Callable<Entity>>(Direction.class);
    }

    public void put(Direction direction, Callable<Entity> method) {
        this.map.put(direction, method);
    }

    /**
     * Applies the Direction's movement-attempt function, and returns the new ROOM Entity.
     * @param direction the direction.
     * @return one of the following:
     * The new ROOM Entity if the Adventurer can move that way (UEXIT or CEXIT pass)
     * {@code null} if the Adventurer cannot move that way and the caller should handle the print-message ("you cannot go that way"). (EXIT absent)
     * or the Adventurer's current room if the Adventurer cannot move that way, but the print-message has already been handled. (FEXIT general, NEXIT, CEXIT fail)
     */
    public Entity move(Direction direction) {
        if(!map.containsKey(direction)) {
            return null;
        }
        try {
            return map.get(direction).call();
        } catch (Exception e) {
            return null;
        }
    }

    //TODO do I need a map.get ? cases where implementation needs to get() but not simply use using move()?
}
