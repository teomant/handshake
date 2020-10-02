package org.teomant.handshake.user.persistance.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.teomant.handshake.user.persistance.model.RelationRelationship;

public interface RelationRelationshipRepository extends Neo4jRepository<RelationRelationship, Long> {
}
