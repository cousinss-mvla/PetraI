public class Main {
    public static void main(String[] args) {
        Petra game = new Petra(new Global());
        game.start();
        for(int i = 0 ; i < 5; i++) {
            game.loop();
        }
    }
}
