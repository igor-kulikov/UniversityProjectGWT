package ua.university.client.view;

import com.google.gwt.user.client.ui.Widget;
import ua.university.client.entity.SubjectDTO;
import java.util.Set;

public interface RefSubjectView {
    interface Presenter {
        void onAddSubjectButtonClicked(SubjectDTO Subject);

        void onDeleteSubjectButtonClicked(SubjectDTO Subject);

        void onEditSubjectButtonClicked(SubjectDTO Subject);

        void onSwitchToProjectDescrViewClicked();
        void onSwitchToTeacherViewClicked();
        void onSwitchToStudentViewClicked();
        void onSwitchToRefClubViewClicked();
    }

    void setPresenter(RefSubjectView.Presenter presenter);
    void setSubjectList(Set<SubjectDTO> subjects);
    Widget asWidget();
}
