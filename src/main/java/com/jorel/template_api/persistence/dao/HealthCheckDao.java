package com.jorel.template_api.persistence.dao;

import com.jorel.template_api.persistence.vos.HealthCheckVO;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.List;
import java.util.Optional;

public interface HealthCheckDao {

    @SqlQuery("SELECT * FROM health_check")
    @RegisterBeanMapper(HealthCheckVO.class)
    List<HealthCheckVO> findAll();

    @SqlQuery("SELECT * FROM health_check WHERE id = :id")
    @RegisterBeanMapper(HealthCheckVO.class)
    Optional<HealthCheckVO> findById(@Bind("id") Long id);

    @SqlUpdate("INSERT INTO health_check (status) VALUES (:status)")
    void insert(@Bind("status") String status);
}
