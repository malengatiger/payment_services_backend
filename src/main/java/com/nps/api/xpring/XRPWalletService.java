package com.nps.api.xpring;

import io.xpring.common.XrplNetwork;
import io.xpring.ilp.IlpClient;
import io.xpring.ilp.IlpException;
import io.xpring.ilp.model.AccountBalance;
import io.xpring.payid.PayIdException;
import io.xpring.payid.XrpPayIdClient;
import io.xpring.payid.generated.model.Address;
import io.xpring.xrpl.Wallet;
import io.xpring.xrpl.XrpClient;
import io.xpring.xrpl.XrpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
public class XRPWalletService {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            XRPWalletService.class.getSimpleName());
    //# Testnet
    private static final String TEST_URL = "test.xrp.xpring.io:50051";
    //  # Mainnet
    private static final String MAIN_URL = "main.xrp.xpring.io:50051";
    private static final String SEED = "ssms4QXcpuBhFkGxU8w4czQCiKFNN";
    private static final String ACCESS_TOKEN = "YjlkY2MzZjEtOGY0Yy00YjU1LTgxZjctNjFjZDdkNjU4YmJh";
    //prod.grpcng.wallet.xpring.io:443
    //https://xpring.io/portal/ilp/hermes/

    public XRPWalletService() {
        LOGGER.info("\uD83D\uDC99 \uD83D\uDC99 XRP Wallet Service constructed and waiting for shit! \uD83E\uDD68");
    }

    public Wallet createWallet(String seed) throws XrpException, PayIdException, IlpException {
        LOGGER.info("\uD83C\uDFB2 \uD83C\uDFB2 Creating Wallet using WalletService:  \uD83E\uDD68 \uD83E\uDD68 \uD83E\uDD68 ...");

        Wallet seedWallet = new Wallet(seed);
        LOGGER.info("\n\uD83C\uDF4E Wallet Address: ".concat(seedWallet.getAddress()
                .concat("\n\uD83C\uDF4E Wallet Public Key: ").concat(seedWallet.getPublicKey()
                        .concat("\n\uD83C\uDF4E Wallet Private Key: ").concat(seedWallet.getPrivateKey()))));

        XrpClient xrpClient = new XrpClient(TEST_URL, XrplNetwork.TEST);
        LOGGER.info("\uD83C\uDF4E  \uD83C\uDF4E XRP Client, getNetworkName: \uD83D\uDC99 \uD83D\uDC99 "
                .concat(xrpClient.getNetwork().getNetworkName()));
        BigInteger balance = xrpClient.getBalance(seedWallet.getAddress());
        LOGGER.info("\uD83C\uDF4E  \uD83C\uDF4E XRP Client: Wallet Balance: \uD83E\uDD66 \uD83E\uDD66 "
                .concat((balance.divide(new BigInteger(String.valueOf(1000000)))).toString())
                .concat(" XRP \uD83E\uDD66 \uD83E\uDD66"));

        XrplNetwork xrpNetwork = XrplNetwork.TEST;
        String payId = "malengatiger$xpring.money";
        XrpPayIdClient xrpPayIdClient = new XrpPayIdClient(xrpNetwork);
        List<Address> address = xrpPayIdClient.allAddressesForPayId(payId);
        LOGGER.info("\uD83E\uDD66 \uD83E\uDD66 \uD83E\uDD66 crypto all addresses: ".concat("" + address.size()));
        LOGGER.info("\uD83E\uDD66 \uD83E\uDD66 \uD83E\uDD66 xrp network: "
                .concat(xrpPayIdClient.getXrplNetwork().getNetworkName()));
        if (address.isEmpty()) {
            LOGGER.info("\uD83D\uDC80 \uD83D\uDC80 \uD83D\uDC80 ...... No addresses found here!");
        } else {
            for (Address address1 : address) {
                LOGGER.info(" \uD83C\uDFB2 \uD83C\uDFB2 Address Payment Network:  \uD83C\uDFB2 :"
                        .concat(address1.getPaymentNetwork()));
            }
        }

        String grpcUrl = "prod.grpcng.wallet.xpring.io"; // Testnet ILP Wallet URL
        IlpClient ilpClient = new IlpClient(grpcUrl);

        AccountBalance accountBalance = ilpClient.getBalance("malengatiger", ACCESS_TOKEN); // Just a demo user on Testnet
        LOGGER.info("\n\uD83C\uDF51 \uD83C\uDF51 \uD83C\uDF51 Net balance was " + accountBalance.netBalance()
                + " \uD83E\uDD68 with asset scale "
                + accountBalance.assetScale() + " \uD83D\uDC99 calculated: \uD83D\uDC99 " + accountBalance.netBalance().divide(new BigInteger("1000000000")));
        LOGGER.info("\uD83C\uDF4E \uD83C\uDF4E \uD83C\uDF4E ............. Testing wallet and ILP completed " +
                "\uD83C\uDF4E \uD83C\uDF4E \uD83C\uDF4E \uD83C\uDF4E on: ".concat(new Date().toString().concat("\n\n")));

        return seedWallet;
    }

    public static void main(String[] args) throws Exception {
        XRPWalletService service = new XRPWalletService();
        service.createWallet(SEED);
    }
}
