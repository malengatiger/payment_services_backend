package com.nps.api.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nps.api.models.ozow.*;
import com.nps.api.services.HashCheckGenerator;
import com.nps.api.services.NetService;
import com.nps.api.util.Emoji;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(maxAge = 3600)
public class OzowController {
    public static final Logger LOGGER = LoggerFactory.getLogger(OzowController.class.getSimpleName());
    private static final Gson G = new GsonBuilder().setPrettyPrinting().create();

    public OzowController() {
        LOGGER.info(Emoji.DICE.concat(Emoji.DICE.concat("Ozow Payments Controller up and away  ".concat(Emoji.RED_APPLE))));
    }

    @Autowired
    private NetService netService;

    @PostMapping(value = "/ozow_success", produces = MediaType.APPLICATION_JSON_VALUE)
    public String ozowSuccess(OzowPaymentResponse paymentResponse) throws Exception {
        LOGGER.info(Emoji.DICE + " Ozow Payments SUCCESS Callback ...".concat(G.toJson(paymentResponse)));
        return Emoji.FLOWER_RED.concat(Emoji.FLOWER_RED.concat("Ozow Payments Success Callback"));
    }

    @PostMapping(value = "/ozow_cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public String ozowCancel(OzowPaymentResponse paymentResponse) throws Exception {
        LOGGER.info(Emoji.DICE + " Ozow Payments CANCEL Callback ...".concat(G.toJson(paymentResponse)));
        return Emoji.FLOWER_RED.concat(Emoji.FLOWER_RED.concat("Ozow Payments cancel Callback"));
    }

    @PostMapping(value = "/ozow_error", produces = MediaType.APPLICATION_JSON_VALUE)
    public String ozowError(OzowPaymentResponse paymentResponse) throws Exception {
        LOGGER.info(Emoji.DICE + " Ozow Payments ERROR Callback ...".concat(G.toJson(paymentResponse)));
        return Emoji.FLOWER_RED.concat(Emoji.FLOWER_RED.concat("Ozow Payments error Callback"));
    }

    @PostMapping(value = "/ozow_notify", produces = MediaType.APPLICATION_JSON_VALUE)
    public String ozowNotification(OzowPaymentResponse paymentResponse) throws Exception {
        LOGGER.info(Emoji.DICE + " Ozow Payments NOTIFY Callback ...".concat(G.toJson(paymentResponse)));
        return Emoji.FLOWER_RED.concat(Emoji.FLOWER_RED.concat("Ozow Payments notify Callback"));
    }

    @GetMapping(value = "/ozow_token_notify", produces = MediaType.APPLICATION_JSON_VALUE)
    public String ozowTokenNotification(OzowPaymentResponse paymentResponse) throws Exception {
        LOGGER.info(Emoji.DICE + " Ozow Payments TOKEN notify Callback ...".concat(G.toJson(paymentResponse)));
        return Emoji.FLOWER_RED.concat(Emoji.FLOWER_RED.concat("Ozow Payments TOKEN notify Callback"));
    }
    @GetMapping(value = "/ozow_token_delete_notify", produces = MediaType.APPLICATION_JSON_VALUE)
    public String ozowTokenDeleteNotification(OzowPaymentResponse paymentResponse) throws Exception {
        LOGGER.info(Emoji.DICE + " Ozow Payments TOKEN DELETE notify Callback ...".concat(G.toJson(paymentResponse)));
        return Emoji.FLOWER_RED.concat(Emoji.FLOWER_RED.concat("Ozow Payments TOKEN DELETE notify Callback"));
    }

    /*
        🌼 🌼 GetTransactionByReference
        https://api.ozow.com/GetTransactionByReference?siteCode={siteCode}&transactionReference=
        {transactionReference}
     */
    @GetMapping(value = "/getOzowTransactionByReference", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OzowTransaction> getOzowTransactionByReference(@RequestParam String siteCode,
                                                               @RequestParam String transactionReference,
                                                               boolean isTest) throws Exception {
        LOGGER.info(apples.concat("getOzowTransactionByReference request starting ......."));
        //todo - call to Ozow - IS THIS A POST or GET ????
        //todo - Headers must contain ApiKey and Accept values eg. application/json - Response is returned as json


        LOGGER.info(Emoji.FLOWER_RED.concat(Emoji.FLOWER_RED.concat("Ozow Payments getOzowTransactionByReference")));
        return null;
    }
    /*
        🌼 🌼 Extract Transaction Report Using API
        GetTransactionReport
        https://api.ozow.com/GetTransactionReport?siteCode={siteCode}&startDate={startDate}&endDate={endDate}
     */
    @GetMapping(value = "/getOzowTransactionReport", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OzowTransactionModel> getOzowTransactionReport(@RequestParam String siteCode,
                                                               @RequestParam String transactionReference,
                                                               boolean isTest) throws Exception {
        LOGGER.info(apples.concat("getOzowTransactionReport request starting ......."));
        //todo - call to Ozow - THIS IS A GET ????
        //todo - Headers must contain ApiKey and Accept values eg. application/json - Response is returned as json


        LOGGER.info(Emoji.FLOWER_RED.concat(Emoji.FLOWER_RED.concat("Ozow Payments getOzowTransactionReport")));
        return null;
    }
    @Value("${ozowSiteCode}")
    private String siteCode;

    @Value("${countryCode}")
    private String countryCode;

    @Value("${currencyCode}")
    private String currencyCode;

    @Value("${ozowSuccessUrl}")
    private String successUrl;

    @Value("${ozowCancelUrl}")
    private String cancelUrl;

    @Value("${ozowErrorUrl}")
    private String errorUrl;

    @Value("${ozowNotifyUrl}")
    private String notifyUrl;

    @Value("${ozowTokenNotifyUrl}")
    private String tokenNotifyUrl;

    @Value("${ozowTokenDeleteNotifyUrl}")
    private String tokenDeleteNotifyUrl;

    @GetMapping(value = "/getOzowHash", produces = MediaType.APPLICATION_JSON_VALUE)
    public OzowHash getOwzoHash(@RequestParam String string) throws Exception {
        LOGGER.info(Emoji.DICE.concat(Emoji.DICE) + "Get Ozow Payments Hash: ".concat(string));
        String hashed = HashCheckGenerator.generateOzowHash(string);
        LOGGER.info(Emoji.HAND2.concat(Emoji.HAND2.concat("OZOW Hash: ".concat(hashed))));
        return new OzowHash(hashed);
    }
    /**
     * Send Payment Request to ozow
     * @param isTest dev indicator
     * @param registerTokenProfile request user to save token
     * @param transactionReference reference
     * @param token token to ease user bank account transaction
     * @param amount amount
     * @return result string
     * @throws Exception payment request failed
     */
    @PostMapping(value = "/sendPaymentRequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public String sendPaymentRequest(@RequestParam boolean isTest,
                                     @RequestParam boolean registerTokenProfile,
                                     @RequestParam String transactionReference,
                                     @RequestParam String bankReference,
                                     @RequestParam String customer,
                                     @RequestParam String token,
                                     @RequestParam double amount) throws Exception {

        LOGGER.info(apples.concat("... sendPaymentRequest request starting .......: OzowPaymentRequest, transactionReference : "
                .concat(transactionReference).concat(" customer: ".concat(customer).concat(" amount: ".concat("" + amount)))));
        if (customer.isEmpty()) {
            throw new Exception("\uD83D\uDC7F\uD83D\uDC7F Customer is missing. wtf?");
        }
        //merchant data
        OzowPaymentRequest request = new OzowPaymentRequest();
        request.setSiteCode(siteCode);
        request.setCountryCode(countryCode);
        request.setCurrencyCode(currencyCode);
        request.setCustomer(customer);
        //ozow callbacks
        request.setErrorUrl(errorUrl);
        request.setNotifyUrl(notifyUrl);
        request.setCancelUrl(cancelUrl);
        request.setSuccessUrl(successUrl);
        request.setTokenNotificationUrl(tokenNotifyUrl);
        request.setTokenDeletedNotificationUrl(tokenDeleteNotifyUrl);

        if (amount == 0.0) {
            throw new Exception(Emoji.NOT_OK + "Missing Amount");
        }
        request.setAmount(amount);
        request.setBankReference(bankReference);
        request.setTransactionReference(transactionReference);
        request.setRegisterTokenProfile(registerTokenProfile);
        request.setTest(isTest);
        request.setToken(token);

        //🌼 🌼 validation is OK
        LOGGER.info(Emoji.RAIN_DROPS.concat(Emoji.RAIN_DROPS)
                .concat("Request about to be sent: ".concat(G.toJson(request))));

        String result = netService.sendOzowPaymentRequest(request);

        LOGGER.info(Emoji.FLOWER_RED.concat(Emoji.FLOWER_RED
                .concat("sendPaymentRequest returned with some string: " )));
        return result;

    }

    public void printOzowCallbacks() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(Emoji.PEAR.concat(Emoji.PEAR.concat(Emoji.PEAR)))
                .append("List of OZOW CALLBACKS used to interact with EFT services "
                .concat(" ".concat(Emoji.RED_APPLE).concat("\n")));
        sb.append(Emoji.PEAR).append("SUCCESS CALLBACK: ".concat(successUrl).concat(" ".concat(Emoji.RED_APPLE).concat("\n")));
        sb.append(Emoji.PEAR).append("CANCEL CALLBACK: ".concat(cancelUrl).concat(" ".concat(Emoji.RED_APPLE).concat("\n")));
        sb.append(Emoji.PEAR).append("ERROR CALLBACK: ".concat(errorUrl).concat(" ".concat(Emoji.RED_APPLE).concat("\n")));
        sb.append(Emoji.PEAR).append("NOTIFY CALLBACK: ".concat(notifyUrl).concat(" ".concat(Emoji.RED_APPLE).concat("\n")));
        sb.append(Emoji.PEAR).append("TOKEN NOTIFY CALLBACK: ".concat(tokenNotifyUrl).concat(" ".concat(Emoji.RED_APPLE).concat("\n")));
        sb.append(Emoji.PEAR).append("TOKEN DELETE NOTIFY CALLBACK: ".concat(tokenDeleteNotifyUrl).concat(" ".concat(Emoji.RED_APPLE).concat("\n")));
        sb.append(Emoji.PEAR.concat(Emoji.PEAR.concat(Emoji.PEAR)));

        LOGGER.info(sb.toString());
    }
    public static final String apples = Emoji.RED_APPLE.concat(Emoji.RED_APPLE.concat(Emoji.RED_APPLE));
}
