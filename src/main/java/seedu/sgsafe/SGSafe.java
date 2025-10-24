package seedu.sgsafe;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;

import seedu.sgsafe.utils.command.Command;
import seedu.sgsafe.utils.exceptions.InvalidCommandException;
import seedu.sgsafe.utils.storage.Storage;
import seedu.sgsafe.utils.ui.Display;
import seedu.sgsafe.utils.ui.Parser;

/**
 * Entry point for the SGSafe application.
 */
public class SGSafe {

    // Logger for logging application events
    private static final Logger logger = Logger.getLogger(SGSafe.class.getName());
    // The location of the save file
    private static final String SAVE_FILE_NAME = "./data.txt";
    //the Storage object to handle loading and saving
    private static final Storage storage = new Storage(SAVE_FILE_NAME);

    /**
     * Main method that starts the SGSafe application.
     * Invokes initialization, enters the command loop, and prints exit message.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        logger.log(Level.INFO, "SGSafe application started.");
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.SEVERE);

        //load the cases from the savefile
        storage.loadCaseManager();
        
        Display.printWelcomeMessage();
        mainLoop();
        Display.printGoodbyeMessage();

        storage.saveToFile();
    }

    /**
     * Starts the main input loop for the application.
     * Continuously reads user input from the console until the user types "bye".
     * Each input is parsed and executed as a command.
     */
    private static void mainLoop() {
        Scanner in = new Scanner(System.in);
        String userInput;
        while (true) {
            userInput = in.nextLine();
            if (userInput.equals("bye")) {
                return;
            }
            handleUserCommand(userInput);
        }
    }

    /**
     * Parses and executes a user command.
     *
     * @param userInput the raw input string entered by the user
     */
    private static void handleUserCommand(String userInput) {
        try {
            Command command = Parser.parseInput(userInput);
            command.execute(storage);
        } catch (InvalidCommandException e) {
            Display.printMessage(e.getErrorMessage());
        }
    }
}
