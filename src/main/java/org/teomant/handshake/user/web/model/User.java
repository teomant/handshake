package org.teomant.handshake.user.web.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(of = "id")
public class User {
    private Long id;
    private String username;
    private String fullName;
    private String about;

    private List<Relation> relations = new ArrayList<>();

}
