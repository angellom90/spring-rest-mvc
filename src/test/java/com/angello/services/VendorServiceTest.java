package com.angello.services;

import com.angello.api.v1.mapper.VendorMapper;
import com.angello.api.v1.model.CategoryDTO;
import com.angello.api.v1.model.CustomerDTO;
import com.angello.api.v1.model.VendorDTO;
import com.angello.controllers.v1.CustomerController;
import com.angello.controllers.v1.VendorController;
import com.angello.domain.Customer;
import com.angello.domain.Vendor;
import com.angello.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

public class VendorServiceTest {

    public static final Long ID = 1L;
    public static final Long ID2 = 2L;
    public static final Long ID3 = 3L;
    public static final String NAME = "Fun Fresh Fruits Ltd.";
    public static final String NAME2 = "Nuts for Nuts Company";
    public static final String NAME3 = "Home Fruits";

    @Mock
    private VendorRepository vendorRepository;

    VendorService vendorService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE,vendorRepository);
    }

    @Test
    public void getAllVendors() throws Exception {

        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        Vendor vendor2 = new Vendor();
        vendor.setId(ID2);
        vendor.setName(NAME2);

        Vendor vendor3 = new Vendor();
        vendor.setId(ID3);
        vendor.setName(NAME3);

        List<Vendor> vendors = Arrays.asList(vendor,vendor2,vendor3);
        //when
        when(vendorRepository.findAll()).thenReturn(vendors);

        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        //then
        assertEquals(3, vendorDTOS.size());
    }

    @Test
    public void getVendorById() throws Exception {

        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        Optional<Vendor> vendorOptional = Optional.of(vendor);
        // Other option: java.util.Optional.ofNullable
        //when
        when(vendorRepository.findById(anyLong())).thenReturn(vendorOptional);

        VendorDTO vendorDTO = vendorService.getVendorById(ID);

        //then
        assertEquals(ID, vendorDTO.getId());
        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    public void createNewVendor() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(ID);
        vendorDTO.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setId(vendorDTO.getId());
        savedVendor.setName(vendorDTO.getName());

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        //when
        VendorDTO savedDto = vendorService.createNewVendor(vendorDTO);

        //then
        assertEquals(vendorDTO.getId(), savedDto.getId());
        assertEquals(vendorDTO.getName(), savedDto.getName());
        assertEquals(VendorController.BASE_URL+"/1", savedDto.getVendorUrl());
    }

    @Test
    public void saveVendorByDTO() throws Exception {

        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(ID);
        vendorDTO.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setId(vendorDTO.getId());
        savedVendor.setName(vendorDTO.getName());

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        //when
        VendorDTO savedDto = vendorService.saveVendorByDTO(ID, vendorDTO);

        //then
        assertEquals(vendorDTO.getName(), savedDto.getName());
        assertEquals(VendorController.BASE_URL+"/1", savedDto.getVendorUrl());
    }

    @Test
    public void deleteCustomerById() throws Exception {

        Long id = 1L;

        vendorRepository.deleteById(id);

        verify(vendorRepository, times(1)).deleteById(anyLong());
    }


}