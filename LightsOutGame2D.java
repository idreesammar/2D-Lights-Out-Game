public class LightsOutGame2D {
    // instance variables
    private BFF bff;
    private Light[][] lights;
    private final String alphaBank[] = {"A", "B", "C", "D", "E"}; // will hold potential letter values
    // ** NOTE: this assignment only uses emoji board
    
    //constructor
    public LightsOutGame2D() {
        // Intro message
        System.out.println("Welcome to Lights Out!\nThe objective is to turn off all the lights.");
        bff = new BFF();
        int numLights = bff.inputInt("How big of a light grid would you like to have? (3-5)",
         3, 5);
        lights = new Light[numLights][numLights];
        // construct each light object
        for (int i=0; i<lights.length; i++) {
            for (int j =0; j<lights.length; j++) {
                lights[i][j] = new Light();
            }
        }
    }
    
    //method: display the board
    public void displayBoard () {
        System.out.println("🌟LIGHTS OUT GAME BOARD🌟");
        // print out letters on top
        String gridAlpha = " "; // for the actual game board
        for (int i = 0; i < lights.length; i++) {
            gridAlpha += " " + alphaBank[i];
        }
        System.out.println(gridAlpha);
        // iterate through lights array
        for (int i=0; i < lights.length; i++) {
            String lightDisplay = "" + (i+1); // restarts for each row 
            for (int j=0; j < lights.length; j++) {
                if (lights[i][j].getStatus()) { // isOn = true
                    lightDisplay += Light.ON;
                }
                else {
                    lightDisplay += Light.OFF;
                }
            }
            System.out.println(lightDisplay); // prints that rows light display
        }
        System.out.println(); // newline after entire grid has been printed
    }

    // method: play the game
    public void play() {
       int rowChoice = 1; // initialized; holds row choice number
       int columnChoice = 0; // initialized
       boolean allOff = false; // indicates if all lights are off and player has won
        // numbering start at 1, 0 is quit
        while (!(rowChoice == 0 || allOff)) {
            displayBoard();
            // selecting the light 
            rowChoice = bff.inputInt("Please enter the row number of the light? (or 0 to quit)?", 0, lights.length);
            if (rowChoice == 0){
                System.out.println("Now leaving game.");
            }
            else { // if not quitting, get column choice & toggle lights
                columnChoice = getColumnLetter(lights.length);
                toggle(rowChoice, columnChoice);
            }
            allOff = checkGameState();
        } 
        if (allOff) {
            System.out.println("You win! Well done!"); // victory message
            displayBoard(); // the final winning board
        }
        // thank you message
        System.out.println("Thanks for playing!");
    }

    // method to obtain user choice for column letter and match it to proper index
    public int getColumnLetter(int length) {
        String columnLetter;
        if (length == 3) {
            columnLetter = bff.inputWord("Please enter the column letter of the light: ", "A", "B", "C");
        }
        else if (length == 4) {
            columnLetter = bff.inputWord("Please enter the column letter of the light: ", "A", "B", "C", "D");
        }
        else { // length is 5
            columnLetter = bff.inputWord("Please enter the column letter of the light: ", "A", "B", "C", "D", "E");
        }
        // now match letter to index
        for (int i=0; i<alphaBank.length; i++) { // iterate through alphaBank array
            if (columnLetter.equalsIgnoreCase(alphaBank[i])) {
                return i; // once match, return the index
            }
        }
        return 0; // if none match 
    }

    // function to change a specific light condition (on/off)
    private void switchColor(int rowNum, int columnNum){
		// if the light to be flipped is in range (exists on board)
        if(isValid(rowNum,columnNum)) { 
			lights[rowNum-1][columnNum].flip(); // change light condition
		}
	}

    // method to first verify the light to be flipped even exists on the board
        // NOTE: column number is properly aligned from 0,1...length-1
        //       row number does NOT and starts from 1,2,...length
    private boolean isValid(int rowNum, int columnNum) {
		return (rowNum >=1 && rowNum <= lights.length) &&
        (columnNum >= 0 && columnNum < lights.length);
	}

    // method to flip state of desired light and (up to 4) neighbors
        //  NOTE: switchColor calls isValid to guarantee surrounding lights 
        //          exist and wont do anything if they don't exist on board
    private void toggle(int rowChoice, int columnChoice) {
		switchColor(rowChoice,columnChoice); // chosen light
        switchColor(rowChoice+1,columnChoice); // down
        switchColor(rowChoice-1,columnChoice); // up
        switchColor(rowChoice,columnChoice-1); // left
        switchColor(rowChoice,columnChoice+1); // right
	}

    // method to check if all lights are off
    public boolean checkGameState(){
        for (int i=0; i< lights.length;i++) {
           for (int j=0; j<lights.length; j++) {
                if (lights[i][j].getStatus() == true) {
                    return false; // if even a single light is ON, you have not won game
                }
           }
        }
        return true; // means all lights are off (won game)
    }

    // MAIN METHOD
    public static void main(String args[]) {
        LightsOutGame2D myGame = new LightsOutGame2D();
        //myGame.displayBoard();
        myGame.play();
    }
}


