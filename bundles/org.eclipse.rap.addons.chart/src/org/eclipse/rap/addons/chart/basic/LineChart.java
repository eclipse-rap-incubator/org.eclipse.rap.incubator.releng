/*******************************************************************************
 * Copyright (c) 2015, 2016 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Ralf Sternberg - initial API and implementation
 ******************************************************************************/
package org.eclipse.rap.addons.chart.basic;

import org.eclipse.rap.addons.chart.NvChart;
import org.eclipse.swt.widgets.Composite;


public class LineChart extends NvChart {

  private String xAxisLabel;
  private String yAxisLabel;
  private String xAxisFormat;
  private String yAxisFormat;

  public LineChart( Composite parent, int style ) {
    super( parent, style, "nv-line" );
    requireJs( registerResource( "chart/nv/nv-line.js" ) );
  }

  public void setXAxisLabel( String label ) {
    checkWidget();
    if( this.xAxisLabel != label ) {
      xAxisLabel = label;
      setOption( "xAxis.axisLabel", label );
    }
  }

  public String getXAxisLabel() {
    checkWidget();
    return xAxisLabel != null ? xAxisLabel : "";
  }

  public void setYAxisLabel( String label ) {
    checkWidget();
    if( this.yAxisLabel != label ) {
      yAxisLabel = label;
      setOption( "yAxis.axisLabel", label );
    }
  }

  public void setXAxisFormat( String format ) {
    checkWidget();
    if( this.xAxisFormat != format ) {
      xAxisFormat = format;
      setOption( "xAxisFormat", format );
    }
  }

  public String getXAxisFormat() {
    checkWidget();
    return xAxisFormat;
  }

  public void setYAxisFormat( String format ) {
    checkWidget();
    if( this.yAxisFormat != format ) {
      yAxisFormat = format;
      setOption( "yAxisFormat", format );
    }
  }

  public String getYAxisFormat() {
    checkWidget();
    return yAxisFormat;
  }

}
