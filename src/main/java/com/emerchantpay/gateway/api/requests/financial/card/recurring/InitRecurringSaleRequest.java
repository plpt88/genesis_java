package com.emerchantpay.gateway.api.requests.financial.card.recurring;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.TransactionTypes;
import com.emerchantpay.gateway.api.requests.RiskParamsAttributes;
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

public class InitRecurringSaleRequest extends Request implements RiskParamsAttributes {

	protected Configuration configuration;
	private Http http;

	private NodeWrapper response;

	private String transactionType = TransactionTypes.INIT_RECURRING_SALE;
	private String transactionId;
	private String usage;
	private String remoteIP;
	private Boolean moto;
	private BigDecimal amount;
	private BigDecimal convertedAmount;
	private String currency;
	private String cardholder;
	private String cardnumber;
	private String expirationMonth;
	private String expirationYear;
	private String cvv;
	private String customerEmail;
	private String customerPhone;
	private String birthDate;

	private InitRecurringSaleAddressRequest billingAddress;
	private InitRecurringSaleAddressRequest shippingAddress;
	private InitRecurringSaleDynamicDescriptorParamsRequest dynamicDescriptorParams;

	public InitRecurringSaleRequest() {
		super();
	}

	public InitRecurringSaleRequest(Configuration configuration) {

		super();
		this.configuration = configuration;
	}

	public InitRecurringSaleRequest setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}

	public InitRecurringSaleRequest setUsage(String usage) {
		this.usage = usage;
		return this;
	}

	public InitRecurringSaleRequest setAmount(BigDecimal amount) {
		this.amount = amount;
		return this;
	}

	public InitRecurringSaleRequest setCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	public InitRecurringSaleRequest setRemoteIp(String remoteIP) {
		this.remoteIP = remoteIP;
		return this;
	}

	public InitRecurringSaleRequest setMoto(Boolean moto) {
		this.moto = moto;
		return this;
	}

	public InitRecurringSaleRequest setCardNumber(String cardnumber) {
		this.cardnumber = cardnumber;
		return this;
	}

	public InitRecurringSaleRequest setCardHolder(String cardholder) {
		this.cardholder = cardholder;
		return this;
	}

	public InitRecurringSaleRequest setCvv(String cvv) {
		this.cvv = cvv;
		return this;
	}

	public InitRecurringSaleRequest setExpirationMonth(String expirationMonth) {
		this.expirationMonth = expirationMonth;
		return this;
	}

	public InitRecurringSaleRequest setExpirationYear(String expirationYear) {
		this.expirationYear = expirationYear;
		return this;
	}

	public InitRecurringSaleRequest setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
		return this;
	}

	public InitRecurringSaleRequest setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
		return this;
	}

	public InitRecurringSaleRequest setBirthDate(String birthDate) {
		this.birthDate = birthDate;
		return this;
	}

	public InitRecurringSaleAddressRequest billingAddress() {
		billingAddress = new InitRecurringSaleAddressRequest(this, "billing_address");
		return billingAddress;
	}

	public InitRecurringSaleAddressRequest shippingAddress() {
		shippingAddress = new InitRecurringSaleAddressRequest(this, "shipping_address");
		return shippingAddress;
	}

	public InitRecurringSaleDynamicDescriptorParamsRequest dynimicDescriptionParams() {
		dynamicDescriptorParams = new InitRecurringSaleDynamicDescriptorParamsRequest(this);
		return dynamicDescriptorParams;
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
				.addElement("transaction_id", transactionId).addElement("usage", usage)
				.addElement("remote_ip", remoteIP).addElement("moto", moto).addElement("amount", convertedAmount)
				.addElement("currency", currency).addElement("card_holder", cardholder)
				.addElement("card_number", cardnumber).addElement("expiration_month", expirationMonth)
				.addElement("expiration_year", expirationYear).addElement("cvv", cvv)
				.addElement("customer_email", customerEmail).addElement("customer_phone", customerPhone)
				.addElement("birth_date", birthDate).addElement("billing_address", billingAddress)
				.addElement("shippingAddress", shippingAddress)
				.addElement("dynamic_descriptor_params", dynamicDescriptorParams)
				.addElement("risk_params", buildRiskParams("risk_params").toXML());

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

	public InitRecurringSaleAddressRequest getBillingAddress() {
		return billingAddress;
	}
}
