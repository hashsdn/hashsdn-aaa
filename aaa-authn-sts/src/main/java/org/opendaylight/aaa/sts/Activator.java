/*
 * Copyright (c) 2014 Hewlett-Packard Development Company, L.P. and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.aaa.sts;

import org.apache.felix.dm.Component;
import org.opendaylight.aaa.api.AuthenticationService;
import org.opendaylight.aaa.api.ClaimAuth;
import org.opendaylight.aaa.api.CredentialAuth;
import org.opendaylight.aaa.api.TokenAuth;
import org.opendaylight.aaa.api.TokenStore;
import org.opendaylight.controller.sal.core.ComponentActivatorAbstractBase;

/**
 * An activator for the secure token server to inject in a
 * {@link CredentialAuth} implementation.
 *
 * @author liemmn
 *
 */
public class Activator extends ComponentActivatorAbstractBase {
    @Override
    public Object[] getImplementations() {
        Object[] res = { ServiceLocator.INSTANCE };
        return res;
    }

    @Override
    public void configureInstance(Component c, Object imp, String container) {
        if (imp.equals(ServiceLocator.INSTANCE)) {
            c.add(createContainerServiceDependency(container).setService(
                    CredentialAuth.class).setRequired(true));
            c.add(createContainerServiceDependency(container)
                    .setService(ClaimAuth.class).setRequired(false)
                    .setCallbacks("claimAuthAdded", "claimAuthRemoved"));
            c.add(createContainerServiceDependency(container)
                    .setService(TokenAuth.class).setRequired(false)
                    .setCallbacks("tokenAuthAdded", "tokenAuthRemoved"));
            c.add(createContainerServiceDependency(container).setService(
                    TokenStore.class).setRequired(true));
            c.add(createContainerServiceDependency(container).setService(
                    AuthenticationService.class).setRequired(true));
        }
    }
}