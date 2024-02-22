package classes;

import java.util.ArrayList;
import java.util.HashSet;

import processing.core.PApplet;
import processing.core.PConstants;

public class Hangman {
    private String secretWord, realWord;
    private char[] guessedLetters;

    public String[] words;

    private int lives = 6;

    private HashSet<Character> validCharacters = new HashSet<Character>();

    private HashSet<Character> guessedLettersSet = new HashSet<Character>();

    private final int PLAYING = 0;
    private final int WON = 1;
    private final int LOST = 2;

    private int currentState = PLAYING;

    public Hangman(String word) {
        realWord = word;
        secretWord = word.toLowerCase();
        guessedLetters = new char[secretWord.length()];

        // init partial word, and set all
        // letters to '_'
        guessedLetters = new char[word.length()];
        for (int i = 0; i < guessedLetters.length; i++) {
            guessedLetters[i] = '_';
        }

        // add all valid characters to the set
        for (char c = 'a'; c <= 'z'; c++) {
            validCharacters.add(c);
        }
        validCharacters.add('æ');
        validCharacters.add('ø');
        validCharacters.add('å');

    }

    public char[] getGuessedLetters() {
        return guessedLetters;
    }

    public boolean guess(char letter) {

        if (currentState != PLAYING) {
            return false;
        }

        char lowerCase = Character.toLowerCase(letter);

        if (!validCharacters.contains(lowerCase)) {
            return false;
        }

        if (guessedLettersSet.contains(lowerCase)) {
            return false;
        } else {
            guessedLettersSet.add(lowerCase);
        }

        boolean letterInSecretWord = false;
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == lowerCase) {
                guessedLetters[i] = lowerCase;
                letterInSecretWord = true;
            }
        }

        if (!letterInSecretWord) {
            lives--;
        }

        return letterInSecretWord;
    }

    public void drawGuessedLetters(PApplet p) {
        // draw word on screen
        String wordToDisplay = "";
        for (int i = 0; i < realWord.length(); i++) {
            if (guessedLetters[i] != '_') {
                wordToDisplay += realWord.charAt(i);
            } else {
                wordToDisplay += "_";

            }
        }

        wordToDisplay += " [" + realWord.length() + "]";

        p.fill(255);
        p.textSize(32);
        p.textAlign(PConstants.CENTER, PConstants.CENTER);
        p.text(wordToDisplay, p.width / 2, 60);
    }

    public void drawWrongLetters(PApplet p) {
        // draw wrong letters on screen
        String wrongLetters = "";
        for (char letter : guessedLettersSet) {
            if (secretWord.indexOf(letter) == -1) {
                wrongLetters += letter + " ";
            }
        }
        p.fill(255);
        p.textSize(32);
        p.textAlign(PConstants.CENTER, PConstants.CENTER);
        p.text(wrongLetters, p.width / 2, p.height - 60);
    }

    public void drawMan(PApplet p) {

        p.pushMatrix();

        p.translate(p.width / 2, p.height / 2);
        p.stroke(255);

        // draw the gallows
        p.line(-50, 100, 50, 100);
        p.line(0, 100, 0, -100);
        p.line(0, -100, 50, -100);

        p.translate(50, 0);

        // draw the head
        if (lives < 6) {
            p.fill(255);
            p.noStroke();
            p.ellipse(0, -80, 40, 40);
        }

        p.stroke(255);
        p.strokeWeight(5);

        // draw the body
        if (lives < 5) {
            p.line(0, -60, 0, 20);
        }

        // draw the left arm
        if (lives < 4) {
            p.line(0, -45, -20, -30);
        }

        // draw the right arm
        if (lives < 3) {
            p.line(0, -45, 20, -30);
        }

        // draw the left leg
        if (lives < 2) {
            p.line(0, 20, -20, 40);
        }

        // draw the right leg
        if (lives < 1) {
            p.line(0, 20, 20, 40);
        }

        // draw the dead eyes
        if (lives < 1) {
            p.fill(0);
            p.textSize(32);
            p.textAlign(PConstants.CENTER, PConstants.CENTER);
            p.text("x x", -2, -84);
        }

        p.popMatrix();

    }

    public void render(PApplet p) {

        if (isWon()) {
            currentState = WON;
        } else if (!isAlive()) {
            currentState = LOST;
        }

        drawMan(p);
        drawGuessedLetters(p);
        drawWrongLetters(p);

        if (currentState == WON) {
            p.fill(0, 255, 0);
            p.textSize(64);
            p.textAlign(PConstants.CENTER, PConstants.CENTER);
            p.text("You won!", p.width / 2, p.height / 2);
        } else if (currentState == LOST) {
            p.fill(255, 0, 0);
            p.textSize(64);
            p.textAlign(PConstants.CENTER, PConstants.CENTER);
            p.text("You lost!", p.width / 2, p.height / 2);

        }

        if (currentState != PLAYING) {
            p.fill(0, 64);
            p.noStroke();
            p.rect(0, 0, p.width, p.height);
            p.textSize(32);
            if (currentState == WON) {
                p.fill(0, 255, 0);
                p.text("Click anywhere to restart!", p.width / 2, p.height / 2 + 100);
            } else if (currentState == LOST) {
                p.fill(255, 0, 0);
                p.text("The word was: " + realWord, p.width / 2, p.height / 2 + 100);
                p.text("Click anywhere to restart!", p.width / 2, p.height / 2 + 150);
            }
            if (p.mousePressed || (p.key == ' ')) {
                currentState = PLAYING;
                lives = 6;
                realWord = words[(int) p.random(words.length)];
                String temp = realWord;
                secretWord = temp.toLowerCase();
                guessedLettersSet.clear();
                guessedLetters = new char[secretWord.length()];
                for (int i = 0; i < guessedLetters.length; i++) {
                    guessedLetters[i] = '_';
                }
            }
        }

    }

    public boolean isAlive() {
        return lives > 0;
    }

    public int getLives() {
        return lives;
    }

    public boolean isWon() {
        for (char letter : guessedLetters) {
            if (letter == '_') {
                return false;
            }
        }
        return true;
    }
}
