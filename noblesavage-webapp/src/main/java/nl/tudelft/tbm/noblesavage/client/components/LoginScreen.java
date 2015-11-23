package nl.tudelft.tbm.noblesavage.client.components;

import nl.tudelft.tbm.noblesavage.client.ClientState;
import nl.tudelft.tbm.noblesavage.client.ServiceRegistry;
import nl.tudelft.tbm.noblesavage.client.events.EventBus;
import nl.tudelft.tbm.noblesavage.client.events.LoginEvent;
import nl.tudelft.tbm.noblesavage.client.events.ReadyEvent;
import nl.tudelft.tbm.noblesavage.client.log.Logger;
import nl.tudelft.tbm.noblesavage.facade.UserDTO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginScreen extends DialogBox {

	private final TextBox usernameBox = new TextBox();
	private final PasswordTextBox passwordBox = new PasswordTextBox();
	private final Label successLabel = new Label(); 
	private ClientState clientState;
	private ServiceRegistry serviceRegistry;
	private Logger logger;

	public LoginScreen(ClientState clientState, ServiceRegistry serviceRegistry, Logger logger) {
		this.logger = logger;
		this.clientState = clientState;
		this.serviceRegistry = serviceRegistry;
		
		this.setText("Login");
		this.setWidget(this.createLoginPanel());
		this.setGlassEnabled(true);
	}

	private VerticalPanel createLoginPanel() {
		
		VerticalPanel panel = new VerticalPanel();
		
		FlexTable table = new FlexTable();
		Button loginButton = new Button("Login");
		loginButton.setStylePrimaryName("ns-smallButton");
		usernameBox.addStyleName("ns-smallInput");
		passwordBox.addStyleName("ns-smallInput");
		
		table.setText(0, 0, "Username:");
		table.setText(1, 0, "Password:");

		table.setWidget(0, 1, usernameBox);
		table.setWidget(1, 1, passwordBox);
		table.setWidget(2, 0, loginButton);
		table.setWidget(2, 1, successLabel);
		
		
		loginButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				successLabel.setText("");
				login(usernameBox.getText(), passwordBox.getText());
			}
		});
		
		panel.setWidth("200px");
		panel.setHeight("150px");
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		panel.add(table);
		
		Button closeButton = new Button("Close");
		closeButton.setStylePrimaryName("ns-smallButton");
		
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				close();
			}
		});
		
		panel.add(closeButton);
		return panel;
	}
	
	private void close() {
		this.hide();
	}
	
	private void login(String username, String password) {
		
		EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_NOT_READY, "Logging in..."));
    this.serviceRegistry.getUserService().login(username, password, new AsyncCallback<UserDTO>() {
			public void onSuccess(UserDTO userDTO) {
				EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, null));
				logger.log("login success");
				if (userDTO != null) {
					logger.log("user is " + userDTO.getUsername());
					clientState.setUser(userDTO);
					EventBus.get().fireEvent(new LoginEvent(LoginEvent.IS_LOGGED_IN));
					close();
				} else {
					logger.log("user is null");
					successLabel.setText("Bad username/password");
				}
			}
			
			public void onFailure(Throwable cause) {
				logger.log(cause.getMessage());
				successLabel.setText("Failed to log in.");
				EventBus.get().fireEvent(new ReadyEvent(ReadyEvent.IS_READY, null));
			}
		});
	}

}
