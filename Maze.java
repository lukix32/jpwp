abstract class MazeLevels {
    protected ScoreList highscores;
    protected int mazeLevel;
    public boolean isHighScore(int score) {
        return score > this.highscores.getLast();
    }
    public void setNewScore(String name, int score) {
        Score s = new Score(name, score, this.mazeLevel);
        this.highscores.addScore(s);
    }
    public void saveScore() {
        this.highscores.saveScores();
    }
    public int getTopScore() {
        return this.highscores.getFirst();
    }
    public abstract int[][] getMaze();
    public abstract int getTimeLimit();
    public abstract MazeLevels nextLevel();
}
final class MazeLevelOne extends MazeLevels {
    final static int TIME_LIMIT = 10000;
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

    public MazeLevelOne() {
        this.mazeLevel = 1;
        this.highscores = new ScoreList(this.mazeLevel);
    }

    @Override
    public int[][] getMaze() {
        return MAZE;
    }

    @Override
    public int getTimeLimit() {
        return TIME_LIMIT;
    }

    @Override
    public MazeLevels nextLevel() {
        return new MazeLevelTwo();
    }
}

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

    public MazeLevelTwo() {
        this.mazeLevel = 2;
        this.highscores = new ScoreList(this.mazeLevel);
    }

    @Override
    public int[][] getMaze() {
        return MAZE;
    }

    @Override
    public int getTimeLimit() {
        return TIME_LIMIT;
    }

    @Override
    public MazeLevels nextLevel() {
        return new MazeLevelThree();
    }
}

final class MazeLevelThree extends MazeLevels {
    final static int TIME_LIMIT = 15000;
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

    public MazeLevelThree() {
        this.mazeLevel = 3;
        this.highscores = new ScoreList(this.mazeLevel);
    }

    @Override
    public int[][] getMaze() {
        return MAZE;
    }

    @Override
    public int getTimeLimit() {
        return TIME_LIMIT;
    }

    @Override
    public MazeLevels nextLevel() { return null; }
}
public class Maze {
    private MazeLevels currLevel;
    public Maze() {
        this.currLevel = new MazeLevelOne();
    }
    public int [][] getCurrMaze() {
        return currLevel.getMaze();
    }
    public int getTimeLimit() {
        return currLevel.getTimeLimit();
    }
    public int getTopScore() { return this.currLevel.getTopScore(); }
    public void setNextLevel() {
        MazeLevels nextLevel = this.currLevel.nextLevel();
        if (nextLevel != null)
            this.currLevel = this.currLevel.nextLevel();
    }
    public void saveMaze() {
        this.currLevel.saveScore();
    }
    public void setNewScore(String name, int score) {
        this.currLevel.setNewScore(name, score);
    }
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
    public boolean isHighScore(int score) {
        return this.currLevel.isHighScore(score);
    }
}
