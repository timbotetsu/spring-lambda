package timbo.spring.lambda;

import com.amazonaws.serverless.proxy.RequestReader;
import com.amazonaws.serverless.proxy.model.ApiGatewayRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Objects;

public class CognitoIdentityFilter implements Filter {

    public static final String COGNITO_IDENTITY_ATTRIBUTE = "com.amazonaws.serverless.cognitoId";

    private static Logger logger = LoggerFactory.getLogger(CognitoIdentityFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Object apiGwContext = request.getAttribute(RequestReader.API_GATEWAY_CONTEXT_PROPERTY);
        if (Objects.isNull(apiGwContext)) {
            logger.warn("API Gateway context is null");
            chain.doFilter(request, response);
        }

        if (!ApiGatewayRequestContext.class.isAssignableFrom(apiGwContext.getClass())) {
            logger.warn("API Gateway context object is not of valid type");
            chain.doFilter(request, response);
        }

        ApiGatewayRequestContext context = (ApiGatewayRequestContext) apiGwContext;
        if (Objects.isNull(context.getIdentity())) {
            logger.warn("Identity context is null");
            chain.doFilter(request, response);
        }

        String cognitoIdentityId = context.getIdentity().getCognitoIdentityId();
        if (StringUtils.isEmpty(cognitoIdentityId)) {
            logger.warn("Cognito identity id in request is null");
        }

        request.setAttribute(COGNITO_IDENTITY_ATTRIBUTE, cognitoIdentityId);
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
