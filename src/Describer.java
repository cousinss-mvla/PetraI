public class Describer {
    public static void describeRoom(Global g) {
        Entity r = g.room();
        Description d = r.describe();
        if(d.getMethod() != null && d.getMethod().apply(g)) {
            //already printed
        } else if (!r.has(EFlag.TOUCH) && null != d.getFirst()) {
            System.out.println(d.getFirst());
        } else if(null != d.getLong()) {
            System.out.println(d.getLong());
        } //if we have no method, first, OR long, then there's no point describing anything more than the short that's already been printed
    }

    public static void describeObjects(Global g) {
        System.out.println("In " + g.room().describe().theLowerCase() + " you can see:");
        for(Entity e : g.room().getDescendants()) {
            if(e.hasAny(EFlag.NDESC, EFlag.INV)) continue;
            System.out.println(" " + e.describe().a());
        }
    }
}
