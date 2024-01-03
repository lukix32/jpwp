import javax.swing.*;
import java.io.FileNotFoundException;

public class Main
{
    final static int GAME_CLK_TICK = 50; // ms

    public static void main(String[] args) {
        UI ui = new UI();
        Game game = new Game(ui);
        Timer gameClk = new Timer(GAME_CLK_TICK, game);
        gameClk.start();
    }
}





