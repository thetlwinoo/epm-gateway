package com.epmserver.gateway.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link OrderLinesSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class OrderLinesSearchRepositoryMockConfiguration {

    @MockBean
    private OrderLinesSearchRepository mockOrderLinesSearchRepository;

}
