package com.vmoscalciuc;

import com.vmoscalciuc.event.*;
import lombok.extern.slf4j.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.kafka.annotation.*;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class);
    }

    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent){
        //send out an email notification
        log.info("Received Notification for Order - {}", orderPlacedEvent.getOrderNumber());
    }
}
