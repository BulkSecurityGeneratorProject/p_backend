package com.medicai.pillpal.service.mapper;

import com.medicai.pillpal.domain.ProductionInfo;
import com.medicai.pillpal.service.dto.ProductionInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ProductionInfo} and its DTO {@link ProductionInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationInfoMapper.class})
public interface ProductionInfoMapper extends EntityMapper<ProductionInfoDTO, ProductionInfo> {

    @Mapping(source = "applicationInfo.id", target = "applicationInfoId")
    ProductionInfoDTO toDto(ProductionInfo productionInfo);

    @Mapping(source = "applicationInfoId", target = "applicationInfo")
    ProductionInfo toEntity(ProductionInfoDTO productionInfoDTO);

    default ProductionInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductionInfo productionInfo = new ProductionInfo();
        productionInfo.setId(id);
        return productionInfo;
    }
}
