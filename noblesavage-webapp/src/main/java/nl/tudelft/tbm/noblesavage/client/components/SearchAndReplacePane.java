package nl.tudelft.tbm.noblesavage.client.components;

import nl.tudelft.tbm.noblesavage.client.ClientState;
import nl.tudelft.tbm.noblesavage.client.ServiceRegistry;
import nl.tudelft.tbm.noblesavage.client.events.EventBus;
import nl.tudelft.tbm.noblesavage.client.events.ReadyEvent;
import nl.tudelft.tbm.noblesavage.client.log.Logger;
import nl.tudelft.tbm.noblesavage.facade.CorpusDTO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchAndReplacePane extends DialogBox {

    private final TextBox textBox = new TextBox();
    private final Button replaceButton = new Button("Replace");
    private ClientState clientState;
    private ServiceRegistry serviceRegistry;
    private Logger logger;

    public SearchAndReplacePane(ClientState clientState, ServiceRegistry serviceRegistry, Logger logger, String searchText, CorpusDTO corpus) {
        this.logger = logger;
        this.clientState = clientState;
        this.serviceRegistry = serviceRegistry;

        this.setText("Search and Replace");
        this.setWidget(this.createSRPanel(searchText, corpus));
        this.setGlassEnabled(true);
    }

    private VerticalPanel createSRPanel(final String text, final CorpusDTO corpus) {

        VerticalPanel panel = new VerticalPanel();

        HTML html = new HTML("Replace <b>" + text + "</b> with: ");

        replaceButton.setStylePrimaryName("ns-smallButton");

        replaceButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                searchAndReplace(text, textBox.getText(), corpus);
            }
        });

        panel.setWidth("200px");
        panel.setHeight("150px");
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

        panel.add(html);
        panel.add(textBox);

        Button closeButton = new Button("Close");
        closeButton.setStylePrimaryName("ns-smallButton");

        closeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent arg0) {
                close();
            }
        });

        HorizontalPanel buttons = new HorizontalPanel();
        buttons.add(replaceButton);
        buttons.add(closeButton);

        panel.add(buttons);

        return panel;
    }

    private void close() {
        this.hide();
    }

    private void searchAndReplace(String search, String replace, CorpusDTO corpus) {
        EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_NOT_READY, "Replacing..."));
        this.serviceRegistry.getCorpusService().replaceTextFromCorpus(corpus, search, replace, new AsyncCallback<Integer>() {
            public void onSuccess(Integer count) {
                if (count > 0) {
                    EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.NEED_REFRESH, "Replaced text in " + count + " articles."));
                } else {
                    EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, "Text not found."));
                }
                close();
            }

            public void onFailure(Throwable arg0) {
                EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, null));
                logger.log("Error searching and replacing");
                close();
            }
        });

    }

}
