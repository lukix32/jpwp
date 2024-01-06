import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Klasa modelujaca stan rozgrwki
 */
abstract class GameState {
    protected Game game;

    /**
     * Konstruktor klasy GameState
     * @param game
     */
    public GameState(Game game) {
        this.game = game;
    }

    /**
     * Funkcja wywolywana podczas nacisniecia przycisku New Game
     */
    public void onNewGamePressed() { }

    /**
     * Funkcja wywolywana podczas ticku zegara
     */
    public void onGameTimerTick() { }

    /**
     * Funkcja zwracajaca aktualny stan rozgrywki
     * @return aktualny stan rozgrywki
     */
    public abstract String getGameInfo();
}

/**
 * Klasa modelujaca stan zaladowania poziomu
 */
class LoadingLevelState extends GameState {

    /**
     * Konstruktor klasy LoadingLevelState
     * @param game
     */
    LoadingLevelState(Game game) {
        super(game);
        game.ui.loadMaze(game.maze.getCurrMaze());
        game.ui.setTopScorer(game.maze.getTopScore());
    }

    @Override
    public void onGameTimerTick() {
        game.player2 = new Player(0,0);
        int mazeSize = game.maze.getCurrMaze().length;
        game.player1 = new Player(mazeSize - 1, mazeSize - 1);
        game.timeRemaining = game.maze.getTimeLimit();
        game.changeGameState(new ReadyState(game));
    }

    @Override
    public String getGameInfo() {
        return "Loading...";
    }
}

/**
 * Klasa modelujaca stan gotowosci do rozpoczecia gry
 */
class ReadyState extends GameState {
    /**
     * Konstruktor klasy ReadyState
     * @param game
     */
    ReadyState(Game game) {
        super(game);
    }
    @Override
    public void onNewGamePressed() {
        game.changeGameState(new PlayingState(game));
    }

    @Override
    public String getGameInfo() {
        return "Ready";
    }
}

/**
 * Klasa modelujaca stan, w ktorym gra sie rozpoczela
 */
class PlayingState extends GameState {
    final static int TICKS_PER_MOVE = 3;
    private int tickCounter = 0;

    /**
     * Konstruktor klasy PlayingState
     * @param game
     */
    PlayingState(Game game) {
        super(game);
        game.player1.SetActive();
        game.player2.SetActive();
    }
    @Override
    public void onNewGamePressed() {
        game.changeGameState(new LoadingLevelState(game));
    }
    @Override
    public void onGameTimerTick() {
        game.timeRemaining = Math.max(game.timeRemaining - Main.GAME_CLK_TICK, 0);
        if (this.tickCounter >= TICKS_PER_MOVE - 1) {
            game.player1.Move(game.maze);
            game.player2.Move(game.maze);
            this.tickCounter = 0;
        }
        else
            this.tickCounter += 1;

        boolean outOfTime = game.timeRemaining <= 0;
        boolean collisionDetected = PlayerPosition.isCollision(game.player1.getCurrentPosition(), game.player2.getCurrentPosition());

        if (outOfTime)
            game.changeGameState(new GameOverState(game));
        else if (collisionDetected)
            game.changeGameState(new GameWonState(game));
    }

    @Override
    public String getGameInfo() {
        return "Playing";
    }
}

/**
 * Klasa modelujaca stan, w ktorym nie zdolano przejsc poziomu
 */
class GameOverState extends GameState {
    /**
     * Konstruktor klasy GameOverState
     * @param game
     */
    GameOverState(Game game) {
        super(game);
    }
    @Override
    public void onNewGamePressed() {
        game.changeGameState(new LoadingLevelState(game));
    }
    @Override
    public String getGameInfo() {
        return "Game Over";
    }
}

/**
 * Klasa modelujaca stan, w ktorym zdolano przejsc poziom
 */
class GameWonState extends  GameState {
    /**
     * Konstruktor klasy GameWonState
     * @param game
     */
    GameWonState(Game game) {
        super(game);
        if(game.maze.isHighScore(game.timeRemaining))
            game.ui.openPlayerInput(game.timeRemaining);
    }
    @Override
    public void onNewGamePressed() {
        game.maze.setNextLevel();
        game.changeGameState(new LoadingLevelState(game));
    }
    @Override
    public String getGameInfo() {
        return "You Won";
    }
}

/**
 * Klasa modelujaca rozgrywke
 */
public class Game implements ActionListener, KeyEventListener, MenuEventListener {
    /**
     *
     */
    Maze maze;
    private GameState currState;
    /**
     *
     */
    UI ui;
    /**
     *
     */
    Player player1;
    /**
     *
     */
    Player player2;
    /**
     *
     */
    int timeRemaining;

    /**
     * Konstruktor klasy Game
     * @param ui interfejs graficzny
     */
    Game(UI ui){
        this.maze = new Maze();
        this.ui = ui;
        this.ui.setKeyListener(this);
        this.ui.setMenuListener(this);

        this.currState = new LoadingLevelState(this);

    }

    /**
     * Funkcja zmieniajaca stan rozgrywki
     * @param state stan rozgrywki
     */
    public void changeGameState(GameState state) { this.currState = state; }

    @Override
    public void actionPerformed(ActionEvent e){
        this.currState.onGameTimerTick();
        this.ui.updatePlayerPosition(player1.getCurrentPosition(), player2.getCurrentPosition());
        this.ui.setGameInfo(this.currState.getGameInfo());
        this.ui.setTimer(this.timeRemaining);
        this.ui.repaint();
    }

    //region KeyEventListener Implementation
    @Override
    public void upKeyPressed() { player1.setCurrentDirection(MazePanel.Directions.UP); }
    @Override
    public void downKeyPressed() { player1.setCurrentDirection(MazePanel.Directions.DOWN); }
    @Override
    public void leftKeyPressed() { player1.setCurrentDirection(MazePanel.Directions.LEFT); }
    @Override
    public void rightKeyPressed() { player1.setCurrentDirection(MazePanel.Directions.RIGHT); }
    @Override
    public void wKeyPressed() { player2.setCurrentDirection(MazePanel.Directions.UP); }
    @Override
    public void sKeyPressed() { player2.setCurrentDirection(MazePanel.Directions.DOWN); }
    @Override
    public void aKeyPressed() { player2.setCurrentDirection(MazePanel.Directions.LEFT); }
    @Override
    public void dKeyPressed() { player2.setCurrentDirection(MazePanel.Directions.RIGHT); }
    //endregion
    @Override
    public void newGamePressed() { this.currState.onNewGamePressed(); }
    @Override
    public void exitPressed() {
        this.maze.saveMaze();
        System.exit(0);
    }
    @Override
    public void saveScore(String name, int score) {
        this.maze.setNewScore(name, score);
    }
}
