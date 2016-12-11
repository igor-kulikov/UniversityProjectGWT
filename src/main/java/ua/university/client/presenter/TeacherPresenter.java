package ua.university.client.presenter;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import ua.university.client.UniversityServiceAsync;
import ua.university.client.entity.SubjectDTO;
import ua.university.client.event.*;
import ua.university.client.view.TeacherView;
import ua.university.client.entity.TeacherDTO;
import ua.university.client.event.SwitchToStudentViewEvent;


import java.util.*;

public class TeacherPresenter implements Presenter,
        TeacherView.Presenter {

    private final TeacherView view;
    private final UniversityServiceAsync rpcService;
    private final HandlerManager eventBus;

    //private final ArrayList<SubjectDTO> subjects = new ArrayList<SubjectDTO>();

    public TeacherPresenter(UniversityServiceAsync rpcService,
                            HandlerManager eventBus, TeacherView view) {
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
    }

    public void onSwitchToProjectDescrViewClicked() {
        eventBus.fireEvent(new SwitchToProjectDescrViewEvent());
    }

    public void onSwitchToStudentViewClicked() {
        eventBus.fireEvent(new SwitchToStudentViewEvent());
    }

    public void onSwitchToRefSubjectViewClicked() {
        eventBus.fireEvent(new SwitchToRefSubjectViewEvent());
    }

    public void onSwitchToRefClubViewClicked() {
        eventBus.fireEvent(new SwitchToRefClubViewEvent());
    }


    public void onSearchTeachersButtonClicked(TeacherDTO searchTeacherDTO) {
        rpcService.searchTeachers(searchTeacherDTO, new AsyncCallback<ArrayList<TeacherDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Can't fetch Teachers from DB!");
            }

            @Override
            public void onSuccess(ArrayList<TeacherDTO> result) {
                view.displayTeachers(result);
            }
        });
    }

    public void onAddTeacherButtonClicked(TeacherDTO teacherDTO) {
        rpcService.addTeacher(teacherDTO,
                new AsyncCallback<TeacherDTO>() {
                    public void onSuccess(TeacherDTO result) {
                        view.refreshSearchView();
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Teacher adding to DB error!");
                    }
                });
    }

    public void onDeleteTeacherButtonClicked(TeacherDTO teacherDTO) {
        rpcService.deleteTeacher(teacherDTO,
                new AsyncCallback<TeacherDTO>() {
                    public void onSuccess(TeacherDTO result) {
                        view.refreshSearchView();
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Teacher deleting from DB error!");
                    }
                });
    }

    public void onEditTeacherButtonClicked(TeacherDTO teacherDTO) {
        rpcService.editTeacher(teacherDTO,
                new AsyncCallback<TeacherDTO>() {
                    public void onSuccess(TeacherDTO result) {
                        view.refreshSearchView();
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Teacher editing in DB error!");
                    }
                });
    }

    public void setSubjects() {
        rpcService.getSubjects(new AsyncCallback<Set<SubjectDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Fetching subjects from DB error!");
            }

            @Override
            public void onSuccess(Set<SubjectDTO> result) {
                view.setSubjectList(result);

            }
        });
    }
}
