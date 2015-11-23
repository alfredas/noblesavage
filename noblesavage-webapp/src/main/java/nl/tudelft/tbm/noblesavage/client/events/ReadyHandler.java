package nl.tudelft.tbm.noblesavage.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface ReadyHandler extends EventHandler {
    void onReady(ReadyEvent event);

    void onNotReady(ReadyEvent event);

    void needRefresh(ReadyEvent event);
}
