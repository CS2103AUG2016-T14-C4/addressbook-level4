//@@author A0141052Y
package seedu.task.commons.events.ui;

import seedu.task.commons.core.UIContext;
import seedu.task.commons.events.BaseEvent;

/**
 * Represents an event where the UI context is switched.
 * @author Syed Abdullah
 *
 */
public class SwitchUIContextEvent extends BaseEvent {
    
    public final UIContext.Type context;
    
    public SwitchUIContextEvent(UIContext.Type context) { 
        this.context = context;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
