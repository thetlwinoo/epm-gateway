package com.epmserver.gateway.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ProductTagsSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ProductTagsSearchRepositoryMockConfiguration {

    @MockBean
    private ProductTagsSearchRepository mockProductTagsSearchRepository;

}
