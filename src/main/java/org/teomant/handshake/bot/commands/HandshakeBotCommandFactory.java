package org.teomant.handshake.bot.commands;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.teomant.handshake.bot.commands.impl.*;
import org.teomant.handshake.user.persistance.model.UserNode;
import org.teomant.handshake.user.persistance.repository.RelationRelationshipRepository;
import org.teomant.handshake.user.persistance.repository.UserNodeRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class HandshakeBotCommandFactory {

    private final UserNodeRepository userNodeRepository;
    private final RelationRelationshipRepository relationRelationshipRepository;
    private final Map<Long, UserNode> creation = new ConcurrentHashMap<>();
    private final Map<Long, BotState> state = new ConcurrentHashMap<>();

    public Command getCommand(Message message) {
        if (message.getText().startsWith("/cancel")) {
            return new CancelCommand(state, creation);
        }
        if (state.containsKey(message.getChatId())) {
            switch (state.get(message.getChatId())) {
                case CREATION_USERNAME:
                    return new AddUsernameCommand(state, creation);
                case CREATION_FULLNAME:
                    return new AddFullNameCommand(state, creation);
                case CREATION_ABOUT:
                    return new AddAboutAndSaveCommand(state, creation, userNodeRepository);
                default:
                    throw new IllegalArgumentException("wrong command");
            }

        }
        if (message.getText().startsWith("/find ")) {
            return new FindCommand(userNodeRepository);
        }
        if (message.getText().startsWith("/create")) {
            return new StartCreateCommand(state, creation);
        }
        if (message.getText().startsWith("/relation")) {
            return new RelationCreationCommand(relationRelationshipRepository, userNodeRepository);
        }
        if (message.getText().startsWith("/path")) {
            return new PathCommand(userNodeRepository);
        }

        throw new IllegalArgumentException("wrong command");
    }
}
