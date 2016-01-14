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

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;


public class ChartSnippetPage extends AbstractEntryPoint {

  @Override
  public void createContents( Composite parent ) {
    parent.setLayout( new GridLayout() );
    createPieChart( parent );
    createLineChart( parent );
  }

  private static void createPieChart( Composite parent ) {
    Composite composite = new Composite( parent, SWT.NONE );
    composite.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    composite.setBackground( new Color( parent.getDisplay(), 250, 250, 250 ) );
    composite.setBackgroundMode( SWT.INHERIT_DEFAULT );
    new PieChartSnippet().createContents( composite );
  }

  private static void createLineChart( Composite parent ) {
    Composite composite = new Composite( parent, SWT.NONE );
    composite.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    composite.setBackground( new Color( parent.getDisplay(), 250, 250, 250 ) );
    composite.setBackgroundMode( SWT.INHERIT_DEFAULT );
    new LineChartSnippet().createContents( composite );
  }

}
