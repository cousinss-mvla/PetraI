import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Much like {@link Description}, the Navigator is a data-child for an Entity's (almost always a Room's) locational "neighbors" -
 * other Rooms which can be accessed as the Adventurer moves.
 */
public class Navigation {
    private final Global G;
    private Map<Entity, Direction> doorMap;
    private EnumMap<Direction, BiFunction<Global, Entity, Entity>> fMap;

    public Navigation(Global G) {
        this.G = G;
        this.fMap = new EnumMap<>(Direction.class);
        this.doorMap = new HashMap<>();
    }

    public Navigation func(BiFunction<Global, Entity, Entity> method, Direction... direction) {
        for(Direction d : direction) {
            this.fMap.put(d, method);
        }
        return this;
    }

    public Navigation free(Entity to, Direction... direction) {
        return func((g, f) -> to, direction);
    }

    public Navigation reject(String rejectionMessage, Direction... direction) {
        return func(
                (g, f) -> {
                    System.out.println(rejectionMessage);
                    return f;}
                , direction);
    }

    public Navigation door(Entity to, Entity throughDoor, Direction direction) {
        doorMap.put(throughDoor, direction);
        return func(
                (g, f) -> {
                    if(throughDoor.has(EFlag.OPEN)) {
                        return to;
                    }
                    System.out.println(throughDoor.describe().The() + " is not open.");
                    return f;
                }
        );
    }

    /**
     * Applies the Direction's movement-attempt function, and returns the new ROOM Entity.
     * @param direction the direction.
     * @return one of the following:
     * The new ROOM Entity if the Adventurer can move that way (UEXIT or CEXIT pass)
     * {@code null} if the Adventurer cannot move that way and the caller should handle the print-message ("you cannot go that way"). (EXIT absent)
     * or the caller room Entity if the Adventurer cannot move that way, but the print-message has already been handled. (FEXIT general, NEXIT, CEXIT fail)
     */
    public Entity move(Direction direction, Entity from) {
        if(!fMap.containsKey(direction)) {
            return null;
        }
        try {
            return fMap.get(direction).apply(G, from);
        } catch (Exception e) {
            System.out.println("An error occurred.");
            return null;
        }
    }

    public Direction getDoorDirection(Entity door) {
        return this.doorMap.get(door);
    }

    /**
     * For use only with door-like exits that are defined using #func:
     * for instance, a specific gate has an action when being moved through, so #func is required, but the player still needs
     * to be able to use "go through gate". for this use, use #putCustomDoor.
     * note that this should be exceedingly unlikely: if some behaviour is needed upon entrance of a room, just use the room method behaviour.
     */
    protected void putCustomDoor(Entity door, Direction direction) {
        this.doorMap.put(door, direction);
    }

    //TODO do I need a map.get ? cases where implementation needs to get() but not simply use using move()?
}
