package nl.tudelft.tbm.noblesavage.client.components;

import java.util.HashMap;

import nl.tudelft.tbm.noblesavage.client.ClientState;
import nl.tudelft.tbm.noblesavage.client.ServiceRegistry;
import nl.tudelft.tbm.noblesavage.client.events.CorpusEvent;
import nl.tudelft.tbm.noblesavage.client.events.EventBus;
import nl.tudelft.tbm.noblesavage.client.events.ReadyEvent;
import nl.tudelft.tbm.noblesavage.client.events.UploadEvent;
import nl.tudelft.tbm.noblesavage.client.events.UploadHandler;
import nl.tudelft.tbm.noblesavage.client.log.Logger;
import nl.tudelft.tbm.noblesavage.facade.CorpusDTO;
import nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CreateCorpusFormPane extends Pane {

    private final static String READER_TYPE_STRING = "readerType";
    private final static String[] LANGUAGES = { "english", "dutch" };
    final Button getButton = new Button("Create");
    HandlerRegistration getButtonHandlerRegistration = null;

    protected CreateCorpusFormPane(ClientState clientState, ServiceRegistry serviceRegistry, Logger logger) {
        super(clientState, serviceRegistry, logger, CreateCorpusFormPane.class.getName());
        HorizontalPanel hp = createNewCorpusPanel();
        initWidget(hp);
    }

    /*
     * PRELOAD: get the reader types
     */
    private void getReaderTypes(final ListBox typesBox) {
        log("getting reader types");
        EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_NOT_READY, "Getting configured data sources..."));
        getServiceRegistry().getCorpusService().getCorpusReaderTypes(new AsyncCallback<String[]>() {
            public void onFailure(Throwable caught) {
                EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, "Error: " + caught.getMessage()));
                log("error getting reader types: " + caught.getMessage());
            }

            public void onSuccess(String[] result) {
                EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, null));
                if (result != null) {
                    for (String type : result) {
                        typesBox.addItem(type);
                    }
                }
            }
        });
    }

    /*
     * Creates a New Query Panel - part of the Query Tab
     */
    private HorizontalPanel createNewCorpusPanel() {
        log("creating new query panel");
        HorizontalPanel hp = new HorizontalPanel();
        hp.setWidth("50%");
        hp.setSpacing(5);

        final VerticalPanel p = new VerticalPanel();
        p.setWidth("50%");
        p.setSpacing(5);
        p.add(new Label("New Query"));

        final ListBox readerTypesBox = new ListBox();
        final VerticalPanel gridPanel = new VerticalPanel();

        p.add(readerTypesBox);
        p.add(gridPanel);
        readerTypesBox.addItem("== Select Source ==");

        getReaderTypes(readerTypesBox);
        readerTypesBox.addChangeHandler(new ChangeHandler() {

            public void onChange(ChangeEvent event) {
                if (readerTypesBox.getSelectedIndex() > 0) {
                    String type = readerTypesBox.getValue(readerTypesBox.getSelectedIndex());
                    log("value changed to " + type);
                    getFieldsByReaderType(gridPanel, type);
                }
            }
        });

        hp.add(p);

        return hp;
    }

    private void getFieldsByReaderType(final VerticalPanel panel, final String type) {
        EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_NOT_READY, "Getting configuration parameters..."));
        getServiceRegistry().getCorpusService().getReaderParameters(type, new AsyncCallback<ReaderParameterDTO[]>() {
            public void onFailure(Throwable caught) {
                EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, "Error: " + caught.getMessage()));
                log("error getFieldsByReaderType: " + caught.getMessage());
            }

            public void onSuccess(ReaderParameterDTO[] result) {
                EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, null));
                try {
                    panel.remove(0);
                } catch (Exception err) {
                    log("Error getFieldsByReaderType: " + err.getMessage());
                }
                panel.add(createParameterGrid(result, type));
            }
        });
    }

    private Grid createParameterGrid(final ReaderParameterDTO[] fields, final String type) {
        log("creating parameter grid with " + fields.length + " values");
        Grid g = new Grid(fields.length + 2, 3);

        final HashMap<String, Widget> readerParameters = new HashMap<String, Widget>();

        TextBox rtb = new TextBox();
        rtb.setText(type);
        TextBox ptb = new TextBox();
        ptb.setText("Web Page");

        getButton.setEnabled(true);

        readerParameters.put(READER_TYPE_STRING, rtb);

        for (int i = 0; i < fields.length; i++) {
            g.setWidget(i, 0, new Label(fields[i].getName()));
            if (fields[i].getType().equals("text")) {
                TextBox tb = new TextBox();
                tb.addStyleName("ns-smallInput");
                g.setWidget(i, 1, tb);
                readerParameters.put(fields[i].getParam(), tb);
            } else if (fields[i].getType().equals("number")) {
                TextBox tb = new TextBox();
                tb = new TextBox();
                tb.addStyleName("ns-verySmallInput");
                g.setWidget(i, 1, tb);
                readerParameters.put(fields[i].getParam(), tb);
            } else if (fields[i].getType().equals("file")) {
                final FileUploader fu = new FileUploader(getClientState(), getServiceRegistry(), getLogger());
                final Label fileNameLabel = new Label();
                fileNameLabel.setVisible(false);

                VerticalPanel vp = new VerticalPanel();
                vp.add(fu);
                vp.add(fileNameLabel);
                getButton.setEnabled(false);
                g.setWidget(i, 1, vp);

                readerParameters.put(fields[i].getParam(), fu);

                EventBus.get().addHandler(UploadEvent.TYPE, new UploadHandler() {
                    public void onUpload(UploadEvent event) {
                        log("got upload event for file: '" + event.getFileName() + "'");
                        fileNameLabel.setText(event.getFileName());
                        fileNameLabel.setVisible(true);
                        fu.setVisible(false);
                        fu.setFileUrl(event.getFileUrl());
                        getButton.setEnabled(true);
                    }
                });
            }

            Label desc = new Label(fields[i].getDesrciption());
            g.setWidget(i, 2, desc);
        }

        ListBox languageBox = new ListBox();
        for (String language : LANGUAGES) {
            languageBox.addItem(language);
        }
        readerParameters.put("language", languageBox);

        g.setWidget(fields.length, 0, languageBox);

        g.setWidget(fields.length + 1, 0, getButton);
        getButton.setStylePrimaryName("ns-smallButton");

        class ExecuteHandler implements ClickHandler, KeyUpHandler {
            public void onClick(ClickEvent event) {
                createCorpusFromReaderParams(readerParameters);
            }

            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    createCorpusFromReaderParams(readerParameters);
                }
            }
        }
        ExecuteHandler handler = new ExecuteHandler();

        if (getButtonHandlerRegistration != null) {
            getButtonHandlerRegistration.removeHandler();
        }
        getButtonHandlerRegistration = getButton.addClickHandler(handler);
        return g;

    }

    private void createCorpusFromReaderParams(HashMap<String, Widget> readerParameters) {
        if ((readerParameters != null) && (readerParameters.size() > 0)) {
            HashMap<String, String> parameters = new HashMap<String, String>();
            for (String field : readerParameters.keySet()) {
                Widget w = readerParameters.get(field);
                if (w instanceof TextBox) {
                    TextBox tb = (TextBox) w;
                    parameters.put(field, tb.getText());
                } else if (w instanceof FileUploader) {
                    FileUploader fu = (FileUploader) w;
                    parameters.put(field, fu.getFileUrl());
                } else if (w instanceof ListBox) {
                    ListBox box = (ListBox) w;
                    parameters.put(field, box.getValue(box.getSelectedIndex()));
                }
            }
            createCorpus(parameters);
        }
    }

    /*
     * Executed when "Create & Run" button is pressed
     */
    private void createCorpus(HashMap<String, String> parameters) {
        if ((parameters != null) && (parameters.size() > 0)) {
            getButton.setEnabled(false);

            EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_NOT_READY, "Creating corpus..."));

            getServiceRegistry().getCorpusService().createCorpus(parameters, new AsyncCallback<CorpusDTO>() {
                public void onFailure(Throwable caught) {
                    log("Error createCorpus" + caught.getMessage());
                }

                public void onSuccess(CorpusDTO dto) {
                    CorpusDTO[] c = { dto };
                    EventBus.get().fireEvent(new CorpusEvent(CorpusEvent.LIST_CHANGED, c));
                    getButton.setEnabled(true);
                    EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, null));
                }
            });
        }
    }

}
