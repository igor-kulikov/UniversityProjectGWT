package ua.university.client.view;

import com.google.gwt.user.client.ui.Widget;
import ua.university.client.entity.SubjectDTO;
import ua.university.client.entity.TeacherDTO;

import java.util.List;
import java.util.Set;

public interface TeacherView {

    interface Presenter {
        void onSearchTeachersButtonClicked(TeacherDTO searchTeacherDTO);

        void onAddTeacherButtonClicked(TeacherDTO teacher);

        void onDeleteTeacherButtonClicked(TeacherDTO teacher);

        void onEditTeacherButtonClicked(TeacherDTO teacher);

        void onSwitchToProjectDescrViewClicked();
        void onSwitchToStudentViewClicked();
        void onSwitchToRefSubjectViewClicked();
        void onSwitchToRefClubViewClicked();
    }

    void setPresenter(Presenter presenter);

    void displayTeachers(List<TeacherDTO> teachers);

    void initSearchGrid();

    void refreshSearchView();

    void setSubjectList(Set<SubjectDTO> subjects);

    Widget asWidget();
}
