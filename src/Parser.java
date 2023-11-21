public class Parser {

    //returns true if the full execution of this command has been handled, and false if not and the general execution sequence should run.
    // essentially, returns false if this command was a non-turn command - either a dud (unreadable) or something like "save" that's not-in-game
    public static boolean parse(Global g, String str) {
        Word[] words;
        try {
            words = toWordList(str);
        } catch (NotAWordException e) {
            System.out.println("ERR: \"" + e.getFatalString() + "\" is not a WORD!"); //TODO
            return true;
        }
        ActionMatch match;
        try {
            match = actionMatch(words);
        } catch (NoVerbException e) {
            if(words.length == 1) {
                Direction dir = Direction.ofWord(words[0]);
                if(dir != null) {
                    g.VERB = Verb.WALK;
                    g.P_DIRECTION = dir; //reskin "east" as "walk east" and carry on to execution sequence - this won't be weird because "walk <dir> will never parser-error
                    return false;
                }
            }
            System.out.println("ERR: No Verb"); //TODO
            return true;
        } catch (SpeakUpException e) {
            System.out.println("ERR: Speak Up!"); //TODO
            return true;
        }
        g.VERB = match.action.getMethod();
        g.PRE_ACTION = match.action.getPreMethod();
        try {
            g.DIR = parseObject(g, match.dir, match.action.dirToken, true);
            g.IND = parseObject(g, match.ind, match.action.indToken, false);
        } catch (DirectionalObjectException deo) {
            //TODO
            if(deo.shouldHaveBeenDirection()) {
                g.P_DIRECTION = deo.getDirection();
                return false;
            }
            System.out.println("ERR: Supplied Direction in place of Noun"); //TODO - zork says "You used the word "<dir>" in a way I don't understand."
            return true;
        }
        return false;
    }

    private static Word[] toWordList(String str) throws NotAWordException {
        return null; //TODO placeholder
    }

    private static ActionMatch actionMatch(Word[] words) throws NoVerbException, SpeakUpException {
        if(words.length == 0) {
            throw new SpeakUpException();
        }
        boolean hasVerb = false;
        for(Word word : words) {
            if(word.isVerb()) {
                hasVerb = true;
                break;
            }
        }
        if(!hasVerb) {
            throw new NoVerbException();
        }
        return new ActionMatch(Verb.actions[1], new Word[] {Word.OUT}, new Word[] {}); //TODO placeholder
    }

    private static Entity parseObject(Global g, Word[] phrase, Token token, boolean dir) throws DirectionalObjectException {
        if(phrase.length == 1) {
            Direction direction = Direction.ofWord(phrase[0]);
            if(direction != null) {
                //object was direction
                throw new DirectionalObjectException(direction, dir, token.flags.contains(Token.ETokenFlag.DIRECTION));
            }
        }
        return g.WHITE_DOOR_HANDLE; //TODO placeholder
    }

    private record ActionMatch(Action action, Word[] dir, Word[] ind) {}

    private static class NotAWordException extends ParseException {
        private final String fatalString;
        public NotAWordException(String fatalString) {
            super();
            this.fatalString = fatalString;
        }
        public String getFatalString() {
            return this.fatalString;
        }
    }

    /**
     * Exception for object parsing when object would return a direction (there are no Global Direction entities)
     * In this case, the parser does its own handling of the action - extremely special case where the
     */
    private static class DirectionalObjectException extends ParseException {
        private final Direction direction;
        private final boolean directObject;
        private final boolean shouldHaveBeenDirection;
        public DirectionalObjectException(Direction direction, boolean directObject, boolean shouldHaveBeenDirection) {
            super();
            this.direction = direction;
            this.directObject = directObject;
            this.shouldHaveBeenDirection = shouldHaveBeenDirection;
        }
        public Direction getDirection() {
            return this.direction;
        }
        public boolean shouldHaveBeenDirection() {
            return this.shouldHaveBeenDirection;
        }
        public boolean wasDirectObject() {
            return this.directObject;
        }
    }

    private static class NoVerbException extends ParseException {}
    private static class SpeakUpException extends ParseException {};

    private static class ParseException extends Exception {
        public ParseException() {
            super();
        }
    }

}
