package nl.tudelft.tbm.noblesavage.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class UploadEvent extends GwtEvent<UploadHandler> {

    private String fileUrl;
    private String fileName;

    public static final Type<UploadHandler> TYPE = new Type<UploadHandler>();

    public UploadEvent(String fileUrl, String fileName) {
        this.fileUrl = fileUrl;
        this.fileName = fileName;
    }

    @Override
    protected void dispatch(UploadHandler handler) {
        handler.onUpload(this);
    }

    @Override
    public Type<UploadHandler> getAssociatedType() {
        return TYPE;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}