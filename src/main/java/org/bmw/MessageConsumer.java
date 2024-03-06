package org.bmw;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class MessageConsumer {

    @Inject
    MessageProducer producer;

    private final Logger logger = Logger.getLogger(MessageConsumer.class);

    @Incoming("messages-in")
    public void receive(String message) {
        logger.infof("Message Received:", message);
        String result[] = message.split("-");
        String val = result[result.length - 2];
        val = val.replaceAll("[^\\d.]", "");
        int number = Integer.parseInt(val);
        if (number % 2 == 0) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
            String formattedDate = sdf.format(date);
            Message messageOut = new Message();
            messageOut.timestamp = formattedDate;
            messageOut.message = message;

            producer.sendMessageToKafka(messageOut);
        } else {
            logger.infof("Message Discarded");
        }

    }
}