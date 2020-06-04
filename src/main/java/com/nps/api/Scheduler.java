package com.nps.api;


import com.nps.api.util.Emoji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.logging.Logger;

@Component
public class Scheduler {
    public static final Logger LOGGER = Logger.getLogger(Scheduler.class.getSimpleName());
    @Autowired
    private ApplicationContext context;
    @Value("${anchorName}")
    private String anchorName;

    public Scheduler() {
        LOGGER.info(Emoji.YELLOW_BIRD.concat(Emoji.YELLOW_BIRD) +
                "Scheduler constructed. Waiting to be triggered every 30 minutes"
                        .concat(Emoji.YELLOW_BIRD.concat(Emoji.YELLOW_BIRD)));
    }

    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void fixedRateScheduled() throws Exception {
        LOGGER.info(Emoji.PRETZEL.concat(Emoji.PRETZEL) + "Fixed Rate scheduler; " +
                "\uD83C\uDF3C CALCULATE LOAN BALANCES, DANCE ON A PIN or OTHER NECESSARY WORK: "
                + " " + Emoji.RED_APPLE.concat(Emoji.RED_APPLE.concat(Emoji.YELLOW_BIRD)));
        try {
            fileCleanUp();
        } catch (Exception e) {
            LOGGER.info(Emoji.NOT_OK.concat(Emoji.NOT_OK) + "File CleanUp fell down");
            e.printStackTrace();
        }
//        try {
//            FirebaseService firebaseService = context.getBean(FirebaseService.class);
//            Anchor anchor = firebaseService.getAnchor(anchorName);
//            List<Agent> list = firebaseService.getAgents(anchor.getAnchorId());
//            for (Agent agent : list) {
//                LOGGER.info(Emoji.DICE.concat(Emoji.DICE) + "Agent: ".concat(agent.getFullName()).concat(" ")
//                .concat(Emoji.HEART_BLUE));
//            }
//        } catch (Exception e) {
//            LOGGER.info(Emoji.NOT_OK.concat(Emoji.NOT_OK) + "Firebase query fell down");
////            e.printStackTrace();
//        }
    }

    public static final String DIRECTORY = "temp";
    public void fileCleanUp() {
        File directory = new File(DIRECTORY);
        if (directory.exists()) {
            LOGGER.info(Emoji.PEAR.concat(Emoji.PEAR.concat("Directory to be cleaned out: ").concat(directory.getAbsolutePath())));
            if (directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    LOGGER.info(Emoji.RAIN_DROP.concat(Emoji.RAIN_DROP) + "Files to be cleaned out : " + files.length);
                    for (File file : files) {
                        boolean deleted = file.delete();
                        LOGGER.info(Emoji.RAIN_DROP + "File cleaned out; "
                                .concat(" deleted = " + deleted + " : ")
                                .concat(file.getAbsolutePath()));
                    }
                }
            }
        } else {
            LOGGER.info(Emoji.HASH.concat(Emoji.HASH) + "Directory "
                    .concat(DIRECTORY).concat(" does not exist. CleanUp not necessary"));
        }
    }
}
