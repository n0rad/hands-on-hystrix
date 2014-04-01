package fr.n0rad.hands.on.hystrix.t99.voicemail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.ThreadSafe;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.netflix.config.DynamicIntProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsPoller;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

@Path("/metrics")
public class Metrics {

    private static final Logger logger = LoggerFactory.getLogger(HystrixMetricsStreamServlet.class);
    private static AtomicInteger concurrentConnections = new AtomicInteger(0);
    private static DynamicIntProperty maxConcurrentConnections = DynamicPropertyFactory.getInstance().getIntProperty("hystrix.stream.maxConcurrentConnections", 5);

    private volatile boolean isDestroyed = false;

    @Context
    HttpServletRequest request;

    @Context
    HttpServletResponse response;

    @GET
    public void metrics() {
        int numberConnections = concurrentConnections.incrementAndGet();
        HystrixMetricsPoller poller = null;
        try {
            if (numberConnections > maxConcurrentConnections.get()) {
                response.sendError(503, "MaxConcurrentConnections reached: " + maxConcurrentConnections.get());
            } else {

                int delay = 500;
                try {
                    String d = request.getParameter("delay");
                    if (d != null) {
                        delay = Integer.parseInt(d);
                    }
                } catch (Exception e) {
                    // ignore if it's not a number
                }

                /* initialize response */
                response.setHeader("Content-Type", "text/event-stream;charset=UTF-8");
                response.setHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
                response.setHeader("Pragma", "no-cache");

                MetricJsonListener jsonListener = new MetricJsonListener();
                poller = new HystrixMetricsPoller(jsonListener, delay);
                // start polling and it will write directly to the output stream
                poller.start();
                logger.info("Starting poller");

                // we will use a "single-writer" approach where the Servlet thread does all the writing
                // by fetching JSON messages from the MetricJsonListener to write them to the output
                try {
                    while (poller.isRunning() && !isDestroyed) {
                        List<String> jsonMessages = jsonListener.getJsonMetrics();
                        if (jsonMessages.isEmpty()) {
                            // https://github.com/Netflix/Hystrix/issues/85 hystrix.stream holds connection open if no metrics
                            // we send a ping to test the connection so that we'll get an IOException if the client has disconnected
                            response.getWriter().println("ping: \n");
                        } else {
                            for (String json : jsonMessages) {
                                response.getWriter().println("data: " + json + "\n");
                            }
                        }

                        /* shortcut breaking out of loop if we have been destroyed */
                        if (isDestroyed) {
                            break;
                        }

                        // after outputting all the messages we will flush the stream
                        response.flushBuffer();

                        // now wait the 'delay' time
                        Thread.sleep(delay);
                    }
                } catch (IOException e) {
                    poller.shutdown();
                    // debug instead of error as we expect to get these whenever a client disconnects or network issue occurs
                    logger.debug("IOException while trying to write (generally caused by client disconnecting). Will stop polling.", e);
                } catch (Exception e) {
                    poller.shutdown();
                    logger.error("Failed to write. Will stop polling.", e);
                }
                logger.debug("Stopping Turbine stream to connection");
            }
        } catch (Exception e) {
            logger.error("Error initializing servlet for metrics event stream.", e);
        } finally {
            concurrentConnections.decrementAndGet();
            if (poller != null) {
                poller.shutdown();
            }
        }
    }

    @ThreadSafe
    private static class MetricJsonListener implements HystrixMetricsPoller.MetricsAsJsonPollerListener {

        private final LinkedBlockingQueue<String> jsonMetrics = new LinkedBlockingQueue<String>(1000);

        @Override
        public void handleJsonMetric(String json) {
            jsonMetrics.add(json);
        }

        public List<String> getJsonMetrics() {
            ArrayList<String> metrics = new ArrayList<String>();
            jsonMetrics.drainTo(metrics);
            return metrics;
        }
    }

}
