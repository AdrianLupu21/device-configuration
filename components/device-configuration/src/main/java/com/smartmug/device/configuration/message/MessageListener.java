package com.smartmug.device.configuration.message;

import com.smartmug.device.configuration.processor.spi.DeviceConfigurationProcessor;
import com.smartmug.notification.client.spi.NotificationServiceClient;
import com.smartmug.rabbitmq.events.UpdateDiagnosticsTemplateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

@Service
public class MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Autowired
    private DeviceConfigurationProcessor deviceConfigurationProcessor;

    @Autowired
    private NotificationServiceClient notificationServiceClient;

    @RabbitListener(queues = "alert-queue")
    public void handleMessage(final UpdateDiagnosticsTemplateEvent updateDiagnosticsTemplateEvent) {
        logger.info("Received updateDiagnosticTemplate event: {}", updateDiagnosticsTemplateEvent);
        final String templateMessage = deviceConfigurationProcessor
                .getResource(updateDiagnosticsTemplateEvent.getResourcePath());
        try {
            notificationServiceClient.postTemplateUpdateNotification(templateMessage.getBytes(StandardCharsets.UTF_8),
                    updateDiagnosticsTemplateEvent.getDeviceId());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
