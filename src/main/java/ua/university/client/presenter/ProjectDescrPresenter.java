package ua.university.client.presenter;


import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import ua.university.client.UniversityServiceAsync;
import ua.university.client.entity.SubjectDTO;
import ua.university.client.event.SwitchToProjectDescrViewEvent;
import ua.university.client.view.ProjectDescrView;
import ua.university.client.view.TeacherView;
import ua.university.client.event.AddTeacherEvent;
import ua.university.client.entity.TeacherDTO;
import ua.university.client.event.SwitchToRefSubjectViewEvent;
import ua.university.client.event.SwitchToTeacherViewEvent;
import ua.university.client.event.SwitchToStudentViewEvent;
import ua.university.client.event.SwitchToRefClubViewEvent;

import java.util.*;

public class ProjectDescrPresenter implements Presenter,
        ProjectDescrView.Presenter {

    private final ProjectDescrView view;
    private final UniversityServiceAsync rpcService;
    private final HandlerManager eventBus;

    //private final ArrayList<SubjectDTO> subjects = new ArrayList<SubjectDTO>();

    public ProjectDescrPresenter(UniversityServiceAsync rpcService,
                            HandlerManager eventBus, ProjectDescrView view) {
        this.rpcService = rpcService;
        this.eventBus = eventBus;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void go(final HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }

	
    public void onSwitchToTeacherViewClicked(){
        eventBus.fireEvent(new SwitchToTeacherViewEvent());
    }

    public void onSwitchToStudentViewClicked(){
        eventBus.fireEvent(new SwitchToStudentViewEvent());
    }

    public void onSwitchToRefSubjectViewClicked(){
        eventBus.fireEvent(new SwitchToRefSubjectViewEvent());
    }

    public void onSwitchToRefClubViewClicked(){
        eventBus.fireEvent(new SwitchToRefClubViewEvent());
    }
}
