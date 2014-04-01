package fr.n0rad.hands.on.hystrix.t99;


import static fr.norad.jaxrs.client.server.rest.RestBuilder.Generic.inStderrLogger;
import static fr.norad.jaxrs.client.server.rest.RestBuilder.Generic.inStdoutLogger;
import static fr.norad.jaxrs.client.server.rest.RestBuilder.Generic.outStderrLogger;
import static fr.norad.jaxrs.client.server.rest.RestBuilder.Generic.outStdoutLogger;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import fr.norad.jaxrs.client.server.resource.mapper.ErrorExceptionMapper;
import fr.norad.jaxrs.client.server.resource.mapper.ErrorResponseExceptionMapper;
import fr.norad.jaxrs.client.server.rest.RestBuilder;

public class Utils {

    private static RestBuilder builder = RestBuilder.rest()
            .addProvider(new JacksonJaxbJsonProvider())
            .addProvider(new ErrorResponseExceptionMapper(new JacksonJaxbJsonProvider()))
            .addProvider(new ErrorExceptionMapper())
            .addInInterceptor(inStdoutLogger)
            .addOutInterceptor(outStdoutLogger)
            .addInFaultInterceptor(inStderrLogger)
            .addOutFaultInterceptor(outStderrLogger);

    public static RestBuilder rest() {
        return builder;
    }
}
