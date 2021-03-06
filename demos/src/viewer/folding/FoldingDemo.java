/****************************************************************************
 **
 ** This demo file is part of yFiles for JavaFX 3.3.
 **
 ** Copyright (c) 2000-2020 by yWorks GmbH, Vor dem Kreuzberg 28,
 ** 72070 Tuebingen, Germany. All rights reserved.
 **
 ** yFiles demo files exhibit yFiles for JavaFX functionalities. Any redistribution
 ** of demo files in source code or binary form, with or without
 ** modification, is not permitted.
 **
 ** Owners of a valid software license for a yFiles for JavaFX version that this
 ** demo is shipped with are allowed to use the demo source code as basis
 ** for their own yFiles for JavaFX powered applications. Use of such programs is
 ** governed by the rights and conditions as set out in the yFiles for JavaFX
 ** license agreement.
 **
 ** THIS SOFTWARE IS PROVIDED ''AS IS'' AND ANY EXPRESS OR IMPLIED
 ** WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 ** MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN
 ** NO EVENT SHALL yWorks BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 ** SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 ** TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 ** PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 ** LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 ** NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 ** SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 **
 ***************************************************************************/
package viewer.folding;

import com.yworks.yfiles.geometry.InsetsD;
import com.yworks.yfiles.geometry.PointD;
import com.yworks.yfiles.geometry.SizeD;
import com.yworks.yfiles.graph.DefaultGraph;
import com.yworks.yfiles.graph.FoldingManager;
import com.yworks.yfiles.graph.IFoldingView;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.graph.labelmodels.InteriorStretchLabelModel;
import com.yworks.yfiles.graph.styles.CollapsibleNodeStyleDecorator;
import com.yworks.yfiles.graph.styles.DefaultLabelStyle;
import com.yworks.yfiles.graph.styles.INodeStyle;
import com.yworks.yfiles.graph.styles.PanelNodeStyle;
import com.yworks.yfiles.graph.styles.ShinyPlateNodeStyle;
import com.yworks.yfiles.view.GraphControl;
import com.yworks.yfiles.view.input.GraphEditorInputMode;
import com.yworks.yfiles.view.input.ICommand;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebView;
import toolkit.DemoApplication;
import toolkit.WebViewUtils;

/**
 * This demo shows how to enable collapsing and expanding of group nodes.
 */
public class FoldingDemo extends DemoApplication {
  public GraphControl graphControl;
  public WebView helpView;

  /**
   * Enables folding and creates a folding view.
   * @return The folding view graph.
   * @param masterGraph The master graph to create a folding view graph for.
   */
  private IGraph enableFolding(IGraph masterGraph) {
    // creates the folding manager
    FoldingManager manager = new FoldingManager(masterGraph);

    // creates a folding view that manages the folded graph
    IFoldingView foldingView = manager.createFoldingView();

    // return the view graph
    return foldingView.getGraph();
  }

  private void wrapGroupNodeStyle(IGraph fullGraph) {
    // add a collapse/expand button to the group node style default
    INodeStyle groupNodeStyle = fullGraph.getGroupNodeDefaults().getStyle();
    CollapsibleNodeStyleDecorator decoratedStyle = new CollapsibleNodeStyleDecorator(groupNodeStyle);
    fullGraph.getGroupNodeDefaults().setStyle(decoratedStyle);
  }

  /**
   * Initializes the graph and the input modes.
   */
  public void initialize() {
    // create and initialize the master graph
    IGraph masterGraph = initializeMasterGraph();

    // enable abilities for folding
    IGraph viewGraph = enableFolding(masterGraph);

    // assign the folding view graph to the graph component
    graphControl.setGraph(viewGraph);

    // initializes the input modes
    initializeInputMode();

    // show the help text
    WebViewUtils.initHelp(helpView, this);
  }

  private IGraph initializeMasterGraph() {
    // create the unfolded master graph
    DefaultGraph masterGraph = new DefaultGraph();

    // set default styles for newly created graph elements
    initializeDefaults(masterGraph);

    // create an initial sample graph
    createInitialGraph(masterGraph);

    // enable undo after the initial graph was populated since we don't want to allow undoing that
    masterGraph.setUndoEngineEnabled(true);

    return masterGraph;
  }

  /**
   * Initializes the defaults for the styles.
   */
  private void initializeDefaults(IGraph graph) {
    // configure defaults for normal nodes
    ShinyPlateNodeStyle defaultNodeStyle = new ShinyPlateNodeStyle();
    defaultNodeStyle.setPaint(Color.DARKORANGE);
    graph.getNodeDefaults().setStyle(defaultNodeStyle);
    graph.getNodeDefaults().setSize(new SizeD(40, 40));

    // configure defaults for group nodes and their labels
    PanelNodeStyle panelNodeStyle = new PanelNodeStyle();
    Color groupNodeColor = Color.rgb(214, 229, 248);
    panelNodeStyle.setColor(groupNodeColor);
    panelNodeStyle.setInsets(new InsetsD(23, 5, 5, 5));
    panelNodeStyle.setLabelInsetsColor(groupNodeColor);
    graph.getGroupNodeDefaults().setStyle(panelNodeStyle);

    DefaultLabelStyle defaultLabelStyle = new DefaultLabelStyle();
    defaultLabelStyle.setTextAlignment(TextAlignment.RIGHT);
    graph.getGroupNodeDefaults().getLabelDefaults().setStyle(defaultLabelStyle);

    graph.getGroupNodeDefaults().getLabelDefaults().setLayoutParameter(InteriorStretchLabelModel.NORTH);

    // wrap group node style to provie +/- buttons to expand/collapse the group
    wrapGroupNodeStyle(graph);
  }

  /**
   * Creates an initial sample graph.
   */
  private void createInitialGraph(IGraph graph) {
    INode node1 = graph.createNode(new PointD(110, 20));
    INode node2 = graph.createNode(new PointD(145, 95));
    INode node3 = graph.createNode(new PointD(75, 95));
    INode node4 = graph.createNode(new PointD(30, 175));
    INode node5 = graph.createNode(new PointD(100, 175));

    INode groupNode = graph.groupNodes(node1, node2, node3);
    graph.addLabel(groupNode, "Group 1");

    graph.createEdge(node1, node2);
    graph.createEdge(node1, node3);
    graph.createEdge(node3, node4);
    graph.createEdge(node3, node5);
    graph.createEdge(node1, node5);
  }

  /**
   * Creates and initializes the input mode.
   */
  private void initializeInputMode() {
    GraphEditorInputMode mode = new GraphEditorInputMode();

    // enables grouping operations such as grouping selected nodes moving nodes into group nodes
    mode.setGroupingOperationsAllowed(true);

    // clear graph when executing NEW command
    mode.getKeyboardInputMode().addCommandBinding(ICommand.NEW,
        (command, parameter, source) -> {
          graphControl.getGraph().clear();
          graphControl.fitGraphBounds();
          return true;
        },
        (command, parameter, source) ->
            graphControl.getGraph().getNodes().size() != 0);

    graphControl.setInputMode(mode);
  }

  /**
   * Adjusts the view by the first start of the demo.
   */
  @Override
  protected void onLoaded() {
    super.onLoaded();
    graphControl.fitGraphBounds();
  }

  public static void main( String[] args ) {
    launch(args);
  }
}
