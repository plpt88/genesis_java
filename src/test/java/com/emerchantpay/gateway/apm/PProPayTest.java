package com.emerchantpay.gateway.apm;

import com.emerchantpay.gateway.api.requests.financial.apm.PProRequest;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PProPayTest {

    private List<Map.Entry<String, Object>> elements;
    private HashMap<String, Object> mappedParams;

    private String uniqueId;

    private PProRequest ppro = new PProRequest();

    @Before
    public void createPPro() throws MalformedURLException {
        uniqueId = new StringUtils().generateUID();

        // PPro
        ppro.setTransactionId(uniqueId).setRemoteIp("82.137.112.202").setUsage("TICKETS").setPaymentType("giropay")
                .setCurrency(Currency.EUR.getCurrency()).setAmount(new BigDecimal("2.00"))
                .setCustomerEmail("john@example.com").setCustomerPhone("+55555555")
                .setReturnSuccessUrl(new URL("http://www.example.com/success"))
                .setReturnFailureUrl(new URL("http://www.example.com/failure")).setBIC("GENODETT488")
                .setIBAN("DE07444488880123456789").billingAddress().setAddress1("Berlin").setAddress2("Berlin")
                .setFirstname("Plamen").setLastname("Petrov").setCity("Berlin").setCountry("DE")
                .setZipCode("M4B1B3").setState("BE").done();
    }

    public void setMissingParams() {
        ppro.billingAddress().setCountry(null).done();
    }

    @Test
    public void testPPro() {

        mappedParams = new HashMap<String, Object>();

        elements = ppro.getElements();

        for (int i = 0; i < elements.size() ; i++) {
            mappedParams.put(elements.get(i).getKey(), ppro.getElements().get(i).getValue());
        }

        assertEquals(mappedParams.get("transaction_id"), uniqueId);
        assertEquals(mappedParams.get("remote_ip"), "82.137.112.202");
        assertEquals(mappedParams.get("usage"), "TICKETS");
        assertEquals(mappedParams.get("currency"), Currency.EUR.getCurrency());
        assertEquals(mappedParams.get("amount"), new BigDecimal("200"));
        assertEquals(mappedParams.get("customer_email"), "john@example.com");
        assertEquals(mappedParams.get("customer_phone"), "+55555555");
        assertEquals(mappedParams.get("payment_type"), "giropay");
        assertEquals(mappedParams.get("bic"), "GENODETT488");
        assertEquals(mappedParams.get("iban"), "DE07444488880123456789");
        assertEquals(mappedParams.get("billing_address"), ppro.getBillingAddress());
    }

    @Test
    public void testPProWithMissingParams() {

        setMissingParams();

        mappedParams = new HashMap<String, Object>();
        elements = ppro.getBillingAddress().getElements();

        for (int i = 0; i < elements.size(); i++) {
            mappedParams.put(elements.get(i).getKey(), ppro.getBillingAddress().getElements().get(i).getValue());
        }

        assertNull(mappedParams.get("country"));
    }
}
