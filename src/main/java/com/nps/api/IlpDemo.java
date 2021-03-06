package com.nps.api;

import com.google.common.primitives.UnsignedLong;
import io.xpring.ilp.IlpClient;
import io.xpring.ilp.IlpException;
import io.xpring.ilp.model.AccountBalance;
import io.xpring.ilp.model.PaymentRequest;
import io.xpring.ilp.model.PaymentResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IlpDemo {

    public static final Logger LOGGER = LoggerFactory.getLogger(IlpDemo.class.getSimpleName());
    public static void main(String[] args) throws IlpException {
        String grpcUrl = "stg.grpcng.wallet.xpring.io";
        String demoUserId = "sdk_account1";
        String demoUserAuthToken = "password";

       LOGGER.info("\n\uD83C\uDFC0 \uD83C\uDFC0 Using Hermes node located at: " + grpcUrl + "\n");
        IlpClient ilpClient = new IlpClient(grpcUrl);

       LOGGER.info("\uD83E\uDD4F \uD83E\uDD4F \uD83E\uDD4F \uD83E\uDD4F Retrieving balance for " + demoUserId + "...");
        AccountBalance balance = ilpClient.getBalance(demoUserId, demoUserAuthToken);
       LOGGER.info("\uD83E\uDD4F \uD83E\uDD4F Net balance was " + balance.netBalance() + " with asset scale " + balance.assetScale());

        String receiverPaymentPointer = "$xpring.money/demo_receiver";
        UnsignedLong amountToSend = UnsignedLong.valueOf(130);
       LOGGER.info("\uD83D\uDD06 \uD83D\uDD06 \uD83D\uDD06 \uD83D\uDD06 \uD83D\uDD06 ... Sending payment ...");
       LOGGER.info("\uD83D\uDC9C \uD83D\uDC9C - From: " + demoUserId + "- To: " + receiverPaymentPointer);
       LOGGER.info("\uD83D\uDC9C \uD83D\uDC9C - Amount: " + amountToSend + " drops");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .amount(amountToSend)
                .destinationPaymentPointer(receiverPaymentPointer)
                .senderAccountId(demoUserId)
                .build();
        PaymentResult payment = ilpClient.sendPayment(paymentRequest, demoUserAuthToken);

       LOGGER.info("\uD83C\uDF3A \uD83C\uDF3A \uD83C\uDF3A \uD83C\uDF3A Payment sent! ....");
       LOGGER.info("\uD83E\uDDA0 \uD83E\uDDA0 Amount sent: " + payment.amountSent());
       LOGGER.info("\uD83E\uDDA0 \uD83E\uDDA0 Amount delivered: " + payment.amountDelivered());
       LOGGER.info("\uD83E\uDDA0 \uD83E\uDDA0 Payment was " + (payment.successfulPayment() ? "successful!" : "unsuccessful!"));

        AccountBalance balanceAfterPayment = ilpClient.getBalance(demoUserId, demoUserAuthToken);
       LOGGER.info("\uD83E\uDDE9 \uD83E\uDDE9 \uD83E\uDDE9 Net balance after sending payment was \uD83D\uDC9C " + balanceAfterPayment.netBalance());
    }
}

