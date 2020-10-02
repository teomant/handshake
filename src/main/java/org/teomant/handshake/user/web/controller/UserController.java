package org.teomant.handshake.user.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.teomant.handshake.user.web.mapper.UserMapper;
import org.teomant.handshake.user.persistance.model.PathResult;
import org.teomant.handshake.user.persistance.model.RelationRelationship;
import org.teomant.handshake.user.persistance.model.UserNode;
import org.teomant.handshake.user.persistance.repository.RelationRelationshipRepository;
import org.teomant.handshake.user.persistance.repository.UserNodeRepository;
import org.teomant.handshake.user.web.model.Distance;
import org.teomant.handshake.user.web.model.Relation;
import org.teomant.handshake.user.web.model.User;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserNodeRepository userNodeRepository;
    private final RelationRelationshipRepository relationRelationshipRepository;
    private final UserMapper userMapper = new UserMapper();

    @PostMapping("/add-user")
    public User addUser(@RequestBody User userDto) {

        UserNode userNode = new UserNode();
        userNode.setAbout(userDto.getAbout());
        userNode.setFullName(userDto.getFullName());
        userNode.setUsername(userDto.getUsername());

        return userMapper.mapToModel(userNodeRepository.save(userNode));
    }

    @PostMapping("/add-relation")
    public Relation addRelation(@RequestParam Long from, @RequestParam Long to, @RequestParam Distance distance) {

        RelationRelationship relationRelationship = new RelationRelationship();
        relationRelationship.setPersonFrom(userNodeRepository.findById(from).orElseThrow(IllegalArgumentException::new));
        relationRelationship.setPersonTo(userNodeRepository.findById(to).orElseThrow(IllegalArgumentException::new));
        relationRelationship.setDistance(distance);

        RelationRelationship save = relationRelationshipRepository.save(relationRelationship);

        Relation response = new Relation();
        response.setId(save.getId());
        response.setDistance(save.getDistance());
        response.setPersonFromId(save.getPersonFrom().getId());
        response.setPersonToId(save.getPersonTo().getId());

        return response;
    }

    @GetMapping("/path")
    public List<User> path(@RequestParam String from, @RequestParam String to) {

        PathResult pathResult = userNodeRepository.findShortestPath(from, to);

        if (pathResult.getPath().isEmpty()) {
            throw new IllegalArgumentException();
        };

        return pathResult.getPath().stream().map(userMapper::mapToModel).collect(Collectors.toList());
    }

    @GetMapping("/user/{id}")
    public User get(@PathVariable Long id) {

        return userNodeRepository.findById(id).map(userMapper::mapToModel).orElseThrow(IllegalArgumentException::new);

    }

}
