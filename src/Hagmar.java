import java.util.List;

public class Hagmar {
    public static void main(String[] args) {
        Global G = new Global();
        Entity loc = G.WHITE_ROOM;
        boolean open = true;
        System.out.println(Verb.actions[0].getMethod().apply(G));
        System.out.println("White Door " + (open ? "OPEN" : "CLOSED"));
        if(open) {
            G.WHITE_DOOR.getFlags().set(EFlag.OPEN);
        }
        System.out.println(Verb.WALK.apply(G));
//        Entity to = loc.getNavigation().move(Direction.OUT);

//        System.out.println("FROM\n" + loc + "\nTO\n"+ to);
    }
}
