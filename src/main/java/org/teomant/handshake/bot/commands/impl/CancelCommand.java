package org.teomant.handshake.bot.commands.impl;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.teomant.handshake.bot.commands.BotState;
import org.teomant.handshake.bot.commands.Command;
import org.teomant.handshake.user.persistance.model.UserNode;

import java.util.Map;

@RequiredArgsConstructor
public class CancelCommand implements Command {

    private final Map<Long, BotState> state;
    private final Map<Long, UserNode> creation;

    @Override
    public SendMessage processMessage(Message message) {
        state.remove(message.getChatId());
        creation.remove(message.getChatId());
        return new SendMessage(message.getChatId(), "ok");
    }
}
