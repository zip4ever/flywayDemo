package com.realdolmen.flyway.demo.flyway;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * Created by Kevin.De.Man on 2-4-14.
 */
@Component(value = "dbMigration")
class DbMigrationRunner {

    private static final Logger log = LoggerFactory.getLogger(DbMigrationRunner.class);

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void migrate() {
        try {
            log.info("Starting FlyWay");
            Flyway flyway = new Flyway();
            flyway.setBaselineOnMigrate(true);
            flyway.setDataSource(dataSource);
            flyway.migrate();
            log.info("... done");
        } catch (Exception e) {
            log.error("Error while migrating database", e);
        }
    }
}
