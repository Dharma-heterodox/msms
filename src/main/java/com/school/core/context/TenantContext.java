package com.school.core.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TenantContext {
    private static Logger logger = LoggerFactory.getLogger(TenantContext.class.getName());
    private static ThreadLocal<Integer> currentTenant = new ThreadLocal<>();
    public static void setCurrentTenant(Integer tenant) {
        logger.debug("Setting tenant to " + tenant);
        currentTenant.set(tenant);
    }
    public static Integer getCurrentTenant() {
        return currentTenant.get();
    }
    public static void clear() {
        currentTenant.set(null);
    }
}
