package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.ApplicationInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApplicationInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationInfoRepository extends JpaRepository<ApplicationInfo, Long> {


}
