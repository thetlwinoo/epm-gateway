package com.epmserver.gateway.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ProductOptionSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ProductOptionSearchRepositoryMockConfiguration {

    @MockBean
    private ProductOptionSearchRepository mockProductOptionSearchRepository;

}
