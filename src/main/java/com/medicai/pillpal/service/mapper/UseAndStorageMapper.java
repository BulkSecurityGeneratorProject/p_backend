package com.medicai.pillpal.service.mapper;

import com.medicai.pillpal.domain.*;
import com.medicai.pillpal.service.dto.UseAndStorageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UseAndStorage} and its DTO {@link UseAndStorageDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationInfoMapper.class})
public interface UseAndStorageMapper extends EntityMapper<UseAndStorageDTO, UseAndStorage> {

    @Mapping(source = "applicationInfo.id", target = "applicationInfoId")
    UseAndStorageDTO toDto(UseAndStorage useAndStorage);

    @Mapping(source = "applicationInfoId", target = "applicationInfo")
    UseAndStorage toEntity(UseAndStorageDTO useAndStorageDTO);

    default UseAndStorage fromId(Long id) {
        if (id == null) {
            return null;
        }
        UseAndStorage useAndStorage = new UseAndStorage();
        useAndStorage.setId(id);
        return useAndStorage;
    }
}
