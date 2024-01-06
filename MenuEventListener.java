/**
 * Interfejs przechowujacy funkcje wywolywane przy nacisnieciu guzikow panelu bocznego
 */
public interface MenuEventListener {
    /**
     * Funkcja wywolywana przy nacisnieciu guzika New Game
     */
    void newGamePressed();

    /**
     * Funkcja wywolywana przy nacisnieciu guzika Exit
     */
    void exitPressed();

    /**
     * Funkcja wywolywana przy nacisnieciu guzika Save
     * @param name nazwa gracza
     * @param score wynik
     */
    void saveScore(String name, int score);
}
