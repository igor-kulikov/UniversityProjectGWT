package ua.university.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SwitchToStudentViewEvent extends GwtEvent<SwitchToStudentViewEventHandler> {
    public static Type<SwitchToStudentViewEventHandler> TYPE = new Type<SwitchToStudentViewEventHandler>();

    @Override
    public Type<SwitchToStudentViewEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SwitchToStudentViewEventHandler handler) {
        handler.onSwitchToStudentView(this);
    }
}
