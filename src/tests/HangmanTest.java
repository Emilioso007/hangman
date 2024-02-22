package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import classes.*;

public class HangmanTest {

    final String[] testWords = {"Kitten", "space", "fisk", "mål"};

    @Test
    public void charArraySet(){
        for (String word : testWords){
            final Hangman game = new Hangman(word);

            char[] charArray = game.getGuessedLetters();

            assertEquals(word.length(), charArray.length);
            
            for (char letter : charArray){
                assertSame(null, letter, '_');
            }
        }
    }

    @Test
    public void guessTrue(){
        final Hangman game = new Hangman("kitten");

        assertTrue(game.guess('k'));
    }

    @Test
    public void guessFalse(){
        final Hangman game = new Hangman("kitten");

        assertFalse(game.guess('z'));
    }

    @Test
    public void guessMultiple(){
        final Hangman game = new Hangman("kitten");

        game.guess('t');

        assertTrue(game.getGuessedLetters()[2] == 't');
        assertTrue(game.getGuessedLetters()[3] == 't');
    }

    @Test
    public void guessCaseSensitive(){
        final Hangman game = new Hangman("KITTEN");

        game.guess('n');
        try{
            assertTrue(game.getGuessedLetters()[5] == 'n');
        } catch (java.lang.AssertionError e){
            fail("should be case insensitive");
        }
    }

    @Test
    public void guessCaseSensitive2(){
        final Hangman game = new Hangman("kitten");

        game.guess('N');
        try{
            assertTrue(game.getGuessedLetters()[5] == 'n');
        } catch (java.lang.AssertionError e){
            fail("should be case insensitive");
        }
    }

    @Test
    public void loseAllLives(){
        final Hangman game = new Hangman("kitten");

        assertTrue(game.isAlive());
        game.guess('Z');
        assertTrue(game.isAlive());
        game.guess('p');
        assertTrue(game.isAlive());
        game.guess('q');
        assertTrue(game.isAlive());
        game.guess('O');
        assertTrue(game.isAlive());
        game.guess('æ');
        assertTrue(game.isAlive());
        game.guess('Å');

        assertFalse(game.isAlive());
    }

    //if we guess the same wrong letter twice
    //we should only lose a life once.
    @Test
    public void loseAllLivesRepeatedLetters(){
        final Hangman game = new Hangman("kitten");

        assertTrue(game.isAlive());
        game.guess('Z');
        game.guess('Z');
        assertTrue(game.isAlive());
        game.guess('p');
        game.guess('P');
        assertTrue(game.isAlive());
        game.guess('q');
        game.guess('z');
        assertTrue(game.isAlive());
        game.guess('O');
        assertTrue(game.isAlive());
        game.guess('æ');
        assertTrue(game.isAlive());
        game.guess('Å');

        assertFalse(game.isAlive());
    }

    //We could implement af test
    //that makes sure we dont lose a life, when
    //guesing a letter that's in the array which
    //we have already guessed.
    
    //corrently our implementation, already behaves
    //in this way (I think)...
    //what would be the reasoning behind implementing 
    //such a test?

    @Test
    public void onlyLetterslooseLives(){
        final Hangman game = new Hangman("qwertyuiopåasdfghjklæøzxcvbnm");

        //OBS haven't tested if the test works as 
        //intended, don't assume it just works...
        
        //we check all unicode symbols (16 bits)
        assertTrue(game.isAlive());
        for (int i = 0; i < 65536; i++){
            game.guess((char)i);
        }
        
        assertTrue(game.isWon());
    }

    //implement a test which test if we can win
    @Test
    public void win(){
        final Hangman game = new Hangman("kitten");

        game.guess('k');
        game.guess('i');
        game.guess('t');
        game.guess('e');
        game.guess('n');

        assertTrue(game.isAlive());
        assertTrue(game.isWon());
    }

}
