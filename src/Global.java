import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Global {


    //gvars
        //set by parser
        public Direction P_DIRECTION = null;
        public Method VERB = null;
        public Method PRE_ACTION = null;
        public Object DIR = null;
        public Object IND = null;

    public boolean VERBOSE = false;

    //this thing (lmao)
    private Map<String, Entity> eHash;

    //rooms
    public Entity ROOMS = null;
    public Entity LOCAL_GLOBALS = null;
    public Entity VOID = null;
    public Entity GLOBALS = null;
    //The player character Entity.
    public Entity HERO = null;
    public Entity WHITE_ROOM = null;
    public Entity WHITE_DOOR = null;
    public Entity WHITE_DOOR_HANDLE = null;
    public Entity BLACK_ROOM = null;


    public Global() {

        ROOMS = new Entity.Builder(this, "ROOMS")
                .setDescription(new Description.Builder().setShort("ROOMS").build())
                .setFlagSet(new EFlagSet(EFlag.NDESC, EFlag.INV))
                .build();
        LOCAL_GLOBALS = new Entity.Builder(this, "LOCAL_GLOBALS")
                .setDescription(new Description.Builder().setShort("LOCAL_GLOBALS").build())
                .setFlagSet(new EFlagSet(EFlag.NDESC, EFlag.INV))
                .build();
        VOID = new Entity.Builder(this, "VOID")
                .setDescription(new Description.Builder().setShort("VOID").build())
                .setFlagSet(new EFlagSet(EFlag.NDESC, EFlag.INV))
                .build();
        GLOBALS = new Entity.Builder(this, "GLOBALS")
                .setDescription(new Description.Builder().setShort("GLOBALS").build())
                .setFlagSet(new EFlagSet(EFlag.NDESC, EFlag.INV))
                .build();

        WHITE_DOOR = new Entity.Builder(this, "WHITE_DOOR")
                .setParent(LOCAL_GLOBALS)
                .setDescription(new Description.Builder()
                        .setShort("white door")
                        .setNames(List.of(Word.DOOR))
                        .setAdjectives(List.of(Word.WHITE))
                        .setFirst("Inscribed in one wall, just barely visible, is the outline of a door, cut into the wall surface, with a small silver handle.")
                        .setLong("There is a white door cut out of the wall surface here.")
                        .build())
                .setFlagSet(new EFlagSet(EFlag.DOOR))
                .build();

        WHITE_DOOR_HANDLE = new Entity.Builder(this, "WHITE_DOOR_HANDLE")
                .setParent(WHITE_DOOR)
                .setDescription(new Description.Builder()
                        .setShort("door handle")
                        .setNames(List.of(Word.HANDLE))
                        .setAdjectives(List.of(Word.DOOR, Word.SILVER, Word.SMALL, Word.WHITE))
                        .build())
                .setFlagSet(new EFlagSet(EFlag.NDESC, EFlag.INT))
                .build();

        WHITE_ROOM = new Entity.Builder(this, "WHITE_ROOM")
                .setParent(ROOMS)
                .setDescription(new Description.Builder()
                        .setShort("White Room")
                        .setLong("You are in a mysterious white room, lit by an unknown source. It is drab but otherwise pleasant.")
                        .build())
                .setFlagSet(new EFlagSet(EFlag.LIT, EFlag.NDIR))
                .setNavigation(new Navigation(this)
                        .put(getDoorNavFunc("WHITE_DOOR", "WHITE_ROOM", "BLACK_ROOM"), Direction.OUT, Direction.IN))
                .putLocalGlobals(List.of(WHITE_DOOR))
                .build();

        BLACK_ROOM = new Entity.Builder(this, "BLACK_ROOM")
                .setParent(ROOMS)
                .setDescription(new Description.Builder()
                        .setShort("Black Room")
                        .setLong("You are in a strange black room, lit by an unknown source. Its novelty excites you.")
                        .build())
                .setFlagSet(new EFlagSet(EFlag.LIT, EFlag.NDIR))
                .setNavigation(new Navigation(this)
                        .put(getDoorNavFunc("WHITE_DOOR", "BLACK_ROOM", "WHITE_ROOM"), Direction.OUT, Direction.IN))
                .putLocalGlobals(List.of(WHITE_DOOR))
                .build();

        HERO = new Entity.Builder(this, "HERO")
                .setParent(WHITE_ROOM)
                .setDescription(new Description.Builder()
                        .setShort("you")
                        .build())
                .setFlagSet(new EFlagSet(EFlag.CHARACTER, EFlag.CONT, EFlag.NART, EFlag.INV, EFlag.TOUCH, EFlag.NDESC, EFlag.OPEN))
//                .putLocalGlobals(List.of()/*TODO hands, fists, all body parts*/)
                .setCapacity(10)
                .build();


        populateHash();
    }

    private void populateHash() {
        eHash = new HashMap<>();
        for(Entity e : ROOMS.getDescendantsWithSelf()) {
            eHash.put(e.getId(), e);
        }
        for(Entity e : VOID.getDescendantsWithSelf()) {
            eHash.put(e.getId(), e);
        }
        for(Entity e : GLOBALS.getDescendantsWithSelf()) {
            eHash.put(e.getId(), e);
        }
//        for(String s : eHash.keySet()) {
//            System.out.println("In hash " + s + " -> " + eHash.get(s));
//        }
    }

    private Entity e(String id) {
        return eHash.get(id);
    }

    private Function<Global, Entity> getDoorNavFunc(String door, String from, String to) {
        return g -> {
            Entity d = e(door);
            Entity f = e(from);
            Entity t = e(to);
//            System.out.println("Can move from \n" + f + "\nto\n" + t + "\nthrough\n" + d + "\n?");
            if(!d.has(EFlag.OPEN)) {
                System.out.println("The " + d.describe().getShort() + " is closed.");
            }
            return d.has(EFlag.OPEN) ? t : null;
        };
    }

    private Function<Global, Entity> sAcc(Entity e) {
        return g -> e;
    }

}
