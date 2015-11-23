package nl.tudelft.tbm.noblesavage.client.components;

import java.util.Map;

import nl.tudelft.tbm.noblesavage.client.ClientState;
import nl.tudelft.tbm.noblesavage.client.ServiceRegistry;
import nl.tudelft.tbm.noblesavage.client.events.CorpusEvent;
import nl.tudelft.tbm.noblesavage.client.events.CorpusEventHandler;
import nl.tudelft.tbm.noblesavage.client.events.EventBus;
import nl.tudelft.tbm.noblesavage.client.log.Logger;
import nl.tudelft.tbm.noblesavage.facade.CorpusDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CorpusStatsPane extends Pane {

  private final VerticalPanel statsPane = new VerticalPanel();
  private final VerticalPanel termPane = new VerticalPanel();
  private final VerticalPanel wordCountPane = new VerticalPanel();
  private final VerticalPanel articleCountPane = new VerticalPanel();

  private ServiceRegistry serviceRegistry;

  private CorpusDTO selectedCorpus = null;

  protected CorpusStatsPane(ClientState clientState, final ServiceRegistry serviceRegistry, Logger logger) {
    super(clientState, serviceRegistry, logger, CorpusStatsPane.class.getName());

    this.serviceRegistry = serviceRegistry;

    statsPane.setWidth("200px");
    statsPane.add(new HTML("<div><b>Corpus Statistics</b></div>"));
    statsPane.add(termPane);
    statsPane.add(wordCountPane);
    statsPane.add(articleCountPane);

    EventBus.get().addHandler(CorpusEvent.TYPE, new CorpusEventHandler() {
      public void onCorpusSelected(CorpusEvent event) {
      	if (event.getCorpusDTO() != null && event.getCorpusDTO().length > 0) {
      		setSelectedCorpus(event.getCorpusDTO()[0]);
      	}
      }

      public void onListChanged(CorpusEvent event) {
        try {
          destroyData();
        } catch (Exception err) {
          log("Error removing search pane");
        }
      }

      public void onListLoaded(CorpusEvent event) {
      }

    });
    initWidget(statsPane);
  }

  private void destroyData() {
    try {
      termPane.remove(0);
      wordCountPane.remove(0);
      articleCountPane.remove(0);
    } catch (Exception err) {
      log("Error removing search pane");
    }
  }

  private VerticalPanel createStatsPane(Map<String, Integer> termMap) {
    VerticalPanel panel = new VerticalPanel();
    panel.add(new HTML("<b>Top 20 most frequent terms</b>"));
    FlexTable table = new FlexTable();
    table.addStyleName("ns-flexTable");
    table.setWidth("100%");
    table.setCellSpacing(2);
    table.setCellPadding(1);
    table.setHTML(0, 0, "<b>Term</b>");
    table.setHTML(0, 1, "<b>Occurences</b>");
    int row = 1;
    for (String term : termMap.keySet()) {
      table.setText(row, 0, term);
      table.setText(row, 1, termMap.get(term).toString());
      row++;
    }
    panel.add(table);
    return panel;
  }

  public CorpusDTO getSelectedCorpus() {
    return selectedCorpus;
  }

  public void setSelectedCorpus(CorpusDTO selectedCorpus) {
    this.selectedCorpus = selectedCorpus;
    setDirty(true);
  }

  @Override
  public void setSelected(boolean selected) {
    if (selected && getSelectedCorpus() != null && isDirty()) {
      requestStats(getSelectedCorpus());
    }
  }

  private void requestStats(final CorpusDTO dto) {
    destroyData();
    serviceRegistry.getCorpusService().getTermFrequency(dto, 20, new AsyncCallback<Map<String, Integer>>() {
      public void onFailure(Throwable arg0) {
        log("Error getting corpus term frequency");
      }

      public void onSuccess(Map<String, Integer> termMap) {
        termPane.add(createStatsPane(termMap));
        setDirty(false);
      }
    });
    serviceRegistry.getCorpusService().getWordCount(dto, new AsyncCallback<Integer>() {
      public void onSuccess(Integer count) {
        wordCountPane.add(new HTML("<b>Word count: " + count + "</b>"));
      }

      public void onFailure(Throwable arg0) {
        log("Error getting corpus word count");
      }
    });
    serviceRegistry.getCorpusService().getArticleCount(dto, new AsyncCallback<Integer>() {
      public void onSuccess(Integer count) {
        articleCountPane.add(new HTML("<b>Article count: " + count + "</b>"));
      }

      public void onFailure(Throwable arg0) {
        log("Error getting corpus word count");
      }
    });
  }

}
