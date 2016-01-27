/*******************************************************************************
 * Copyright (c) 2013, 2016 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Ralf Sternberg - initial API and implementation
 ******************************************************************************/
package org.eclipse.rap.addons.chart.nv.demo;

import org.eclipse.rap.addons.chart.basic.LineChart;
import org.eclipse.rap.json.JsonArray;
import org.eclipse.rap.json.JsonObject;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;


public class LineChartSnippet extends AbstractEntryPoint {

  private LineChart lineChart;

  @Override
  public void createContents( Composite parent ) {
    parent.setLayout( new GridLayout() );
    createLineChart( parent );
    createUpdateButton( parent );
    update();
  }

  private void createLineChart( Composite parent ) {
    lineChart = new LineChart( parent, SWT.NONE );
    lineChart.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    lineChart.setXAxisLabel( "Time" );
    lineChart.setYAxisLabel( "Radiation" );
    lineChart.setYAxisFormat( "d" );
    lineChart.addListener( SWT.Selection, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        System.out.println( "Selected line item #" + event.index + ", point #" + event.detail );
      }
    } );
  }

  private void createUpdateButton( Composite parent ) {
    Button button = new Button( parent, SWT.PUSH );
    button.setText( "Change data" );
    button.addListener( SWT.Selection, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        update();
      }
    } );
  }

  private void update() {
    lineChart.setChartData( createData() );
  }

  private static JsonArray createData() {
    return new JsonArray()
      .add( createItem( "Series 1", "#ff0000", createRandomSeries() ) )
      .add( createItem( "Series 2", "#0000ff", createRandomSeries() ) );
  }

  private static JsonObject createItem( String text, String color, JsonArray values ) {
    return new JsonObject()
      .add( "key", text )
      .add( "color", color )
      .add( "values", values );
  }

  private static JsonArray createRandomSeries() {
    JsonArray data = new JsonArray();
    for( int i = 0; i < 100; i++ ) {
      data.add( new JsonObject().add( "x", i ).add( "y", Math.random() * 100 ) );
    }
    return data;
  }

}
