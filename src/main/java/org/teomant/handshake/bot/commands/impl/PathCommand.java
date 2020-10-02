package org.teomant.handshake.bot.commands.impl;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.teomant.handshake.bot.commands.Command;
import org.teomant.handshake.user.persistance.model.PathResult;
import org.teomant.handshake.user.persistance.model.UserNode;
import org.teomant.handshake.user.persistance.repository.UserNodeRepository;

@RequiredArgsConstructor
public class PathCommand implements Command {

    private final UserNodeRepository repository;

    @Override
    public SendMessage processMessage(Message message) {
        String[] s = message.getText().split(" ");
        PathResult shortestPath = repository.findShortestPath(s[1], s[2]);
        if (shortestPath.getPath().isEmpty()) {
            return new SendMessage(message.getChatId(), "No path");
        }
        StringBuilder builder = new StringBuilder();
        while (!shortestPath.getPath().isEmpty()) {
            UserNode remove = shortestPath.getPath().remove(0);

            builder.append("id: ").append(remove.getId()).append("\n")
                    .append("username: ").append(remove.getUsername()).append("\n")
                    .append("fullName: ").append(remove.getFullName()).append("\n")
                    .append("about: ").append(remove.getAbout()).append("\n");
            if (!shortestPath.getRelations().isEmpty()) {
                builder.append(shortestPath.getRelations().remove(0).getDistance()).append("\n");
            }
        }
        return new SendMessage(message.getChatId(), builder.toString());
    }
}
