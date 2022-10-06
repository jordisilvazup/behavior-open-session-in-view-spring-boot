package br.com.zup.edu.library.base;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public abstract class SpringBootIntegrationTest {
    @Autowired
    protected ObjectMapper mapper;
    @Autowired
    protected MockMvc mockMvc;
    protected static final Logger LOGGER = LoggerFactory.getLogger(SpringBootIntegrationTest.class);

    /**
     * Starts many threads concurrently to execute the <code>operation</code> at the same time.
     * This method only returns after all threads have been executed.
     */
    protected void doSyncAndConcurrently(int threadCount, Consumer<String> operation) throws InterruptedException {

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            String threadName = "Thread-" + i;
            new Thread(() -> {
                try {
                    startLatch.await();
                    operation.accept(threadName);
                } catch (Exception e) {
                    LOGGER.error("error while executing operation {}: {}", threadName, e.getMessage());
                } finally {
                    endLatch.countDown();
                }
            }).start();
        }

        startLatch.countDown();
        endLatch.await();
    }

    public String toJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }
}
