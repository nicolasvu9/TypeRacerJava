package application;

import java.util.Random;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;


public class TypeRace {
    String filename = System.getProperty("user.dir")+ "\\src\\data.txt";
    double start;
    double end;
    String quote;

    public TypeRace() throws IOException{
        this.quote = getRandomQuote();
    }


    // Read each line from the text file to an ArrayList
    public ArrayList<String> readTxtFile(){

        ArrayList<String> result = new ArrayList<>();
        try (Scanner s = new Scanner(new FileReader(filename))) {
            while (s.hasNext()) {
                result.add(s.nextLine());
            }
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }


    public String getRandomQuote(){
        ArrayList<String> quotes = readTxtFile();
        Random rand = new Random();
        int int_random = rand.nextInt(quotes.size());
        String quote = quotes.get(int_random);

        return quote;
    }

    // capture the time when we start the typing test
    public void StartTest(){
        this.start = java.time.LocalTime.now().toNanoOfDay();
    }
    public void EndTest(){
        this.end = java.time.LocalTime.now().toNanoOfDay();
    }
    public int calculateWPM(String input){
        double timeDiff = this.end - this.start;
        double seconds = timeDiff/1000000000.0;
        double numChars = input.length();

        int WPM = (int) (((numChars / 5)/seconds)*60);

        return WPM;

    }
    public int calculateAcc(String input, String real_quote){
        float correct=0;
        float acc;
        for(int i=0; i<real_quote.length();i++){
            if(input.charAt(i) == real_quote.charAt(i)){
                System.out.println(input.charAt(i)+"=="+real_quote.charAt(i));
                correct++;
            }
        }
        acc= correct/real_quote.length()*100;


        return (int) acc;

    }


}
