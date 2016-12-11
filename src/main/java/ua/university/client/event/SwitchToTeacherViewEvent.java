package ua.university.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SwitchToTeacherViewEvent extends GwtEvent<SwitchToTeacherViewEventHandler> {
    public static Type<SwitchToTeacherViewEventHandler> TYPE = new Type<SwitchToTeacherViewEventHandler>();

    @Override
    public Type<SwitchToTeacherViewEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SwitchToTeacherViewEventHandler handler) {
        handler.onSwitchToTeacherView(this);
    }
}
