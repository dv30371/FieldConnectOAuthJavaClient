package com.deere.democlient.apis;

import com.deere.api.axiom.generated.v3.*;
import com.deere.rest.HttpHeader;
import com.deere.rest.RestRequest;
import com.deere.rest.RestResponse;

public class FieldConnectInfo extends AbstractApiBase {

    public static void main(String[] arg ) throws Exception{

        String userName = "Enter user name";

        final RestRequest restRequest = oauthRequestTo("https://apicert.soa-proxy.deere.com:443/fc/users/"+userName+"/organizations")

                .method("GET")
                .addHeader(new HttpHeader("Accept", "application/vnd.deere.axiom.v3+json"))
                .build();

        final RestResponse orgResponse = restRequest.fetchResponse();

        Organizations organizations = read(orgResponse).as(Organizations.class);

        for (Organization org: organizations.getOrganizations()){
            Link link =  hasLink(org.getLinks(), "managementZones");
             if(link != null){
                 System.out.println("Org Name:   " + org.getName());
                 getManagementZone(link.getUri());
                 break;
             }
        }
    }

    public static Link hasLink(Iterable<Link> linkTypes, final String rel) {
      if(null != linkTypes) {
          for (Link link : linkTypes) {
              if (link.getRel().equalsIgnoreCase(rel)) {
                  return link;
              }
          }
      }
        return null;
    }

    public static void getManagementZone(String link) throws Exception {

        final RestRequest restRequest = oauthRequestTo(link)
                .method("GET")
                .addHeader(new HttpHeader("Accept", "application/vnd.deere.axiom.v3+json"))
                .build();

        final RestResponse managementZoneResponse = restRequest.fetchResponse();

        ManagementZones managementZones = read(managementZoneResponse).as(ManagementZones.class);
        for (ManagementZone managementZone: managementZones.getManagementZones()) {
            Link notesLink = hasLink(managementZone.getLinks(), "latestStatus");
            if (notesLink != null) {
                System.out.println("Management Zone: "+ managementZone.getName());
                getLatestSoilMoisture(notesLink.getUri());
                break;
            }

        }

    }

    public static void getLatestSoilMoisture(String link) throws Exception {

        final RestRequest restRequest = oauthRequestTo(link)
                .method("GET")
                .addHeader(new HttpHeader("Accept", "application/vnd.deere.axiom.v3+json"))
                .build();

        final RestResponse notesResponse = restRequest.fetchResponse();

        SoilMoisture soilMoisture = read(notesResponse).as(SoilMoisture.class);
        System.out.println("SoilMoisture Status:  " + soilMoisture.getStatus());

    }
}
