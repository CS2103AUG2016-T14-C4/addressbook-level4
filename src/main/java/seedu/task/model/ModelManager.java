package seedu.task.model;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.events.ui.JumpToListRequestEvent;
import seedu.task.commons.logic.CommandKeys.Commands;
import seedu.task.commons.util.StringUtil;
import seedu.task.model.task.DateTime;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the task list data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<Task> filteredTasks;
    private final SortedList<Task> sortedTasks;
    private final UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given TaskManager
     * TaskManager and its variables should not be null
     */
    public ModelManager(TaskManager src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task list: " + src + " and user prefs " + userPrefs);

        taskManager = new TaskManager(src);
        sortedTasks = new SortedList<>(taskManager.getTasks(), this::totalOrderSorting);
        filteredTasks = new FilteredList<>(sortedTasks);
        this.userPrefs = userPrefs;
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
        taskManager = new TaskManager(initialData);
        sortedTasks = new SortedList<>(taskManager.getTasks(), this::totalOrderSorting);
        filteredTasks = new FilteredList<>(sortedTasks);
        this.userPrefs = userPrefs;
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void updateTask(ReadOnlyTask originalTask, Task updateTask)
            throws UniqueTaskList.DuplicateTaskException {
        taskManager.updateTask(originalTask, updateTask);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }
    
    //@@author A0153467Y
    @Override
    public synchronized void completeTask(ReadOnlyTask originalTask, Task completeTask){
        taskManager.completeTask(originalTask, completeTask);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }
    //@@author
    @Override
    public synchronized void rollback() {
        taskManager.rollback();
        indicateTaskManagerChanged();
    }

    //@@author A0153467Y
    @Override
    public synchronized void pinTask(ReadOnlyTask originalTask, Task toPin) {
        taskManager.pinTask(originalTask, toPin);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }
    
    //@@author A0153467Y
    @Override
    public synchronized void uncompleteTask(ReadOnlyTask originalTask, Task uncompleteTask){
        taskManager.uncompleteTask(originalTask, uncompleteTask);
    }
    
    public synchronized void unpinTask(ReadOnlyTask originalTask, Task toUnpin) {
        taskManager.unpinTask(originalTask, toUnpin);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }
    //@@author
    
    // ========== Methods for aliasing ==========================================================================
    
    //@@author A0144939R

    public HashMap<String, Commands> getAliasMap() {
        return userPrefs.getAliasMap();
    }
    
    public void setMapping(Commands command, String alias) {
        userPrefs.setMapping(command, alias);
    }
    
    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
        EventsCenter.getInstance().post(new JumpToListRequestEvent(filteredTasks.size() - 1));
    }
    
    //@@author A0141052Y
    @Override
    public void updateFilteredList(FilterType filter) {
        
        updateFilteredListToShowAll();
        
        switch (filter) {
        case ALL:
            updateFilteredListToShowAll();
            break;
            
        case PIN:
            updateFilteredTaskList(new PredicateExpression(new PinQualifier(true)));
            break;
            
        case COMPLETED:
            updateFilteredTaskList(new PredicateExpression(new CompletedQualifier(true)));
            break;
        
        case PENDING:
            updateFilteredTaskList(new PredicateExpression(new CompletedQualifier(false)));
            break;
            
        case OVERDUE:
            DateTime now = DateTime.fromEpoch(System.currentTimeMillis());
            updateFilteredTaskList(new PredicateExpression(new DueDateQualifier(now)));
            break;
            
        default:
            // does nothing
            break;
        }
    }
    //@@author

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    //@@author A0141052Y
    // ========== Methods for sorting ==========================================================================
    
    private int totalOrderSorting(Task task, Task otherTask) {
        return task.compareTo(otherTask);
    }
    //@@author

    // ========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask tasm);

        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);

        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().taskName, keyword)).findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }

    }
    
    //@@author A0141052Y
    /**
     * Qualifier that checks if Task is not due based on reference time
     * @author Syed Abdullah
     *
     */
    private class DueDateQualifier implements Qualifier {
        
        private DateTime referencePoint;
        
        DueDateQualifier(DateTime reference) {
            this.referencePoint = reference;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            DateTime toCompare = task.getCloseTime();
            
            if (toCompare.isEmpty()) {
                return false;
            } else {
                return (toCompare.compareTo(this.referencePoint) < 1);
            }
        }
    }
    
    /**
     * Qualifier to check the Pin property of the underlying Task.
     * @author Syed Abdullah
     *
     */
    private class PinQualifier implements Qualifier {
        
        private boolean isPinned;
        
        PinQualifier(boolean isPinned) {
            this.isPinned = isPinned;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return (task.getImportance() == this.isPinned);
        }
    }
    
    /**
     * Qualifier to check the Completed property of the underlying Task.
     * @author Syed Abdullah
     *
     */
    private class CompletedQualifier implements Qualifier {
        
        private boolean isCompleted;
        
        CompletedQualifier(boolean isCompleted) {
            this.isCompleted = isCompleted;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return (task.getComplete() == this.isCompleted);
        }
    }
}
