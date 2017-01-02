package ua.university.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

import ua.university.client.event.*;
import ua.university.client.presenter.*;
import ua.university.client.view.*;

public class AppController implements Presenter, ValueChangeHandler<String> {
    private final HandlerManager eventBus;
    private final UniversityServiceAsync rpcService;
    private HasWidgets container;

    private ProjectDescrViewImpl projectDescrView = null;
    private TeacherViewImpl teacherView = null;
    private StudentViewImpl studentView = null;
    private RefSubjectViewImpl refSubjectView = null;
    private RefClubViewImpl refClubView = null;

    public AppController(UniversityServiceAsync rpcService, HandlerManager eventBus) {
        this.eventBus = eventBus;
        this.rpcService = rpcService;
        bind();
    }

    private void bind() {
        History.addValueChangeHandler(this);

        eventBus.addHandler(AddTeacherEvent.TYPE,
                new AddTeacherEventHandler() {
                    public void onAddTeacher(AddTeacherEvent event) {
                        doAddNewTeacher();
                    }
                });

        eventBus.addHandler(SwitchToProjectDescrViewEvent.TYPE,
                new SwitchToProjectDescrViewEventHandler() {
                    public void onSwitchToProjectDescrView(SwitchToProjectDescrViewEvent event) {
                        doSwitchToProjectDescrView();
                    }
                });

        eventBus.addHandler(SwitchToTeacherViewEvent.TYPE,
                new SwitchToTeacherViewEventHandler() {
                    public void onSwitchToTeacherView(SwitchToTeacherViewEvent event) {
                        doSwitchToTeacherView();
                    }
                });

        eventBus.addHandler(SwitchToStudentViewEvent.TYPE,
                new SwitchToStudentViewEventHandler() {
                    public void onSwitchToStudentView(SwitchToStudentViewEvent event) {
                        doSwitchToStudentView();
                    }
                });

        eventBus.addHandler(SwitchToRefSubjectViewEvent.TYPE,
                new SwitchToRefSubjectViewEventHandler() {
                    public void onSwitchToRefSubjectView(SwitchToRefSubjectViewEvent event) {
                        doSwitchToRefSubjectView();
                    }
                });

        eventBus.addHandler(SwitchToRefClubViewEvent.TYPE,
                new SwitchToRefClubViewEventHandler() {
                    public void onSwitchToRefClubView(SwitchToRefClubViewEvent event) {
                        doSwitchToRefClubView();
                    }
                });
    }

    private void doAddNewTeacher() {
        History.newItem("add");
    }

    private void doSwitchToProjectDescrView() {
        History.newItem("project_descr");
    }

    private void doSwitchToTeacherView() {
        History.newItem("teacher");
    }

    private void doSwitchToStudentView() {
        History.newItem("student");
    }

    private void doSwitchToRefSubjectView() {
        History.newItem("ref_subjects");
    }

    private void doSwitchToRefClubView() {
        History.newItem("ref_clubs");
    }

    public void go(final HasWidgets container) {
        this.container = container;

        if ("".equals(History.getToken())) {
            History.newItem("project_descr");
        } else {
            History.fireCurrentHistoryState();
        }
    }

    public void onValueChange(ValueChangeEvent<String> event) {
        String token = event.getValue();
        if (token != null) {
        if (token.equals("project_descr")) {
            GWT.runAsync(new RunAsyncCallback() {
                public void onFailure(Throwable caught) {
                }

                public void onSuccess() {
                    // lazily initialize our views, and keep them around to be reused
                    //
                    if (projectDescrView == null) {
                        projectDescrView = new ProjectDescrViewImpl();
                    }
                    new ProjectDescrPresenter(rpcService, eventBus, projectDescrView).go(container);
                }
            });
        }
        else if (token.equals("teacher")) {
                GWT.runAsync(new RunAsyncCallback() {
                    public void onFailure(Throwable caught) {
                    }

                    public void onSuccess() {
                        // lazily initialize our views, and keep them around to be reused
                        //
                        if (teacherView == null) {
                            teacherView = new TeacherViewImpl();
                        }
                        new TeacherPresenter(rpcService, eventBus, teacherView).go(container);
                    }
                });
            }
            //else if (token.equals("add") || token.equals("edit")) {
            else if (token.equals("student")) {
                GWT.runAsync(new RunAsyncCallback() {
                    public void onFailure(Throwable caught) {
                    }

                    public void onSuccess() {
                        // lazily initialize our views, and keep them around to be reused
                        //
                        if (studentView == null) {
                            studentView = new StudentViewImpl();
                        }
                        new StudentPresenter(rpcService, eventBus, studentView).go(container);
                    }
                });
            }
            else if (token.equals("ref_subjects")) {
                GWT.runAsync(new RunAsyncCallback() {
                    public void onFailure(Throwable caught) {
                    }

                    public void onSuccess() {
                        // lazily initialize our views, and keep them around to be reused
                        //
                        if (refSubjectView == null) {
                            refSubjectView = new RefSubjectViewImpl();
                        }
                        new RefSubjectPresenter(rpcService, eventBus, refSubjectView).go(container);
                    }
                });
            }
            else if (token.equals("ref_clubs")) {
                GWT.runAsync(new RunAsyncCallback() {
                    public void onFailure(Throwable caught) {
                    }

                    public void onSuccess() {
                        // lazily initialize our views, and keep them around to be reused
                        //
                        if (refClubView == null) {
                            refClubView = new RefClubViewImpl();
                        }
                        new RefClubPresenter(rpcService, eventBus, refClubView).go(container);
                    }
                });
            }
        }
    }
}
