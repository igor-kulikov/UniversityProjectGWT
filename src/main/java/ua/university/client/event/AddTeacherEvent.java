package ua.university.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddTeacherEvent extends GwtEvent<AddTeacherEventHandler> {
    public static Type<AddTeacherEventHandler> TYPE = new Type<AddTeacherEventHandler>();

    @Override
    public Type<AddTeacherEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AddTeacherEventHandler handler) {
        handler.onAddTeacher(this);
    }
}
