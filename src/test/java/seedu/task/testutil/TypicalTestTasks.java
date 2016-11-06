//@@author A0144939R
package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask cs2103, laundry, carl, daniel, elle, fiona, george, hoon, ida, same, recur, recur2, name;

    public TypicalTestTasks() {
        try {
            cs2103 =  new TaskBuilder().withName("Do CS 2103").withOpenTime("9 hours from now")
                    .withCloseTime("17 hours from now").withTags("friends").withImportance(false).build();
            laundry = new TaskBuilder().withName("Do Meier laundry").withOpenTime("tomorrow")
                    .withCloseTime("day after tomorrow").withImportance(false).withTags("urgent", "important").build();
            carl = new TaskBuilder().withName("Meet Carl").withOpenTime("5 days from now")
                    .withCloseTime("14 days from now").build();
            daniel = new TaskBuilder().withName("Have lunch with Meier").withOpenTime("6 hours from now")
                    .withCloseTime("8 hours from now").build();
            elle = new TaskBuilder().withName("Take Ellie out on a date").withOpenTime("6 hours from now")
                    .withCloseTime("12 hours from now").build();
            fiona = new TaskBuilder().withName("Buy a Shrek and Fiona Toy").withOpenTime("tomorrow")
                    .withCloseTime("day after tomorrow").build();
            george = new TaskBuilder().withName("Watch George Best Videos").withOpenTime("tomorrow")
                    .withCloseTime("day after tomorrow").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withOpenTime("tomorrow")
                    .withCloseTime("day after tomorrow").withTags("omg").withImportance(false).build();//.withRecurrentWeek(0)
            ida = new TaskBuilder().withName("Ida Mueller").withOpenTime("tomorrow")
                    .withCloseTime("the day after tomorrow").build();
            same =new TaskBuilder().withName("Ida Mueller").withOpenTime("one week from now")
                    .withCloseTime("two weeks from now").build();
            recur = new TaskBuilder().withName("Ida").withOpenTime("one week from now")
                    .withCloseTime("two weeks from now").build();
            recur2 = new TaskBuilder().withName("Do Homework").withOpenTime("two days from now")
                    .withCloseTime("five days from now").build();
            name = new TaskBuilder().withName("task name").build();
            
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
        return new TestTask[]{daniel, elle, cs2103, fiona, laundry, george, carl};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        try {
            loadTaskManagerWithSampleData(ab);
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false: "Invalid sample task data";
        }
        return ab;
    }
}
