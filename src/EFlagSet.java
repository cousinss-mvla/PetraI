import java.util.EnumSet;

/**
 * Utility class for storage of {@link EFlag}s using an EnumSet.
 */
public class EFlagSet {
    private final EnumSet<EFlag> set;

    public EFlagSet(EFlag... flags) {
        this.set = EnumSet.noneOf(EFlag.class);
        for(EFlag f : flags) {
            this.set.add(f);
        }
    }

    public boolean has(EFlag flag) {
        return set.contains(flag);
    }

    public boolean hasAnd(EFlag... flags) {
        for(EFlag f : flags) {
            if(!set.contains(f)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasOr(EFlag... flags) {
        for(EFlag f : flags) {
            if(set.contains(f)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the flag.
     * @param flag the flag
     * @return {@code true} if the flag was added, {@code false} if the flag was already present.
     */
    public boolean set(EFlag flag) {
        return set.add(flag);
    }

    /**
     * Sets the flag.
     * @param flag the flag
     * @return {@code true} if the flag was removed, {@code false} if the flag was already absent.
     */
    public boolean remove(EFlag flag) {
        return set.remove(flag);
    }
}
