package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.ApplicationInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ApplicationInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationInfoRepository extends JpaRepository<ApplicationInfo, Long> {
    @Query("select appInfo from ApplicationInfo appInfo where appInfo.genericName in :genericNameList ")
    Page<ApplicationInfo> findByGenericNameList(Pageable pageable , @Param( "genericNameList" )  List<String>genericNameList );

}
