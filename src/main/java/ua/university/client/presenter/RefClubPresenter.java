package ua.university.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import ua.university.client.UniversityServiceAsync;
import ua.university.client.entity.ClubDTO;
import ua.university.client.event.*;
import ua.university.client.view.RefClubView;
import ua.university.client.event.SwitchToTeacherViewEvent;

import java.util.*;

public class RefClubPresenter implements Presenter,
        RefClubView.Presenter {

    private final RefClubView view;
    private final UniversityServiceAsync rpcService;
    private final HandlerManager eventBus;

    public RefClubPresenter(UniversityServiceAsync rpcService,
                            HandlerManager eventBus, RefClubView view) {
        this.rpcService = rpcService;
        this.eventBus = eventBus;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void go(final HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
        setClubs();
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

    public void onSwitchToRefSubjectViewClicked(){
        eventBus.fireEvent(new SwitchToRefSubjectViewEvent());
    }

    public void onAddClubButtonClicked(ClubDTO ClubDTO) {
        rpcService.addClub(ClubDTO,
                new AsyncCallback<ClubDTO>() {
                    public void onSuccess(ClubDTO result) {
                        setClubs();
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Club adding to DB error!");
                    }
                });
    }

    public void onDeleteClubButtonClicked(ClubDTO ClubDTO) {
        rpcService.deleteClub(ClubDTO,
                new AsyncCallback<ClubDTO>() {
                    public void onSuccess(ClubDTO result) {
                        setClubs();
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Club deleting from DB error!");
                    }
                });
    }

    public void onEditClubButtonClicked(ClubDTO ClubDTO) {
        rpcService.editClub(ClubDTO,
                new AsyncCallback<ClubDTO>() {
                    public void onSuccess(ClubDTO result) {
                        setClubs();
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Club editing in DB error!");
                    }
                });
    }

    private void setClubs(){
        rpcService.getClubs(new AsyncCallback<Set<ClubDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Fetching clubs from DB error!");
            }

            @Override
            public void onSuccess(Set<ClubDTO> result){
                view.setClubList(result);
            }
        });
    }
}
