package ua.university.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SwitchToProjectDescrViewEvent extends GwtEvent<SwitchToProjectDescrViewEventHandler> {
    public static Type<SwitchToProjectDescrViewEventHandler> TYPE = new Type<SwitchToProjectDescrViewEventHandler>();

    @Override
    public Type<SwitchToProjectDescrViewEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SwitchToProjectDescrViewEventHandler handler) {
        handler.onSwitchToProjectDescrView(this);
    }
}
