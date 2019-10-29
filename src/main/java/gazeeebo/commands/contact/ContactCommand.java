package gazeeebo.commands.contact;

import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;

import java.io.IOException;
import java.util.*;

/**
 * Deals with the user input in the contacts page.
 */
public class ContactCommand extends Command {
    private static final String LINEBREAK = "------------------------------------------\n";
    /**
     * This method is the list of all the contact numbers and you got add/find/delete contacts.
     *
     * @param list    list of all tasks
     * @param ui      the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @param commandStack
     * @throws IOException Catch error if the read file fails
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<ArrayList<Task>> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws IOException {
        HashMap<String, String> map = storage.readFromContactFile(); //Read the file
        Map<String, String> contactList = new TreeMap<String, String>(map);

        System.out.print("Welcome to your contacts page! What would you like to do?\n\n");
        String helpContact = "__________________________________________________________\n"
                + "1. Add contacts: add\n"
                + "2. Find contacts base on name: find name\n"
                + "3. Delete a contact: delete name\n"
                + "4. See your contacts list: list\n"
                + "5. Help Command: help\n"
                + "6. Exit contact page: esc\n"
                + "__________________________________________________________\n\n";
        System.out.print(helpContact);
        ui.readCommand();
        while (!ui.fullCommand.equals("esc")) {
            if (ui.fullCommand.equals("add")) {
                new AddContactCommand(ui, contactList);
            } else if (ui.fullCommand.split(" ")[0].equals("find")) {
                new FindContactCommand(ui, contactList, LINEBREAK);
            } else if (ui.fullCommand.equals("list")) {
                new ListContactCommand(contactList, LINEBREAK);
            } else if (ui.fullCommand.contains("delete")) {
                new DeleteContactCommand(ui, contactList);
            } else if (ui.fullCommand.equals("help")) {
                System.out.println(helpContact);
            }
            else {
                System.out.println("Incorrect format");
            }
            String toStore = "";
            for (String key : contactList.keySet()) {

                toStore = toStore.concat(key + "|" + contactList.get(key) + "\n");
            }
            storage.writeToContactFile(toStore);
            System.out.println("What do you want to do next ?");
            ui.readCommand();
        }
        System.out.print("Going back to Main Menu\n");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}