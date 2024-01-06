/**
 * Interfejs przechowujacy funkcje wywolywane przez nacisniecie odpowiednich klawiszy klawiatury
 */
public interface KeyEventListener {
    /**
     * Funkcja wywolywana przy nacisnieciu strzalki w gore
     */
    void upKeyPressed();

    /**
     * Funkcja wywolywana przy nacisnieciu strzalki w dol
     */
    void downKeyPressed();

    /**
     * Funkcja wywolywana przy nacisnieciu strzalki w lewo
     */
    void leftKeyPressed();

    /**
     * Funkcja wywolywana przy nacisnieciu strzalki w prawo
     */
    void rightKeyPressed();

    /**
     * Funkcja wywolywana przy nacisnieciu klawisza W
     */
    void wKeyPressed();

    /**
     * Funkcja wywolywana przy nacisnieciu klawisza S
     */
    void sKeyPressed();

    /**
     * Funkcja wywolywana przy nacisnieciu klawisza A
     */
    void aKeyPressed();

    /**
     * Funkcja wywolywana przy nacisnieciu klawisza D
     */
    void dKeyPressed();
}
