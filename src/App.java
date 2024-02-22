import processing.core.*;

import java.util.HashSet;

import classes.*;

public class App extends PApplet {

    private static String[] words = { "Hund", "Kat", "Fugl", "Fisk", "Hest", "Mus",
            "Elefant", "Giraf", "Krokodille", "Flodhest", "Pingvin", "Papegøje", "Kænguru", 
            "Kamel", "Zebra", "Løve", "Tiger", "Bjørn", "Ulv", "Ræv", "Hjort", "Elg", "Gazelle", 
            "Pikachu", "Charizard", "Bulbasaur", "Squirtle", "Jigglypuff", "Mewtwo", "Eevee", 
            "Snorlax", "Gyarados", "Dragonite", "Mew", "Gengar", "Arcanine", "Lapras", "Vaporeon", 
            "Flareon", "Jolteon", "Articuno", "Zapdos", "Moltres", "Raichu", "Golem", "Alakazam", 
            "Machamp", "Gyarados", "Lugia", "Tyranitar", "Sceptile", "Blaziken", "Swampert",
            "Næsehorn", "Flagermus", "Panda", "Koala" };

    Hangman game;

    public static void main(String[] args) {
        String[] processingArgs = { "MySketch" };
        App mySketch = new App();
        PApplet.runSketch(processingArgs, mySketch);
    }

    public void settings() {
        size(600, 400);
    }

    public void setup() {
        game = new Hangman(words[(int) random(words.length)]);
        game.words = words;
    }

    public void draw() {
        background(42);
        game.render(this);
    }

    public void keyPressed() {
        if (key != CODED) {
            game.guess(key);
        }
    }
}
