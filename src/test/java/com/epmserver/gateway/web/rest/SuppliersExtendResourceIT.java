package com.epmserver.gateway.web.rest;

import com.epmserver.gateway.EpmgatewayApp;
import com.epmserver.gateway.service.SuppliersExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the SuppliersExtendResource REST controller.
 *
 * @see SuppliersExtendResource
 */
@SpringBootTest(classes = EpmgatewayApp.class)
public class SuppliersExtendResourceIT {

    private MockMvc restMockMvc;
    private final SuppliersExtendService suppliersExtendService;

    public SuppliersExtendResourceIT(SuppliersExtendService suppliersExtendService) {
        this.suppliersExtendService = suppliersExtendService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        SuppliersExtendResource suppliersExtendResource = new SuppliersExtendResource(suppliersExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(suppliersExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/suppliers-extend/default-action"))
            .andExpect(status().isOk());
    }
}
