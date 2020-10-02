package org.teomant.handshake.user.persistance.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.teomant.handshake.user.web.model.Distance;

@Data
@EqualsAndHashCode(of = "id")
@RelationshipEntity(type = "KNOWS")
public class RelationRelationship {
    @Id
    @GeneratedValue
    private Long id;

    @Property
    @Convert(Distance.Converter.class)
    private Distance distance;

    @StartNode
    private UserNode personFrom;

    @EndNode
    private UserNode personTo;
}