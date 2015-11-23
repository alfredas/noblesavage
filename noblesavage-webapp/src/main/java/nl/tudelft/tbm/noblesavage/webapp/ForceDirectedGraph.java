package nl.tudelft.tbm.noblesavage.webapp;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ForceDirectedGraph implements Controller {

  @SuppressWarnings("unchecked")
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Map<String, Object> map = request.getParameterMap();
    Set<String> keys = map.keySet();
    Iterator<String> itr = keys.iterator();

    String graphData = "";

    while (itr.hasNext()) {
      Object key = itr.next();
      String[] nodes = key.toString().split(";");
      if (nodes.length > 0) {
        for (int i = 0; i < nodes.length; i++) {
          String[] pars = nodes[i].split(":");
          float w = Float.valueOf(pars[1]);
          String weightString = String.format("%.1f", w * 100) + "%";
          String title = pars[0];
          String weight = String.valueOf((1 - w) * 300);

          if (i == 0) {
            graphData += "<div id='origin' style='position:absolute;font-size:18px;font-weight:bold;font-family:Verdana,sans-serif;'>"
                + title + "</div>";
          } else {
            title += " " + weightString;
            graphData += "<span class='node'  title='" + title + "' mass='1' weight='" + weight + "'/>";
          }
        }
      }
    }
    ModelAndView modelAndView = new ModelAndView("graph");
    modelAndView.addObject("graphData", graphData);
    return modelAndView;
  }

}
