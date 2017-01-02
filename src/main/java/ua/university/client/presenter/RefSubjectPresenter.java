package ua.university.client.presenter;


import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import ua.university.client.UniversityServiceAsync;
import ua.university.client.entity.SubjectDTO;
import ua.university.client.event.*;
import ua.university.client.view.RefSubjectView;
import ua.university.client.event.SwitchToTeacherViewEvent;

import java.util.*;

public class RefSubjectPresenter implements Presenter,
        RefSubjectView.Presenter {

    private final RefSubjectView view;
    private final UniversityServiceAsync rpcService;
    private final HandlerManager eventBus;

    public RefSubjectPresenter(UniversityServiceAsync rpcService,
                            HandlerManager eventBus, RefSubjectView view) {
        this.rpcService = rpcService;
        this.eventBus = eventBus;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void go(final HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
        setSubjects();
    }

    public void onSwitchToProjectDescrViewClicked(){
        eventBus.fireEvent(new SwitchToProjectDescrViewEvent());
    }

    public void onSwitchToTeacherViewClicked(){
        eventBus.fireEvent(new SwitchToTeacherViewEvent());
    }

    public void onSwitchToStudentViewClicked(){
        eventBus.fireEvent(new SwitchToStudentViewEvent());
    }

    public void onSwitchToRefClubViewClicked(){
        eventBus.fireEvent(new SwitchToRefClubViewEvent());
    }

    public void onAddSubjectButtonClicked(SubjectDTO SubjectDTO) {
        rpcService.addSubject(SubjectDTO,
                new AsyncCallback<SubjectDTO>() {
                    public void onSuccess(SubjectDTO result) {
                        setSubjects();
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Subject adding to DB error!");
                    }
                });
    }

    public void onDeleteSubjectButtonClicked(SubjectDTO SubjectDTO) {
        rpcService.deleteSubject(SubjectDTO,
                new AsyncCallback<SubjectDTO>() {
                    public void onSuccess(SubjectDTO result) {
                        setSubjects();
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Subject deleting from DB error!");
                    }
                });
    }

    public void onEditSubjectButtonClicked(SubjectDTO SubjectDTO) {
        rpcService.editSubject(SubjectDTO,
                new AsyncCallback<SubjectDTO>() {
                    public void onSuccess(SubjectDTO result) {
                        setSubjects();
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Subject editing in DB error!");
                    }
                });
    }

    private void setSubjects(){
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
    }
}
