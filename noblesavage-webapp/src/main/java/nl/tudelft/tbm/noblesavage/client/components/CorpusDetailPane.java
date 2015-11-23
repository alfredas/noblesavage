package nl.tudelft.tbm.noblesavage.client.components;

import nl.tudelft.tbm.noblesavage.client.ClientState;
import nl.tudelft.tbm.noblesavage.client.ServiceRegistry;
import nl.tudelft.tbm.noblesavage.client.events.CorpusEvent;
import nl.tudelft.tbm.noblesavage.client.events.CorpusEventHandler;
import nl.tudelft.tbm.noblesavage.client.events.EventBus;
import nl.tudelft.tbm.noblesavage.client.log.Logger;
import nl.tudelft.tbm.noblesavage.facade.CorpusDTO;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CorpusDetailPane extends Pane {

  private final CorpusSearchPane searchPane;
  private final CorpusStatsPane statsPane;
  private final VerticalPanel detailPanel = new VerticalPanel();

  final HTML corpusTitleHTML = new HTML();

  private CorpusDTO selectedCorpus = null;

  protected CorpusDetailPane(ClientState clientState, ServiceRegistry serviceRegistry, Logger logger) {
    super(clientState, serviceRegistry, logger, CorpusDetailPane.class.getName());
    searchPane = new CorpusSearchPane(clientState, serviceRegistry, logger);
    statsPane = new CorpusStatsPane(clientState, serviceRegistry, logger);
    HorizontalPanel hp = new HorizontalPanel();
    hp.setWidth("100%");

    hp.add(statsPane);
    hp.add(searchPane);

    detailPanel.setWidth("100%");
    detailPanel.add(corpusTitleHTML);
    detailPanel.add(hp);

    initWidget(detailPanel);

    EventBus.get().addHandler(CorpusEvent.TYPE, new CorpusEventHandler() {

      public void onCorpusSelected(CorpusEvent event) {
      	if (event.getCorpusDTO() != null && event.getCorpusDTO().length > 0) {
	        CorpusDTO corpusDTO = event.getCorpusDTO()[0];
	        if (corpusDTO != null) {
	          corpusTitleHTML.setHTML("<b>" + corpusDTO.getName() + "</b>");
	        }
	        setSelectedCorpus(corpusDTO);
      	}
      }

      public void onListChanged(CorpusEvent event) {
      }

      public void onListLoaded(CorpusEvent event) {
      }
    });
  }

  public CorpusDTO getSelectedCorpus() {
    return selectedCorpus;
  }

  public void setSelectedCorpus(CorpusDTO selectedCorpus) {
    this.selectedCorpus = selectedCorpus;
  }

  @Override
  public void setSelected(boolean selected) {
    searchPane.setSelected(selected);
    statsPane.setSelected(selected);
  }

}
