public enum Word {

    //Nouns
    SWORD("SWORD"),
    DOOR("DOOR", "DOORS"),
    TRAPDOOR("TRAPDOOR", "TRAP DOOR", "TRAPDOORS", "TRAPDOORS"),
    MAILBOX("MAILBOX", "MAIL BOX"),
    MAIL("MAIL"),
    PAMPHLET("PAMPHLET", "PAMPLET", "PAMPHET", "PAMHLET"),
    PAPER("PAPER"),
    HANDLE("HANDLE"),


    //Articles
    THE("THE", "THAT", "THIS"),
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
    AROUND("AROUND"),
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
    BACK("BACK", "BACKWARDS", "BACKWARD")
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

}
