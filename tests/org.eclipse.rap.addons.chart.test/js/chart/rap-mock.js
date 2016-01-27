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

rap = {

  _listeners: {},

  on: function( eventType, listener ) {
    if( !this._listeners[ eventType ] ) {
      this._listeners[ eventType ] = [];
    }
    this._listeners[ eventType ].push( listener );
  },

  off: function( eventType, listener ) {
    if( this._listeners[ eventType ] ) {
      var listeners = this._listeners[ eventType ];
      var index = -1;
      for( var i = 0; i < listeners.length; i++ ) {
        if( listeners[ i ] === listener ) {
          index = i;
        }
      }
      if( index !== -1 ) {
        listeners.splice( i, 1 );
      }
    }
  },

  registerTypeHandler: function() {
  },

  setup: function() {
    this._listeners = {};
  },

  notify: function( eventType ) {
    if( this._listeners[ eventType ] ) {
      var listeners = this._listeners[ eventType ];
      for( var i = 0; i < listeners.length; i++ ) {
        listeners[ i ]();
      }
    }
  }

};

rwt = {
};
