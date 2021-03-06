package com.emerchantpay.gateway.api.requests.financial.oBeP;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.requests.financial.apm.TrustlySaleAddressRequest;
import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.Http;
import com.emerchantpay.gateway.util.NodeWrapper;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Map;

/*
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @license http://opensource.org/licenses/MIT The MIT License
 */

public class IDebitPayInRequest extends Request {

    protected Configuration configuration;
    private Http http;

    private NodeWrapper response;

    private String transactionType = TransactionTypes.IDEBIT_PAYIN;
    private String transactionId;
    private String usage;
    private String remoteIP;
    private String customerAccountId;
    private String customerEmail;
    private String customerPhone;
    private URL returnUrl;
    private URL notificationUrl;
    private BigDecimal amount;
    private BigDecimal convertedAmount;
    private String currency;

    private IDebitAddressRequest billingAddress;
    private IDebitAddressRequest shippingAddress;

    public IDebitPayInRequest() {
        super();
    }

    public IDebitPayInRequest(Configuration configuration) {

        super();
        this.configuration = configuration;
    }

    public IDebitPayInRequest setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public IDebitPayInRequest setCustomerAccountId(String customerAccountId) {
        this.customerAccountId = customerAccountId;
        return this;
    }

    public IDebitPayInRequest setReturnUrl(URL returnUrl) {
        this.returnUrl = returnUrl;
        return this;
    }

    public IDebitPayInRequest setNotificationUrl(URL notificationUrl) {
        this.notificationUrl = notificationUrl;
        return this;
    }

    public IDebitPayInRequest setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public IDebitPayInRequest setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public IDebitPayInRequest setUsage(String usage) {
        this.usage = usage;
        return this;
    }

    public IDebitPayInRequest setRemoteIp(String remoteIp) {
        this.remoteIP = remoteIp;
        return this;
    }

    public IDebitPayInRequest setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public IDebitPayInRequest setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
        return this;
    }

    public IDebitAddressRequest billingAddress() {
        billingAddress = new IDebitAddressRequest(this, "billing_address");
        return billingAddress;
    }

    public IDebitAddressRequest shippingAddress() {
        shippingAddress = new IDebitAddressRequest(this, "shipping_address");
        return shippingAddress;
    }

    @Override
    public String toXML() {
        return buildRequest("payment_transaction").toXML();
    }

    @Override
    public String toQueryString(String root) {
        return buildRequest(root).toQueryString();
    }

    protected RequestBuilder buildRequest(String root) {

        if (amount != null && currency != null) {

            Currency curr = new Currency();

            curr.setAmountToExponent(amount, currency);
            convertedAmount = curr.getAmount();
        }

        return new RequestBuilder(root).addElement("transaction_id", transactionId).addElement("transaction_type", transactionType)
                .addElement("usage", usage).addElement("return_url", returnUrl).addElement("notification_url", notificationUrl)
                .addElement("remote_ip", remoteIP).addElement("customer_account_id", customerAccountId)
                .addElement("customer_email", customerEmail).addElement("customer_phone", customerPhone)
                .addElement("amount", convertedAmount).addElement("currency", currency).addElement("billing_address", billingAddress)
                .addElement("shippingAddress", shippingAddress);
    }

    public Request execute(Configuration configuration) {

        configuration.setAction("process");
        http = new Http(configuration);
        response = http.post(configuration.getBaseUrl(), this);

        return this;
    }

    public NodeWrapper getResponse() {
        return response;
    }

    public List<Map.Entry<String, Object>> getElements() {
        return buildRequest("payment_transaction").getElements();
    }

    public IDebitAddressRequest getBillingAddress() {
        return billingAddress;
    }
}
