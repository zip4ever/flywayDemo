package com.realdolmen.flyway.demo.flyway;

import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.callback.FlywayCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.logging.Level;

/**
 * Created by KDAAU95 on 14/01/2015.
 */
public class MyFlywayCallbackImpl implements FlywayCallback {

    private final static Logger LOGGER = LoggerFactory.getLogger(MyFlywayCallbackImpl.class);

    public void beforeClean(Connection connection) {
    }

    public void afterClean(Connection connection) {
    }

    public void beforeMigrate(Connection connection) {
        LOGGER.info("Before migration");
    }
    public void afterMigrate(Connection connection) {
        LOGGER.info("After migration");
    }

    public void beforeEachMigrate(Connection connection, MigrationInfo migrationInfo) {
        String version = migrationInfo.getVersion().toString();
        String descritption = migrationInfo.getDescription();
        System.out.println(String.format("beforeEachMigrate: Version %s: %s", version, descritption));
        LOGGER.info("  Before migrate on " + version + " : " + descritption);
    }

    public void afterEachMigrate(Connection connection, MigrationInfo migrationInfo) {

    }

    public void beforeValidate(Connection connection) {

    }

    public void afterValidate(Connection connection) {

    }

    public void beforeBaseline(Connection connection) {

    }

    public void afterBaseline(Connection connection) {

    }

    public void beforeInit(Connection connection) {

    }

    public void afterInit(Connection connection) {

    }

    public void beforeRepair(Connection connection) {

    }

    public void afterRepair(Connection connection) {

    }

    public void beforeInfo(Connection connection) {

    }

    public void afterInfo(Connection connection) {

    }
}
