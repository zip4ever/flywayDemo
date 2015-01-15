package com.realdolmen.flyway.demo.util;

import com.realdolmen.flyway.demo.model.LegalHoliday;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Kevin De Man on 26/06/2014.
 */
public class FetchLegalHolidaysViaWebserviceUtil {

    private static int year;
    private static List<LegalHoliday> legalHolidayList;

    private final static String WSDL_URL = "http://kayaposoft.com/enrico/ws/v1.0/index.php";
    private final static String SERVER_URI = "http://www.kayaposoft.com/enrico/ws/v1.0/";

    public FetchLegalHolidaysViaWebserviceUtil() {

    }

    public FetchLegalHolidaysViaWebserviceUtil(int year) {
        FetchLegalHolidaysViaWebserviceUtil.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        FetchLegalHolidaysViaWebserviceUtil.year = year;
    }

    public List<LegalHoliday> getLegalHolidayList() {
        return legalHolidayList;
    }

    public void setLegalHolidayList(List<LegalHoliday> legalHolidayList) {
        FetchLegalHolidaysViaWebserviceUtil.legalHolidayList = legalHolidayList;
    }

    public List<LegalHoliday> fetchVacationsForYear() {
        year = (year == 0) ? DateCalculcationsUtil.getCurrentYear() : year;

        legalHolidayList = new ArrayList<LegalHoliday>();
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), WSDL_URL );
            processSoapResult(soapResponse);
            soapConnection.close();
        } catch (Exception e ) {
            System.out.println();
            e.printStackTrace();
        }

        return legalHolidayList;
    }

    private static SOAPMessage createSOAPRequest() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("v1", SERVER_URI);

        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElement = soapBody.addChildElement("getPublicHolidaysForYear", "v1");
        SOAPElement yearElement = soapBodyElement.addChildElement("year");
        yearElement.addTextNode( "" + year);
        SOAPElement countryElement = soapBodyElement.addChildElement("country");
        countryElement.addTextNode("bel");

        MimeHeaders mimeHeader = soapMessage.getMimeHeaders();
        mimeHeader.addHeader("SOAPAction", SERVER_URI + "getPublicHolidaysForYear");
        soapMessage.saveChanges();

        return soapMessage;
    }

    private static void printSOAPResponse(SOAPMessage soapResponse) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        System.out.print("\nResponse SOAP Message = ");
        StreamResult result = new StreamResult(System.out);
        transformer.transform(sourceContent, result);
    }

    private static void processSoapResult(SOAPMessage soapResponse) throws SOAPException {
        NodeList nodeList = soapResponse.getSOAPBody().getChildNodes().item(0).getChildNodes().item(0).getChildNodes();
        org.w3c.dom.Node current = null;
        int count = nodeList.getLength();
        for (int i = 0; i < count; i++) {
            current = nodeList.item(i);
            if (current.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) current;
                String tagName = element.getTagName();
                if (element.getTagName().equalsIgnoreCase("publicHolidays")) {
                    LegalHoliday legalHoliday = new LegalHoliday();
                    String day = element.getElementsByTagName("day").item(0).getTextContent();
                    String month = element.getElementsByTagName("month").item(0).getTextContent();
                    String[] descriptions = element.getElementsByTagName("localName").item(0).getTextContent().split("/");
                    legalHoliday.setDescriptionNl(descriptions[0].trim());
                    legalHoliday.setDescriptionFr(descriptions[1].trim());
                    legalHoliday.setDescriptionDe(descriptions[2].trim());
                    legalHoliday.setDescriptionEn(element.getElementsByTagName("englishName").item(0).getTextContent());
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, Integer.parseInt(month) - 1, Integer.parseInt(day));
                    legalHoliday.setDate(calendar.getTime());
                    legalHolidayList.add(legalHoliday);
                }
            }
        }
    }

}
