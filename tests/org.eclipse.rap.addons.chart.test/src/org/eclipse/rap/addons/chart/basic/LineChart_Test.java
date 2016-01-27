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
package org.eclipse.rap.addons.chart.basic;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;

import org.eclipse.rap.json.JsonObject;
import org.eclipse.rap.rwt.internal.remote.ConnectionImpl;
import org.eclipse.rap.rwt.remote.Connection;
import org.eclipse.rap.rwt.remote.RemoteObject;
import org.eclipse.rap.rwt.testfixture.TestContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SuppressWarnings( "restriction" )
public class LineChart_Test {

  private Display display;
  private Shell shell;
  private RemoteObject remoteObject;
  private Connection connection;
  private LineChart chart;

  @Rule
  public TestContext context = new TestContext();

  @Before
  public void setUp() {
    display = new Display();
    shell = new Shell( display );
    remoteObject = mock( RemoteObject.class );
    connection = fakeConnection( remoteObject );
    chart = new LineChart( shell, SWT.NONE );
  }

  @Test
  public void testCreate_createsRemoteObject() {
    verify( connection ).createRemoteObject( eq( "rwt.chart.Chart" ) );
  }

  @Test
  public void testCreate_setsRenderer() {
    verify( remoteObject ).set( "renderer", "nv-line" );
  }

  @Test
  public void testGetXAxisLabel_hasDefault() {
    assertEquals( "", chart.getXAxisLabel() );
  }

  @Test( expected = SWTException.class )
  public void testGetXAxisLabel_checksWidget() {
    chart.dispose();

    chart.getXAxisLabel();
  }

  @Test
  public void testSetXAxisLabel_changesValue() {
    chart.setXAxisLabel( "foo" );

    assertEquals( "foo", chart.getXAxisLabel() );
  }

  @Test( expected = SWTException.class )
  public void testSetXAxisLabel_checksWidget() {
    chart.dispose();

    chart.setXAxisLabel( "foo" );
  }

  @Test
  public void testSetXAxisLabel_isRendered() {
    reset( remoteObject );

    chart.setXAxisLabel( "foo" );

    verify( remoteObject ).call( "setOptions", new JsonObject().add( "xAxis.axisLabel", "foo" ) );
  }

  @Test
  public void testSetXAxisLabel_notRenderedIfUnchanged() {
    chart.setXAxisLabel( chart.getXAxisLabel() );

    verify( remoteObject, times( 0 ) ).set( eq( "barWidth" ), anyInt() );
  }

  @Test( expected = SWTException.class )
  public void testGetXAxisFormat_checksWidget() {
    chart.dispose();

    chart.getXAxisFormat();
  }

  @Test
  public void testSetXAxisFormat_changesValue() {
    chart.setXAxisFormat( "foo" );

    assertEquals( "foo", chart.getXAxisFormat() );
  }

  @Test( expected = SWTException.class )
  public void testSetXAxisFormat_checksWidget() {
    chart.dispose();

    chart.setXAxisFormat( "d" );
  }

  @Test
  public void testSetXAxisFormat_isRendered() {
    reset( remoteObject );

    chart.setXAxisFormat( "d" );

    verify( remoteObject ).call( "setOptions", new JsonObject().add( "xAxisFormat", "d" ) );
  }

  @Test
  public void testSetXAxisFormat_notRenderedIfUnchanged() {
    chart.setXAxisFormat( chart.getXAxisFormat() );

    verify( remoteObject, times( 0 ) ).set( eq( "spacing" ), anyInt() );
  }

  private Connection fakeConnection( RemoteObject remoteObject ) {
    ConnectionImpl connection = mock( ConnectionImpl.class );
    when( connection.createRemoteObject( anyString() ) ).thenReturn( remoteObject );
    when( connection.createServiceObject( anyString() ) ).thenReturn( mock( RemoteObject.class ) );
    context.replaceConnection( connection );
    return connection;
  }

}
