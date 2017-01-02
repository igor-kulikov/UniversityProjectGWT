package ua.university.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface ProjectDescrView {
    interface Presenter {
        void onSwitchToTeacherViewClicked();
        void onSwitchToStudentViewClicked();
        void onSwitchToRefSubjectViewClicked();
        void onSwitchToRefClubViewClicked();
    }

    void setPresenter(ProjectDescrView.Presenter presenter);
    Widget asWidget();
}
