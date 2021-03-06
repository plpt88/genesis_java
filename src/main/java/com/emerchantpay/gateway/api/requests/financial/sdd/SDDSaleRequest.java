package com.emerchantpay.gateway.api.requests.financial.sdd;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.util.Configuration;
import com.emerchantpay.gateway.util.Currency;
import com.emerchantpay.gateway.util.Http;
import com.emerchantpay.gateway.util.NodeWrapper;

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

public class SDDSaleRequest extends Request {

	protected Configuration configuration;
	private Http http;

	private NodeWrapper response;

	private String transactionType = TransactionTypes.SDD_SALE;
	private String transactionId;
	private String usage;
	private String remoteIP;
	private String customerEmail;
	private String customerPhone;
	private BigDecimal amount;
	private BigDecimal convertedAmount;
	private String currency;
	private String iban;
	private String bic;

	private SDDSaleAddressRequest billingAddress;
	private SDDSaleAddressRequest shippingAddress;

	public SDDSaleRequest() {
		super();
	}

	public SDDSaleRequest(Configuration configuration) {

		super();
		this.configuration = configuration;
	}

	public SDDSaleRequest setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}

	public SDDSaleRequest setUsage(String usage) {
		this.usage = usage;
		return this;
	}

	public SDDSaleRequest setAmount(BigDecimal amount) {

		this.amount = amount;
		return this;
	}

	public SDDSaleRequest setCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	public SDDSaleRequest setRemoteIp(String remoteIP) {
		this.remoteIP = remoteIP;
		return this;
	}

	public SDDSaleRequest setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
		return this;
	}

	public SDDSaleRequest setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
		return this;
	}

	public SDDSaleRequest setIBAN(String iban) {
		this.iban = iban;
		return this;
	}

	public SDDSaleRequest setBIC(String bic) {
		this.bic = bic;
		return this;
	}

	public SDDSaleAddressRequest billingAddress() {
		billingAddress = new SDDSaleAddressRequest(this, "billing_address");
		return billingAddress;
	}

	public SDDSaleAddressRequest shippingAddress() {
		shippingAddress = new SDDSaleAddressRequest(this, "shipping_address");
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

		return new RequestBuilder(root).addElement("transaction_type", transactionType)
				.addElement("transaction_id", transactionId).addElement("usage", usage).addElement("remote_ip", remoteIP)
				.addElement("amount", convertedAmount).addElement("currency", currency)
				.addElement("customer_email", customerEmail).addElement("customer_phone", customerPhone)
				.addElement("iban", iban).addElement("bic", bic)
				.addElement("billing_address", billingAddress).addElement("shipping_address", shippingAddress);
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

	public SDDSaleAddressRequest getBillingAddress() {
		return billingAddress;
	}
}
