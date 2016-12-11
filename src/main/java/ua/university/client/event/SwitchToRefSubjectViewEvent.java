package ua.university.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SwitchToRefSubjectViewEvent extends GwtEvent<SwitchToRefSubjectViewEventHandler> {
    public static Type<SwitchToRefSubjectViewEventHandler> TYPE = new Type<SwitchToRefSubjectViewEventHandler>();

    @Override
    public Type<SwitchToRefSubjectViewEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SwitchToRefSubjectViewEventHandler handler) {
        handler.onSwitchToRefSubjectView(this);
    }
}
