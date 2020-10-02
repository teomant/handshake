package org.teomant.handshake.bot.commands.impl;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.teomant.handshake.bot.commands.Command;
import org.teomant.handshake.user.persistance.model.RelationRelationship;
import org.teomant.handshake.user.persistance.model.UserNode;
import org.teomant.handshake.user.persistance.repository.RelationRelationshipRepository;
import org.teomant.handshake.user.persistance.repository.UserNodeRepository;
import org.teomant.handshake.user.web.model.Distance;

@RequiredArgsConstructor
public class RelationCreationCommand implements Command {
    private final RelationRelationshipRepository relationRelationshipRepository;
    private final UserNodeRepository userNodeRepository;

    @Override
    public SendMessage processMessage(Message message) {

        String[] s = message.getText().split(" ");
        RelationRelationship relationship = new RelationRelationship();
        relationship.setPersonFrom(userNodeRepository.findById(Long.valueOf(s[1])).orElseThrow(() -> new IllegalArgumentException("no user")));
        relationship.setDistance(Distance.valueOf(s[2]));
        relationship.setPersonTo(userNodeRepository.findById(Long.valueOf(s[3])).orElseThrow(() -> new IllegalArgumentException("no user")));

        relationRelationshipRepository.save(relationship);

        return new SendMessage(message.getChatId(), "created");
    }
}
