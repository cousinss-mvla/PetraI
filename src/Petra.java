public class Petra {
    private final Global g;
    private final Clock clock;

    public Petra(Global g, Clock clock) {
        this.g = g;
        this.clock = clock;


        //TODO testing
        g.WHITE_DOOR.getFlags().set(EFlag.OPEN);
    }

    public boolean loop() {
        System.out.println(g.HERO.getParent().describe().getShort());
        String input = input();

        System.out.println(" > " + input);

        preclearPGlobals();
        //false parse TODO
        g.VERB = Verb.WALK_DIRECTION;
        g.P_DIRECTION = Direction.IN;
        //false parse

//        if(Parser.parse(g, input)) {
//            return false; //loop over, parser handled
//        }
        g.VERB.apply(g);
        System.out.println();
        return false;
    }

    private void preclearPGlobals() {
        g.P_DIRECTION = null;
        g.VERB = null;
        g.PRE_ACTION = null;
        g.DIR = null;
        g.IND = null;
    }

    private String input() {
        return "go in"; //TODO
    }
}
