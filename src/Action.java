public class Action {

    private static final Method WALK = (g -> false); //now other methods will reference VERB as A_WALK (static verb method)
    //now multiple Actions can point to the same Action.SOME_METHOD
    Action a = new Action(/*A_WALK, etc, etc*/); //example TODO remove
    //actions will not need to be generally accessible (except to the parser) or named - they will point to a method (A_WALK), which is of note to other classes.
    //many actions a, a1, a2, a3 may have the same METHOD, such as "walk to DIR"

    private Token[] syntax; //for use with Token...
    private Method method;
    //optional
    private Method preMethod;

    public class Token {
        //VERB, DIR, IND, or a WORD (prep)
        //TODO think: are VERBs and WORDs the same? perhaps

        //discussion: should there be a special "Direction" flag?
        //thoughts: just use "Walk DIR (ETokenFlag DIRECTION)", necessitates DIR is single Word that satisfies Direction.ofWord(DIR) != null
        //can set a Global flag to Direction OBJECT_DIR? (we will never ever use two DIRECTION-flagged objects in the same command ("look north with the south"?))
        //this is not specifically what Zork does but it's close - not really sure how zork identifies the object as a direction, but it's not using the same class of flag as HELD or CARRIED. whatever

        //DIR or IND can have FIND or ETokenFlag

        public enum ETokenFlag {
            PRETAKE,
            HOLDING,
            DIRECTION
            //held, carried, on-ground, in-room ?
        }
    }
}
