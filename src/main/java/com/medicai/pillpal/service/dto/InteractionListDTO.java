package com.medicai.pillpal.service.dto;

import com.medicai.pillpal.domain.ApplicationInfo;
import com.medicai.pillpal.domain.enumeration.RecommendationType;

public class InteractionListDTO {
    private Long id;
    private RecommendationType recommendationType;
    private String description;
    private Long baseApplicationInfoId;
    private Long descApplicationInfoId;
    private ApplicationInfoDTO applicationInfoDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RecommendationType getRecommendationType() {
        return recommendationType;
    }

    public void setRecommendationType(RecommendationType recommendationType) {
        this.recommendationType = recommendationType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBaseApplicationInfoId() {
        return baseApplicationInfoId;
    }

    public void setBaseApplicationInfoId(Long baseApplicationInfoId) {
        this.baseApplicationInfoId = baseApplicationInfoId;
    }

    public Long getDescApplicationInfoId() {
        return descApplicationInfoId;
    }

    public void setDescApplicationInfoId(Long descApplicationInfoId) {
        this.descApplicationInfoId = descApplicationInfoId;
    }

    public ApplicationInfoDTO getApplicationInfoDTO() {
        return applicationInfoDTO;
    }

    public void setApplicationInfoDTO(ApplicationInfoDTO applicationInfoDTO) {
        this.applicationInfoDTO = applicationInfoDTO;
    }

    public InteractionListDTO(Long id, RecommendationType recommendationType, String description, Long baseApplicationInfoId, Long descApplicationInfoId, ApplicationInfoDTO applicationInfoDTO) {
        this.id = id;
        this.recommendationType = recommendationType;
        this.description = description;
        this.baseApplicationInfoId = baseApplicationInfoId;
        this.descApplicationInfoId = descApplicationInfoId;
        this.applicationInfoDTO = applicationInfoDTO;
    }
}
