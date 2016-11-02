//@@author A0141052Y
package seedu.task.commons.core;

/**
 * Provides static methods for the context of the current UI
 * @author Syed Abdullah
 *
 */
public class UIContext {
    /**
     * The type of UI context being referenced
     * @author Syed Abdullah
     *
     */
    public enum Type {
        RESULT_PAGE,
        HELP_BROWSER
    }
    
    /**
     * A non-specific reference to a type of tab
     * @author Syed Abdullah
     *
     */
    public enum Tabs {
        ALL,
        PENDING,
        COMPLETED,
        PINNED,
        HELP
    }
}
