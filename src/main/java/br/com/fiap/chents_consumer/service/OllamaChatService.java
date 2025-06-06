package br.com.fiap.chents_consumer.service;

import br.com.fiap.chents_consumer.dto.AlertMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Service
public class OllamaChatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OllamaChatService.class);

    private final OllamaChatModel chatClient;


    public OllamaChatService(OllamaChatModel chatClient) {
        this.chatClient = chatClient;
    }

    public String generateAIMessage(AlertMessage alertMessage) {
        try{
            String prompt = String.format(
                    "Gere uma mensagem personalizada e empática para um cidadão de nove %s informando que um alerta de enchente foi criado em %s" +
                            "próximo à sua localização(coordenadas %s, %s, na cidade %s), com a mensagem %s. Seja cordial e direto, adequada para um alerto no Telegram.",
                    alertMessage.getUserName(),
                    alertMessage.getMessageCreation().toString(),
                    alertMessage.getLatitude(),
                    alertMessage.getLongitude(),
                    alertMessage.getCity(),
                    alertMessage.getMessage()
            );
            String personalizedMessage = chatClient.call(prompt);
            LOGGER.info("Generated personalized message with AI for alert: {}", alertMessage.getId());
            return personalizedMessage;
        }
        catch(Exception e){
            LOGGER.error("Failed to generate personalized message: {}", e.getMessage());
            return "Failed to generate personalized message";
        }
    }
}
