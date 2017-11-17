package com.angello.api.v1.mapper;

import com.angello.api.v1.model.CategoryDTO;
import com.angello.api.v1.model.CustomerDTO;
import com.angello.api.v1.model.VendorDTO;
import com.angello.domain.Customer;
import com.angello.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VendorMapper {
    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    //    @Mapping(source = "id",target = "id")
    VendorDTO vendorToVendorDTO(Vendor vendor);

    Vendor vendorDtoToVendor(VendorDTO vendorDTO);
}
