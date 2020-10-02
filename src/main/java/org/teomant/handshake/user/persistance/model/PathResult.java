package org.teomant.handshake.user.persistance.model;

import lombok.Getter;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

@Getter
@QueryResult
public class PathResult {
    private List<UserNode> path;
    private List<RelationRelationship> relations;


    public PathResult(List<UserNode> path, List<RelationRelationship> relations) {
        this.path = path;
        this.relations = relations;
    }
}