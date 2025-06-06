package br.com.fiap.chents_consumer.service;

import br.com.fiap.chents_consumer.dto.AlertMessage;
import br.com.fiap.chents_consumer.repository.AlertMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AlertConsumerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlertConsumerService.class);

    private final TelegramBotService botService;
    private final OllamaChatService chatService;
    private final AlertMessageRepository alertMessageRepository;

    public AlertConsumerService(TelegramBotService botService, OllamaChatService chatService, AlertMessageRepository alertMessageRepository) {
        this.botService = botService;
        this.chatService = chatService;
        this.alertMessageRepository = alertMessageRepository;
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consumeAlertMessage(AlertMessage alertMessage) {
        LOGGER.info("Received alert message: {}", alertMessage);

        alertMessageRepository.save(alertMessage);
        LOGGER.info("message saved to mongo database: {}", alertMessage);

        String aiMessage = chatService.generateAIMessage(alertMessage);
        botService.sendMessageToTelegram(aiMessage);
    }
}
