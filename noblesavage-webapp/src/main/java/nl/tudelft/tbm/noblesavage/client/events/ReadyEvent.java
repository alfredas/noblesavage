package nl.tudelft.tbm.noblesavage.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class ReadyEvent extends GwtEvent<ReadyHandler> {

    public static final int IS_READY = 1;
    public static final int IS_NOT_READY = 0;
    public static final int NEED_REFRESH = 2;

    private String message;
    private int eventType;

    public static final Type<ReadyHandler> TYPE = new Type<ReadyHandler>();

    public ReadyEvent(int eventType, String message) {
        this.eventType = eventType;
        this.message = message;
    }

    @Override
    protected void dispatch(ReadyHandler handler) {
        switch (this.getEventType()) {
        case IS_NOT_READY:
            handler.onNotReady(this);
            break;
        case NEED_REFRESH:
            handler.needRefresh(this);
            break;
        case IS_READY:
            handler.onReady(this);
            break;
        }
    }

    @Override
    public Type<ReadyHandler> getAssociatedType() {
        return TYPE;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
}
