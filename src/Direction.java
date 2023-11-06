public enum Direction {
    /**
     * Used both for straight-up modes like ladders but also for e.g. staircases that may also transport horizontally -
     * applies simply to any direction that could reasonably be referred to using "go up".
     */
    UP,
    /**
     * see {@link Direction#UP}
     */
    DOWN,
    /**
     * Used by rooms with only one port of entry.
     */
    IN,
    /**
     * Used similarly to (and always together with) {@link Direction#IN}.
     */
    OUT,
    NORTH,
    EAST,
    SOUTH,
    WEST,
    NORTHEAST,
    SOUTHWEST,
    SOUTHEAST,
    NORTHWEST,
    /**
     * Like {@link Direction#IN} and {@link Direction#OUT}, but for pathways with clear bi-direction. Can be used in
     * tandem with a Navigator method to switch the direction that "forwards" represents given the direction the player is coming from (the room they entered from).
     */
    FORTH,
    /**
     * see {@link Direction#FORTH}
     */
    BACK
}
