package ua.university.client.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import ua.university.client.entity.SubjectDTO;
import ua.university.client.entity.TeacherDTO;
import ua.university.shared.Teacher;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TeacherViewImpl extends Composite implements TeacherView {

    @UiTemplate("TeacherView.ui.xml")
    interface TeacherViewUiBinder extends UiBinder<Widget, TeacherViewImpl> {
    }

    private static TeacherViewUiBinder uiBinder =
            GWT.create(TeacherViewUiBinder.class);

    @UiField
    TextBox searchFirstName;
    @UiField
    TextBox searchLastName;
    @UiField
    DateBox searchBirthday;
    @UiField
    TextBox searchPosition;
    @UiField
    ListBox searchSubject;
    @UiField
    Button searchTeachers;
    @UiField
    DataGrid teacherGrid;

    @UiField
    TextBox manageFirstName;
    @UiField
    TextBox manageLastName;
    @UiField
    DateBox manageBirthday;
    @UiField
    TextBox managePosition;
    @UiField
    ListBox manageSubject;
    @UiField
    Button addTeacher;

    final SubjectDTO selectedSubject = new SubjectDTO(0, "");
    final TeacherDTO selectedTeacher = new TeacherDTO(0, "", "", new Date(0), "", selectedSubject);
    Set<SubjectDTO> subjectsList;

    private Presenter presenter;

    public TeacherViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        searchBirthday.setFormat(
                new DateBox.DefaultFormat(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT)));
        manageBirthday.setFormat(
                new DateBox.DefaultFormat(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT)));
    }

    public void initSearchGrid() {
        int len = teacherGrid.getColumnCount();
        for(int i = 0; i < len; i++){
            teacherGrid.removeColumn(0);
        }

        TextColumn<TeacherDTO> id = new TextColumn<TeacherDTO>() {
            @Override
            public String getValue(TeacherDTO object) {
                return "" + object.getId();
            }
        };
        teacherGrid.addColumn(id, "Id");
        //teacherGrid.setColumnWidth(0, 1, Style.Unit.PX);

        TextColumn<TeacherDTO> lastName = new TextColumn<TeacherDTO>() {
            @Override
            public String getValue(TeacherDTO object) {
                return object.getLastName();
            }
        };
        teacherGrid.addColumn(lastName, "Last Name");

        TextColumn<TeacherDTO> firstName = new TextColumn<TeacherDTO>() {
            @Override
            public String getValue(TeacherDTO object) {
                return object.getFirstName();
            }
        };
        teacherGrid.addColumn(firstName, "First Name");

        TextColumn<TeacherDTO> birthday = new TextColumn<TeacherDTO>() {
            @Override
            public String getValue(TeacherDTO object) {
                return "" + DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT).format(object.getBirthday());
            }
        };
        teacherGrid.addColumn(birthday, "Birthday");

        TextColumn<TeacherDTO> position = new TextColumn<TeacherDTO>() {
            @Override
            public String getValue(TeacherDTO object) {
                return object.getPosition();
            }
        };
        teacherGrid.addColumn(position, "Position");

        TextColumn<TeacherDTO> subject = new TextColumn<TeacherDTO>() {
            @Override
            public String getValue(TeacherDTO object) {
                return object.getSubject().getSubjectName();
            }
        };
        teacherGrid.addColumn(subject, "Subject");

        // Add a selection model to handle user selection.
        final SingleSelectionModel<TeacherDTO> selectionModel = new SingleSelectionModel<TeacherDTO>();
        teacherGrid.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                TeacherDTO selected = selectionModel.getSelectedObject();
                if (selected != null) {
                    manageLastName.setValue(selected.getLastName());
                    manageFirstName.setValue(selected.getFirstName());
                    manageBirthday.setValue(selected.getBirthday());
                    managePosition.setValue(selected.getPosition());
                    int i = 0;
                    while ((i < manageSubject.getItemCount()) && (manageSubject.getValue(i) != selected.getSubject().getSubjectName())) {
                        i++;
                    }
                    manageSubject.setItemSelected(i, true);

                    selectedTeacher.setId(selected.getId());
                    selectedTeacher.setLastName(selected.getLastName());
                    selectedTeacher.setFirstName(selected.getFirstName());
                    selectedTeacher.setBirthday(selected.getBirthday());
                    selectedTeacher.setPosition(selected.getPosition());
                    selectedSubject.setSubjectName(selected.getSubject().getSubjectName());
                }
            }
        });
    }

    public void setSubjectList(Set<SubjectDTO> subjects) {
        searchSubject.clear();
        searchSubject.addItem("<<ANY SUBJECT>>");
        for (SubjectDTO subject : subjects) {
            searchSubject.addItem(subject.getSubjectName());
        }
        manageSubject.clear();
        for (SubjectDTO subject : subjects) {
            manageSubject.addItem(subject.getSubjectName());
        }
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("searchTeachers")
    void onSearchTeachersButtonClicked(ClickEvent event) {
        refreshSearchView();
    }

    public void refreshSearchView(){
        if (presenter != null) {
            presenter.onSearchTeachersButtonClicked(new TeacherDTO(0, searchFirstName.getText(),
                    searchLastName.getText(), searchBirthday.getValue(),
                    searchPosition.getText(), new SubjectDTO(0, searchSubject.getSelectedItemText())));
        }
    };


    @UiHandler("addTeacher")
    void onAddTeacherButtonClicked(ClickEvent event) {
        if (presenter != null) {
            presenter.onAddTeacherButtonClicked(new TeacherDTO(0, manageFirstName.getText(),
                    manageLastName.getText(), manageBirthday.getValue(),
                    managePosition.getText(), new SubjectDTO(0, manageSubject.getSelectedItemText())));
        }
    }

    @UiHandler("deleteTeacher")
    void onDeleteTeacherButtonClicked(ClickEvent event) {
        if (presenter != null) {
            presenter.onDeleteTeacherButtonClicked(selectedTeacher);
        }
    }

    @UiHandler("editTeacher")
    void onEditTeacherButtonClicked(ClickEvent event) {
        if (presenter != null) {
            presenter.onEditTeacherButtonClicked(new TeacherDTO(selectedTeacher.getId(), manageFirstName.getText(),
                    manageLastName.getText(), manageBirthday.getValue(),
                    managePosition.getText(), new SubjectDTO(0, manageSubject.getSelectedItemText())));
        }
    }

    public void displayTeachers(ArrayList<TeacherDTO> teachers) {
        teacherGrid.setRowCount(teachers.size(), true);
        teacherGrid.setRowData(0, teachers);
    }

    public Widget asWidget() {
        return this;
    }

    @UiHandler("switchToProjectDescrView")
    void onSwitchToProjectDescrViewClicked(ClickEvent event){
        if(presenter != null){
            presenter.onSwitchToProjectDescrViewClicked();
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

    @UiHandler("switchToRefClubView")
    void onSwitchToRefClubViewClicked(ClickEvent event){
        if(presenter != null){
            presenter.onSwitchToRefClubViewClicked();
        }
    }

}
