package fr.n0rad.hands.on.hystrix.t8;

import java.util.Collection;
import java.util.List;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import fr.n0rad.hands.on.hystrix.Greeting;

public class t8CommandCollapser extends HystrixCollapser<List<Greeting>, Greeting, String> {

    private String name;

    public t8CommandCollapser(String name) {
        this.name = name;
    }

    @Override
    public String getRequestArgument() {
        return name;
    }

    @Override
    protected HystrixCommand<List<Greeting>> createCommand(Collection<CollapsedRequest<Greeting, String>> collapsedRequests) {
        return new BatchCommand(collapsedRequests);
    }

    @Override
    protected void mapResponseToRequests(List<Greeting> batchResponse, Collection<CollapsedRequest<Greeting, String>> collapsedRequests) {
        // TODO spread the batch response into the collapsed requests
    }

    private static final class BatchCommand extends HystrixCommand<List<Greeting>> {

        private final Collection<CollapsedRequest<Greeting, String>> requests;

        private BatchCommand(Collection<CollapsedRequest<Greeting, String>> requests) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("GetValueForKey")));
            this.requests = requests;
        }

        @Override
        protected List<Greeting> run() throws Exception {
            // TODO run a batch command for multiple request
            return null;
        }
    }
}
