package ua.university.client.presenter;


import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import ua.university.client.UniversityServiceAsync;
import ua.university.client.entity.ClubDTO;
import ua.university.client.entity.StudentDTO;
import ua.university.client.entity.SubjectDTO;
import ua.university.client.event.*;
import ua.university.client.view.StudentView;
import ua.university.client.view.TeacherView;
import ua.university.client.entity.TeacherDTO;

import java.util.*;

public class StudentPresenter implements Presenter,
        StudentView.Presenter {

    private final StudentView view;
    private final UniversityServiceAsync rpcService;
    private final HandlerManager eventBus;

    //private final ArrayList<SubjectDTO> subjects = new ArrayList<SubjectDTO>();

    public StudentPresenter(UniversityServiceAsync rpcService,
                            HandlerManager eventBus, StudentView view) {
        this.rpcService = rpcService;
        this.eventBus = eventBus;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void go(final HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
        view.initSearchGrid();
        setSubjects();
        setClubs();
    }

    /*public void setSubjectsList(){
        rpcService.getSubjects(new AsyncCallback<Set<SubjectDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Fetching subjects from DB error!");
            }

            @Override
            public void onSuccess(Set<SubjectDTO> result){
                view.setSubjectList(result);

            }
        });
    }*/

    public void onSwitchToProjectDescrViewClicked(){
        eventBus.fireEvent(new SwitchToProjectDescrViewEvent());
    }
	
    public void onSwitchToTeacherViewClicked(){
        eventBus.fireEvent(new SwitchToTeacherViewEvent());
    }

    public void onSwitchToRefSubjectViewClicked(){
        eventBus.fireEvent(new SwitchToRefSubjectViewEvent());
    }

    public void onSwitchToRefClubViewClicked(){
        eventBus.fireEvent(new SwitchToRefClubViewEvent());
    }

    public void onSearchStudentsButtonClicked(StudentDTO searchStudentDTO) {
        rpcService.searchStudents(searchStudentDTO, new AsyncCallback<ArrayList<StudentDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Can't fetch Students from DB!");
            }

            @Override
            public void onSuccess(ArrayList<StudentDTO> result) {
                view.displayStudents(result);
            }
        });
    }



    public void onAddStudentButtonClicked(StudentDTO studentDTO) {
        rpcService.addStudent(studentDTO,
                new AsyncCallback<StudentDTO>() {
                    public void onSuccess(StudentDTO result) {
                        view.refreshSearchView();
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Student adding to DB error!");
                    }
                });
    }

    public void onDeleteStudentButtonClicked(StudentDTO studentDTO) {
        rpcService.deleteStudent(studentDTO,
                new AsyncCallback<StudentDTO>() {
                    public void onSuccess(StudentDTO result) {
                        view.refreshSearchView();
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Student deleting from DB error!");
                    }
                });
    }

    public void onEditStudentButtonClicked(StudentDTO studentDTO) {
        rpcService.editStudent(studentDTO,
                new AsyncCallback<StudentDTO>() {
                    public void onSuccess(StudentDTO result) {
                        view.refreshSearchView();
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Student editing in DB error!");
                    }
                });
    }

    public void setSubjects(){
        rpcService.getSubjects(new AsyncCallback<Set<SubjectDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Fetching subjects from DB error!");
            }

            @Override
            public void onSuccess(Set<SubjectDTO> result){
                view.setSubjectLists(result);

            }
        });
    }

    public void setClubs(){
        rpcService.getClubs(new AsyncCallback<Set<ClubDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Fetching subjects from DB error!");
            }

            @Override
            public void onSuccess(Set<ClubDTO> result){
                view.setClubLists(result);

            }
        });
    }
}
