package com.h3c.dropbox.listener;

import com.h3c.dropbox.TimerManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {


    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
//        new TimerManager();
    }

}
