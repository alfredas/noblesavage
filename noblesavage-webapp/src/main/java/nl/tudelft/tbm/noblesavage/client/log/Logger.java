package nl.tudelft.tbm.noblesavage.client.log;

import com.google.gwt.user.client.ui.ListBox;

public class Logger extends ListBox {

  public Logger() {
    super();
    setWidth("100%");
    setVisibleItemCount(7);
  }

  public void log(String mesasge) {
    super.addItem(mesasge);
  }

  public void info(String mesasge) {
    log(mesasge);
  }

  public void error(String mesasge) {
    log(mesasge);
  }

}
