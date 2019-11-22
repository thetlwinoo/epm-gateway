package com.epmserver.gateway.web.rest;

import com.epmserver.gateway.EpmgatewayApp;
import com.epmserver.gateway.repository.PhotosExtendRepository;
import com.epmserver.gateway.service.PhotosExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the PhotosExtendResource REST controller.
 *
 * @see PhotosExtendResource
 */
@SpringBootTest(classes = EpmgatewayApp.class)
public class PhotosExtendResourceIT {

    private MockMvc restMockMvc;
    private final PhotosExtendService photosExtendService;
    private final PhotosExtendRepository photosExtendRepository;

    public PhotosExtendResourceIT(PhotosExtendService photosExtendService, PhotosExtendRepository photosExtendRepository) {
        this.photosExtendService = photosExtendService;
        this.photosExtendRepository = photosExtendRepository;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        PhotosExtendResource photosExtendResource = new PhotosExtendResource(photosExtendService, photosExtendRepository);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(photosExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/photos-extend/default-action"))
            .andExpect(status().isOk());
    }
}
