package nl.tudelft.tbm.noblesavage.client.components;

import java.util.Map;

import nl.tudelft.tbm.noblesavage.client.ClientState;
import nl.tudelft.tbm.noblesavage.client.ServiceRegistry;
import nl.tudelft.tbm.noblesavage.client.events.CorpusEvent;
import nl.tudelft.tbm.noblesavage.client.events.CorpusEventHandler;
import nl.tudelft.tbm.noblesavage.client.events.EventBus;
import nl.tudelft.tbm.noblesavage.client.events.ReadyEvent;
import nl.tudelft.tbm.noblesavage.client.log.Logger;
import nl.tudelft.tbm.noblesavage.facade.CorpusDTO;
import nl.tudelft.tbm.noblesavage.facade.SearchResultDTO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CorpusSearchPane extends Pane {

    private String[] searchMethods = null;
    private final VerticalPanel container = new VerticalPanel();
    private final VerticalPanel searchPanel = new VerticalPanel();
    private final VerticalPanel searchResultsPanel = new VerticalPanel();
    private static final String[] FREQUENCIES = { "Yearly", "Monthly" };
    private final NumberFormat percentFormat = NumberFormat.getPercentFormat();

    final TextBox searchField = new TextBox();

    private CorpusDTO selectedCorpus = null;

    protected CorpusSearchPane(ClientState clientState, ServiceRegistry serviceRegistry, Logger logger) {
        super(clientState, serviceRegistry, logger, CorpusSearchPane.class.getName());
        getSearchMethods();

        searchPanel.setWidth("100%");

        searchPanel.add(new HTML("<b>Analyze term relations:</b>"));
        EventBus.get().addHandler(CorpusEvent.TYPE, new CorpusEventHandler() {
            public void onCorpusSelected(CorpusEvent event) {
                if (event.getCorpusDTO() != null && event.getCorpusDTO().length > 0) {
                    setSelectedCorpus(event.getCorpusDTO()[0]);
                }
            }

            public void onListChanged(CorpusEvent event) {
                destroyData();
            }

            public void onListLoaded(CorpusEvent event) {
            }

        });
        container.add(searchPanel);
        container.add(searchResultsPanel);
        initWidget(container);
    }

    private void destroyData() {
        try {
            for (int i = 1; i < searchPanel.getWidgetCount(); i++) {
                searchPanel.remove(i);
            }
            try {
                searchResultsPanel.remove(0);
            } catch (Exception err) {
                log("Error removing results");
            }
        } catch (Exception err) {
            log("Error removing search pane");
        }
    }

    private HorizontalPanel createSearchPane(final CorpusDTO corpusDTO) {
        HorizontalPanel hp = new HorizontalPanel();
        FlexTable table = new FlexTable();

        final ListBox methodBox = new ListBox(false);
        if (searchMethods != null) {
            for (String searchMethod : searchMethods) {
                methodBox.addItem(searchMethod);
            }
        }
        methodBox.addStyleName("ns-smallInput");

        Button searchQueryButton = new Button("Search");
        searchQueryButton.setStylePrimaryName("ns-smallButton");

        Button stemButton = new Button("Stem keywords");
        stemButton.setStylePrimaryName("ns-smallButton");

        final TextBox fromYear = new TextBox();
        final TextBox toYear = new TextBox();
        final TextBox numberOfresults = new TextBox();
        numberOfresults.setText("10");
        fromYear.addStyleName("ns-smallInput");
        toYear.addStyleName("ns-smallInput");
        numberOfresults.addStyleName("ns-smallInput");

        final ListBox frequencyListBox = new ListBox(false);
        for (String frequency : FREQUENCIES) {
            frequencyListBox.addItem(frequency);
        }
        frequencyListBox.addStyleName("ns-smallInput");
        table.setText(0, 0, "Search term:");
        table.setText(1, 0, "Search method:");
        table.setText(2, 0, "From year:");
        table.setText(3, 0, "To year:");
        table.setText(4, 0, "Frequency:");
        table.setText(5, 0, "Number of results:");

        table.setWidget(0, 1, searchField);
        table.setWidget(1, 1, methodBox);
        table.setWidget(2, 1, fromYear);
        table.setWidget(3, 1, toYear);
        table.setWidget(4, 1, frequencyListBox);
        table.setWidget(5, 1, numberOfresults);
        table.setWidget(6, 1, searchQueryButton);
        searchField.addStyleName("ns-smallInput");

        table.setWidget(0, 2, stemButton);

        searchField.addKeyUpHandler(new KeyUpHandler() {
            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    try {
                        searchResultsPanel.remove(0);
                    } catch (Exception err) {
                        log("Error removing results");
                    }
                    try {
                        int from = Integer.parseInt(fromYear.getText());
                        int to = Integer.parseInt(toYear.getText());
                        int number = Integer.parseInt(numberOfresults.getText());
                        searchQuery(corpusDTO, searchField.getText(), from, to, frequencyListBox.getSelectedIndex(),
                                methodBox.getSelectedIndex(), number);
                    } catch (Exception err) {
                        log("Error creating search query");
                    }
                }
            }
        });
        searchQueryButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                try {
                    searchResultsPanel.remove(0);
                } catch (Exception err) {
                    log("Error removing results");
                }
                try {
                    int from = Integer.parseInt(fromYear.getText());
                    int to = Integer.parseInt(toYear.getText());
                    int number = Integer.parseInt(numberOfresults.getText());
                    searchQuery(corpusDTO, searchField.getText(), from, to, frequencyListBox.getSelectedIndex(),
                            methodBox.getSelectedIndex(), number);
                } catch (Exception err) {
                    log("Error creating search query");
                }
            }
        });

        stemButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                stemTerms(corpusDTO, searchField.getText());
            }
        });

        hp.add(table);
        return hp;
    }

    private void getSearchMethods() {
        getServiceRegistry().getCorpusService().getSearchMethods(new AsyncCallback<String[]>() {
            public void onFailure(Throwable caught) {
                log("getSearchMethods: " + caught.getMessage());
            }

            public void onSuccess(String[] result) {
                searchMethods = result;
            }
        });
    }

    private void searchQuery(CorpusDTO corpusDTO, final String term, int fromYear, int toYear, int frequency, int searchMethod,
            int numberOfResults) {

        EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_NOT_READY, "Analyzing corpus..."));

        // increment toYear to be inclusive so 2006-2010 would include 2010
        toYear++;

        getServiceRegistry().getCorpusService().searchCorpus(corpusDTO, term, searchMethod, fromYear, toYear, frequency, numberOfResults,
                new AsyncCallback<Map<Integer, SearchResultDTO[]>>() {
                    public void onFailure(Throwable caught) {
                        log("Error searchQuery: " + caught.getMessage());
                    }

                    public void onSuccess(Map<Integer, SearchResultDTO[]> resultMap) {
                        searchResultsPanel.add(createResultsView(resultMap));

                        EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, ""));

                        setDirty(false);
                    }
                });
    }

    private void stemTerms(CorpusDTO corpusDTO, String terms) {
        EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_NOT_READY, "Stemming keywords..."));

        getServiceRegistry().getCorpusService().stemKeywords(corpusDTO, terms, new AsyncCallback<String>() {
            public void onSuccess(String stemmedKeywords) {
                searchField.setText(stemmedKeywords);
                EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, ""));
            }

            public void onFailure(Throwable caught) {
                log("Error stemKeywords: " + caught.getMessage());
            }
        });

    }

    private HorizontalPanel createResultsView(Map<Integer, SearchResultDTO[]> resultMap) {
        HorizontalPanel p = new HorizontalPanel();
        FlexTable table = new FlexTable();
        table.addStyleName("ns-flexTable");

        int column = 0;

        int minYear = 10000;
        int maxYear = -1;

        for (Integer year : resultMap.keySet()) {
            if (year < minYear) {
                minYear = year;
            }
            if (year > maxYear) {
                maxYear = year;
            }
        }

        for (int year = minYear; year <= maxYear; year++) {
            table.setHTML(0, column, "<b>" + year + "</b>");
            table.setHTML(1, column, "<b>Term</b>");
            table.setHTML(1, column + 1, "<b>Score</b>");
            int offset = 2;
            SearchResultDTO[] dtos = resultMap.get(year);
            for (int i = 0; i < dtos.length; i++) {
                table.setText(i + offset, column, dtos[i].getTerm());
                table.setText(i + offset, column + 1, percentFormat.format(dtos[i].getScore()));
            }
            column += 2;
        }
        p.add(table);
        return p;
    }

    /*
     * private DialogBox createSearchResultDialogBox(SearchResultDTO[]
     * searchResults, String searchTerm) {
     * 
     * // Create a dialog box and set the caption text final DialogBox dialogBox
     * = new DialogBox();
     * 
     * dialogBox.setWidth("800px"); dialogBox.setHeight("600px");
     * dialogBox.setText("Search term: " + searchTerm);
     * 
     * Grid mainGrid = new Grid(1, 2); mainGrid.setSize("100%", "100%");
     * 
     * dialogBox.setWidget(mainGrid);
     * 
     * // Create a table to layout the content VerticalPanel searchResultsPanel
     * = new VerticalPanel(); searchResultsPanel.setSpacing(4); Grid g = new
     * Grid(searchResults.length, 2); for (int i = 1; i < searchResults.length;
     * i++) { if (searchResults[i] != null) { g.setWidget(i, 0, new
     * Label(searchResults[i].getTerm())); String formatted =
     * NumberFormat.getFormat("##.0%").format(searchResults[i].getScore());
     * g.setWidget(i, 1, new Label(formatted)); } } searchResultsPanel.add(g);
     * mainGrid.setWidget(0, 0, searchResultsPanel);
     * 
     * VerticalPanel graphPanel = new VerticalPanel(); graphPanel.add(new
     * HTML("<iframe src='/forcedirectedgraph.html?" +
     * createGraphParams(searchResults, searchTerm) +
     * "' width='400' height='400' scrolling='no' frameborder='0' style='border:0;'/>"
     * ));
     * 
     * mainGrid.setWidget(0, 1, graphPanel);
     * 
     * // Add a close button at the bottom of the dialog Button closeButton =
     * new Button("Close", new ClickHandler() { public void onClick(ClickEvent
     * event) { dialogBox.hide(); } });
     * closeButton.setStylePrimaryName("ns-smallButton");
     * searchResultsPanel.add(closeButton);
     * 
     * return dialogBox; }
     * 
     * private String createGraphParams(SearchResultDTO[] searchResults, String
     * searchTerm) { String s = ""; for (SearchResultDTO searchResult :
     * searchResults) { if (searchResult != null) { s += searchResult.getTerm();
     * s += ":"; s += String.valueOf(searchResult.getScore()); s += ";"; } }
     * return s;
     * 
     * }
     */

    public CorpusDTO getSelectedCorpus() {
        return selectedCorpus;
    }

    public void setSelectedCorpus(CorpusDTO selectedCorpus) {
        this.selectedCorpus = selectedCorpus;
        setDirty(true);
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected && (getSelectedCorpus() != null) && isDirty()) {
            destroyData();
            searchPanel.add(createSearchPane(getSelectedCorpus()));
        }
    }

}
