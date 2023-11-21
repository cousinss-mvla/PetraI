import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public class Token {

    //VERB, DIR, IND, or a WORD (prep)
    //TODO think: are VERBs and WORDs the same? perhaps

    //discussion: should there be a special "Direction" flag?
    //thoughts: just use "Walk DIR (ETokenFlag DIRECTION)", necessitates DIR is single Word that satisfies Direction.ofWord(DIR) != null
    //can set a Global flag to Direction OBJECT_DIR? (we will never ever use two DIRECTION-flagged objects in the same command ("look north with the south"?))
    //this is not specifically what Zork does but it's close - not really sure how zork identifies the object as a direction, but it's not using the same class of flag as HELD or CARRIED. whatever

    //DIR or IND can have FIND or ETokenFlag

    final Word[] words;
    final TokenType type;
    final EFlag find;
    final Set<ETokenFlag> flags;

    public Token(Word... words) {
        this.words = words;
        this.type = TokenType.WORD;
        this.find = null;
        this.flags = EnumSet.noneOf(ETokenFlag.class);
    }

    public Token(TokenType type, EFlag find, ETokenFlag... flags) {
        this.words = null;
        this.type = type;
        this.find = find;
        this.flags = flags.length > 0 ? EnumSet.copyOf(Arrays.asList(flags)) : EnumSet.noneOf(ETokenFlag.class);
    }

    public Token(TokenType type, ETokenFlag... flags) {
        this(type, null, flags);
    }

    public enum TokenType {
        WORD,
        DIR,
        IND
    }

    public enum ETokenFlag {
        PRETAKE,
        HOLDING,
        DIRECTION
        //held, carried, on-ground, in-room ?
    }
}
