package com.angello.controllers.v1;

import com.angello.api.v1.model.VendorDTO;
import com.angello.controllers.RestResponseEntityExceptionHandler;
import com.angello.services.ResourceNotFoundException;
import com.angello.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.angello.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest {

    public static final Long ID = 1L;
    public static final String NAME = "Exotic Fruits Company";

    public static final Long ID2 = 2l;
    public static final String NAME2 = "Home Fruits";

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void getAllVendors() throws Exception {
        VendorDTO vendor = new VendorDTO();
        vendor.setId(ID);
        vendor.setName(NAME);
        vendor.setVendorUrl(VendorController.BASE_URL + "/1");

        VendorDTO vendor1 = new VendorDTO();
        vendor1.setId(ID2);
        vendor1.setName(NAME2);
        vendor1.setVendorUrl(VendorController.BASE_URL + "/2");

        List<VendorDTO> vendors = Arrays.asList(vendor, vendor1);

        when(vendorService.getAllVendors()).thenReturn(vendors);

        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void getVendorById() throws Exception {

        VendorDTO vendor = new VendorDTO();
        vendor.setId(ID);
        vendor.setName(NAME);
        vendor.setVendorUrl(VendorController.BASE_URL + "/1");

        when(vendorService.getVendorById(anyLong())).thenReturn(vendor);

        mockMvc.perform(get(VendorController.BASE_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    public void createNewVendor() throws Exception {
        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setId(ID);
        vendor.setName(NAME);

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setId(vendor.getId());
        returnDTO.setName(vendor.getName());
        returnDTO.setVendorUrl(VendorController.BASE_URL+"/1");

        when(vendorService.createNewVendor(vendor)).thenReturn(returnDTO);

        //when then
        mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isCreated())
               // .andExpect(jsonPath("$.id",equalTo(ID)))
                .andExpect(jsonPath("$.name",equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url",equalTo(VendorController.BASE_URL+"/1")));
    }

    @Test
    public void updateVendor() throws Exception {
        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setId(ID);
        vendor.setName(NAME);

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setId(vendor.getId());
        returnDTO.setName(vendor.getName());
        returnDTO.setVendorUrl(VendorController.BASE_URL+"/1");

        when(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);

        //then
        mockMvc.perform(put(VendorController.BASE_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL+"/1")));
    }

    @Test
    public void deleteVendor() throws Exception {

        mockMvc.perform(delete(VendorController.BASE_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService).deleteVendorById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(VendorController.BASE_URL + "/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}