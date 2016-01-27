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
package org.eclipse.rap.addons.chart.demo.internal;

import org.eclipse.rap.addons.chart.nv.demo.ChartSnippetPage;
import org.eclipse.rap.examples.IExamplePage;
import org.eclipse.swt.widgets.Composite;


public class ChartExamplePage implements IExamplePage {

  @Override
  public void createControl( Composite parent ) {
    new ChartSnippetPage().createContents( parent );
  }

}
