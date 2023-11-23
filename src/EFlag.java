public enum EFlag {
    CHARACTER,
    DOOR,
    CONT,
    OPEN,
    SURFACE,
    ITEM,
    LOCK,
    TOUCH,
    EXAM,
    /**
     * No-Describe: Do not describe this Entity.
     */
    NDESC,
    TAKE,
    /**
     * No articles: Do not use articles before this Entity's S-DESC.
     */
    NART,
    /**
     * Begins with a vowel: Use "an" before this Entity's S-DESC.
     */
    BVOW,
    /**
     * (Potentially) abstract usage: whether the entity is "on".
     */
    ON,
    /**
     * Whether the entity is providing light.
     */
    LIT,
    /**
     * Integral: Marked as cannot be removed from parent
     */
    INT,
    /**
     * Directionless: Use for rooms where directions NESW, etc. should be rejected ("you don't know which way is "north")
     */
    NDIR,
    /**
     * Describe as plural. (generally, avoid plural objects because of complication - but this is used for the "me" entity)
     */
    PLURAL,
    /**
     * Invisible: Act as if this Entity is not in its parent. In the case of normal objects, move to VOID instead, but useful for LOCAL_GLOBALS.
     */
    INV,
    /**
     * No un-capitalizing this Entity's name. Ex: a room named "Hades' Gate".
     */
    NUNC
}
