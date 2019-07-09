package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.ProductionInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductionInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionInfoRepository extends JpaRepository<ProductionInfo, Long> {

}
