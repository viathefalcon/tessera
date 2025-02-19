package com.quorum.tessera.data.internal;

import com.quorum.tessera.config.Config;
import com.quorum.tessera.config.ConfigFactory;
import com.quorum.tessera.data.DataSourceFactory;
import com.quorum.tessera.data.EncryptedMessageDAO;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptedMessageDAOProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(EncryptedMessageDAOProvider.class);

  public static EncryptedMessageDAO provider() {

    Config config = ConfigFactory.create().getConfig();

    final DataSource dataSource = DataSourceFactory.create().create(config.getJdbcConfig());

    Map properties = new HashMap();

    properties.put("javax.persistence.nonJtaDataSource", dataSource);

    properties.put(
        "eclipselink.logging.logger", "org.eclipse.persistence.logging.slf4j.SLF4JLogger");
    properties.put("eclipselink.logging.level", "FINE");
    properties.put("eclipselink.logging.parameters", "true");
    properties.put("eclipselink.logging.level.sql", "FINE");

    properties.put(
        "javax.persistence.schema-generation.database.action",
        config.getJdbcConfig().isAutoCreateTables() ? "create" : "none");

    LOGGER.debug("Creating EntityManagerFactory from {}", properties);
    final EntityManagerFactory entityManagerFactory =
        Persistence.createEntityManagerFactory("tessera", properties);
    LOGGER.debug("Created EntityManagerFactory from {}", properties);

    return new EncryptedMessageDAOImpl(entityManagerFactory);
  }
}
