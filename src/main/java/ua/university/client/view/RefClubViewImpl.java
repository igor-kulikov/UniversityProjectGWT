package ua.university.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.SingleSelectionModel;
import ua.university.client.entity.ClubDTO;
import ua.university.client.entity.StudentDTO;
import ua.university.client.entity.SubjectDTO;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Win7 on 15.10.2016.
 */
public class RefClubViewImpl extends Composite implements RefClubView{

    @UiTemplate("RefClubView.ui.xml")
    interface RefClubViewUiBinder extends UiBinder<Widget, RefClubViewImpl> {
    }

    private RefClubView.Presenter presenter;

    @UiField
    ListBox clubsList;

    @UiField
    TextBox manageClubName;

    final Set<ClubDTO> clubsDTO = new HashSet<ClubDTO>();
    ClubDTO selectedClubDTO = new ClubDTO();

    private static RefClubViewUiBinder ourUiBinder = GWT.create(RefClubViewUiBinder.class);

    public RefClubViewImpl() {
        initWidget(ourUiBinder.createAndBindUi(this));
        // Add a selection model to handle user selection.
        final SingleSelectionModel<SubjectDTO> selectionModel = new SingleSelectionModel<SubjectDTO>();
        clubsList.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                for(ClubDTO c : clubsDTO)
                    if(c.getClubName().equals(clubsList.getSelectedItemText())) {
                        selectedClubDTO.setId(c.getId());
                        selectedClubDTO.setClubName(c.getClubName());
                    }
                manageClubName.setText(selectedClubDTO.getClubName());
            }
        });
    }


    @UiHandler("addClub")
    void onAddClubButtonClicked(ClickEvent event){
        if (presenter != null) {
            presenter.onAddClubButtonClicked(new ClubDTO(0, manageClubName.getText()));
        }
    };

    @UiHandler("deleteClub")
    void onDeleteClubButtonClicked(ClickEvent event){
        if (presenter != null) {
            presenter.onDeleteClubButtonClicked(selectedClubDTO);
        }
    };

    @UiHandler("editClub")
    void onEditClubButtonClicked(ClickEvent event){
        if (presenter != null) {
            ClubDTO c = new ClubDTO(selectedClubDTO.getId(),
                    manageClubName.getText());
            presenter.onEditClubButtonClicked(c);
        }
    };


    @UiHandler("switchToProjectDescrView")
    void onSwitchToProjectDescrViewClicked(ClickEvent event){
        if(presenter != null){
            presenter.onSwitchToProjectDescrViewClicked();
        }
    }

    @UiHandler("switchToTeacherView")
    void onSwitchToTeacherViewClicked(ClickEvent event){
        if(presenter != null){
            presenter.onSwitchToTeacherViewClicked();
        }
    }

    @UiHandler("switchToStudentView")
    void onSwitchToStudentViewClicked(ClickEvent event){
        if(presenter != null){
            presenter.onSwitchToStudentViewClicked();
        }
    }

    @UiHandler("switchToRefSubjectView")
    void onSwitchToRefSubjectViewClicked(ClickEvent event){
        if(presenter != null){
            presenter.onSwitchToRefSubjectViewClicked();
        }
    }

    public void setPresenter(RefClubView.Presenter presenter) {
        this.presenter = presenter;
    }

    public void setClubList(Set<ClubDTO> clubs) {
        clubsDTO.clear();
        clubsDTO.addAll(clubs);

        clubsList.clear();
        for (ClubDTO subject : clubs) {
            clubsList.addItem(subject.getClubName());
        }
    }

    public Widget asWidget() {
        return this;
    }
}