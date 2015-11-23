package nl.tudelft.tbm.noblesavage.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface UploadHandler extends EventHandler {
    void onUpload(UploadEvent event);
}
