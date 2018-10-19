package timbo.spring.lambda;

import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringLambdaContainerHandler;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, IntegrationTestConfig.class})
@WebAppConfiguration
@TestExecutionListeners(inheritListeners = false, listeners = {DependencyInjectionTestExecutionListener.class})
public class HelloControllerTest {

    @Autowired
    private MockLambdaContext lambdaContext;

    @Autowired
    private SpringLambdaContainerHandler handler;

    @Test
    @Ignore
    public void hello() {
        AwsProxyRequest request = new AwsProxyRequestBuilder("/hello", "GET").build();
        AwsProxyResponse response = (AwsProxyResponse) handler.proxy(request, lambdaContext);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getBody(), "Hello from the other side");
    }

}
