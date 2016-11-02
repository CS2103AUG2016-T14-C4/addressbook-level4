//@@author A0141052Y
package seedu.task.commons.events.ui;

import seedu.task.commons.core.UIContext;
import seedu.task.commons.events.BaseEvent;

/**
 * Represents an event where the UI tabs are switched.
 * @author Syed Abdullah
 *
 */
public class SwitchUITabEvent extends BaseEvent {

    public final UIContext.Tabs tabs;
    
    public SwitchUITabEvent(UIContext.Tabs tabs) { 
        this.tabs = tabs;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
