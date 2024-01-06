import java.io.PrintWriter;
import java.util.*;
import java.io.File;
import java.util.stream.Collectors;

/**
 * Klasa modelujaca wynik gracza uzyskany w rozgrywce
 */
class Score implements Comparable<Score> {
    private String playerName;
    private int timeRemaining;
    private int gameLevel;

    /**
     * Konstruktor klasy Score
     * @param name nazwa gracza
     * @param score wynik gracza
     * @param level poziom gry
     */
    public Score(String name, int score, int level) {
        this.playerName = name;
        this.timeRemaining = score;
        this.gameLevel = level;
    }

    /**
     * Konstruktor klasy Score kreujacy gracza na podstawie wyniku z pliku .csv
     * @param record wynik gracza
     */
    public Score(List<String> record) {
        // TODO: Exception handling
        this.playerName = record.getFirst();
        this.timeRemaining = Integer.parseInt(record.get(1));
        this.gameLevel = Integer.parseInt(record.getLast());
    }

    /**
     *
     * @return
     */
    public int getTimeRemaining() { return this.timeRemaining; }

    /**
     *
     * @return
     */
    public int getGameLevel() { return this.gameLevel; }
    public String getPlayerName() { return this.playerName; }

    /**
     *
     * @param o porownywany obiekt
     * @return
     */
    @Override
    public int compareTo(Score o) {
        if (this.timeRemaining > o.getTimeRemaining())
            return 1;
        else if (this.timeRemaining < o.getTimeRemaining())
            return -1;
        return 0;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.playerName + "," + this.timeRemaining + "," + this.gameLevel;
    }
}

/**
 * Klasa modelujaca liste rekordow dla poziomu
 */
public class ScoreList {
    private static final String CSV_PATH = "scores.csv";
    private final int level;
    private List<Score> scores;

    /**
     * Konstruktor klasy ScoreList
     * @param level poziom gry
     */
    public ScoreList(int level) {
        this.level = level;
        this.scores = new ArrayList<>();
        this.readScores();
    }

    /**
     * Funkcja, ktora dodaje wynik do listy rekordow przechowujacej 5 najlepszych wynikow
     * @param s wynik uzyskany za dany poziom
     */
    public void addScore(Score s) {
        this.scores.add(s);
        Collections.sort(this.scores, Collections.reverseOrder());
        if (this.scores.size() > 5)
            this.scores = this.scores.subList(0, 5);
    }

    /**
     * Funkcja zapisujaca wyniki w pliku .csv
     */
    public void saveScores() {
        List<String> dataLower = new ArrayList<>();
        List<String> dataUpper = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(CSV_PATH))) {
            while (scanner.hasNextLine()) {
                Score s = new Score(ScoreList.getScoreFromLine(scanner.nextLine()));
                if (s.getGameLevel() < this.level)
                    dataLower.add(s.toString());
                else if (s.getGameLevel() > this.level)
                    dataUpper.add(s.toString());
            }
            dataLower.add(this.toString());
            dataLower.addAll(dataUpper);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        File f = new File(CSV_PATH);
        try (PrintWriter pw = new PrintWriter(f)) {
            dataLower.forEach(pw::println);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Funkcja zwracajaca ostatni wynik z listy
     * @return ostatni wynik z listy
     */
    public int getLast() {
        return this.scores.getLast().getTimeRemaining();
    }

    /**
     * Funkcja zwracajaca pierwszy wynik z listy
     * @return
     */
    public int getFirst() {
        return this.scores.getFirst().getTimeRemaining();
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.scores.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

    private void readScores() {
        try (Scanner scanner = new Scanner(new File(CSV_PATH))) {
            while (scanner.hasNextLine()) {
                Score s = new Score(ScoreList.getScoreFromLine(scanner.nextLine()));
                if (s.getGameLevel() == this.level)
                    this.addScore(s);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    private static List<String> getScoreFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

}
