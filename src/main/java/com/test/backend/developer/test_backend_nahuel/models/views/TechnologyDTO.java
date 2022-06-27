package com.test.backend.developer.test_backend_nahuel.models.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TechnologyDTO {
    private Long id;
    private String name;
    private String version;
}
