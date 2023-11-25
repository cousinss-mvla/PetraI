public enum Word {

    //verbs
    VERB_HEADER(),
    WALK("WALK", "GO"),
    EAT("EAT", "MUNCH", "TASTE", "CONSUME"),
    TAKE("TAKE", "GRAB", "STEAL"),
    DRINK("DRINK"),
    PUT("PUT", "PLACE"),
    DROP("DROP", "THROW", "DISCARD"),
    ATTACK("ATTACK", "KILL", "STAB", "MURDER", "BEAT", "ASSAULT", "DESTROY"),
    EXAMINE("EXAMINE", "INSPECT", "STUDY", "INVESTIGATE"),
    LOOK("LOOK", "GAZE", "L"),

    VERB_FOOTER(),

    //Nouns
    NOUN_HEADER(),
    SWORD("SWORD"),
    DOOR("DOOR", "DOORS"),
    TRAPDOOR("TRAPDOOR", "TRAP DOOR", "TRAPDOORS", "TRAPDOORS"),
    MAILBOX("MAILBOX", "MAIL BOX"),
    MAIL("MAIL"),
    PAMPHLET("PAMPHLET", "PAMPLET", "PAMPHET", "PAMHLET"),
    PAPER("PAPER"),
    HANDLE("HANDLE"),
    IT("IT", "THAT"), //special IT token
    NOUN_FOOTER(),


    //Articles
    THE("THE"),
    A("A", "AN"),
    SOME("SOME", "A COUPLE", "A FEW"),
    YOUR("YOUR"),
    OUR("OUR"),
    MY("MY"),
    THEIR("THEIR"),

    //Adjectives
    RED("RED", "CRIMSON"),
    GREEN("GREEN", "LIMEGREEN", "LIME GREEN", "LIME"),
    BLUE("BLUE", "AZURE"),
    PURPLE("PURPLE", "LILAC"),
    WHITE("WHITE", "COLORLESS"),
    SILVER("SILVER", "SILVERY"),
    SMALL("SMALL", "SMALLISH", "MINIATURE"),

    //Prepositions
    ON("ON", "ATOP", "ONTO"),
    IN("IN", "INTO", "INSIDE"),
    THROUGH("WITH", "USING", "THRU", "THROUGH", "VIA"),
    UNDER("UNDER", "BENEATH", "BELOW", "UNDERNEATH"),
    OFF("OFF"),
    FROM("FROM"),
    TO("TO", "TOWARDS"),

    //technically not grammatically prepositions but similar Token strings
    //?

    //Directions
    NORTH("NORTH", "NORTHWARD", "NORTHWARDS", "N"),
    EAST("EAST", "EASTWARD", "EASTWARDS", "E"),
    SOUTH("SOUTH", "SOUTHWARD", "SOUTHWARDS", "S"),
    WEST("WEST", "WESTWARD", "WESTWARDS", "W"),
    NORTHEAST("NORTHEAST", "NORTHEASTWARD", "NORTHWESTWARD", "NE"),
    NORTHWEST("NORTHWEST", "NORTHWESTWARD", "NORTHWESTWARDS", "NW"),
    SOUTHWEST("SOUTHWEST", "SOUTHWESTWARD", "SOUTHWESTWARDS", "SW"),
    SOUTHEAST("SOUTHEAST", "SOUTHEASTWARD", "SOUTHEASTWARDS", "SE"),
    OUT("OUT", "OUTSIDE"),
    //see Word.IN in Prepositions (double-purpose) //TODO THINK is this gonna be a problem?
    UP("UP", "U", "UPWARDS", "UPSTAIRS"),
    DOWN("DOWN", "D", "DOWNWARDS", "DOWNSTAIRS"),
    FORTH("FORWARD", "FORTH"),
    BACK("BACK", "BACKWARDS", "BACKWARD"),
    AROUND("AROUND"),
    ;

    private final String[] matchString;

    Word(String... matchString) {
        String[] match = new String[matchString.length];
        for(int i = 0; i < matchString.length; i++) {
            match[i] = matchString[i].toUpperCase();
        }
        this.matchString = match;
    }

    public String[] getMatchStrings() {
        return this.matchString;
    }

    public boolean matches(String string) {
        string = string.toUpperCase();
        for(String s : matchString) {
            if(s.equals(string)) {
                return true;
            }
        }
        return false;
    }

    public boolean isVerb() {
        return this.ordinal() > Word.VERB_HEADER.ordinal() && this.ordinal() < Word.VERB_FOOTER.ordinal();
    }

    public boolean isNoun() {
        return this.ordinal() > Word.NOUN_HEADER.ordinal() && this.ordinal() < Word.NOUN_FOOTER.ordinal();
    }

}
