/**
 * Verbs return false only if they are meta-verbs such as "save" or "help" - otherwise, it is assumed that they "handle" the input - they *have* to.
 */
public class Verb {
    public static final Method WALK_DIRECTION = g -> {
        I_goTo(g.HERO.getParent().getNavigation().move(g.P_DIRECTION, g.HERO.getParent()), g);
        return true;
    }; //TODO this probably needs to be more complicated?

    public static final Method VERSION = g -> {
        System.out.println("\"Petra - Reloaded\" by Samuel Cousins");
        System.out.println("Version " + Petra.VERSION);
        System.out.println("https://github.com/cousinss-mvla/PetraI");
        System.out.println("MIT License\n");
        return true;
    };

    public static final Method LOOK = g -> {
        boolean canSee = false;
        for(Entity e : g.room().getDescendantsWithSelf()) {
            if(e.has(EFlag.LIT)) {
                canSee = true;
                break;
            }
        }
        if(!canSee) {
            System.out.println("You can't see a thing. Where's the light switch?");
            return true;
        }
        System.out.println(g.room().describe().getShort());
        Describer.describeRoom(g);
        Describer.describeObjects(g);
        g.room().getFlags().set(EFlag.TOUCH);
        return true;
    };

    protected static void I_goTo(Entity to, Global g) {
        g.HERO.move(to);
        g.R_FLAG = RContextFlag.ROOM_ENTER;
        g.room().methodNotNull(g);
        LOOK.apply(g);
    }

    protected static final Action[] ACTIONS = new Action[] {
            new Action(WALK_DIRECTION, new Token(Word.WALK), new Token(Token.TokenType.DIR, Token.ETokenFlag.DIRECTION)),
            new Action(WALK_DIRECTION, new Token(Token.TokenType.DIR, Token.ETokenFlag.DIRECTION)),
    };
    //actions will not need to be generally accessible (except to the parser) or named - they will point to a method (A_WALK), which is of note to other classes.
    //many actions a, a1, a2, a3 may have the same METHOD, such as "walk to DIR"
}
