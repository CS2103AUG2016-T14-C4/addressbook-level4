package seedu.task.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.task.commons.exceptions.DuplicateDataException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.history.ListMutation;
import seedu.task.model.history.Mutation;

import java.util.*;
import java.util.Map.Entry;

/**
 * A list of tasks that enforces uniqueness between its elements and does not
 * allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    /**
     * Signals that an operation would have violated the 'no duplicates'
     * property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would
     * fail because there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {
    }

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    private final ListMutation<ReadOnlyTask> history = new ListMutation<ReadOnlyTask>();

    /**
     * Constructs empty TaskList.
     */
    public UniqueTaskList() {
    }

    /**
     * Returns true if the list contains an equivalent task as the given
     * argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException
     *             if the task to add is a duplicate of an existing task in the
     *             list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
        history.addAsNewMutation(internalList.size() - 1, new Mutation<ReadOnlyTask>(null, toAdd.getImmutable()));
    }

    /**
     * Updates a task to the list.
     * 
     * @throws DuplicateTaskException
     *             if the task to add is a duplicate of an existing task in the
     *             list.
     */
    public void update(ReadOnlyTask toUpdate, Task updatedTask) throws DuplicateTaskException {
        assert toUpdate != null;
        assert updatedTask != null;

        int index = internalList.indexOf(toUpdate);
        assert index >= 0;

        if (contains(updatedTask) && !toUpdate.equals(updatedTask)) {
            throw new DuplicateTaskException();
        }

        internalList.set(index, updatedTask);
        history.addAsNewMutation(index, new Mutation<ReadOnlyTask>(toUpdate, updatedTask.getImmutable()));
    }

    /**
     * Pins a task as important.
     * 
     * @throws TaskNotFoundException
     *             if the task cannot be found in the list.
     */
    public void pin(ReadOnlyTask originalTask, Task toPin) {
        assert toPin != null;
        assert originalTask != null;

        int index = internalList.indexOf(originalTask);
        assert index >= 0;
        internalList.set(index, toPin);
        history.addAsNewMutation(index, new Mutation<ReadOnlyTask>(originalTask, toPin.getImmutable()));
    }

    /**
     * Marks a task as completed in the list.
     */
    public void complete(ReadOnlyTask originalTask, Task completeTask) {
        assert originalTask != null;
        assert completeTask != null;
        
        int index = internalList.indexOf(originalTask);
        assert index >= 0;
        
        internalList.set(index, completeTask);
        history.addAsNewMutation(index, new Mutation<ReadOnlyTask>(originalTask, completeTask.getImmutable()));
    }
    
    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException
     *             if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final int index = internalList.indexOf(toRemove);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        final Task taskRemoved = internalList.remove(index);
        history.addAsNewMutation(index, new Mutation<ReadOnlyTask>(taskRemoved.getImmutable(), null));

        return (index != -1);
    }
    
    /**
     * Applies a ReadOnlyTask mutation to the current list.
     * 
     * @param index the index in the task list
     * @param mutation the Mutation of the element
     * @return true if the operation succeeds, or else false
     * @throws IllegalValueException if the ReadOnlyTask states have invalid fields
     */
    public boolean applyMutation(int index, Mutation<ReadOnlyTask> mutation) throws IllegalValueException {
        if (index < 0 || index > internalList.size()) {
            return false;
        }
        
        if (mutation.getPreviousState() == null) {
            internalList.add(index, new Task(mutation.getPresentState()));
            return true;
        }
        
        if (!internalList.get(index).equals(mutation.getPreviousState())) {
            return false;
        } else if (mutation.getPresentState() == null) {
            internalList.remove(index);
            return true;
        } else {
            internalList.set(index, new Task(mutation.getPresentState()));
            return true;
        }
    }
    
    /**
     * Rollback the previous operation done on the task list.
     */
    public void rollback() {
        if (!history.hasMutation()) {
            return;
        }
        
        for (Entry<Integer, Mutation<ReadOnlyTask>> element : history.getMutations()) {
            try {
                applyMutation(element.getKey(), element.getValue().reverse());
            } catch (IllegalValueException e) {
                assert false : "Task was retrieved from TaskList, incorrect fields not possible";
            }
        }
        
        history.clear();
    }

    public ObservableList<Task> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                        && this.internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
