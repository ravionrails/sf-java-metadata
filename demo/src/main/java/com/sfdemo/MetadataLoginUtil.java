package com.sfdemo;

// import com.sforce.soap.enterprise.EnterpriseConnection;
// import com.sforce.soap.enterprise.LoginResult;
import com.sforce.soap.partner.*;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;


/**
 * Login utility.
 */
public class MetadataLoginUtil {

    private final static String URL = "https://login.salesforce.com/services/Soap/u/54.0";

    public static MetadataConnection loginToSalesforce( String username, String password ) throws ConnectionException{

        ConnectorConfig partnerConfig = new ConnectorConfig();
        partnerConfig.setAuthEndpoint( URL );
        partnerConfig.setServiceEndpoint( URL );
        partnerConfig.setUsername( username );
        partnerConfig.setPassword( password );
        PartnerConnection partnerConnection = Connector.newConnection(partnerConfig);
        LoginResult loginResult = partnerConnection.login(  username ,  password  );
        return createMetadataConnection( loginResult );
    }

    private static MetadataConnection createMetadataConnection(
            final LoginResult loginResult) throws ConnectionException{
        ConnectorConfig metadataConfig = new ConnectorConfig();
        metadataConfig.setServiceEndpoint( loginResult.getMetadataServerUrl() );
        metadataConfig.setSessionId( loginResult.getSessionId() );
        MetadataConnection metadataConnection = new MetadataConnection( metadataConfig );
        return metadataConnection;
    }
}