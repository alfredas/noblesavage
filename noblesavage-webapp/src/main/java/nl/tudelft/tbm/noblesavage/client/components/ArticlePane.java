package nl.tudelft.tbm.noblesavage.client.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import nl.tudelft.tbm.noblesavage.client.ClientState;
import nl.tudelft.tbm.noblesavage.client.ServiceRegistry;
import nl.tudelft.tbm.noblesavage.client.events.CorpusEvent;
import nl.tudelft.tbm.noblesavage.client.events.CorpusEventHandler;
import nl.tudelft.tbm.noblesavage.client.events.EventBus;
import nl.tudelft.tbm.noblesavage.client.events.LoginEvent;
import nl.tudelft.tbm.noblesavage.client.events.LoginHandler;
import nl.tudelft.tbm.noblesavage.client.events.ReadyEvent;
import nl.tudelft.tbm.noblesavage.client.events.ReadyHandler;
import nl.tudelft.tbm.noblesavage.client.log.Logger;
import nl.tudelft.tbm.noblesavage.facade.ArticleDTO;
import nl.tudelft.tbm.noblesavage.facade.CorpusDTO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gen2.table.client.AbstractScrollTable;
import com.google.gwt.gen2.table.client.FixedWidthFlexTable;
import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.client.ScrollTable;
import com.google.gwt.gen2.table.client.SelectionGrid;
import com.google.gwt.gen2.table.client.SelectionGrid.SelectionGridCellFormatter;
import com.google.gwt.gen2.table.client.SortableGrid;
import com.google.gwt.gen2.table.client.SortableGrid.ColumnSorter;
import com.google.gwt.gen2.table.client.SortableGrid.ColumnSorterCallback;
import com.google.gwt.gen2.table.client.TableModelHelper.ColumnSortList;
import com.google.gwt.gen2.table.event.client.RowSelectionEvent;
import com.google.gwt.gen2.table.event.client.RowSelectionHandler;
import com.google.gwt.gen2.table.event.client.TableEvent.Row;
import com.google.gwt.gen2.table.override.client.FlexTable.FlexCellFormatter;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ArticlePane extends Pane {

    final VerticalPanel currentQueryPanel = new VerticalPanel();
    final DateTimeFormat df = DateTimeFormat.getFormat("dd-MMM-yyyy");

    private ArticleDTO selectedArticle = null;
    private ArticleDTO[] articleList = null;
    private CorpusDTO selectedCorpus = null;

    final AbstractScrollTable articleTable;
    final TextArea articleTextArea = new TextArea();
    final HTML corpusTitleHTML = new HTML();

    private final Button deleteArticleButton = new Button("Delete");
    private final Button deleteNonUniqueArticleButton = new Button("Delete Repetitive Articles");
    private final Button removeSelectedTextButton = new Button("Remove Selected Text From All Articles");
    private final Button replaceSelectedTextButton = new Button("Replace Selected Text From All Articles");

    protected ArticlePane(ClientState clientState, ServiceRegistry serviceRegistry, Logger logger) {
        super(clientState, serviceRegistry, logger, ArticlePane.class.getName());

        articleTable = createArticleTable();
        VerticalPanel container = createArticlesPanel();
        container.add(articleTable);

        deleteArticleButton.setStylePrimaryName("ns-smallButton");
        deleteArticleButton.setEnabled(false);

        deleteNonUniqueArticleButton.setStylePrimaryName("ns-smallButton");
        removeSelectedTextButton.setStylePrimaryName("ns-smallButton");
        replaceSelectedTextButton.setStylePrimaryName("ns-smallButton");

        if (getClientState().isAdmin()) {
            deleteArticleButton.setVisible(true);
            deleteNonUniqueArticleButton.setVisible(true);
            removeSelectedTextButton.setVisible(true);
        } else {
            deleteArticleButton.setVisible(false);
            deleteNonUniqueArticleButton.setVisible(false);
            removeSelectedTextButton.setVisible(false);
        }

        deleteArticleButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                deleteArticle(getSelectedArticle());
            }
        });

        deleteNonUniqueArticleButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent arg0) {
                deleteNonUniqueArticles();
            }
        });

        removeSelectedTextButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent arg0) {
                removeSelectedText();
            }
        });

        replaceSelectedTextButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent arg0) {
                replaceSelectedText();
            }
        });

        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.add(deleteArticleButton);
        buttonPanel.add(deleteNonUniqueArticleButton);
        buttonPanel.add(removeSelectedTextButton);
        buttonPanel.add(replaceSelectedTextButton);

        container.add(buttonPanel);

        articleTextArea.setSize("100%", "250px");

        container.add(new HTML("Article Text"));
        container.add(articleTextArea);

        EventBus.get().addHandler(ReadyEvent.TYPE, new ReadyHandler() {

            public void onReady(ReadyEvent event) {
            }

            public void onNotReady(ReadyEvent event) {
            }

            public void needRefresh(ReadyEvent event) {
                getArticles(getSelectedCorpus());
            }
        });

        EventBus.get().addHandler(CorpusEvent.TYPE, new CorpusEventHandler() {
            public void onCorpusSelected(CorpusEvent event) {
                if (event.getCorpusDTO() != null && event.getCorpusDTO().length > 0) {
                    setSelectedCorpus(event.getCorpusDTO()[0]);
                }
            }

            public void onListChanged(CorpusEvent event) {
            }

            public void onListLoaded(CorpusEvent event) {
            }
        });

        EventBus.get().addHandler(LoginEvent.TYPE, new LoginHandler() {
            public void onLogout(LoginEvent event) {
                deleteArticleButton.setVisible(false);
                deleteNonUniqueArticleButton.setVisible(false);
                removeSelectedTextButton.setVisible(false);
                replaceSelectedTextButton.setVisible(false);
            }

            public void onLogin(LoginEvent event) {
                if (getClientState().isAdmin()) {
                    deleteArticleButton.setVisible(true);
                    deleteNonUniqueArticleButton.setVisible(true);
                    removeSelectedTextButton.setVisible(true);
                    replaceSelectedTextButton.setVisible(true);
                }
            }
        });

        initWidget(container);
    }

    private VerticalPanel createArticlesPanel() {
        log("creating articles tab");
        VerticalPanel p = new VerticalPanel();
        p.add(corpusTitleHTML);
        return p;
    }

    private void showArticleText() {
        if (getSelectedArticle() != null) {
            getServiceRegistry().getArticleService().getArticleText(getSelectedArticle(), new AsyncCallback<String>() {
                public void onSuccess(String text) {
                    articleTextArea.setText(text);
                }

                public void onFailure(Throwable arg0) {
                    log("Error getting article text");
                    articleTextArea.setText("Error getting article text\n " + arg0.getMessage());
                }
            });
        } else {
            log("No articles selected");
        }
    }

    private void deleteNonUniqueArticles() {
        if (getSelectedCorpus() != null) {
            EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_NOT_READY, "Deleting articles..."));
            getServiceRegistry().getCorpusService().deleteNonUniqueArticlesInCorpus(getSelectedCorpus(), new AsyncCallback<Integer>() {
                public void onSuccess(Integer count) {
                    if (count > 0) {
                        getArticles(getSelectedCorpus());
                        EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, "Deleted " + count + " articles."));
                    } else {
                        EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, "No articles deleted."));
                    }
                }

                public void onFailure(Throwable caught) {
                    log("Error deleteNonUniqueArticles: " + caught.getMessage());
                }
            });
        }
    }

    private void replaceSelectedText() {
        SearchAndReplacePane pane = new SearchAndReplacePane(getClientState(), getServiceRegistry(), getLogger(),
                articleTextArea.getSelectedText(), getSelectedCorpus());
        pane.center();
        pane.show();
    }

    private void removeSelectedText() {
        String text = articleTextArea.getSelectedText();
        if (getSelectedCorpus() != null && text != null && text.length() > 0) {
            EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_NOT_READY, "Removing text..."));
            getServiceRegistry().getCorpusService().removeTextFromCorpus(getSelectedCorpus(), text, new AsyncCallback<Integer>() {
                public void onSuccess(Integer count) {
                    if (count > 0) {
                        EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, "Removed text from " + count + " articles."));
                        getArticles(getSelectedCorpus());
                    } else {
                        EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, "Text not found."));
                    }
                }

                public void onFailure(Throwable caught) {
                    EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, "Error: " + caught.getMessage()));
                    log("Error removeSelectedText: " + caught.getMessage());
                }
            });
        }
    }

    private void deleteArticle(final ArticleDTO articleDTO) {
        EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_NOT_READY, "Deleting article..."));
        getServiceRegistry().getArticleService().deleteArticle(articleDTO, new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
                EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, "Error: " + caught.getMessage()));
                log("Error deleteArticle: " + caught.getMessage());
            }

            public void onSuccess(Void result) {
                EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, ""));
                getArticles(getSelectedCorpus());
            }
        });
    }

    /*
     * Add row to the Articles Table
     */
    private void addArticleToTable(FixedWidthGrid table, final ArticleDTO articleDTO, int row) {
        String title = (articleDTO.getTitle() == null) ? "<none>" : articleDTO.getTitle();
        String date = (articleDTO.getDateCreated() != null) ? df.format(articleDTO.getDateCreated()) : "";
        if (title.length() > 100) {
            title = title.substring(0, 100);
        }
        String url = (articleDTO.getUrl() == null) ? "<none>" : articleDTO.getUrl();
        if (url.length() > 50) {
            url = url.substring(0, Math.min(20, url.length())) + "..." + url.substring(url.lastIndexOf("/"));
        }
        if (url.length() > 70) {
            url = url.substring(0, 70);
        }

        table.setWidget(row, 0, new Label(String.valueOf(articleDTO.getId())));
        table.setWidget(row, 1, new Label(title));
        table.setWidget(row, 2, new Label(date));
        table.setWidget(row, 3, new HTML("<a href='" + articleDTO.getUrl() + "'>" + url + "</a>"));

    }

    private void getArticles(final CorpusDTO queryDTO) {
        getServiceRegistry().getCorpusService().getArticles(queryDTO, new AsyncCallback<ArticleDTO[]>() {
            public void onFailure(Throwable caught) {
                EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, "Error: " + caught.getMessage()));
                log("Error getArticles" + caught.getMessage());
            }

            public void onSuccess(ArticleDTO[] articles) {
                if (articles != null) {
                    populateArticleTable(articles);
                    setDirty(false);
                } else {
                    log("getArticles returned null");
                }
            }
        });
    }

    private void showCorpus(CorpusDTO corpusDTO) {
        corpusTitleHTML.setHTML("<b>" + corpusDTO.getName() + "</b>");
        getArticles(corpusDTO);
    }

    private void populateArticleTable(ArticleDTO[] articles) {
        this.articleTextArea.setText("");
        FixedWidthGrid dataTable = getArticleTable().getDataTable();
        log("about to remove rows " + dataTable.getRowCount());
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            dataTable.removeRow(i);
            log("removed row " + i);
        }
        setArticleList(articles);
        if (articles != null) {
            dataTable.resizeRows(articles.length);
            for (int i = 0; i < articles.length; i++) {
                addArticleToTable(dataTable, articles[i], i);
            }
        } else {
            setSelectedArticle(null);
        }
    }

    private ScrollTable createArticleTable() {
        FixedWidthFlexTable headerTable = createHeaderTable();
        FixedWidthGrid dataTable = createDataTable();
        ScrollTable scrollTable = new ScrollTable(dataTable, headerTable);

        scrollTable.setSortPolicy(AbstractScrollTable.SortPolicy.SINGLE_CELL);

        scrollTable.setResizePolicy(ScrollTable.ResizePolicy.FILL_WIDTH);
        scrollTable.setWidth("100%");
        scrollTable.setHeight("400px");
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
        final FixedWidthGrid dataTable = new FixedWidthGrid(0, 4);
        dataTable.setSelectionPolicy(SelectionGrid.SelectionPolicy.ONE_ROW);
        dataTable.addRowSelectionHandler(new RowSelectionHandler() {
            public void onRowSelection(RowSelectionEvent event) {
                Set<Row> rows = event.getSelectedRows();
                if ((rows != null) && rows.iterator().hasNext()) {
                    deleteArticleButton.setEnabled(true);
                    Row row = rows.iterator().next();
                    int selectedIndex = row.getRowIndex();
                    String id = dataTable.getCellFormatter().getElement(selectedIndex, 0).getInnerText();
                    log("selected id is: " + id);
                    ArticleDTO article = getArticleById(id);
                    setSelectedArticle(article);
                    showArticleText();
                } else {
                    setSelectedArticle(null);
                }
            }
        });
        dataTable.setColumnSorter(new ColumnSorter() {
            @Override
            public void onSortColumn(SortableGrid grid, ColumnSortList sortList, ColumnSorterCallback callback) {
                int column = sortList.getPrimaryColumn();
                boolean ascending = sortList.isPrimaryAscending();
                // Get all of the cell elements
                SelectionGridCellFormatter formatter = grid.getSelectionGridCellFormatter();
                int rowCount = grid.getRowCount();
                List<Element> tdElems = new ArrayList<Element>(rowCount);
                for (int i = 0; i < rowCount; i++) {
                    tdElems.add(formatter.getElement(i, column));
                }

                if (column == 2) {
                    // Sort the cell elements
                    if (ascending) {
                        Collections.sort(tdElems, new Comparator<Element>() {
                            public int compare(Element o1, Element o2) {
                                return df.parse(o1.getInnerText()).compareTo(df.parse(o2.getInnerText()));
                            }
                        });
                    } else {
                        Collections.sort(tdElems, new Comparator<Element>() {
                            public int compare(Element o1, Element o2) {
                                return df.parse(o2.getInnerText()).compareTo(df.parse(o1.getInnerText()));
                            }
                        });
                    }
                } else {
                    // Sort the cell elements
                    if (ascending) {
                        Collections.sort(tdElems, new Comparator<Element>() {
                            public int compare(Element o1, Element o2) {
                                return o1.getInnerText().compareTo(o2.getInnerText());
                            }
                        });
                    } else {
                        Collections.sort(tdElems, new Comparator<Element>() {
                            public int compare(Element o1, Element o2) {
                                return o2.getInnerText().compareTo(o1.getInnerText());
                            }
                        });
                    }
                }
                // Convert tdElems to trElems, reversing if needed
                Element[] trElems = new Element[rowCount];
                for (int i = 0; i < rowCount; i++) {
                    trElems[i] = DOM.getParent(tdElems.get(i));
                }

                // Use the callback to complete the sorting
                callback.onSortingComplete(trElems);
            }
        });

        return dataTable;
    }

    private FixedWidthFlexTable createHeaderTable() {
        // Create a new table
        FixedWidthFlexTable headerTable = new FixedWidthFlexTable();
        FlexCellFormatter formatter = headerTable.getFlexCellFormatter();

        headerTable.setHTML(0, 0, "<b>#</b>");
        formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
        headerTable.setHTML(0, 1, "<b>Title</b>");
        formatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
        headerTable.setHTML(0, 2, "<b>Date</b>");
        formatter.setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_CENTER);
        headerTable.setHTML(0, 3, "<b>URL</b>");
        formatter.setHorizontalAlignment(0, 3, HasHorizontalAlignment.ALIGN_CENTER);

        // Return the header table
        return headerTable;
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected && (getSelectedCorpus() != null) && isDirty()) {
            showCorpus(getSelectedCorpus());
        }
    }

    private ArticleDTO getArticleById(String idString) {
        try {
            int id = Integer.parseInt(idString);
            for (ArticleDTO article : this.getArticleList()) {
                if (article.getId() == id) {
                    return article;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public ArticleDTO getSelectedArticle() {
        return selectedArticle;
    }

    public void setSelectedArticle(ArticleDTO selectedArticle) {
        this.selectedArticle = selectedArticle;
    }

    public ArticleDTO[] getArticleList() {
        return articleList;
    }

    public void setArticleList(ArticleDTO[] articleList) {
        this.articleList = articleList;
    }

    public AbstractScrollTable getArticleTable() {
        return articleTable;
    }

    public CorpusDTO getSelectedCorpus() {
        return selectedCorpus;
    }

    public void setSelectedCorpus(CorpusDTO selectedCorpus) {
        this.selectedCorpus = selectedCorpus;
        deleteNonUniqueArticleButton.setEnabled(selectedCorpus != null);
        removeSelectedTextButton.setEnabled(selectedCorpus != null);
        replaceSelectedTextButton.setEnabled(selectedCorpus != null);
        setDirty(true);
    }
}
