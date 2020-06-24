import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Hangman {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        //Random random = new Random();
        String guesses = readFromFile();
        long start = System.currentTimeMillis();




        boolean Playing = true;
        while (Playing) {

            System.out.println("Welcome to Hangman!");
            char[] RandomWordToGuess = guesses.toUpperCase().toCharArray();
            int AmountOfGuesses = guesses.length();
            String[] countryAndCapitalList = guesses.split("[ | ]+");
            String country = countryAndCapitalList[0].strip().toUpperCase();
            String capitalToGuess = countryAndCapitalList[1].strip().toUpperCase();
            char[] PlayerGuess = new char[AmountOfGuesses];
            for (int i = 0; i < capitalToGuess.length(); i++) {
                PlayerGuess[i] = '_';
                AmountOfGuesses += 1;
            }

            boolean WordIsGuessed = false;
            int tries = 0;

            while (!WordIsGuessed && tries != AmountOfGuesses) {
                System.out.print("Current guesses: ");
                printArray(PlayerGuess);
                System.out.printf("You have %d tries left. \n", AmountOfGuesses - tries);
                System.out.println("Enter a single character");
                char input = scanner.nextLine().toUpperCase().charAt(0); // Taking first input letter
                tries++;

                if (input == '-') {
                    Playing = false;
                    WordIsGuessed = true;
                } else {
                    for (int i = 0; i < capitalToGuess.length(); i++) {
                        if (RandomWordToGuess[i] == input)  {
                            PlayerGuess[i] = input;
                        }
                    }

                    if (IsTheWordGuessed(PlayerGuess))  {
                        WordIsGuessed = true;
                        System.out.println("Congratulations you won !");
                        long end = System.currentTimeMillis();
                        long gameDuration = (end - start) / 1000;
                        System.out.println("You played for:"+ gameDuration +"seconds");
                    }
                }
            }
            if (!WordIsGuessed) System.out.println("You have run out of tries:/");
            System.out.println("Do you want to play again ? (Yes/No)");
            String AnotherGame = scanner.nextLine();
            AnotherGame.toLowerCase();
            if (AnotherGame.equals("no")) Playing = false;
        }

        System.out.println("GAME OVER");


    }
    public static void printArray(char[] array){
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static boolean IsTheWordGuessed(char[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == '_') return false;
        }
        return true;
    }

    public static String readFromFile() {
        ArrayList<String> file = new ArrayList<String>();
        try {
            File textObject = new File("countries_and_capitals.txt");
            Scanner textReader = new Scanner(textObject);
            while (textReader.hasNextLine()) {
                String data = textReader.nextLine();
                file.add(data);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Random randomInt = new Random();
        String countryOrCapital = file.get(randomInt.nextInt(file.size()));
        return countryOrCapital;
    }
}