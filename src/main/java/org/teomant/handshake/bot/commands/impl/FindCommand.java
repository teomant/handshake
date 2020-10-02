package org.teomant.handshake.bot.commands.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.teomant.handshake.bot.commands.Command;
import org.teomant.handshake.user.persistance.model.UserNode;
import org.teomant.handshake.user.persistance.repository.UserNodeRepository;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class FindCommand implements Command {

    private final UserNodeRepository userNodeRepository;

    @Override
    public SendMessage processMessage(Message message) {

        StringBuilder builder = new StringBuilder();

        userNodeRepository.findByFullNameContains(StringUtils.substringAfter(message.getText(), " "))
                .forEach(getUserNodeConsumer(builder));

        userNodeRepository.findByUsernameContains(StringUtils.substringAfter(message.getText(), " "))
                .forEach(getUserNodeConsumer(builder));
        SendMessage sendMessage = new SendMessage(message.getChatId(), builder.toString());
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    private Consumer<UserNode> getUserNodeConsumer(StringBuilder builder) {
        return userNode -> {
            builder.append("id: ").append(userNode.getId()).append("\n")
                    .append("username: ").append(userNode.getUsername()).append("\n")
                    .append("fullName: ").append(userNode.getFullName()).append("\n")
                    .append("about: ").append(userNode.getAbout()).append("\n");
            builder.append("relations:\n");
            userNode.getRelations().forEach(relation -> {
                builder.append("type: ").append(relation.getDistance().toString()).append("\n")
                        .append("    id: ").append(relation.getPersonTo().getId()).append("\n")
                        .append("    username: ").append(relation.getPersonTo().getUsername()).append("\n")
                        .append("    fullName: ").append(relation.getPersonTo().getFullName()).append("\n")
                        .append("    about: ").append(relation.getPersonTo().getAbout()).append("\n");
            });
            builder.append("-----\n");
        };
    }
}
