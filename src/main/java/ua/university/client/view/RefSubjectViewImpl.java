package ua.university.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import ua.university.client.entity.ClubDTO;
import ua.university.client.entity.SubjectDTO;
import ua.university.shared.Subject;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Win7 on 15.10.2016.
 */
public class RefSubjectViewImpl extends Composite implements RefSubjectView {

    @UiTemplate("RefSubjectView.ui.xml")
    interface RefSubjectViewUiBinder extends UiBinder<Widget, RefSubjectViewImpl> {
    }

    private RefSubjectView.Presenter presenter;

    @UiField
    ListBox subjectsList;

    @UiField
    TextBox manageSubjectName;

    final Set<SubjectDTO> subjectsDTO = new HashSet<SubjectDTO>();
    SubjectDTO selectedSubjectDTO = new SubjectDTO();

    private static RefSubjectViewUiBinder ourUiBinder = GWT.create(RefSubjectViewUiBinder.class);

    public RefSubjectViewImpl() {
        initWidget(ourUiBinder.createAndBindUi(this));

        // Add a selection model to handle user selection.
        final SingleSelectionModel<SubjectDTO> selectionModel = new SingleSelectionModel<SubjectDTO>();
        subjectsList.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                for(SubjectDTO s : subjectsDTO)
                    if(s.getSubjectName().equals(subjectsList.getSelectedItemText())) {
                        selectedSubjectDTO.setId(s.getId());
                        selectedSubjectDTO.setSubjectName(s.getSubjectName());
                    }
                manageSubjectName.setText(selectedSubjectDTO.getSubjectName());
            }
        });
    }

    @UiHandler("addSubject")
    void onAddSubjectButtonClicked(ClickEvent event) {
        if (presenter != null) {
            presenter.onAddSubjectButtonClicked(new SubjectDTO(0, manageSubjectName.getText()));
        }
    }

    @UiHandler("deleteSubject")
    void onDeleteSubjectButtonClicked(ClickEvent event) {
        if (presenter != null) {
                    presenter.onDeleteSubjectButtonClicked(selectedSubjectDTO);
        }
    }

    @UiHandler("editSubject")
    void onEditSubjectButtonClicked(ClickEvent event) {
        if (presenter != null) {
            SubjectDTO s = new SubjectDTO(selectedSubjectDTO.getId(),
                    manageSubjectName.getText());
            presenter.onEditSubjectButtonClicked(s);
        }
    }

    @UiHandler("switchToProjectDescrView")
    void onSwitchToProjectDescrViewClicked(ClickEvent event) {
        if (presenter != null) {
            presenter.onSwitchToProjectDescrViewClicked();
        }
    }

    @UiHandler("switchToTeacherView")
    void onSwitchToTeacherViewClicked(ClickEvent event) {
        if (presenter != null) {
            presenter.onSwitchToTeacherViewClicked();
        }
    }

    @UiHandler("switchToStudentView")
    void onSwitchToStudentViewClicked(ClickEvent event) {
        if (presenter != null) {
            presenter.onSwitchToStudentViewClicked();
        }
    }

    @UiHandler("switchToRefClubView")
    void onSwitchToRefClubViewClicked(ClickEvent event) {
        if (presenter != null) {
            presenter.onSwitchToRefClubViewClicked();
        }
    }


    public void setPresenter(RefSubjectView.Presenter presenter) {
        this.presenter = presenter;
    }

    public void setSubjectList(Set<SubjectDTO> subjects) {
        subjectsDTO.clear();
        subjectsDTO.addAll(subjects);

        subjectsList.clear();
        for (SubjectDTO subject : subjectsDTO) {
            subjectsList.addItem(subject.getSubjectName());
        }
    }

    public Widget asWidget() {
        return this;
    }
}