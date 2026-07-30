package com.jorel.template_api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthCheck {
    private Long id;

    private String status;

    private LocalDateTime createdAt;
}
