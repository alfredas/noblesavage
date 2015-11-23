package nl.tudelft.tbm.noblesavage.client.components;

import nl.tudelft.tbm.noblesavage.client.ClientState;
import nl.tudelft.tbm.noblesavage.client.ServiceRegistry;
import nl.tudelft.tbm.noblesavage.client.log.Logger;

import com.google.gwt.user.client.ui.Composite;

public abstract class Pane extends Composite {

  private final ServiceRegistry serviceRegistry;
  private final ClientState clientState;
  private final Logger logger;
  private final String title;
  private boolean selected;
  private boolean dirty = false;

  protected Pane(ClientState clientState, ServiceRegistry serviceRegistry, Logger logger, String title) {
    this.serviceRegistry = serviceRegistry;
    this.clientState = clientState;
    this.logger = logger;
    this.title = title;
  }

  protected ServiceRegistry getServiceRegistry() {
    if (serviceRegistry == null) {
      throw new RuntimeException("serviceRegistry state in uninitialized");
    }
    return serviceRegistry;
  }

  public ClientState getClientState() {
    if (clientState == null) {
      throw new RuntimeException("client state in uninitialized");
    }
    return clientState;
  }

  public Logger getLogger() {
    if (logger == null) {
      throw new RuntimeException("logger not provided");
    }
    return logger;
  }

  public void log(String message) {
    getLogger().log("[" + getTitle() + "] " + message);
  }

  @Override
  public String getTitle() {
    return title;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public boolean isDirty() {
    return dirty;
  }

  public void setDirty(boolean dirty) {
    this.dirty = dirty;
  }

}
