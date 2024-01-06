/**
 * Klasa modelujaca pozycje gracza na planszy
 */
class PlayerPosition {
    private int column;
    private int row;

    /**
     * Konstruktor klasy PlayerPosition
     * @param initialColumn numer kolumny startowej gracza
     * @param initialRow numer wiersza startowego gracza
     */
    PlayerPosition(int initialColumn, int initialRow) {
        this.column = initialColumn;
        this.row = initialRow;
    }

    /**
     *
     * @return
     */
    public int getCurrColumn() { return this.column; }

    /**
     *
     * @return
     */
    public int getCurrRow() { return this.row; }

    /**
     * Funkcja ustawiajaca pozycje gracza
     * @param column numer kolumny
     * @param row numer wiersza
     */
    public void setPlayerPosition(int column, int row) {
        this.column = column;
        this.row = row;
    }

    /**
     * Funkcja wykrywajaca kolizje pomiedzy graczami
     * @param p1 pozycja gracza nr 1
     * @param p2 pozycja gracza nr 2
     * @return prawda w przypadku kolizji dwoch graczy, falsz w przeciwnym wypadku
     */
    public static boolean isCollision(PlayerPosition p1, PlayerPosition p2) {
        return p1.column == p2.column && p1.row == p2.row;
    }
}

/**
 * Klasa modelujaca zachowanie gracza
 */
public class Player {
    private final PlayerPosition position;
    private boolean isPlaying = false;
    private MazePanel.Directions currentDirection = MazePanel.Directions.NONE;

    /**
     * Konstruktor klasy Player
     * @param initialColumn numer kolumny startowej
     * @param initialRow numer wiersza startowego
     */
    Player(int initialColumn, int initialRow){
        position = new PlayerPosition(initialColumn, initialRow);
    }

    /**
     *
     * @return
     */
    public PlayerPosition getCurrentPosition() {
        return this.position;
    }

    /**
     *
     * @param direction
     */
    public void setCurrentDirection(MazePanel.Directions direction) {
        if (this.isPlaying)
            this.currentDirection = direction;
    }

    /**
     * Funkcja przesuwajaca gracza na planszy
     * @param maze Plansza, po ktorej porusza sie gracz
     */
    public void Move(Maze maze){
        int newColumn = this.position.getCurrColumn();
        int newRow = this.position.getCurrRow();
        switch (this.currentDirection) {
            case UP: { newRow -= 1; break; }
            case DOWN: { newRow += 1; break; }
            case LEFT: { newColumn -= 1; break; }
            case RIGHT: { newColumn += 1; break; }
            case NONE: { break; }
        }
        if (maze.isMoveValid(newColumn, newRow))
            this.position.setPlayerPosition(newColumn, newRow);
    }

    /**
     * Funkcja inicjalizujaca ruch gracza
     */
    public void SetActive() {
        this.isPlaying = true;
    }
}
