package org.teomant.handshake.user.web.mapper;

import org.teomant.handshake.user.web.model.Relation;
import org.teomant.handshake.user.web.model.User;
import org.teomant.handshake.user.persistance.model.UserNode;

import java.util.stream.Collectors;

public class UserMapper {

    public User mapToModel(UserNode userNode) {
        final User user = new User();
        user.setId(userNode.getId());
        user.setAbout(userNode.getAbout());
        user.setFullName(userNode.getFullName());
        user.setUsername(userNode.getUsername());

        user.setRelations(
                userNode.getRelations().stream()
                        .map(relationNode -> {
                            Relation relation = new Relation();
                            relation.setId(relationNode.getId());
                            relation.setDistance(relationNode.getDistance());
                            relation.setPersonFromId(user.getId());
                            relation.setPersonToId(relationNode.getPersonTo().getId());
                            return relation;
                        }).collect(Collectors.toList())
        );

        return user;
    }
}
