public class Verb {
    public static final Method WALK_DIRECTION = (g -> {
        g.HERO.move(g.HERO.getParent().getNavigation().move(g.P_DIRECTION));
        return true;
    }); //TODO replace this is sample code

    //
    static final Action[] actions = new Action[] {
            new Action(WALK_DIRECTION, new Token(Word.WALK), new Token(Token.TokenType.DIR, Token.ETokenFlag.DIRECTION)),
            new Action(WALK_DIRECTION, new Token(Token.TokenType.DIR, Token.ETokenFlag.DIRECTION)),
    };
    //actions will not need to be generally accessible (except to the parser) or named - they will point to a method (A_WALK), which is of note to other classes.
    //many actions a, a1, a2, a3 may have the same METHOD, such as "walk to DIR"
}
