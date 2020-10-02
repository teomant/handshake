package org.teomant.handshake.bot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Command {

    SendMessage processMessage(Message message);
}
