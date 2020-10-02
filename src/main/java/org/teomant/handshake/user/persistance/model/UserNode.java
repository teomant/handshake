package org.teomant.handshake.user.persistance.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(of = "id")
@NodeEntity(label = "USER")
public class UserNode {
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String username;

    @Property
    private String fullName;

    @Property
    private String about;

    @Relationship(type = "KNOWS")
    private List<RelationRelationship> relations = new ArrayList<>();
}
