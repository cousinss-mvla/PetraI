public enum Direction {
    /**
     * Used both for straight-up modes like ladders but also for e.g. staircases that may also transport horizontally -
     * applies simply to any direction that could reasonably be referred to using "go up".
     */
    UP(Word.UP),
    /**
     * see {@link Direction#UP}
     */
    DOWN(Word.DOWN),
    /**
     * Used by rooms with only one port of entry.
     */
    IN(Word.IN),
    /**
     * Used similarly to (and always together with) {@link Direction#IN}.
     */
    OUT(Word.OUT),
    NORTH(Word.NORTH),
    EAST(Word.EAST),
    SOUTH(Word.SOUTH),
    WEST(Word.WEST),
    NORTHEAST(Word.NORTHEAST),
    SOUTHWEST(Word.SOUTHWEST),
    SOUTHEAST(Word.SOUTHEAST),
    NORTHWEST(Word.NORTHWEST),
    /**
     * Like {@link Direction#IN} and {@link Direction#OUT}, but for pathways with clear bi-direction. Can be used in
     * tandem with a Navigator method to switch the direction that "forwards" represents given the direction the player is coming from (the room they entered from).
     */
    FORTH(Word.FORTH),
    /**
     * see {@link Direction#FORTH}
     */
    BACK(Word.BACK),
    AROUND(Word.AROUND);

    private final Word word;

    Direction(Word word) {
        this.word = word;
    }

    public Word getWord() {
        return this.word;
    }

    public static Direction ofWord(Word word) {
        for(Direction d : Direction.values()) {
            if(d.getWord().equals(word)) {
                return d;
            }
        }
        return null;
    }

    public boolean any(Direction... directions) {
        for(Direction direction : directions) {
            if(direction.equals(this)) {
                return true;
            }
        }
        return false;
    }
}
