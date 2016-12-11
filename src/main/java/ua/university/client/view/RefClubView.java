package ua.university.client.view;

import com.google.gwt.user.client.ui.Widget;
import ua.university.client.entity.ClubDTO;
import ua.university.client.entity.SubjectDTO;
import ua.university.client.entity.TeacherDTO;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Win7 on 15.10.2016.
 */
public interface RefClubView {
    public interface Presenter {
        void onAddClubButtonClicked(ClubDTO Club);

        void onDeleteClubButtonClicked(ClubDTO Club);

        void onEditClubButtonClicked(ClubDTO Club);
        
        void onSwitchToProjectDescrViewClicked();
        void onSwitchToTeacherViewClicked();
        void onSwitchToStudentViewClicked();
        void onSwitchToRefSubjectViewClicked();
    }

    void setPresenter(RefClubView.Presenter presenter);
    void setClubList(Set<ClubDTO> clubs);
    Widget asWidget();
}
