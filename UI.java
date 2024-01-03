import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UI extends JFrame {
    private final ControlPanel menu = new ControlPanel();
    private final MazePanel maze = new MazePanel();
    public UI() {
        super();
        this.setSize(1280, 1024);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        this.add(this.maze, BorderLayout.CENTER);
        this.add(this.menu, BorderLayout.EAST);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public void setKeyListener(KeyEventListener listener) { this.maze.setKeyListener(listener); }
    public void setMenuListener(MenuEventListener listener) { this.menu.setMenuListener(listener); }
    public void setTimer(int time) { this.menu.SetTimer(time); }
    public void setGameInfo(String info) { this.menu.SetInfo(info); }
    public void setTopScorer(int s) { this.menu.setTopScorer(s); }
    public void loadMaze(int[][] maze) { this.maze.loadMap(maze); }
    public void updatePlayerPosition(PlayerPosition player1, PlayerPosition player2) {
        this.maze.setPlayer1Position(player1.getCurrColumn(), player1.getCurrRow());
        this.maze.setPlayer2Position(player2.getCurrColumn(), player2.getCurrRow());
    }
    public void openPlayerInput(int score) {
        ScoreInput s = new ScoreInput(this.menu, score);
        s.setLocationRelativeTo(null);
        s.setVisible(true);
    }
}

class ControlPanel extends JPanel {
    private MenuEventListener menuListener;
    JLabel timer = new JLabel("--.--");
    JLabel info = new JLabel("Playing");
    JLabel topScorer = new JLabel("");
    JButton startBtn = new JButton("New Game");
    JButton menuBtn = new JButton("Menu");
    ControlPanel() {
        startBtn.setFocusable(false);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(info);
        this.add(timer);
        this.add(topScorer);
        this.add(startBtn);
        this.add(menuBtn);

        this.startBtn.addActionListener(e -> this.menuListener.newGamePressed());
        this.menuBtn.addActionListener(e -> {
            MenuDialog dialog = new MenuDialog(this);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });
    }

    public void SetTimer(int time) {
        double seconds = time / 1000.0;
        String timer = String.format("%2.1f s", seconds);
        this.timer.setText(timer);
    }

    public void SetInfo(String info) {
        this.info.setText(info);
    }
    public void setTopScorer(int score) {
        String scorer = "Best: " + score;
        this.topScorer.setText(scorer);
    }

    public void setMenuListener(MenuEventListener listener) {
        this.menuListener = listener;
    }
    public void closeUI() { this.menuListener.exitPressed(); }
    public void saveScore(String name, int score) { this.menuListener.saveScore(name, score); }
}
class MazePanel extends JPanel {

    int player1Column;
    int player1Row;
    int player2Column = 0;
    int player2Row = 0;
    private KeyEventListener keyListener;
    private int[][] maze;
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
            public void keyPressed(KeyEvent key) {
                switch (key.getKeyCode()) {
                    case KeyEvent.VK_LEFT: { keyListener.leftKeyPressed(); break; }
                    case KeyEvent.VK_RIGHT: { keyListener.rightKeyPressed(); break; }
                    case KeyEvent.VK_UP: { keyListener.upKeyPressed(); break; }
                    case KeyEvent.VK_DOWN: { keyListener.downKeyPressed(); break; }
                    case KeyEvent.VK_A: { keyListener.aKeyPressed(); break; }
                    case KeyEvent.VK_D: { keyListener.dKeyPressed(); break; }
                    case KeyEvent.VK_W: { keyListener.wKeyPressed(); break; }
                    case KeyEvent.VK_S: { keyListener.sKeyPressed(); break; }
                    default: { break; }
                }
            }
        });

        repaint();
    }
    public void setKeyListener(KeyEventListener listener){
        this.keyListener = listener;
    }

    public void setPlayer1Position(int column, int row){
        this.player1Column = column;
        this.player1Row = row;
    }
    public void setPlayer2Position(int column, int row){
        this.player2Column = column;
        this.player2Row = row;
    }

    public void loadMap(int[][] map) {
        this.maze = map;
        this.player1Column = this.maze.length - 1;
        this.player1Row = this.maze.length - 1;
        this.repaint();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (maze == null)
            return;

        for(int j=0;j<maze.length;j++)
        {
            for(int i=0;i<maze.length;i++)
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

class MenuDialog extends JDialog {
    public MenuDialog(ControlPanel p) {
        super();
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setSize(100, 100);
        this.setUndecorated(true);
        this.getContentPane().setBackground(Color.GRAY);

        JButton highscoresBtn = new JButton("Highscores");
        highscoresBtn.addActionListener(e -> {
            HighScoresDialog hs = new HighScoresDialog();
            hs.setLocationRelativeTo(null);
            hs.setVisible(true);
        });
        this.add(highscoresBtn);

        JButton returnBtn = new JButton("Return");
        returnBtn.addActionListener(e -> this.dispose());
        this.add(returnBtn);

        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(e -> p.closeUI());
        this.add(exitBtn);
    }
}

class ScoreInput extends JDialog {

    public ScoreInput(ControlPanel c, int score) {
        super();
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setSize(200, 200);
        this.setUndecorated(true);
        this.getContentPane().setBackground(Color.YELLOW);

        JTextField txt = new JTextField();
        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> {
            c.saveScore(txt.getText(), score);
            this.dispose();
        });

        this.add(txt);
        this.add(saveBtn);
    }
}

class HighScoresDialog extends JDialog {
    public HighScoresDialog() {
        super();
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setSize(200, 200);
        this.setUndecorated(true);
        this.getContentPane().setBackground(Color.RED);
    }
}