package com.epmserver.gateway.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link StateProvincesSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class StateProvincesSearchRepositoryMockConfiguration {

    @MockBean
    private StateProvincesSearchRepository mockStateProvincesSearchRepository;

}
