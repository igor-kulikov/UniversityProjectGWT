package ua.university.client.view;

import com.google.gwt.user.client.ui.Widget;
import ua.university.client.entity.ClubDTO;
import java.util.Set;

public interface RefClubView {
    interface Presenter {
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
