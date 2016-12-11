package ua.university.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SwitchToRefClubViewEvent extends GwtEvent<SwitchToRefClubViewEventHandler> {
    public static Type<SwitchToRefClubViewEventHandler> TYPE = new Type<SwitchToRefClubViewEventHandler>();

    @Override
    public Type<SwitchToRefClubViewEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SwitchToRefClubViewEventHandler handler) {
        handler.onSwitchToRefClubView(this);
    }
}
