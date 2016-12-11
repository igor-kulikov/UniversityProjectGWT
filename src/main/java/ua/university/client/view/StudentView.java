package ua.university.client.view;

import com.google.gwt.user.client.ui.Widget;
import ua.university.client.entity.ClubDTO;
import ua.university.client.entity.StudentDTO;
import ua.university.client.entity.SubjectDTO;
import ua.university.client.entity.TeacherDTO;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Win7 on 15.10.2016.
 */
public interface StudentView {
    public interface Presenter {
        void onSearchStudentsButtonClicked(StudentDTO searchStudentDTO);

        void onAddStudentButtonClicked(StudentDTO Student);

        void onDeleteStudentButtonClicked(StudentDTO Student);

        void onEditStudentButtonClicked(StudentDTO Student);

        void onSwitchToProjectDescrViewClicked();

        void onSwitchToTeacherViewClicked();

        void onSwitchToRefSubjectViewClicked();

        void onSwitchToRefClubViewClicked();
    }

    void setPresenter(StudentView.Presenter presenter);

    Widget asWidget();

    void displayStudents(ArrayList<StudentDTO> students);

    void initSearchGrid();

    void refreshSearchView();

    void setSubjectLists(Set<SubjectDTO> subjects);

    void setClubLists(Set<ClubDTO> clubs);

}
