package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.ApplicationInfo;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ApplicationInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationInfoRepository extends JpaRepository<ApplicationInfo, Long> {
    List<ApplicationInfo> findByName(String name);
}
