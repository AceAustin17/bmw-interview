package org.bmw;

import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class MessageProducer {

    @Inject
    @Channel("messages-out")
    Emitter<Record<String, String>> emitter;

    public void sendMessageToKafka(Message message) {
        emitter.send(Record.of(message.timestamp, message.message));
    }
}