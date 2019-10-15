package parsers;

import Exception.DukeException;
import commands.*;

import commands.expense.AddExpensesCommand;
import commands.expense.ExpenseCommand;
//import commands.expense.ExpenseListCommand;

import commands.Contact.ContactsCommand;
import commands.Edit.EditCommand;

import commands.note.AddNoteCommand;
import commands.note.DeleteNoteCommand;
import commands.note.EditNoteCommand;
import commands.note.ListNoteCommand;

import java.io.IOException;

public class Parser {
    public static Command parse(String command) throws DukeException, IOException {
        String[] splitCommand = command.split(" ");
        if (splitCommand[0].equals("list")) {
            if (command.contains("event")) {
                return new CategoryListCommand();
            } else if (command.contains("deadline")) {
                return new CategoryListCommand();
            } else if (command.contains("todo")) {
                return new CategoryListCommand();
            } else if (command.contains("fixed")) {
                return new CategoryListCommand();
            } else if (command.contains("timebound")) {
                return new CategoryListCommand();
            } else {
                return new ListCommand();
            }
         } else if (command.equals("done list")) {
            return new DoneListCommand();
        } else if (command.equals("undo list")) {
            return new UndoneListCommand();
        } else if (splitCommand[0].equals("done")) {
            return new DoneCommand();
        } else if (splitCommand[0].equals("delete")) {
            return new DeleteCommand();
        } else if (splitCommand[0].equals("deadline")) {
            return new DeadlineCommand();
        } else if (command.contains("/after")) {
            return new DoAfterCommand();
        } else if (splitCommand[0].equals("event")) {
            return new EventCommand();
        } else if (splitCommand[0].equals("todo")) {
            return new TodoCommand();
        } else if (command.contains("/between")) {
            return new TimeboundCommand();
        } else if (splitCommand[0].equals("find")) {
            return new FindCommand();
        }
        else if(command.equals("contact")) {
            return new ContactsCommand();
        } else if(command.equals("expenses")) {
                return new ExpenseCommand();
        }else if (splitCommand[0].equals("bye")) {
            return new ByeCommand();
        } else if (command.contains("/require")) {
            return new FixDurationCommand();
        } else if (splitCommand[0].equals("reschedule")) {
            return new RescheduleCommand();
        } else if(splitCommand[0].equals("sort")){
            return new SortCommand();
        } else if (splitCommand[0].equals("scheduleDaily")) {
            return new ScheduleDailyCommand();
        } else if (splitCommand[0].equals("scheduleWeekly")) {
            return new ScheduleWeeklyCommand();
        } else if (splitCommand[0].equals("scheduleMonthly")) {
            return new ScheduleMonthlyCommand();
        } else if (splitCommand[0].equals("snooze")) {
            return new SnoozeCommand();
        } else if (splitCommand[0].equals("tentative")) {
            return new TentativeEventCommand();
        } else if (splitCommand[0].equals("confirm")) {
            return new ConfirmTentativeCommand();
        } else if (splitCommand[0].contains("undone")) {
            return new UndoneCommand();
        } else if (splitCommand[0].equals("undo")) {
            return new UndoCommand();
        } else if (splitCommand[0].equals("edit")) {
            return new EditCommand();
        } else if (splitCommand[0].equals("addNote")) {
            return new AddNoteCommand();
        } else if (splitCommand[0].equals("editNote")) {
            return new EditNoteCommand();
        } else if (splitCommand[0].equals("deleteNote")) {
            return new DeleteNoteCommand();
        } else if (splitCommand[0].equals("listNote")) {
                return new ListNoteCommand();
        } else if ((splitCommand[0] + " " + splitCommand[1]).equals("change password")) {
            return new ChangePasswordCommand();
        } else if (command.contains("#")) {
            return new TagCommand();
        }
        else {
            throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}