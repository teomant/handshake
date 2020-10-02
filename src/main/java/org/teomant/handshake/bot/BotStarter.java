package org.teomant.handshake.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.teomant.handshake.bot.commands.HandshakeBotCommandFactory;
import org.teomant.handshake.user.persistance.repository.RelationRelationshipRepository;
import org.teomant.handshake.user.persistance.repository.UserNodeRepository;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class BotStarter {

    private final UserNodeRepository userNodeRepository;
    private final RelationRelationshipRepository relationRelationshipRepository;

    @PostConstruct
    public void init() {

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new HandShakeBot(new HandshakeBotCommandFactory(userNodeRepository, relationRelationshipRepository)));
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

}
