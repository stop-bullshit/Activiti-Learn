package cn.masterlover.activiti6.conf;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.activiti.engine.ActivitiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan
public class LiquiBaseConfig {

    protected static final String LIQUIBASE_CHANGELOG_PREFIX = "ACT_DE_";

    @Autowired
    DataSource dataSource;

    @Bean(name = "liquibase")
    public Liquibase liquibase() {
//        log.info("Configuring Liquibase");
        try {
            DatabaseConnection connection = new JdbcConnection(dataSource.getConnection());
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
            database.setDatabaseChangeLogTableName(LIQUIBASE_CHANGELOG_PREFIX + database.getDatabaseChangeLogTableName());
            database.setDatabaseChangeLogLockTableName(LIQUIBASE_CHANGELOG_PREFIX + database.getDatabaseChangeLogLockTableName());

            Liquibase liquibase = new Liquibase("META-INF/liquibase/activiti-app-db-changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update("activiti");
            return liquibase;

        } catch (Exception e) {
            throw new ActivitiException("Error creating liquibase database");
        }
    }
}
