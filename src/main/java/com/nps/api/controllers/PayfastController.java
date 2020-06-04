package com.nps.api.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nps.api.models.ozow.*;
import com.nps.api.models.payfast.PayFastRequest;
import com.nps.api.services.HashCheckGenerator;
import com.nps.api.services.NetService;
import com.nps.api.util.Emoji;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(maxAge = 3600)
public class PayfastController {
    public static final Logger LOGGER = LoggerFactory.getLogger(PayfastController.class.getSimpleName());
    private static final Gson G = new GsonBuilder().setPrettyPrinting().create();

    public PayfastController() {
        LOGGER.info(Emoji.PIG.concat(Emoji.PIG.concat("PayFast Payments Controller up! ".concat(Emoji.RED_APPLE))));
    }

    @Autowired
    private NetService netService;

    @PostMapping(value = "/payfast_return", produces = MediaType.APPLICATION_JSON_VALUE)
    public String payfastSuccess(OzowPaymentResponse paymentResponse) throws Exception {
        LOGGER.info(Emoji.PIG + " PayFast Payments RETURN Callback ...".concat(G.toJson(paymentResponse)));
        return Emoji.FLOWER_RED.concat(Emoji.FLOWER_RED.concat("PayFast Payments Success Callback"));
    }

    @PostMapping(value = "/payfast_cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public String payfastCancel(OzowPaymentResponse paymentResponse) throws Exception {
        LOGGER.info(Emoji.PIG + " PayFastPayments CANCEL Callback ...".concat(G.toJson(paymentResponse)));
        return Emoji.FLOWER_RED.concat(Emoji.FLOWER_RED.concat("PayFast Payments cancel Callback"));
    }

    @PostMapping(value = "/payfast_error", produces = MediaType.APPLICATION_JSON_VALUE)
    public String payfastError(OzowPaymentResponse paymentResponse) throws Exception {
        LOGGER.info(Emoji.PIG + " PayFast Payments ERROR Callback ...".concat(G.toJson(paymentResponse)));
        return Emoji.FLOWER_RED.concat(Emoji.FLOWER_RED.concat("PayFast Payments error Callback"));
    }

    @PostMapping(value = "/payfast_notify", produces = MediaType.APPLICATION_JSON_VALUE)
    public String payfastNotification(OzowPaymentResponse paymentResponse) throws Exception {
        LOGGER.info(Emoji.PIG + " PayFast Payments NOTIFY Callback ...".concat(G.toJson(paymentResponse)));
        return Emoji.FLOWER_RED.concat(Emoji.FLOWER_RED.concat("PayFast Payments notify Callback"));
    }

    @Value("${payfast.merchantId}")
    private String merchantId;

    @Value("${payfast.merchantKey}")
    private String merchantKey;

    @Value("${countryCode}")
    private String countryCode;

    @Value("${currencyCode}")
    private String currencyCode;

    @Value("${payfast.returnUrl}")
    private String returnUrl;

    @Value("${payfast.cancelUrl}")
    private String cancelUrl;

    @Value("${payfast.notifyUrl}")
    private String notifyUrl;

    @Autowired
    private HashCheckGenerator hashCheckGenerator;


    @PostMapping(value = "/getPayfastSignature", produces = MediaType.APPLICATION_JSON_VALUE)
    public OzowHash getPayfastSignatureFromObject(@RequestBody PayFastRequest request) throws Exception {
        assert request != null;
        assert request.getTransactionDetails() != null;
        assert request.getTransactionDetails().getItem_name() != null;
        assert request.getTransactionDetails().getM_payment_id() != null;
        assert request.getTransactionDetails().getAmount() > 0.0;

        request.setMerchant_id(merchantId);
        request.setMerchant_key(merchantKey);
        request.setReturn_url(returnUrl);
        request.setCancel_url(cancelUrl);
        request.setNotify_url(notifyUrl);

        LOGGER.info(Emoji.PIG.concat(Emoji.PIG) + "Get PayFast Payments Signature: ".concat(G.toJson(request)));
        String hashed = hashCheckGenerator.generatePayfastSignature(request);
        LOGGER.info(Emoji.HAND2.concat(Emoji.HAND2.concat("PayFast Signature: ".concat(hashed))));
        return new OzowHash(hashed);
    }

    public void printPayfastCallbacks() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(Emoji.FLOWER_YELLOW.concat(Emoji.FLOWER_YELLOW.concat(Emoji.FLOWER_YELLOW)))
                .append("List of PayFast CALLBACKS used to interact with EFT services "
                .concat(" ".concat(Emoji.RED_APPLE).concat("\n")));
        sb.append(Emoji.FLOWER_YELLOW).append("RETURN CALLBACK: ".concat(returnUrl).concat(" ".concat(Emoji.RED_APPLE).concat("\n")));
        sb.append(Emoji.FLOWER_YELLOW).append("CANCEL CALLBACK: ".concat(cancelUrl).concat(" ".concat(Emoji.RED_APPLE).concat("\n")));
        sb.append(Emoji.FLOWER_YELLOW).append("NOTIFY CALLBACK: ".concat(notifyUrl).concat(" ".concat(Emoji.RED_APPLE).concat("\n")));
        sb.append(Emoji.FLOWER_YELLOW.concat(Emoji.FLOWER_YELLOW.concat(Emoji.FLOWER_YELLOW)));

        LOGGER.info(sb.toString());
    }
    public static final String apples = Emoji.RED_APPLE.concat(Emoji.RED_APPLE.concat(Emoji.RED_APPLE));
}
