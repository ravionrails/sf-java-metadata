package com.sfdemo;

import com.sforce.soap.metadata.*;
import com.sforce.ws.*;

public class App {
    private static MetadataConnection metadataConnection;
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );

        final String USERNAME = "ravionsalesforce@sf.com";
        final String PASSWORD = "ravi@2021gzjTDaJZhmvm3zCT20GS8nAvL";

        try {
            metadataConnection = MetadataLoginUtil.loginToSalesforce( USERNAME, PASSWORD );
            readAddressSettingsForCountriesStatesList();
        } catch (Exception e) {
            System.out.println( e );
        }
    }

    //TODO: create JSON file
    public static void readAddressSettingsForCountriesStatesList() {
        try {
            String[] m = new String[]{ "Address" };

            ReadResult readResult = metadataConnection.readMetadata("AddressSettings", m);

            System.out.println(readResult);
            Metadata[] mdInfo = readResult.getRecords();
            System.out.println("Number of component info returned: " + mdInfo.length);
            for (Metadata md : mdInfo) {
                if (md != null) {

                    System.out.println("Metadata name: " + md.getFullName() );
                    AddressSettings add = (AddressSettings)md;

                    CountriesAndStates c = (CountriesAndStates)add.getCountriesAndStates();

                    for( Country country : c.getCountries() ){
                        System.out.println( country.getLabel() );
                        System.out.print( country.getIntegrationValue() + " - " );
                        System.out.print( country.getIsoCode() + " - " );

                        for( State state : country.getStates() ){
                            System.out.println( state.getLabel() + " - " );
                            System.out.print( state.getIntegrationValue() + " - " );
                            System.out.print( state.getIsoCode() + " - " );
                        }

                    }

                } else {
                    System.out.println("Empty metadata.");
                }
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }
    }

    public static void describeMetadata() {
        try {
          double apiVersion = 55.0;
          // Assuming that the SOAP binding has already been established.
          DescribeMetadataResult res =
              metadataConnection.describeMetadata(apiVersion);
          StringBuffer sb = new StringBuffer();
          if (res != null && res.getMetadataObjects().length > 0) {
            for (DescribeMetadataObject obj : res.getMetadataObjects()) {
              sb.append("***************************************************\n");
              sb.append("XMLName: " + obj.getXmlName() + "\n");
              sb.append("DirName: " + obj.getDirectoryName() + "\n");
              sb.append("Suffix: " + obj.getSuffix() + "\n");
              sb.append("***************************************************\n");
            }
          } else {
            sb.append("Failed to obtain metadata types.");
          }
          System.out.println(sb.toString());
        } catch (ConnectionException ce) {
          ce.printStackTrace();
        }
      }

    public static void readCustomObjectSync() {
        try {
            String[] m = new String[]{ "Address" };
            System.out.println( metadataConnection.getConfig() );
            System.out.println( metadataConnection.getSessionHeader() );
            // ReadResult readResult = metadataConnection.readMetadata("AddressSettings", m);
            ReadResult readResult = metadataConnection.readMetadata("CustomObject", new String[]{ "Account" } );

            System.out.println(readResult);
            Metadata[] mdInfo = readResult.getRecords();
            System.out.println("Number of component info returned: " + mdInfo.length);
            for (Metadata md : mdInfo) {
                if (md != null) {
                    CustomObject obj = (CustomObject) md;
                    System.out.println("Custom object full name: "
                            + obj.getFullName());
                    System.out.println("Label: " + obj.getLabel());
                    System.out.println("Number of custom fields: "
                            + obj.getFields().length);
                    System.out.println("Sharing model: "
                            + obj.getSharingModel());
                } else {
                    System.out.println("Empty metadata.");
                }
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }
    }
}
