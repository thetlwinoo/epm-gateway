package com.epmserver.gateway.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CompareProductsSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CompareProductsSearchRepositoryMockConfiguration {

    @MockBean
    private CompareProductsSearchRepository mockCompareProductsSearchRepository;

}
