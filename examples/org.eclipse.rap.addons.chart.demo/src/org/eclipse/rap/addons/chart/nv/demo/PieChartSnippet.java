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

import org.eclipse.rap.addons.chart.basic.PieChart;
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


public class PieChartSnippet extends AbstractEntryPoint {

  private PieChart pieChart;

  @Override
  public void createContents( Composite parent ) {
    parent.setLayout( new GridLayout() );
    createPieChart( parent );
    createUpdateButton( parent );
    update();
  }

  private void createPieChart( Composite parent ) {
    pieChart = new PieChart( parent, SWT.NONE );
    pieChart.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    pieChart.setShowLabels( true );
    pieChart.addListener( SWT.Selection, new Listener() {
      @Override
      public void handleEvent( Event event ) {
        System.out.println( "Selected pie item #" + event.index );
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
    pieChart.setChartData( createData() );
  }

  private static JsonArray createData() {
    return new JsonArray()
      .add( createItem( "Item 1", "#ff0000", Math.random() * 100 ) )
      .add( createItem( "Item 2", "#00ff00", Math.random() * 100 ) )
      .add( createItem( "Item 3", "#0000ff", Math.random() * 100 ) );
  }

  private static JsonObject createItem( String text, String color, double value ) {
    return new JsonObject()
      .add( "label", text )
      .add( "color", color )
      .add( "value", value );
  }

}
