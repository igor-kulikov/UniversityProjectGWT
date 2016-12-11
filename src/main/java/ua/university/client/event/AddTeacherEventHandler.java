package ua.university.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface AddTeacherEventHandler extends EventHandler {
    void onAddTeacher(AddTeacherEvent event);
}
