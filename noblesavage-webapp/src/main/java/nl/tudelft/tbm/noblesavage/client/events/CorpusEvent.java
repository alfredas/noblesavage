package nl.tudelft.tbm.noblesavage.client.events;

import nl.tudelft.tbm.noblesavage.facade.CorpusDTO;

import com.google.gwt.event.shared.GwtEvent;

public class CorpusEvent extends GwtEvent<CorpusEventHandler> {

	public static final Type<CorpusEventHandler> TYPE = new Type<CorpusEventHandler>();

	public static final int CORPUS_SELECTED = 0;
	public static final int LIST_LOADED = 1;
	public static final int LIST_CHANGED = 2;

	private int eventType;
	private CorpusDTO[] corpusDTO;

	public CorpusEvent(int eventType, CorpusDTO[] corpusDTO) {
		this.eventType = eventType;
		this.corpusDTO = corpusDTO;
	}

	@Override
	protected void dispatch(CorpusEventHandler handler) {
		switch (this.getEventType()) {
		case CORPUS_SELECTED:
			handler.onCorpusSelected(this);
			break;
		case LIST_LOADED:
			handler.onListLoaded(this);
			break;
		case LIST_CHANGED:
			handler.onListChanged(this);
			break;
		}
	}

	@Override
	public Type<CorpusEventHandler> getAssociatedType() {
		return TYPE;
	}

	public CorpusDTO[] getCorpusDTO() {
		return corpusDTO;
	}

	public void setCorpusDTO(CorpusDTO[] corpusDTO) {
		this.corpusDTO = corpusDTO;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

}
