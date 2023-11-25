import java.util.Scanner;

public class Petra {
    protected static final String VERSION = "1.0a02b";

    private final Global g;
    private final Scanner scanner;

    public Petra(Global g, Scanner scanner) {
        this.g = g;
        this.scanner = scanner;
    }

    public void start() {
        Verb.VERSION.apply(g);
        Verb.LOOK.apply(g);
        System.out.println();
    }

    /**
     * Returns true if game is over.
     * @return
     */
    public boolean loop() {
        String input = input();

        preclearPGlobals();

        if (Parser.parse(g, input)) {
            return false; //loop over, parser handled
        }

//        g.VERB = Verb.WALK_DIRECTION;//false parse TODO
//        g.P_DIRECTION = Direction.IN;//false parse TODO

        runTurn(false);

        System.out.println();
        return g.GAME_OVER;
    }

    private void runTurn(boolean finish) {
        boolean noTime = false;
        if (null != g.HERO.getMethod() && g.HERO.getMethod().apply(g)) {
            System.out.println("Handled by HERO method");
        } else if (null != g.room().getMethod() && g.room().getMethod().apply(g)) {
//            System.out.println("Handled by ROOM PRETURN by " + g.room().describe().getShort());
        } else if (null != g.PRE_ACTION && g.PRE_ACTION.apply(g)) {
            System.out.println("Handled by VERB PREACTION");
        } else if (null != g.IND && null != g.IND.getMethod() && g.IND.getMethod().apply(g)) {
            System.out.println("Handled by IND by " + g.IND.describe().getShort());
        } else if(null != g.DIR && null != g.DIR.getMethod() && g.DIR.getMethod().apply(g)) {
            System.out.println("Handled by DIR by " + g.DIR.describe().getShort());
        } else {
            if(g.VERB.apply(g)) {
//                System.out.println("Handled by VERB");
                noTime = true; //some verbs take no time - should be accounted for later
            }
        }

        //If this point reached and g.TURN_OVER is false, either:
        // - in parent loop and nobody called the public method runTurn() to alias the turn. That means this is the first time all of this is being done.
        // - in (probably) PRE_ACTION runTurn() call and need to set TURN_OVER so that this isn't repeated again after turn execution completes
        if(!g.TURN_OVER) {
            if(finish) {
                g.TURN_OVER = true;
            }
            //finish
            if(noTime) {
                return;
            }
            g.R_FLAG = RContextFlag.ROOM_POSTTURN;
            if(null != g.room().getMethod()) {
                g.room().getMethod().apply(g);
            }
            g.CLOCK.tick(g);
        }
    }

    /**
     * Set new G-Vars and call this for turn aliasing.
     */
    public void runTurn() {
        this.runTurn(true);
    }

    private void preclearPGlobals() {
        g.P_DIRECTION = null;
        g.VERB = null;
        g.PRE_ACTION = null;
        g.DIR = null;
        g.IND = null;
        g.R_FLAG = RContextFlag.ROOM_PRETURN;
        g.TURN_OVER = false;
    }

    private String input() {
        System.out.print(" > ");
        String in = scanner.nextLine();
        return in;
    }
}
