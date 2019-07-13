package com.medicai.pillpal.service.dto;

import com.medicai.pillpal.domain.ApplicationInfo;
import com.medicai.pillpal.domain.enumeration.RecommendationType;

public class InteractionListDTO {
    private Long id;
    private RecommendationType recommendationType;
    private String description;
    private Long descApplicationInfoId;
    private String fdaApplicationNo;
    private String genericName;

    public InteractionListDTO(Long id, RecommendationType recommendationType, String description,  String fdaApplicationNo, String genericName) {
        this.id = id;
        this.recommendationType = recommendationType;
        this.description = description;
        this.fdaApplicationNo = fdaApplicationNo;
        this.genericName = genericName;
    }

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

    public Long getDescApplicationInfoId() {
        return descApplicationInfoId;
    }

    public void setDescApplicationInfoId(Long descApplicationInfoId) {
        this.descApplicationInfoId = descApplicationInfoId;
    }

    public String getFdaApplicationNo() {
        return fdaApplicationNo;
    }

    public void setFdaApplicationNo(String fdaApplicationNo) {
        this.fdaApplicationNo = fdaApplicationNo;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }
}
