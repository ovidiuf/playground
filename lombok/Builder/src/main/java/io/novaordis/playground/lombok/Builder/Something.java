package io.novaordis.playground.lombok.Builder;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
public class Something {

    private long id;
    private String name;
}
