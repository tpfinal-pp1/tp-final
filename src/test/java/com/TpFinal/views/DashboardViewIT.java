package com.TpFinal.views;

import com.TpFinal.views.pageobjects.TBDashboardView;

import com.TpFinal.views.pageobjects.TBLoginView;
import com.TpFinal.views.pageobjects.TBMainView;
import org.junit.Assert;
import org.junit.Before;


import com.vaadin.testbench.TestBenchTestCase;

public class DashboardViewIT extends TestBenchTestCase {

    private TBLoginView loginView;
    private TBMainView mainView;

    @Before
    public void setUp() {
        setDriver(TBUtils.initializeDriver());
        loginView = TBUtils.loginView(this.getDriver());
        mainView = loginView.login();
    }


    public void testReadNotifications() {
        TBDashboardView dashboardView = mainView.openDashboardView();
        Assert.assertEquals(mainView.getUnreadNotificationsCount(),
                dashboardView.getUnreadNotificationsCount());
        dashboardView.openNotifications();
        Assert.assertEquals(mainView.getUnreadNotificationsCount(),
                dashboardView.getUnreadNotificationsCount());
    }


}
