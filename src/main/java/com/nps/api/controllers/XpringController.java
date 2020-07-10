package com.nps.api.controllers;


import com.nps.api.util.Emoji;
import com.nps.api.xpring.XRPWalletService;
import io.xpring.xrpl.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(maxAge = 3600)
public class XpringController {
    public static final Logger LOGGER = LoggerFactory.getLogger(XpringController.class.getSimpleName());

    @Autowired
    private XRPWalletService XRPWalletService;

    public XpringController() {
        LOGGER.info(Emoji.DICE.concat(Emoji.DICE.concat("RIPPLE: XpringController ready to go ... \uD83C\uDF4E")));
    }

    @GetMapping(value = "/createWallet", produces = MediaType.APPLICATION_JSON_VALUE)
    public Wallet createWallet(String seed) throws Exception {
        LOGGER.info(Emoji.DICE + "  Network Payment Services creating Wallet from seed ...  \uD83C\uDF4E ");

        Wallet wallet = XRPWalletService.createWallet(seed);
        String msg = Emoji.FLOWER_RED.concat(Emoji.FLOWER_RED.concat(" ... Network Payment Services: \uD83D\uDD35 \uD83D\uDD35 " +
                "Wallet created  ...  \uD83C\uDF4E \uD83C\uDFB2 ".concat(Emoji.YELLOW_BIRD.concat(Emoji.YELLOW_BIRD))));
        LOGGER.info(msg);
        return wallet;
    }

}
