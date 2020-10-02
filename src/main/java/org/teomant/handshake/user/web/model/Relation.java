package org.teomant.handshake.user.web.model;

import lombok.Data;

@Data
public class Relation {
    private Long id;
    private Distance distance;
    private Long personFromId;
    private Long personToId;
}