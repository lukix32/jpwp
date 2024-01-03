import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;


public class Main			// The recursive 2D maze program where the output is done graphically
{
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Game game = new Game();
        frame.setSize(1280, 1024);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game.GetMaze());

        frame.setVisible(true);
    }


}

class Game implements ActionListener, KeyEventListener {
    Player player1;
    Player player2;
    MazePanel maze;
    Timer timer;

    Game(){
        player1 = new Player(9, 9);
        player2 = new Player(0, 0);
        maze = new MazePanel();
        timer = new Timer(300, this);
        timer.start();
        maze.setKeyListener(this);

    }
    @Override
    public void actionPerformed(ActionEvent e){
        OnTimerTick();
    }
    public void OnTimerTick(){
        player1.Move(this.maze);
        maze.setPlayer1Position(player1.currentColumn, player1.currentRow);
        player2.Move(this.maze);
        maze.setPlayer2Position(player2.currentColumn, player2.currentRow);
    }
    public MazePanel GetMaze(){
        return this.maze;
    }

    @Override
    public void UpKeyPressed() {
        player1.currentDirection = MazePanel.Directions.UP;
    }
    @Override
    public void DownKeyPressed(){
        player1.currentDirection = MazePanel.Directions.DOWN;
    }
    @Override
    public void LeftKeyPressed(){
        player1.currentDirection = MazePanel.Directions.LEFT;
    }
    @Override
    public void RightKeyPressed(){
        player1.currentDirection = MazePanel.Directions.RIGHT;
    }
    @Override
    public void WKeyPressed() {
        player2.currentDirection = MazePanel.Directions.UP;
    }
    @Override
    public void SKeyPressed(){
        player2.currentDirection = MazePanel.Directions.DOWN;
    }
    @Override
    public void AKeyPressed(){
        player2.currentDirection = MazePanel.Directions.LEFT;
    }
    @Override
    public void DKeyPressed(){
        player2.currentDirection = MazePanel.Directions.RIGHT;
    }
}

class Player {
    int currentColumn;
    int currentRow;
    MazePanel.Directions currentDirection = MazePanel.Directions.NONE;
    Player(int initialColumn, int initialRow){
        this.currentColumn = initialColumn;
        this.currentRow = initialRow;
    }
    public void Move(MazePanel maze){
        int newColumn = this.currentColumn;
        int newRow = this.currentRow;
        switch (this.currentDirection) {
            case UP: {newRow -= 1; break;}
            case DOWN: {newRow += 1; break;}
            case LEFT: {newColumn -= 1; break;}
            case RIGHT: {newColumn += 1; break;}
            case NONE: {break;}
        }
        if (maze.IsMoveValid(newColumn, newRow)){
            this.currentColumn = newColumn;
            this.currentRow = newRow;
        }
        maze.repaint();
    }

}



class MazePanel extends JPanel {

    int player1Column = 9;
    int player1Row = 9;
    int player2Column = 0;
    int player2Row = 0;
    private KeyEventListener keyListener;
    private int[][] maze={							// initialize the maze 2D array
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
    private int size, start, end;				// start and end are used in paintComponent to denote the starting point in a different color
    private boolean gameActive = false;
    enum Directions {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        NONE
    }
    public MazePanel() {
        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (!gameActive){
                    gameActive = true;
                }
                int key = ke.getKeyCode();
                if (key == KeyEvent.VK_LEFT) {
                    keyListener.LeftKeyPressed();
                }
                if (key == KeyEvent.VK_RIGHT) {
                    keyListener.RightKeyPressed();
                }
                if (key == KeyEvent.VK_UP) {
                    keyListener.UpKeyPressed();
                }
                if (key == KeyEvent.VK_DOWN) {
                    keyListener.DownKeyPressed();
                }
                if (key == KeyEvent.VK_A) {
                    keyListener.AKeyPressed();
                }
                if (key == KeyEvent.VK_D) {
                    keyListener.DKeyPressed();
                }
                if (key == KeyEvent.VK_W) {
                    keyListener.WKeyPressed();
                }
                if (key == KeyEvent.VK_S) {
                    keyListener.SKeyPressed();
                }
            }
        });

        size = 10;
        start = 0;
        end = 0;
        repaint();
    }
    public void setKeyListener(KeyEventListener listener){
        this.keyListener = listener;
    }

    public boolean IsMoveValid(int targetColumn, int targetRow){
        if (targetColumn < 0 || targetRow < 0 || targetColumn > 9 || targetRow > 9)
            return false;
        if (maze[targetColumn][targetRow] == 0)
            return false;

        return true;
    }
    public void setPlayer1Position(int column, int row){
        this.player1Column = column;
        this.player1Row = row;
    }
    public void setPlayer2Position(int column, int row){
        this.player2Column = column;
        this.player2Row = row;
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        for(int j=0;j<size;j++)
        {
            for(int i=0;i<size;i++)
                {
                    if (player1Column == i && player1Row == j) g.setColor(Color.yellow);
                    else if (player2Column == i && player2Row == j) g.setColor(Color.blue);
                    else if(maze[i][j]==1) g.setColor(Color.white);
                    else if(maze[i][j]==0) g.setColor(Color.black);
                    else g.setColor(Color.pink);
                    g.fillRect(i*40,j*40,40,40);
                }
        }
    }
}


