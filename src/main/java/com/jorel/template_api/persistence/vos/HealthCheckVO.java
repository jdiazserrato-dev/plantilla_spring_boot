package com.jorel.template_api.persistence.vos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthCheckVO {
    private Long id;

    private String status;

    private LocalDateTime createdAt;
}
