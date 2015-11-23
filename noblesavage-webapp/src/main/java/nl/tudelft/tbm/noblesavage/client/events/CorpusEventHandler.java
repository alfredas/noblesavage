package nl.tudelft.tbm.noblesavage.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface CorpusEventHandler extends EventHandler {
  void onListLoaded(CorpusEvent event);
  void onCorpusSelected(CorpusEvent event);
  void onListChanged(CorpusEvent event);
}