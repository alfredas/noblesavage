package nl.tudelft.tbm.noblesavage.client;

import nl.tudelft.tbm.noblesavage.client.components.MainPane;
import nl.tudelft.tbm.noblesavage.client.components.SplashScreen;
import nl.tudelft.tbm.noblesavage.client.events.EventBus;
import nl.tudelft.tbm.noblesavage.client.events.ReadyEvent;
import nl.tudelft.tbm.noblesavage.client.events.ReadyHandler;
import nl.tudelft.tbm.noblesavage.client.log.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class Client implements EntryPoint {

    final static String MAIN_DIV_ID = "mainPane";
    final static String LOG_DIV_ID = "logPane";

    private final Logger logger;
    private final MainPane mainPane;
    private final SplashScreen splashScreenPane;

    public Client() {
        logger = new Logger();
        mainPane = new MainPane(new ClientState(), new ServiceRegistry(), logger);
        splashScreenPane = new SplashScreen();
    }

    public void onModuleLoad() {
        RootPanel.get(MAIN_DIV_ID).add(mainPane);
        // RootPanel.get(LOG_DIV_ID).add(logger);

        // show splash screen
        EventBus.get().addHandler(ReadyEvent.TYPE, new ReadyHandler() {

            public void onReady(ReadyEvent event) {
                if (event.getMessage() != null && event.getMessage().length() > 0) {
                    splashScreenPane.showCloseButton(true);
                    splashScreenPane.setMessage(event.getMessage());
                } else {
                    splashScreenPane.hide();
                }
            }

            public void onNotReady(ReadyEvent event) {
                splashScreenPane.showCloseButton(false);
                splashScreenPane.setMessage(event.getMessage());
                splashScreenPane.center();
                splashScreenPane.show();
            }

            public void needRefresh(ReadyEvent event) {
                onReady(event);
            }
        });
    }

}
