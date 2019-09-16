package commands;
import Tasks.Task;
import UI.Ui;
import Tasks.*;
import Storage.Storage;
import Exception.DukeException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class TodoCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws DukeException, ParseException, IOException, NullPointerException {
        String description = "";
        if(ui.FullCommand.length() <= 4) {
            throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
        }
        else{
            description = ui.FullCommand.substring(5);
        }
        Todo to = new Todo(description);
        list.add(to);
        System.out.println("Got it. I've added this task:");
        System.out.println(to.listformat());
        System.out.println("Now you have " + list.size() + " tasks in the list.");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getClass().getName().equals("Tasks.Deadline")) {
                sb.append(list.get(i).toString()+"\n");
            }
            else if(list.get(i).getClass().getName().equals("Tasks.Event")){
                sb.append(list.get(i).toString()+"\n");
            }
            else{
                sb.append(list.get(i).toString()+"\n");
            }
        }
        storage.Storages(sb.toString());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}