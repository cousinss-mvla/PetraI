import java.util.List;
import java.util.function.Function;

public class Global {

    public Entity ROOMS = null;
    public Entity WHITE_ROOM = null;
    public Entity WHITE_DOOR = null;
    public Entity WHITE_DOOR_HANDLE = null;
    public Entity BLACK_ROOM = null;
    public Entity LOCAL_GLOBALS = null;

    public Global() {

        ROOMS = new Entity.Builder(this).build();
        LOCAL_GLOBALS = new Entity.Builder(this).build();

        WHITE_DOOR = new Entity.Builder(this)
                .setParent(LOCAL_GLOBALS)
                .setDescription(new Description.Builder()
                        .setShort("white door")
                        .setNames(List.of(Word.DOOR))
                        .setAdjectives(List.of(Word.WHITE))
                        .setFirst("Inscribed in one wall, just barely visible, is the outline of a door, cut into the wall surface, with a small silver handle.")
                        .setLong("There is a white door cut out of the wall surface here.")
                        .build())
                .setFlagSet(new EFlagSet(EFlag.DOOR, EFlag.LOCK))
                .build();

        WHITE_DOOR_HANDLE = new Entity.Builder(this)
                .setParent(WHITE_DOOR)
                .setDescription(new Description.Builder()
                        .setShort("door handle")
                        .setNames(List.of(Word.HANDLE))
                        .setAdjectives(List.of(Word.DOOR, Word.SILVER, Word.SMALL, Word.WHITE))
                        .build())
                .setFlagSet(new EFlagSet(EFlag.NDESC, EFlag.INT))
                .build();

        WHITE_ROOM = new Entity.Builder(this)
                .setParent(ROOMS)
                .setDescription(new Description.Builder()
                        .setShort("White Room")
                        .setLong("You are in a mysterious white room, lit by an unknown source. It is drab but otherwise pleasant.")
                        .build())
                .setFlagSet(new EFlagSet(EFlag.LIT, EFlag.NDIR))
                .setNavigation(new Navigation(this)
                        .put(getDoorNavFunc(WHITE_DOOR, WHITE_ROOM, BLACK_ROOM, "The door is closed."), Direction.OUT, Direction.IN))
                .putLocalGlobals(List.of(WHITE_DOOR))
                .build();

        BLACK_ROOM = new Entity.Builder(this)
                .setParent(ROOMS)
                .setDescription(new Description.Builder()
                        .setShort("Black Room")
                        .setLong("You are in a strange black room, lit by an unknown source. Its novelty excites you.")
                        .build())
                .setFlagSet(new EFlagSet(EFlag.LIT, EFlag.NDIR))
                .setNavigation(new Navigation(this)
                        .put(getDoorNavFunc(WHITE_DOOR, BLACK_ROOM, WHITE_ROOM, "The door is closed."), Direction.OUT, Direction.IN))
                .putLocalGlobals(List.of(WHITE_DOOR))
                .build();

        biconnectParents();
    }

    private void biconnectParents() {
        for(Entity e : ROOMS.getDescendants()) {
            if(e.getParent() != null) {
                e.getParent().addChild(e);
            }
        }
        for(Entity e : LOCAL_GLOBALS.getDescendants()) {
            if(e.getParent() != null) {
                e.getParent().addChild(e);
            }
        }
    }
    //TODO second-pass (need to do once all variables are initialized) to add all have-parents to contains lists
    //should be able to recurse through descendants tree of ROOMS and LOCAL-GLOBALS for this? all things that have a parent
    //should be able to trace their lineage back to one of these two entities

    private Function<Global, Entity> getDoorNavFunc(Entity door, Entity from, Entity to, String rejectionMessage) {
        return g -> door.has(EFlag.OPEN) ? to : from;
    }

}
