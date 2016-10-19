package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask cs2103, laundry, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            cs2103 =  new TaskBuilder().withName("Do CS 2103").withOpenTime("thursday")
                    .withCloseTime("friday").withTags("friends").build();
            laundry = new TaskBuilder().withName("Meier").withOpenTime("tomorrow")
                    .withCloseTime("day after tomorrow").withTags("urgent", "important").build();
            carl = new TaskBuilder().withName("Meet Carl").withOpenTime("thursday")
                    .withCloseTime("friday").build();
            daniel = new TaskBuilder().withName("Have lunch with Meier").withOpenTime("")
                    .withCloseTime("thursday").build();
            elle = new TaskBuilder().withName("Take Ellie out on a date").withOpenTime("6 hours from now")
                    .withCloseTime("12 hours from now").build();
            fiona = new TaskBuilder().withName("Buy a Shrek and Fiona Toy").withOpenTime("tomorrow")
                    .withCloseTime("day after tomorrow").build();
            george = new TaskBuilder().withName("Watch George Best Videos").withOpenTime("tomorrow")
                    .withCloseTime("day after tomorrow").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withOpenTime("tomorrow")
                    .withCloseTime("day after tomorrow").withTags("omg").build();
            ida = new TaskBuilder().withName("Ida Mueller").withOpenTime("tomorrow")
                    .withCloseTime("day after tomorrow").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) throws IllegalValueException {

        try {
            ab.addTask(new Task(cs2103));
            ab.addTask(new Task(laundry));
            ab.addTask(new Task(carl));
            ab.addTask(new Task(daniel));
            ab.addTask(new Task(elle));
            ab.addTask(new Task(fiona));
            ab.addTask(new Task(george));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{cs2103, laundry, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        try {
            loadTaskManagerWithSampleData(ab);
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ab;
    }
}
