package nl.tudelft.tbm.noblesavage.client.components;

import java.util.LinkedHashMap;
import java.util.Map;

import nl.tudelft.tbm.noblesavage.client.ClientState;
import nl.tudelft.tbm.noblesavage.client.ServiceRegistry;
import nl.tudelft.tbm.noblesavage.client.events.CorpusEvent;
import nl.tudelft.tbm.noblesavage.client.events.CorpusEventHandler;
import nl.tudelft.tbm.noblesavage.client.events.EventBus;
import nl.tudelft.tbm.noblesavage.client.events.LoginEvent;
import nl.tudelft.tbm.noblesavage.client.events.LoginHandler;
import nl.tudelft.tbm.noblesavage.client.log.Logger;
import nl.tudelft.tbm.noblesavage.facade.CorpusDTO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainPane extends Pane {

  private final CorpusPane corpusPane;
  private final ArticlePane articlePane;
  private final CorpusDetailPane detailPane;
  private final DecoratedTabPanel tabPanel;
  private final VerticalPanel containerPanel;
  private final LoginScreen loginScreen;
  private final Button loginButton = new Button();
  private HandlerRegistration loginButtonHandlerRegistration;
  
  private static final String HELP_MESSAGE = "<div style=\"font-size: 12pt; text-align:center;\"><p style=\"font-size: 18pt; text-align:center;\">NobleSavage</p><img src=\"http://3.bp.blogspot.com/_aOQLE4Fqk3w/TCo3ZZVlCYI/AAAAAAAAFMg/as6mEYBcyWs/s1600/vlcsnap-00155.jpg\" width=\"256\" height=\"192\" style=\"margin-left:auto;margin-right:auto;\"/><br/><br/>Please contact <a href=\"mailto:a.chmieliauskas@tudelft.nl\" style=\"font-size: 12pt\">Alfredas Chmieliauskas</a> or <J.Kasmire@tudelft.nl> <a href=\"mailto:J.Kasmire@tudelft.nl\" style=\"font-size: 12pt\">Julia Kasmire</a> for more information on NobleSavage </div>";
  
  
  private CorpusDTO selectedCorpus = null;

  private Map<String, Pane> tabMap = new LinkedHashMap<String, Pane>();

  public MainPane(ClientState clientState, ServiceRegistry serviceRegistry, Logger logger) {
    super(clientState, serviceRegistry, logger, MainPane.class.getName());
    
    loginScreen = new LoginScreen(clientState, serviceRegistry, logger);
    corpusPane = new CorpusPane(clientState, serviceRegistry, logger);
    articlePane = new ArticlePane(clientState, serviceRegistry, logger);
    detailPane = new CorpusDetailPane(clientState, serviceRegistry, logger);

    tabMap.put("CORPORA", corpusPane);
    tabMap.put("ARTICLES", articlePane);
    tabMap.put("ANALYZE", detailPane);

    tabPanel = new DecoratedTabPanel();

    tabPanel.setWidth("100%");
    tabPanel.setHeight("100%");
    tabPanel.setAnimationEnabled(false);
    
    for (String name : tabMap.keySet()) {
      tabPanel.add(tabMap.get(name), name);
    }

    tabPanel.selectTab(0);

    tabPanel.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {
      public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
        if (event.getItem() != 0) {
          if (getSelectedCorpus() == null) {
            event.cancel();
          }
        }
      }
    });

    tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
      public void onSelection(SelectionEvent<Integer> event) {
        Integer selectedIndex = event.getSelectedItem();
        log("Selected index: " + selectedIndex);
        int i = 0;
        for (String name : tabMap.keySet()) {
          Pane pane = tabMap.get(name);
          if (i != selectedIndex) {
            pane.setSelected(false);
          } else {
            pane.setSelected(true);
            log("Setting " + pane.getTitle() + " to selected");
          }
          i++;
        }
      }
    });

    // handle corpus events
    EventBus.get().addHandler(CorpusEvent.TYPE, new CorpusEventHandler() {
      public void onCorpusSelected(CorpusEvent event) {
      	if (event.getCorpusDTO() != null && event.getCorpusDTO().length > 0) {
      		setSelectedCorpus(event.getCorpusDTO()[0]);
      	}
      }

      public void onListChanged(CorpusEvent event) {
        setSelectedCorpus(null);
      }

      public void onListLoaded(CorpusEvent event) {
      }
    });
    
    containerPanel = new VerticalPanel();
    containerPanel.setWidth("100%");
    containerPanel.setHeight("100%");
    
    HorizontalPanel loginButtonPanel = new HorizontalPanel();
    HorizontalPanel p = new HorizontalPanel();
    p.setWidth("100px");
    loginButtonPanel.setWidth("100%");
    loginButtonPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
    
    setLoginLogoutButtonMode();
    loginButton.setStylePrimaryName("ns-smallButton");
    
    Button helpButton = new Button("Help");
    helpButton.setStylePrimaryName("ns-smallButton");
    
    helpButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				HelpPopup hp = new HelpPopup();
				hp.center();
				hp.show();
			}
		});
    
    p.add(loginButton);
    p.add(helpButton);
    loginButtonPanel.add(p);
    
    containerPanel.add(loginButtonPanel);
    containerPanel.add(tabPanel);
    
    EventBus.get().addHandler(LoginEvent.TYPE, new LoginHandler() {
			public void onLogout(LoginEvent event) {
				setLoginLogoutButtonMode();
			}
			public void onLogin(LoginEvent event) {
				setLoginLogoutButtonMode();
			}
		});
    
    initWidget(containerPanel);
  }
  
  
  private void setLoginLogoutButtonMode() {
  	if (!this.getClientState().isLoggedIn()) {
	    loginButton.setText("Login");
	    if (loginButtonHandlerRegistration != null) {
	    	loginButtonHandlerRegistration.removeHandler();
	    }
	    loginButtonHandlerRegistration = loginButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					loginScreen.center();
					loginScreen.show();
				}
			});
    } else {
    	loginButton.setText("Logout " + getClientState().getUsername());
    	if (loginButtonHandlerRegistration != null) {
    		loginButtonHandlerRegistration.removeHandler();
    	}
    	loginButtonHandlerRegistration = loginButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					getClientState().setUser(null);
					EventBus.get().fireEvent(new LoginEvent(LoginEvent.IS_LOGGED_OUT));
				}
			});
    }
  }
  
  
  public CorpusDTO getSelectedCorpus() {
    return selectedCorpus;
  }

  public void setSelectedCorpus(CorpusDTO selectedCorpus) {
    this.selectedCorpus = selectedCorpus;
  }
  
  class HelpPopup extends PopupPanel {

		public HelpPopup() {
			super(true);
			
			setWidth("300px");
			setHeight("350px");
			
			setWidget(new HTML(HELP_MESSAGE));
		}
	}
  
}
