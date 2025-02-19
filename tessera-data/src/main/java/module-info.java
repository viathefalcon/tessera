open module tessera.data {
  requires java.instrument;
  requires java.persistence;
  requires org.bouncycastle.provider;
  requires org.slf4j;
  requires tessera.config;
  requires tessera.enclave.api;
  requires tessera.encryption.api;
  requires tessera.shared;
  requires java.sql;
  requires com.zaxxer.hikari;
  requires java.validation;
  requires tessera.eclipselink.utils;

  //    opens com.quorum.tessera.data to org.eclipse.persistence.core;
  //    opens com.quorum.tessera.data.staging to org.eclipse.persistence.core;

  exports com.quorum.tessera.data;
  exports com.quorum.tessera.data.staging;

  uses com.quorum.tessera.enclave.PayloadDigest;
  uses com.quorum.tessera.data.EncryptedTransactionDAO;
  uses com.quorum.tessera.data.EncryptedRawTransactionDAO;
  uses com.quorum.tessera.data.staging.StagingEntityDAO;
  uses com.quorum.tessera.data.DataSourceFactory;
  uses com.quorum.tessera.data.PrivacyGroupDAO;
  uses com.quorum.tessera.data.EncryptedMessageDAO;

  provides com.quorum.tessera.data.EncryptedTransactionDAO with
      com.quorum.tessera.data.internal.EncryptedTransactionDAOProvider;
  provides com.quorum.tessera.data.EncryptedRawTransactionDAO with
      com.quorum.tessera.data.internal.EncryptedRawTransactionDAOProvider;
  provides com.quorum.tessera.data.staging.StagingEntityDAO with
      com.quorum.tessera.data.staging.internal.StagingEntityDAOProvider;
  provides com.quorum.tessera.data.PrivacyGroupDAO with
      com.quorum.tessera.data.internal.PrivacyGroupDAOProvider;
  provides com.quorum.tessera.data.DataSourceFactory with
      com.quorum.tessera.data.internal.DataSourceFactoryProvider;
  provides com.quorum.tessera.data.EncryptedMessageDAO with
      com.quorum.tessera.data.internal.EncryptedMessageDAOProvider;
}
