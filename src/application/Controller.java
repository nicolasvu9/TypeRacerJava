package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable{

    @FXML
    private TextArea QuoteArea;
    @FXML
    private TextField inputText;
    @FXML
    private TextField displayResults;
    @FXML
    private TextField Record;


    private String quote;
    private boolean started = false;
    private TypeRace currentGame;
    private int record=0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Make a new game window and set the quote
            this.currentGame = new TypeRace();
            this.quote = currentGame.getRandomQuote();
            QuoteArea.setText(quote);
            QuoteArea.setWrapText(true);
            Record.setText("The current record: ");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void KeyTyped(KeyEvent e){
        // Uses a boolean to see if the person just started typing, so the time starts as soon as the user starts typing
        if(inputText.getText().length()==1 && !started){
            currentGame.StartTest();
            started = true;
        }
        // Highlights the characters that were already typed in
        QuoteArea.selectRange(0, inputText.getText().length());

        // Check if the race is over
        if(inputText.getText().length()==quote.length()){
            raceOver();
            inputText.setEditable(false);

        }

    }

    public void raceOver(){
        // captures the time the typing test ended
        currentGame.EndTest();
        String userInput = inputText.getText();
        int acc = currentGame.calculateAcc(userInput, this.quote);
        int wpm = currentGame.calculateWPM(userInput);
        CheckRecord(wpm);

        displayResults.setText("WPM = " + wpm + "\t\t Accuracy = " + acc);

    }
    //button to next quote
    public void nextQuote(ActionEvent e){
        try {
            this.currentGame = new TypeRace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        this.quote = currentGame.getRandomQuote();
        QuoteArea.setText(quote);
        inputText.setEditable(true);
        inputText.clear();
        displayResults.clear();
        started=false;

    }
    // Redo the same quote
    public void redo(ActionEvent e){
        inputText.setEditable(true);
        inputText.clear();
        displayResults.clear();
        started=false;
    }
    // Current session's record
    public void CheckRecord(int wpm){
        if(wpm>this.record){
            this.record = wpm;
            Record.setText("The current record: "+ wpm+ " WPM");
        }
    }

}
