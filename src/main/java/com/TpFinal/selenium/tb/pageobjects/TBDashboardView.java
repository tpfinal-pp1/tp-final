package com.TpFinal.selenium.tb.pageobjects;

import com.TpFinal.view.DashboardView;
import org.openqa.selenium.WebDriver;


import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.LabelElement;

public class TBDashboardView extends TestBenchTestCase {

    public TBDashboardView(WebDriver driver) {
        setDriver(driver);

    }


    public String getDashboardTitle() {
        return $(LabelElement.class).id(DashboardView.TITLE_ID).getText();
    }

    public int getUnreadNotificationsCount() {
        int result = 0;
        String caption = $(ButtonElement.class).id(DashboardView.NotificationsButton.ID)
                .getCaption();
        if (caption != null && !caption.isEmpty()) {
            result = Integer.parseInt(caption);
        }
        return result;
    }

    public void openNotifications() {
        $(ButtonElement.class).id(DashboardView.NotificationsButton.ID).click();
    }

}
