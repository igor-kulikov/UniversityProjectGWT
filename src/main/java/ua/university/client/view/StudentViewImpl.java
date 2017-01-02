package ua.university.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import ua.university.client.entity.ClubDTO;
import ua.university.client.entity.StudentDTO;
import ua.university.client.entity.SubjectDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class StudentViewImpl extends Composite implements StudentView {

    @UiTemplate("StudentView.ui.xml")
    interface StudentViewUiBinder extends UiBinder<Widget, StudentViewImpl> {
    }

    private static StudentViewUiBinder uiBinder = GWT.create(StudentViewUiBinder.class);

    @UiField
    TextBox searchFirstName;
    @UiField
    TextBox searchLastName;
    @UiField
    DateBox searchBirthday;
    @UiField
    ListBox searchSubject;
    @UiField
    ListBox searchClub;
    @UiField
    Button searchStudents;
    @UiField
    DataGrid studentGrid;

    @UiField
    TextBox manageFirstName;
    @UiField
    TextBox manageLastName;
    @UiField
    DateBox manageBirthday;
    @UiField
    ListBox manageSubjects;
    @UiField
    ListBox manageClubs;
    @UiField
    Button addStudent;

    private final Set<SubjectDTO> subjectsList = new HashSet<SubjectDTO>();
    private final Set<ClubDTO> clubsList = new HashSet<ClubDTO>();

    private final StudentDTO selectedStudent = new StudentDTO(0, "", "", new Date(0),
            new HashSet<SubjectDTO>(), new HashSet<ClubDTO>());


    private Presenter presenter;

    public StudentViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        searchBirthday.setFormat(
                new DateBox.DefaultFormat(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT)));
        manageBirthday.setFormat(
                new DateBox.DefaultFormat(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT)));
    }

    public void initSearchGrid() {
        int len = studentGrid.getColumnCount();
        for (int i = 0; i < len; i++) {
            studentGrid.removeColumn(0);
        }

        TextColumn<StudentDTO> id = new TextColumn<StudentDTO>() {
            @Override
            public String getValue(StudentDTO object) {
                return "" + object.getId();
            }
        };
        studentGrid.addColumn(id, "Id");
        studentGrid.setColumnWidth(0, 1, Style.Unit.PX);

        TextColumn<StudentDTO> lastName = new TextColumn<StudentDTO>() {
            @Override
            public String getValue(StudentDTO object) {
                return object.getLastName();
            }
        };
        studentGrid.addColumn(lastName, "Last Name");

        TextColumn<StudentDTO> firstName = new TextColumn<StudentDTO>() {
            @Override
            public String getValue(StudentDTO object) {
                return object.getFirstName();
            }
        };
        studentGrid.addColumn(firstName, "First Name");

        TextColumn<StudentDTO> birthday = new TextColumn<StudentDTO>() {
            @Override
            public String getValue(StudentDTO object) {
                return "" + DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT).format(object.getBirthday());
            }
        };
        studentGrid.addColumn(birthday, "Birthday");

        TextColumn<StudentDTO> subjects = new TextColumn<StudentDTO>() {
            @Override
            public String getValue(StudentDTO object) {
                String res = "";
                for (SubjectDTO s : object.getSubjects())
                    res += s.getSubjectName() + "; ";
                return res;
            }
        };
        studentGrid.addColumn(subjects, "Subjects");

        TextColumn<StudentDTO> clubs = new TextColumn<StudentDTO>() {
            @Override
            public String getValue(StudentDTO object) {
                String res = "";
                for (ClubDTO c : object.getClubs())
                    res += c.getClubName() + "; ";
                return res;
            }
        };
        studentGrid.addColumn(clubs, "Clubs");

        // Add a selection model to handle user selection.
        final SingleSelectionModel<StudentDTO> selectionModel = new SingleSelectionModel<StudentDTO>();
        studentGrid.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                StudentDTO selected = selectionModel.getSelectedObject();
                if (selected != null) {
                    selectedStudent.setId(selected.getId());
                    selectedStudent.setLastName(selected.getLastName());
                    selectedStudent.setFirstName(selected.getFirstName());
                    selectedStudent.setBirthday(selected.getBirthday());
                    selectedStudent.getSubjects().clear();
                    selectedStudent.getSubjects().addAll(selected.getSubjects());
                    selectedStudent.getClubs().clear();
                    selectedStudent.getClubs().addAll(selected.getClubs());

                    manageLastName.setText(selectedStudent.getLastName());
                    manageFirstName.setText(selectedStudent.getFirstName());
                    manageBirthday.setValue(selectedStudent.getBirthday());

                    for (int i = 0; i < manageSubjects.getItemCount(); i++) {
                        manageSubjects.setItemSelected(i, false);
                        if (selectedStudent.getSubjects().contains(new SubjectDTO(0, manageSubjects.getValue(i))))
                            manageSubjects.setItemSelected(i, true);
                    }

                    for (int i = 0; i < manageClubs.getItemCount(); i++) {
                        manageClubs.setItemSelected(i, false);
                        if (selectedStudent.getClubs().contains(new ClubDTO(0, manageClubs.getValue(i))))
                            manageClubs.setItemSelected(i, true);
                    }
                }
            }
        });
    }

    public void setSubjectLists(Set<SubjectDTO> subjects) {
        subjectsList.clear();
        subjectsList.addAll(subjects);

        searchSubject.clear();
        searchSubject.addItem("<<ANY SUBJECT>>");
        for (SubjectDTO subject : subjectsList) {
            searchSubject.addItem(subject.getSubjectName());
        }

        manageSubjects.clear();
        for (SubjectDTO subject : subjectsList) {
            manageSubjects.addItem(subject.getSubjectName());
        }
    }

    public void setClubLists(Set<ClubDTO> clubs) {
        clubsList.clear();
        clubsList.addAll(clubs);

        searchClub.clear();
        searchClub.addItem("<<ANY SUBJECT>>");
        for (ClubDTO club : clubsList) {
            searchClub.addItem(club.getClubName());
        }

        manageClubs.clear();
        for (ClubDTO club : clubsList) {
            manageClubs.addItem(club.getClubName());
        }
    }

    @UiHandler("searchStudents")
    void onSearchStudentsButtonClicked(ClickEvent event) {
        HashSet<SubjectDTO> ss = new HashSet<SubjectDTO>();
        ss.add(new SubjectDTO(0, searchSubject.getSelectedItemText()));

        HashSet<ClubDTO> sc = new HashSet<ClubDTO>();
        sc.add(new ClubDTO(0, searchClub.getSelectedItemText()));

        if (presenter != null) {
            presenter.onSearchStudentsButtonClicked(new StudentDTO(0, searchFirstName.getText(),
                    searchLastName.getText(), searchBirthday.getValue(), ss, sc));
        }
    }//

    @UiHandler("addStudent")
    void onAddStudentButtonClicked(ClickEvent event) {
        HashSet<SubjectDTO> ms = new HashSet<SubjectDTO>();
        for(int i =0; i < manageSubjects.getItemCount(); i++){
            if(manageSubjects.isItemSelected(i)){
                ms.add(new SubjectDTO(0, manageSubjects.getItemText(i)));
            }
        }

        HashSet<ClubDTO> mc = new HashSet<ClubDTO>();
        for(int i =0; i < manageClubs.getItemCount(); i++){
            if(manageClubs.isItemSelected(i)){
                mc.add(new ClubDTO(0, manageClubs.getItemText(i)));
            }
        }

        if (presenter != null) {
            presenter.onAddStudentButtonClicked(new StudentDTO(0, manageFirstName.getText(),
                    manageLastName.getText(), manageBirthday.getValue(), ms, mc));
            refreshSearchView();
        }
    }

    public void refreshSearchView(){
        HashSet<SubjectDTO> ss = new HashSet<SubjectDTO>();
        ss.add(new SubjectDTO(0, searchSubject.getSelectedItemText()));

        HashSet<ClubDTO> sc = new HashSet<ClubDTO>();
        sc.add(new ClubDTO(0, searchClub.getSelectedItemText()));

        presenter.onSearchStudentsButtonClicked(new StudentDTO(0, searchFirstName.getText(),
                searchLastName.getText(), searchBirthday.getValue(), ss, sc));
    }

    @UiHandler("deleteStudent")
    void onDeleteStudentButtonClicked(ClickEvent event) {
        if (presenter != null) {
            presenter.onDeleteStudentButtonClicked(selectedStudent);
        }
    }

    @UiHandler("editStudent")
    void onEditStudentButtonClicked(ClickEvent event) {
        HashSet<SubjectDTO> ms = new HashSet<SubjectDTO>();
        for(int i =0; i < manageSubjects.getItemCount(); i++){
            if(manageSubjects.isItemSelected(i)){
                ms.add(new SubjectDTO(0, manageSubjects.getItemText(i)));
            }
        }

        HashSet<ClubDTO> mc = new HashSet<ClubDTO>();
        for(int i =0; i < manageClubs.getItemCount(); i++){
            if(manageClubs.isItemSelected(i)){
                mc.add(new ClubDTO(0, manageClubs.getItemText(i)));
            }
        }

        if (presenter != null) {
            presenter.onEditStudentButtonClicked(new StudentDTO(selectedStudent.getId(), manageFirstName.getText(),
                    manageLastName.getText(), manageBirthday.getValue(), ms, mc));
        }
    }

    public void displayStudents(ArrayList<StudentDTO> Students) {
        studentGrid.setRowCount(Students.size(), true);
        studentGrid.setRowData(0, Students);
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

    @UiHandler("switchToRefSubjectView")
    void onSwitchToRefSubjectViewClicked(ClickEvent event) {
        if (presenter != null) {
            presenter.onSwitchToRefSubjectViewClicked();
        }
    }

    @UiHandler("switchToRefClubView")
    void onSwitchToRefClubViewClicked(ClickEvent event) {
        if (presenter != null) {
            presenter.onSwitchToRefClubViewClicked();
        }
    }

    public void setPresenter(StudentView.Presenter presenter) {
        this.presenter = presenter;
    }

    public Widget asWidget() {
        return this;
    }
}