
var timer = new TimerControl();
timer.initialize( 1 );
var graphui=new GraphUI();
graphui.initialize(document.getElementById('frame'), document.getElementById('origin'), true, false);
var graph=new Graph();
graph.initialize( 400, 400 );

graph.setUI( graphui );
timer.subscribe( graph );

var control = new UserControl();
control.initialize( timer, graph, graphui );

var elements = document.getElementsByClassName("node");
var nodes = new Array();

for (var i = 0; i < elements.length; i++) {
  var element = elements[i];
  var title = element.attributes.getNamedItem("title").value;
  var mass = element.attributes.getNamedItem("mass").value;
  var weight = element.attributes.getNamedItem("weight").value;
  nodes[i] = control.addNode(title, i, true);
  
  graphui.getNode( nodes[i].id ).style.backgroundColor="#99ee55";
  graphui.getNode( nodes[i].id ).style.border="1px solid #69be25";
}



