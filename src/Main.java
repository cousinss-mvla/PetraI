import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Petra game = new Petra(new Global(), scanner);
        game.start();
        while(!game.loop()) {}
        scanner.close();
    }
}
