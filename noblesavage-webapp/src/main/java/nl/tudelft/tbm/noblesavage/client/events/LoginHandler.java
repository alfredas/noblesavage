package nl.tudelft.tbm.noblesavage.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface LoginHandler extends EventHandler {
  void onLogin(LoginEvent event);
  void onLogout(LoginEvent event);
}
