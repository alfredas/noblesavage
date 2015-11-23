package nl.tudelft.tbm.noblesavage.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class LoginEvent extends GwtEvent<LoginHandler> {

	public static final int IS_LOGGED_IN = 1;
	public static final int IS_LOGGED_OUT = 0;

	private int eventType;

	public static final Type<LoginHandler> TYPE = new Type<LoginHandler>();

	public LoginEvent(int eventType) {
		this.eventType = eventType;
	}

	@Override
	protected void dispatch(LoginHandler handler) {
		switch (this.getEventType()) {
		case IS_LOGGED_IN:
			handler.onLogin(this);
			break;
		case IS_LOGGED_OUT:
			handler.onLogout(this);
			break;
		}
	}

	@Override
	public Type<LoginHandler> getAssociatedType() {
		return TYPE;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
}
