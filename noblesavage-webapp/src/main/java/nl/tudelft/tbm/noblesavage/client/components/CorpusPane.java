package nl.tudelft.tbm.noblesavage.client.components;

import nl.tudelft.tbm.noblesavage.client.ClientState;
import nl.tudelft.tbm.noblesavage.client.ServiceRegistry;
import nl.tudelft.tbm.noblesavage.client.log.Logger;

import com.google.gwt.user.client.ui.VerticalPanel;

public class CorpusPane extends Pane {

  private final CreateCorpusFormPane createCorpusPane;
  private final CorpusListPane corpusListPane;

  protected CorpusPane(ClientState clientState, ServiceRegistry serviceRegistry, Logger logger) {
    super(clientState, serviceRegistry, logger, CorpusPane.class.getName());
    VerticalPanel vsp = new VerticalPanel();
    vsp.setSize("100%", "100%");
    

    VerticalPanel formAndList = new VerticalPanel();
    formAndList.setSize("100%", "100%");

    createCorpusPane = new CreateCorpusFormPane(clientState, serviceRegistry, logger);
    corpusListPane = new CorpusListPane(clientState, serviceRegistry, logger);

    formAndList.add(createCorpusPane);
    formAndList.add(corpusListPane);

    vsp.add(formAndList);

    initWidget(vsp);
  }
}
