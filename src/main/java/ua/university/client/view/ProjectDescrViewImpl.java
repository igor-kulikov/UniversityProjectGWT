package ua.university.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Created by Win7 on 15.10.2016.
 */
public class ProjectDescrViewImpl extends Composite implements ProjectDescrView{

    @UiTemplate("ProjectDescrView.ui.xml")
    interface ProjectDescrViewUiBinder extends UiBinder<Widget, ProjectDescrViewImpl> {
    }

    private ProjectDescrView.Presenter presenter;

    private static ProjectDescrViewUiBinder ourUiBinder = GWT.create(ProjectDescrViewUiBinder.class);

    public ProjectDescrViewImpl() {
        initWidget(ourUiBinder.createAndBindUi(this));
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

    @UiHandler("switchToRefClubView")
    void onSwitchToRefClubViewClicked(ClickEvent event){
        if(presenter != null){
            presenter.onSwitchToRefClubViewClicked();
        }
    }

    public void setPresenter(ProjectDescrView.Presenter presenter) {
        this.presenter = presenter;
    }

    public Widget asWidget() {
        return this;
    }
}