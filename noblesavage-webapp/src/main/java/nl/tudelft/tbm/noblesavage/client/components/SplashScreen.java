package nl.tudelft.tbm.noblesavage.client.components;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SplashScreen extends DialogBox {
	
	private String message;
	private VerticalPanel container;
	private Label messageLabel;
	private Button closeButton = new Button("Close"); 
	
	public SplashScreen() {
		// no autohide, yes modal
		super(false, true);
		
		container = new VerticalPanel();
		messageLabel = new Label();
		
		closeButton.setStylePrimaryName("ns-smallButton");
		closeButton.setVisible(false);
		
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				hide();
			}
		});
		
		this.setText("Please wait!");
		
		container.setWidth("200px");
		container.setHeight("200px");
		container.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		container.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		container.add(messageLabel);
		container.add(closeButton);
		
		this.setWidget(container);
		this.setGlassEnabled(true);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
		this.messageLabel.setText(message);
	}
	
	public void showCloseButton(boolean visible) {
		this.closeButton.setVisible(visible);
	}

}
