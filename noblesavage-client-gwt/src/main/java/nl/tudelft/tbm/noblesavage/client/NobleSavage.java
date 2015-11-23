package nl.tudelft.tbm.noblesavage.client;

import nl.tudelft.tbm.noblesavage.facade.CorpusFacade;
import nl.tudelft.tbm.noblesavage.facade.CorpusFacadeAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class NobleSavage implements EntryPoint {

  private final CorpusFacadeAsync corpusFacade = GWT.create(CorpusFacade.class);

  final TextBox textBox = new TextBox();

  @Override
  public void onModuleLoad() {
    RootPanel.get("textBoxContainer").add(textBox);
    textBox.setText("no response");
    corpusFacade.test(new AsyncCallback<String>() {
      @Override
      public void onSuccess(String response) {
        textBox.setText(response);
      }

      @Override
      public void onFailure(Throwable err) {
        textBox.setText(err.getMessage());
      }
    });

  }
}
