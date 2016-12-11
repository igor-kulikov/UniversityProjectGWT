package ua.university.client.view;

import com.google.gwt.user.client.ui.Widget;
import ua.university.client.entity.SubjectDTO;
import ua.university.client.entity.TeacherDTO;

import java.util.ArrayList;

/**
 * Created by Win7 on 15.10.2016.
 */
public interface ProjectDescrView {
    public interface Presenter {
        void onSwitchToTeacherViewClicked();
        void onSwitchToStudentViewClicked();
        void onSwitchToRefSubjectViewClicked();
        void onSwitchToRefClubViewClicked();
    }

    void setPresenter(ProjectDescrView.Presenter presenter);
    Widget asWidget();
}
