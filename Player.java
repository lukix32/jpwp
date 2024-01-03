class PlayerPosition {
    private int column;
    private int row;
    PlayerPosition(int initialColumn, int initialRow) {
        this.column = initialColumn;
        this.row = initialRow;
    }
    public int getCurrColumn() { return this.column; }
    public int getCurrRow() { return this.row; }
    public void setPlayerPosition(int column, int row) {
        this.column = column;
        this.row = row;
    }
    public static boolean isCollision(PlayerPosition p1, PlayerPosition p2) {
        return p1.column == p2.column && p1.row == p2.row;
    }
}

class Player {
    private final PlayerPosition position;
    private boolean isPlaying = false;
    private MazePanel.Directions currentDirection = MazePanel.Directions.NONE;
    Player(int initialColumn, int initialRow){
        position = new PlayerPosition(initialColumn, initialRow);
    }

    public PlayerPosition getCurrentPosition() {
        return this.position;
    }

    public void setCurrentDirection(MazePanel.Directions direction) {
        if (this.isPlaying)
            this.currentDirection = direction;
    }

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
    public void SetActive() {
        this.isPlaying = true;
    }
}
