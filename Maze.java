/**
 * Klasa modelujaca poziomy rozgrywki
 */
abstract class MazeLevels {
    protected ScoreList highscores;
    protected int mazeLevel;

    /**
     * Funkcja sprawdzajaca czy dany wynik jest jednym z 5 dotychczasowych najlepszych wynikow
     * @param score wynik
     * @return prawda jezeli wynik jest jednym z top 5 dotychczasowych wynikow, w przeciwynik wypadku falsz
     */
    public boolean isHighScore(int score) {
        return score > this.highscores.getLast();
    }

    /**
     * Funkcja dodajaca nowy wynik
     * @param name nazwa gracza
     * @param score wynik
     */
    public void setNewScore(String name, int score) {
        Score s = new Score(name, score, this.mazeLevel);
        this.highscores.addScore(s);
    }

    /**
     * Funkcja zapisujaca wynik
     */
    public void saveScore() {
        this.highscores.saveScores();
    }

    /**
     * Funkcja zwracajaca najlepszy dotychczasowy wynik
     * @return najlepszy dotychczasowy wynik
     */
    public int getTopScore() {
        return this.highscores.getFirst();
    }

    /**
     * Funkcja zwracajaca plansze do gry
     * @return
     */
    public abstract int[][] getMaze();

    /**
     *
     * @return
     */
    public abstract int getTimeLimit();

    /**
     * Funkcja zmieniajaca poziom gry
     * @return
     */
    public abstract MazeLevels nextLevel();
}

/**
 * Klasa modelujaca pierwszy poziom gry
 */
final class MazeLevelOne extends MazeLevels {
    final static int TIME_LIMIT = 5000;
    final static int[][] MAZE ={
            {1,1,1,1,1,1,1,1,0,0},
            {0,1,0,0,1,0,0,1,0,0},
            {0,1,0,0,1,0,0,1,0,0},
            {0,1,1,0,1,1,1,1,0,0},
            {0,0,1,0,0,1,0,1,1,1},
            {0,0,1,1,1,1,0,0,1,0},
            {0,0,1,0,0,1,0,0,1,0},
            {0,1,1,0,0,1,0,0,1,0},
            {0,1,0,0,0,1,1,0,1,0},
            {0,1,1,1,1,1,1,1,1,1}};

    /**
     * Konstruktor klasy MazeLevelOne
     */
    public MazeLevelOne() {
        this.mazeLevel = 1;
        this.highscores = new ScoreList(this.mazeLevel);
    }

    /**
     *
     * @return
     */
    @Override
    public int[][] getMaze() {
        return MAZE;
    }

    /**
     *
     * @return
     */
    @Override
    public int getTimeLimit() {
        return TIME_LIMIT;
    }

    /**
     *
     * @return
     */
    @Override
    public MazeLevels nextLevel() {
        return new MazeLevelTwo();
    }
}

/**
 * Klasa modelujaca drugi poziom gry
 */
final class MazeLevelTwo extends MazeLevels {
    final static int TIME_LIMIT = 15000;
    final static int[][] MAZE ={
            {1,1,1,1,1,1,1,1,0,0,0,0,0,0,0},
            {0,1,0,0,1,0,0,1,0,1,1,1,1,1,1},
            {0,1,0,0,1,0,0,1,0,0,0,1,0,0,1},
            {0,1,1,0,1,1,1,1,0,0,0,1,0,0,1},
            {0,0,1,0,0,1,0,1,1,1,1,1,1,0,1},
            {0,0,1,1,1,1,0,0,1,0,0,0,0,0,1},
            {0,0,1,0,0,1,0,0,1,1,1,1,1,1,1},
            {0,1,1,0,0,1,0,0,1,0,0,1,0,0,0},
            {0,1,0,0,0,1,1,1,1,0,1,1,1,1,1},
            {0,1,1,1,1,1,0,1,0,0,1,0,0,0,1},
            {0,0,0,0,0,1,0,1,0,1,1,0,1,1,1},
            {0,0,1,1,1,1,1,1,1,1,0,0,1,0,0},
            {0,1,1,0,0,0,1,0,1,0,0,0,1,0,1},
            {0,1,0,0,1,0,1,0,1,0,1,1,1,0,1},
            {0,1,1,1,1,1,1,0,1,1,1,0,1,1,1}};

    /**
     * Konstruktor klasy MazeLevelTwo
     */
    public MazeLevelTwo() {
        this.mazeLevel = 2;
        this.highscores = new ScoreList(this.mazeLevel);
    }

    /**
     *
     * @return
     */
    @Override
    public int[][] getMaze() {
        return MAZE;
    }

    /**
     *
     * @return
     */
    @Override
    public int getTimeLimit() {
        return TIME_LIMIT;
    }

    /**
     *
     * @return
     */
    @Override
    public MazeLevels nextLevel() {
        return new MazeLevelThree();
    }
}

/**
 * Klasa modelujaca trzeci poziom gry
 */
final class MazeLevelThree extends MazeLevels {
    final static int TIME_LIMIT = 10000;
    final static int[][] MAZE ={
            {1,1,1,0,1,1,1,1,0,1,1,0,1,1,1},
            {1,0,1,0,1,0,0,1,0,1,0,0,1,0,1},
            {1,0,1,1,1,0,0,1,1,1,0,1,0,0,1},
            {0,0,1,0,1,1,1,1,0,0,0,1,0,0,1},
            {1,1,1,0,0,1,0,1,1,1,1,1,1,0,1},
            {1,0,1,1,1,1,1,0,1,0,0,0,0,0,1},
            {1,0,0,0,0,1,0,1,1,1,1,1,1,1,1},
            {1,1,1,1,0,1,1,0,1,0,0,1,0,0,0},
            {0,1,0,1,0,1,0,1,1,0,1,1,1,1,0},
            {1,1,1,1,1,0,0,0,0,1,1,0,1,0,0},
            {1,0,0,0,0,1,0,1,0,1,0,0,1,1,1},
            {1,0,1,1,1,1,1,1,1,1,0,0,0,0,1},
            {1,1,1,0,0,0,1,0,1,0,1,1,1,1,1},
            {0,1,0,1,1,1,1,0,1,0,1,0,1,0,0},
            {0,1,1,1,0,0,1,0,1,1,1,0,1,1,1}};

    /**
     * Konstruktor klasy MazeLevelThree
     */
    public MazeLevelThree() {
        this.mazeLevel = 3;
        this.highscores = new ScoreList(this.mazeLevel);
    }

    /**
     *
     * @return
     */
    @Override
    public int[][] getMaze() {
        return MAZE;
    }

    /**
     *
     * @return
     */
    @Override
    public int getTimeLimit() {
        return TIME_LIMIT;
    }

    /**
     *
     * @return
     */
    @Override
    public MazeLevels nextLevel() { return null; }
}

/**
 * Klasa modelujaca rozgrywke
 */
public class Maze {
    private MazeLevels currLevel;

    /**
     * Konstruktor klasy Maze
     */
    public Maze() {
        this.currLevel = new MazeLevelOne();
    }

    /**
     *
     * @return
     */
    public int [][] getCurrMaze() {
        return currLevel.getMaze();
    }

    /**
     *
     * @return
     */
    public int getTimeLimit() {
        return currLevel.getTimeLimit();
    }

    /**
     * Funkcja zwraca najlepszy wynik dla danego poziomu
     * @return
     */
    public int getTopScore() { return this.currLevel.getTopScore(); }

    /**
     * Funkcja ustanwia nastepny poziom
     */
    public void setNextLevel() {
        MazeLevels nextLevel = this.currLevel.nextLevel();
        if (nextLevel != null)
            this.currLevel = this.currLevel.nextLevel();
    }

    /**
     * Funkcja zapisuje aktualny wynik
     */
    public void saveMaze() {
        this.currLevel.saveScore();
    }

    /**
     * Funkcja ustawia nowy wynik
     * @param name nazwa gracza
     * @param score wynik
     */
    public void setNewScore(String name, int score) {
        this.currLevel.setNewScore(name, score);
        this.saveMaze();
    }

    /**
     * Funkcja sprawdzajaca czy zadany przez gracza ruch moze zostac wykonany
     * @param targetColumn docelowa kolumna
     * @param targetRow docelowy wiersz
     * @return prawda, jezeli ruch moze zostac wykonany, w przeciwnym wypadku falsz
     */
    public boolean isMoveValid(int targetColumn, int targetRow) {
        int mazeSize = this.currLevel.getMaze().length - 1;

        boolean outOfBounds = targetColumn < 0 ||
                targetRow < 0 ||
                targetColumn > mazeSize ||
                targetRow > mazeSize;

        if (outOfBounds)
            return false;

        boolean wallDetected = this.currLevel.getMaze()[targetColumn][targetRow] == 0;

        return !wallDetected;
    }

    /**
     * Funkcja sprawdzajaca czy dany wynik znajduje sie w top 5 dotychczasowych wynikow danego poziomu
     * @param score wynik
     * @return prawda, jezeli dany wynik znajduje sie w top 5 dotychczasowych wynikow danego poziomu, w przeciwnym wypadku falsz
     */
    public boolean isHighScore(int score) {
        return this.currLevel.isHighScore(score);
    }
}
