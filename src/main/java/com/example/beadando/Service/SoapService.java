package com.example.beadando.Service;

import com.example.beadando.model.ExchangeRatesResponse;
import com.example.beadando.model.SoapRequestObj;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import soapclient.MNBArfolyamServiceSoap;
import soapclient.MNBArfolyamServiceSoapGetCurrenciesStringFaultFaultMessage;
import soapclient.MNBArfolyamServiceSoapGetExchangeRatesStringFaultFaultMessage;
import soapclient.MNBArfolyamServiceSoapImpl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SoapService {

    // Lekérdezzük a pénznemeket és Jsoup-pal feldolgozzuk az XML-t
    public List<String> getCurrencies() throws MNBArfolyamServiceSoapGetCurrenciesStringFaultFaultMessage {
        MNBArfolyamServiceSoapImpl impl = new MNBArfolyamServiceSoapImpl();
        MNBArfolyamServiceSoap service = impl.getCustomBindingMNBArfolyamServiceSoap();

        String xml = service.getCurrencies();
        Document document = Jsoup.parse(xml, "", Parser.xmlParser());
        Elements elements = document.select("Curr");

        return elements.eachText();
    }

    public ExchangeRatesResponse getExchangeRates(SoapRequestObj requestObj) throws MNBArfolyamServiceSoapGetExchangeRatesStringFaultFaultMessage {
        MNBArfolyamServiceSoapImpl impl = new MNBArfolyamServiceSoapImpl();
        MNBArfolyamServiceSoap service = impl.getCustomBindingMNBArfolyamServiceSoap();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String currency = requestObj.getCurrency();
        String startDate = formatter.format(requestObj.getStartDate());
        String endDate = formatter.format(requestObj.getEndDate());

        String xml = service.getExchangeRates(startDate, endDate, currency);
        Map<String, String> exchangeRates = new LinkedHashMap<>();

        Document document = Jsoup.parse(xml, "", Parser.xmlParser());
        Elements days = document.select("Day");

        for (Element day : days) {
            String date = day.attr("date");
            Element rateElement = day.selectFirst("Rate");

            if(rateElement != null){
                String rate = day.selectFirst("Rate").text().trim();
                rate = rate.replace(",", ".");
                exchangeRates.put(date, rate);
            }
        }

        return new ExchangeRatesResponse(xml, exchangeRates);
    }
}
