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
package viewer.graphmlcompatibility.extensions.for2x;

import com.yworks.yfiles.graph.IBend;
import com.yworks.yfiles.graph.ILabel;
import com.yworks.yfiles.graph.ILookup;
import com.yworks.yfiles.graph.IPort;
import com.yworks.yfiles.graph.styles.IEdgeStyle;
import com.yworks.yfiles.graphml.GraphML;
import com.yworks.yfiles.graphml.GraphMLMemberVisibility;
import com.yworks.yfiles.graphml.MarkupExtension;
import com.yworks.yfiles.utils.Obfuscation;

import java.util.List;

/**
 * Handles the yFiles for JavaFX 2.0.x version of GraphML element
 * <code>&lt;EdgeViewState&gt;</code>.
 */
@Obfuscation(stripAfterObfuscation = false, exclude = true, applyToMembers = true)
public class EdgeViewStateExtension extends MarkupExtension {
  final com.yworks.yfiles.graphml.FoldingEdgeStateExtension impl;

  public EdgeViewStateExtension() {
    impl = new com.yworks.yfiles.graphml.FoldingEdgeStateExtension();
  }

  @GraphML(visibility = GraphMLMemberVisibility.CONTENT)
  public List<ILabel> getLabels() {
    return impl.getLabels();
  }

  public IEdgeStyle getStyle() {
    return impl.getStyle();
  }

  public void setStyle( IEdgeStyle value ) {
    impl.setStyle(value);
  }

  @GraphML(visibility = GraphMLMemberVisibility.CONTENT)
  public List<IBend> getBends() {
    return impl.getBends();
  }

  public IPort getSourcePort() {
    return impl.getSourcePort();
  }

  public void setSourcePort( IPort value ) {
    impl.setSourcePort(value);
  }

  public IPort getTargetPort() {
    return impl.getTargetPort();
  }

  public void setTargetPort( IPort value ) {
    impl.setTargetPort(value);
  }

  public Object getTag() {
    return impl.getTag();
  }

  public void setTag( Object value ) {
    impl.setTag(value);
  }


  @Override
  public Object provideValue( ILookup serviceProvider ) {
    return impl.provideValue(serviceProvider);
  }
}
