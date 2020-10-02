package org.teomant.handshake.user.persistance.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.teomant.handshake.user.persistance.model.PathResult;
import org.teomant.handshake.user.persistance.model.UserNode;

import java.util.List;

public interface UserNodeRepository extends Neo4jRepository<UserNode, Long> {

    @Query("MATCH (from:USER { username:$u1 }), (to:USER { username:$u2 }) , p = (from)-[:KNOWS*]->(to) " +
    "RETURN nodes(p) as path, relationships(p) as relations " +
    "ORDER BY reduce(distance = 0, r in relationships(p) | distance+r.distance) ASC " +
    "LIMIT 1")
    PathResult findShortestPath(@Param("u1")String u1, @Param("u2")String u2);

    List<UserNode> findByFullNameContains(String fullName);

    List<UserNode> findByUsernameContains(String username);
}
