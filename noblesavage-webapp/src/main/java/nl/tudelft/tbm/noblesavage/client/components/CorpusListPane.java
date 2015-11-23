package nl.tudelft.tbm.noblesavage.client.components;

import java.util.ArrayList;
import java.util.Set;

import nl.tudelft.tbm.noblesavage.client.ClientState;
import nl.tudelft.tbm.noblesavage.client.ServiceRegistry;
import nl.tudelft.tbm.noblesavage.client.events.CorpusEvent;
import nl.tudelft.tbm.noblesavage.client.events.CorpusEventHandler;
import nl.tudelft.tbm.noblesavage.client.events.EventBus;
import nl.tudelft.tbm.noblesavage.client.events.LoginEvent;
import nl.tudelft.tbm.noblesavage.client.events.LoginHandler;
import nl.tudelft.tbm.noblesavage.client.events.ReadyEvent;
import nl.tudelft.tbm.noblesavage.client.log.Logger;
import nl.tudelft.tbm.noblesavage.facade.CorpusDTO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gen2.table.client.AbstractScrollTable;
import com.google.gwt.gen2.table.client.FixedWidthFlexTable;
import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.client.ScrollTable;
import com.google.gwt.gen2.table.client.SelectionGrid;
import com.google.gwt.gen2.table.event.client.RowSelectionEvent;
import com.google.gwt.gen2.table.event.client.RowSelectionHandler;
import com.google.gwt.gen2.table.event.client.TableEvent.Row;
import com.google.gwt.gen2.table.override.client.FlexTable;
import com.google.gwt.gen2.table.override.client.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CorpusListPane extends Pane {

    final AbstractScrollTable corpusTable;
    final Button deleteCorpusButton;
    final Button mergeCorpusButton;
    private ArrayList<CorpusDTO> selectedCorpora = null;
    private CorpusDTO[] corporaList;

    protected CorpusListPane(ClientState clientState, ServiceRegistry serviceRegistry, Logger logger) {
        super(clientState, serviceRegistry, logger, CorpusListPane.class.getName());

        corpusTable = createCorpusTable();
        VerticalPanel container = createCorporaPanel();
        container.add(corpusTable);

        deleteCorpusButton = new Button("Delete Corpus");
        deleteCorpusButton.setStylePrimaryName("ns-smallButton");

        mergeCorpusButton = new Button("Merge Corpora");
        mergeCorpusButton.setStylePrimaryName("ns-smallButton");

        if (getClientState().isAdmin()) {
            deleteCorpusButton.setVisible(true);
            mergeCorpusButton.setVisible(true);
        } else {
            deleteCorpusButton.setVisible(false);
            mergeCorpusButton.setVisible(false);
        }

        deleteCorpusButton.setEnabled(false);
        deleteCorpusButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                for (CorpusDTO corpus : getSelectedCorpora()) {
                    deleteCorpus(corpus);
                }
            }
        });

        mergeCorpusButton.setEnabled(false);
        mergeCorpusButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                EnterCorpusNamePopup popup = new EnterCorpusNamePopup();
                popup.center();
                popup.show();
            }
        });

        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.add(deleteCorpusButton);
        buttonPanel.add(mergeCorpusButton);
        container.add(buttonPanel);

        getChachedCorpora();

        EventBus.get().addHandler(CorpusEvent.TYPE, new CorpusEventHandler() {
            public void onCorpusSelected(CorpusEvent event) {
            }

            public void onListChanged(CorpusEvent event) {
                log("list changed");
                getChachedCorpora();

                setSelectedCorpora(null);
                deleteCorpusButton.setEnabled(false);
                mergeCorpusButton.setEnabled(false);
            }

            public void onListLoaded(CorpusEvent event) {
            }
        });

        EventBus.get().addHandler(LoginEvent.TYPE, new LoginHandler() {
            public void onLogout(LoginEvent event) {
                deleteCorpusButton.setVisible(false);
                mergeCorpusButton.setVisible(false);
            }

            public void onLogin(LoginEvent event) {
                if (getClientState().isAdmin()) {
                    deleteCorpusButton.setVisible(true);
                    mergeCorpusButton.setVisible(true);
                }
            }
        });

        initWidget(container);
    }

    /*
     * Creates and populates the Cached Query Table
     */
    private void getChachedCorpora() {
        EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_NOT_READY, "Retrieving corpora..."));
        getServiceRegistry().getCorpusService().getCachedCorpora(new AsyncCallback<CorpusDTO[]>() {
            public void onFailure(Throwable caught) {
                EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, "Error: " + caught.getMessage()));
                log("Error getChachedCorpora" + caught.getMessage());
            }

            public void onSuccess(CorpusDTO[] result) {
                EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, null));
                populateCorpusTable(result);
                EventBus.get().fireEvent(new CorpusEvent(CorpusEvent.LIST_LOADED, result));
            }
        });
    }

    private void populateCorpusTable(CorpusDTO[] corpora) {
        FixedWidthGrid dataTable = getCorpusTable().getDataTable();
        log("about to remove rows " + dataTable.getRowCount());
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            dataTable.removeRow(i);
            log("removed row " + i);
        }

        setCorporaList(corpora);
        if (corpora != null) {
            dataTable.resizeRows(corpora.length);
            for (int i = 0; i < corpora.length; i++) {
                addCorpusToTable(dataTable, corpora[i], i);
            }
        } else {
            setSelectedCorpora(null);
        }
    }

    private VerticalPanel createCorporaPanel() {
        log("creating queries tab");
        VerticalPanel p = new VerticalPanel();
        p.setWidth("100%");
        p.setHeight("100%");
        p.add(new Label("Cached Corpora"));
        return p;
    }

    private ScrollTable createCorpusTable() {
        FixedWidthFlexTable headerTable = createHeaderTable();
        FixedWidthGrid dataTable = createDataTable();
        ScrollTable scrollTable = new ScrollTable(dataTable, headerTable);
        scrollTable.setResizePolicy(ScrollTable.ResizePolicy.FILL_WIDTH);
        scrollTable.setWidth("100%");
        scrollTable.setHeight("500px");
        scrollTable.setCellPadding(3);
        scrollTable.setCellSpacing(0);

        scrollTable.setMinimumColumnWidth(0, 30);
        scrollTable.setPreferredColumnWidth(0, 30);
        scrollTable.setColumnTruncatable(0, false);

        scrollTable.setMinimumColumnWidth(1, 100);
        scrollTable.setPreferredColumnWidth(1, 200);
        scrollTable.setColumnTruncatable(1, false);

        return scrollTable;
    }

    private FixedWidthGrid createDataTable() {
        final FixedWidthGrid dataTable = new FixedWidthGrid(0, 5);
        dataTable.addRowSelectionHandler(new RowSelectionHandler() {
            public void onRowSelection(RowSelectionEvent event) {
                Set<Row> selectedRows = event.getSelectedRows();
                Set<Row> deselectedRows = event.getDeselectedRows();
                if (selectedRows != null && selectedRows.iterator().hasNext()) {
                    Row row = selectedRows.iterator().next();
                    int selectedIndex = row.getRowIndex();
                    String id = dataTable.getCellFormatter().getElement(selectedIndex, 3).getInnerText();
                    log("selected id is: " + id);
                    CorpusDTO selected = getCorpusById(id);
                    // CorpusDTO selected = getCorporaList()[selectedIndex];
                    log("adding corpus to selected list:" + selected.getName());
                    addSelectedCorpus(selected);
                } else if (deselectedRows != null && deselectedRows.iterator().hasNext()) {
                    Row row = deselectedRows.iterator().next();
                    int deselectedIndex = row.getRowIndex();
                    CorpusDTO deselected = getCorporaList()[deselectedIndex];
                    log("removing corpus from selected list:" + deselected.getName());
                    removeSelectedCorpus(deselected);
                }

                if (getSelectedCorpora() != null) {
                    CorpusDTO[] c = new CorpusDTO[getSelectedCorpora().size()];
                    c = getSelectedCorpora().toArray(c);
                    EventBus.get().fireEvent(new CorpusEvent(CorpusEvent.CORPUS_SELECTED, c));
                } else {
                    EventBus.get().fireEvent(new CorpusEvent(CorpusEvent.CORPUS_SELECTED, null));
                }

                if (getSelectedCorpora() != null && !getSelectedCorpora().isEmpty()) {

                    deleteCorpusButton.setEnabled(true);
                    if (getSelectedCorpora().size() > 1) {
                        mergeCorpusButton.setEnabled(true);
                    } else {
                        mergeCorpusButton.setEnabled(false);
                    }
                } else {
                    mergeCorpusButton.setEnabled(false);
                    deleteCorpusButton.setEnabled(false);
                }
            }
        });

        dataTable.setSelectionPolicy(SelectionGrid.SelectionPolicy.CHECKBOX);
        return dataTable;
    }

    private FixedWidthFlexTable createHeaderTable() {
        // Create a new table
        FixedWidthFlexTable headerTable = new FixedWidthFlexTable();
        FlexCellFormatter formatter = headerTable.getFlexCellFormatter();

        headerTable.setHTML(0, 0, "<b>#</b>");
        formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
        headerTable.setHTML(0, 1, "<b>Name</b>");
        formatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
        headerTable.setHTML(0, 2, "<b>Type</b>");
        formatter.setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_CENTER);
        headerTable.setHTML(0, 3, "<b>URL</b>");
        formatter.setHorizontalAlignment(0, 3, HasHorizontalAlignment.ALIGN_CENTER);
        headerTable.setHTML(0, 4, "<b>ID</b>");
        formatter.setHorizontalAlignment(0, 4, HasHorizontalAlignment.ALIGN_CENTER);
        headerTable.setHTML(0, 5, "<b>Language</b>");
        formatter.setHorizontalAlignment(0, 5, HasHorizontalAlignment.ALIGN_CENTER);
        // Return the header table
        return headerTable;
    }

    private void addCorpusToTable(FixedWidthGrid table, final CorpusDTO corpusDTO, int row) {
        String name = corpusDTO.getName();
        log("adding " + name + " to table");
        String type = corpusDTO.getReaderType();
        String url = corpusDTO.getUrl();
        String id = String.valueOf(corpusDTO.getId());
        String lang = corpusDTO.getLang();
        if (url.length() > 50) {
            url = url.substring(0, Math.min(50, url.length())) + "..." + url.substring(url.lastIndexOf("/"));
        }
        if (url.length() > 70) {
            url = url.substring(0, 70);
        }
        table.setWidget(row, 1, new Label(type));
        table.setWidget(row, 0, new Label(name));
        table.setWidget(row, 2, new HTML("<a href='" + corpusDTO.getUrl() + "'>" + url + "</a>"));
        table.setWidget(row, 3, new Label(id));
        table.setWidget(row, 4, new Label(lang));
    }

    private CorpusDTO getCorpusById(String idString) {
        try {
            int id = Integer.parseInt(idString);
            for (CorpusDTO corpus : this.getCorporaList()) {
                if (corpus.getId() == id) {
                    return corpus;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    private void deleteCorpus(final CorpusDTO corpusDTO) {
        if (corpusDTO != null) {
            getServiceRegistry().getCorpusService().deleteCorpus(corpusDTO, new AsyncCallback<Void>() {
                public void onFailure(Throwable caught) {
                    log("Error deteleCorpus: " + caught.getMessage());
                }

                public void onSuccess(Void result) {
                    log("firing list changed");
                    EventBus.get().fireEvent(new CorpusEvent(CorpusEvent.LIST_CHANGED, null));
                }
            });
        }
    }

    private void mergeCorpora(String name) {
        if (this.getSelectedCorpora() != null && this.getSelectedCorpora().size() > 1) {
            log("will try to merge corpora with name " + name + " selected corpora: " + this.getSelectedCorpora().size());
            CorpusDTO[] c = new CorpusDTO[this.getSelectedCorpora().size()];
            c = this.getSelectedCorpora().toArray(c);
            getServiceRegistry().getCorpusService().mergeCorpora(c, name, new AsyncCallback<Void>() {
                public void onSuccess(Void arg0) {
                    log("firing list changed");
                    EventBus.get().fireEvent(new CorpusEvent(CorpusEvent.LIST_CHANGED, null));
                }

                public void onFailure(Throwable caught) {
                    log("Error mergeCorpora: " + caught.getMessage());
                }
            });
        }
    }

    public AbstractScrollTable getCorpusTable() {
        return corpusTable;
    }

    public CorpusDTO[] getCorporaList() {
        return corporaList;
    }

    public void setCorporaList(CorpusDTO[] corporaList) {
        this.corporaList = corporaList;
    }

    public ArrayList<CorpusDTO> getSelectedCorpora() {
        return selectedCorpora;
    }

    public void setSelectedCorpora(ArrayList<CorpusDTO> selectedCorpora) {
        this.selectedCorpora = selectedCorpora;
        if (this.selectedCorpora == null) {
            deleteCorpusButton.setEnabled(false);
            mergeCorpusButton.setEnabled(false);
        }
    }

    public void addSelectedCorpus(CorpusDTO corpus) {
        if (this.selectedCorpora == null) {
            this.selectedCorpora = new ArrayList<CorpusDTO>();
        }
        this.selectedCorpora.add(corpus);
    }

    public void removeSelectedCorpus(CorpusDTO corpus) {
        if (this.selectedCorpora != null) {
            this.selectedCorpora.remove(corpus);
        }
        if (this.selectedCorpora == null || this.selectedCorpora.isEmpty()) {
            deleteCorpusButton.setEnabled(false);
            mergeCorpusButton.setEnabled(false);
        }
    }

    class EnterCorpusNamePopup extends PopupPanel {

        public EnterCorpusNamePopup() {
            super(false);

            setWidth("300px");
            setHeight("100px");

            FlexTable table = new FlexTable();
            final TextBox corpusNameBox = new TextBox();
            corpusNameBox.addStyleName("ns-smallInput");

            final Button okButton = new Button("OK");
            final Button cancelButton = new Button("Cancel");

            okButton.setStylePrimaryName("ns-smallButton");
            cancelButton.setStylePrimaryName("ns-smallButton");

            table.setWidget(0, 0, new Label("Enter the name for the merged corpus:"));
            table.setWidget(0, 1, corpusNameBox);

            table.setWidget(1, 0, okButton);
            table.setWidget(1, 1, cancelButton);

            okButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent arg0) {
                    if (corpusNameBox.getText().length() > 0) {
                        hide();
                        mergeCorpora(corpusNameBox.getText());
                    }
                }
            });

            cancelButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent arg0) {
                    hide();
                }
            });

            setWidget(table);
        }
    }

}
