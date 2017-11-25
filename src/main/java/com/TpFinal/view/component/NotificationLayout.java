package com.TpFinal.view.component;

import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;


public class NotificationLayout extends VerticalLayout{
    private String identification="";
    private Label titleLabel;
    private Label timeLabel;
    private Label contentLabel;



    public NotificationLayout(String identification, String titleLabel,
                              String timeLabel, String contentLabel){
        super();
        this.identification=identification;
        this.titleLabel=new Label(titleLabel);
        this.titleLabel.addStyleName("notification-title");
        this.timeLabel=new Label(timeLabel);
        this.timeLabel.addStyleName("notification-time");
        this.contentLabel=new Label(contentLabel);
        this.contentLabel.addStyleName("notification-content");
        setMargin(false);
        setSpacing(false);

        addStyleName("notification-item");
        addComponents(this.titleLabel, this.timeLabel,
                this.contentLabel);

    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(Label titleLabel) {
        this.titleLabel = titleLabel;
    }

    public Label getTimeLabel() {
        return timeLabel;
    }

    public void setTimeLabel(Label timeLabel) {
        this.timeLabel = timeLabel;
    }

    public Label getContentLabel() {
        return contentLabel;
    }

    public void setContentLabel(Label contentLabel) {
        this.contentLabel = contentLabel;
    }

}
