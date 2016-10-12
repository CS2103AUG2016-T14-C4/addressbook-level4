package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the task list.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by its name or the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) or NAME (the name of a task to delete)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final int targetIndex;
    public final String targetName;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
    }

    public DeleteCommand(String name) {
        this.targetIndex = -1;
        this.targetName = name;
    }


    @Override
    public CommandResult execute() {
        ReadOnlyTask taskToDelete;

        if(this.targetName != null) {
            try {
                taskToDelete = model.deleteTask(this.targetName);
            } catch (TaskNotFoundException pnfe) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_NAME_SEARCH);
            }

        }else {

            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

            if (lastShownList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            taskToDelete = lastShownList.get(targetIndex - 1);

            try {
                model.deleteTask(taskToDelete);
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
