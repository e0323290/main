//@@author e0323290

package expensetest;

import gazeeebo.UI.Ui;
import gazeeebo.commands.expenses.AddExpenseCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing AddExpenseCommand for successful expense addition and
 * wrong formats (e.g. date and price format).
 */
public class AddExpenseCommandTest {
    /**
     * Message shown when price format in adding expenses is wrong.
     */
    private static final String PRICE_FORMAT_ERROR_MESSAGE = "Please key in the correct format for money: $__\r\n";
    /**
     * Message shown when the date format in adding expenses is wrong.
     */
    private static final String WRONG_DATE_FORMAT_ERROR_MESSAGE = "Wrong date format\r\n";
    /**
     * Message shown when general format in adding expenses is wrong.
     */
    private static final String INCORRECT_FORMAT_ERROR_MESSAGE = "Please input in the correct format\n";
    /**
     * Create UI object that deals with printing things to user.
     */
    private Ui ui = new Ui();
    /**
     * Output stream in which data is written into a byte array.
     */
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    /**
     * Print representation of actual data values.
     */
    private PrintStream mine = new PrintStream(output);
    /**
     * Print representation of original data values.
     */
    private PrintStream original = System.out;

    @BeforeEach
    void setupStream() {
        System.setOut(mine);
    }

    @AfterEach
    void restoreStream() {
        System.out.flush();
        System.setOut(original);
    }

    /**
     * Test when adding expense is successful.
     */
    @Test
    void testAddExpenseCommand() {
        HashMap<LocalDate, ArrayList<String>> map = new HashMap<>();
        Map<LocalDate, ArrayList<String>> expenses = new TreeMap<>();
        ui.fullCommand = "add coffee, $4, 2019-09-09";
        new AddExpenseCommand(ui, expenses);
        assertEquals("Successfully added: \n" + "coffee, $4, bought on 2019-09-09\r\n", output.toString());
    }

    /**
     * Test the adding of expense in the incorrect format.
     */
    @Test
    void testAddWrongFormatExpenseCommand() {
        HashMap<LocalDate, ArrayList<String>> map = new HashMap<>();
        Map<LocalDate, ArrayList<String>> expenses = new TreeMap<>();
        ui.fullCommand = "add coffee$42019-09-09";
        new AddExpenseCommand(ui, expenses);
        assertIncorrectFormatErrorMessageDisplayed();
    }

    /**
     * Test the adding of expense in the incorrect date format.
     */
    @Test
    void testAddWrongDateTimeFormatExpenseCommand() {
        HashMap<LocalDate, ArrayList<String>> map = new HashMap<>();
        Map<LocalDate, ArrayList<String>> expenses = new TreeMap<>();
        ui.fullCommand = "add coffee,$4,2019-9-09";
        new AddExpenseCommand(ui, expenses);
        assertWrongDateFormatErrorMessageDisplayed();
    }

    /**
     * Test the adding of expense in the incorrect price format.
     */
    @Test
    void testAddWrongPriceFormatExpenseCommand() {
        HashMap<LocalDate, ArrayList<String>> map = new HashMap<>();
        Map<LocalDate, ArrayList<String>> expenses = new TreeMap<>();
        ui.fullCommand = "add coffee,4,2019-09-09";
        new AddExpenseCommand(ui, expenses);
        assertPriceFormatErrorMessageDisplayed();
    }

    /**
     * Display assertion of price format error message.
     */
    private void assertPriceFormatErrorMessageDisplayed() {
        assertEquals(PRICE_FORMAT_ERROR_MESSAGE, output.toString());
    }

    /**
     * Display assertion of wrong date format error message.
     */
    private void assertWrongDateFormatErrorMessageDisplayed() {
        assertEquals(WRONG_DATE_FORMAT_ERROR_MESSAGE, output.toString());
    }

    /**
     * Display assertion of incorrect format error message.
     */
    private void assertIncorrectFormatErrorMessageDisplayed() {
        assertEquals(INCORRECT_FORMAT_ERROR_MESSAGE, output.toString());

    }
}
