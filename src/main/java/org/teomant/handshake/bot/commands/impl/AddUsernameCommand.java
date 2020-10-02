package org.teomant.handshake.bot.commands.impl;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.teomant.handshake.bot.commands.BotState;
import org.teomant.handshake.bot.commands.Command;
import org.teomant.handshake.user.persistance.model.UserNode;

import java.util.Map;

@RequiredArgsConstructor
public class AddUsernameCommand implements Command {

    private final Map<Long, BotState> state;
    private final Map<Long, UserNode> creation;

    @Override
    public SendMessage processMessage(Message message) {
        state.replace(message.getChatId(), BotState.CREATION_FULLNAME);
        creation.get(message.getChatId()).setUsername(message.getText());
        return new SendMessage(message.getChatId(), "Username added. Send Fullname");
    }

}
