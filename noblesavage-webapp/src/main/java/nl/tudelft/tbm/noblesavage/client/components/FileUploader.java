package nl.tudelft.tbm.noblesavage.client.components;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.Utils;
import gwtupload.client.SingleUploader;
import nl.tudelft.tbm.noblesavage.client.ClientState;
import nl.tudelft.tbm.noblesavage.client.ServiceRegistry;
import nl.tudelft.tbm.noblesavage.client.events.EventBus;
import nl.tudelft.tbm.noblesavage.client.events.UploadEvent;
import nl.tudelft.tbm.noblesavage.client.log.Logger;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

public class FileUploader extends Pane {

    private String fileUrl;

    protected FileUploader(ClientState clientState, ServiceRegistry serviceRegistry, Logger logger) {
        super(clientState, serviceRegistry, logger, FileUploader.class.getName());
        SingleUploader singleUploader = new SingleUploader();
        singleUploader.setValidExtensions("zip");
        singleUploader.addOnFinishUploadHandler(onFinishUploaderHandler);
        initWidget(singleUploader);
    }

    private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
        public void onFinish(IUploader uploader) {
            if (uploader.getStatus() == Status.SUCCESS) {
                // weird bug with the event firing twice
                if (uploader.getFileName() != null && uploader.getFileName().length() > 0) {
                    Document doc = XMLParser.parse(uploader.getServerResponse());
                    String message = Utils.getXmlNodeValue(doc, "message");
                    Document messageDoc = XMLParser.parse(message);
                    String filePath = Utils.getXmlNodeValue(messageDoc, "file-1-path");
                    log("File path: " + filePath);
                    EventBus.get().fireEvent(new UploadEvent(filePath, uploader.getFileName()));
                }
            }
        }
    };

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

}
