package org.teomant.handshake.bot.commands.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.teomant.handshake.bot.commands.BotState;
import org.teomant.handshake.bot.commands.Command;
import org.teomant.handshake.user.persistance.model.UserNode;
import org.teomant.handshake.user.persistance.repository.UserNodeRepository;

import java.util.Map;

@RequiredArgsConstructor
public class AddAboutAndSaveCommand implements Command {

    private final Map<Long, BotState> state;
    private final Map<Long, UserNode> creation;
    private final UserNodeRepository repository;

    @Override
    public SendMessage processMessage(Message message) {
        UserNode userNode = creation.get(message.getChatId());
        userNode.setAbout(message.getText());
        UserNode saved = repository.save(userNode);

        StringBuilder builder = new StringBuilder();
        builder.append("id: ").append(saved.getId()).append("\n")
                .append("username: ").append(saved.getUsername()).append("\n")
                .append("fullName: ").append(saved.getFullName()).append("\n")
                .append("about: ").append(saved.getAbout()).append("\n");
        builder.append("relations:\n");
        saved.getRelations().forEach(relation -> {
            builder.append("type: ").append(relation.getDistance().toString()).append("\n")
                    .append("    id: ").append(relation.getPersonTo().getId()).append("\n")
                    .append("    username: ").append(relation.getPersonTo().getUsername()).append("\n")
                    .append("    fullName: ").append(relation.getPersonTo().getFullName()).append("\n")
                    .append("    about: ").append(relation.getPersonTo().getAbout()).append("\n");
        });
        builder.append("-----\n");

        creation.remove(message.getChatId());
        state.remove(message.getChatId());

        return new SendMessage(message.getChatId(), builder.toString());
    }

}
