package nl.tudelft.tbm.noblesavage.client.events;

import com.google.gwt.event.shared.HandlerManager;

public class EventBus {

  private static final HandlerManager INSTANCE = new HandlerManager(null);

  public static HandlerManager get() {
    return INSTANCE;
  }
}
