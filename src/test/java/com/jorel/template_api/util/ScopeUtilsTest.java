package com.jorel.template_api.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScopeUtilsTest {

    @Test
    void isLocal_whenProfileLocal_returnsTrue() {
        ScopeUtils utils = new ScopeUtils("local");
        assertTrue(utils.isLocal());
        assertFalse(utils.isBeta());
        assertFalse(utils.isProd());
        assertEquals("local", utils.getActiveProfile());
    }

    @Test
    void isBeta_whenProfileBeta_returnsTrue() {
        ScopeUtils utils = new ScopeUtils("beta");
        assertFalse(utils.isLocal());
        assertTrue(utils.isBeta());
        assertFalse(utils.isProd());
        assertEquals("beta", utils.getActiveProfile());
    }

    @Test
    void isProd_whenProfileProd_returnsTrue() {
        ScopeUtils utils = new ScopeUtils("prod");
        assertFalse(utils.isLocal());
        assertFalse(utils.isBeta());
        assertTrue(utils.isProd());
        assertEquals("prod", utils.getActiveProfile());
    }

    @Test
    void getActiveProfile_returnsConfiguredProfile() {
        ScopeUtils utils = new ScopeUtils("custom-profile");
        assertEquals("custom-profile", utils.getActiveProfile());
    }

    @Test
    void logProfileInfo_withLocalProfile_logsLocalDescription() {
        ScopeUtils utils = new ScopeUtils("local");
        utils.logProfileInfo();
    }

    @Test
    void logProfileInfo_withBetaProfile_logsBetaDescription() {
        ScopeUtils utils = new ScopeUtils("beta");
        utils.logProfileInfo();
    }

    @Test
    void logProfileInfo_withProdProfile_logsProdDescription() {
        ScopeUtils utils = new ScopeUtils("prod");
        utils.logProfileInfo();
    }

    @Test
    void logProfileInfo_withUnknownProfile_logsDefaultDescription() {
        ScopeUtils utils = new ScopeUtils("unknown");
        utils.logProfileInfo();
    }
}
