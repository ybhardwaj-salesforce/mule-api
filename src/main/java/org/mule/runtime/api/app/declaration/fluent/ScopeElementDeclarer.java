/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import org.mule.runtime.api.app.declaration.RouteElementDeclaration;
import org.mule.runtime.api.app.declaration.ScopeElementDeclaration;

/**
 * Allows configuring an {@link ScopeElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class ScopeElementDeclarer
    extends ComponentElementDeclarer<ScopeElementDeclarer, ScopeElementDeclaration> {

  /**
   * Creates a new instance
   *
   */
  ScopeElementDeclarer(ScopeElementDeclaration declaration) {
    super(declaration);
  }

  /**
   * Sets a {@link RouteElementDeclaration component} to the {@link ScopeElementDeclaration}
   *
   * @param route the {@link RouteElementDeclaration route} to set
   * @return {@code this} declarer
   */
  public ScopeElementDeclarer withRoute(RouteElementDeclaration route) {
    declaration.setRoute(route);
    return this;
  }

}