package org.teomant.handshake.user.web.model;

import lombok.RequiredArgsConstructor;
import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.util.Arrays;

@RequiredArgsConstructor
public enum Distance {
    RELATIVE(2l), FRIEND(10l), KNOWS(20l);

    public Long getDistance() {
        return distance;
    }

    private final Long distance;

    public static class Converter implements AttributeConverter<Distance, Long> {
        @Override
        public Long toGraphProperty(Distance distance) {
            return distance.distance;
        }

        @Override
        public Distance toEntityAttribute(Long l) {
            return Arrays.stream(Distance.values()).filter(d -> d.distance.equals(l)).findFirst().orElseThrow(IllegalArgumentException::new);
        }
    }
}
