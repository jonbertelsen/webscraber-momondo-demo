package rest;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import webscraper.TagCounter;
import webscraper.Tester;
import webscraper.TagDTO;

/**
 * REST Web Service
 *
 * @author lam
 */
@Path("scrape")
public class WebScraperResource {
    @Context
    private UriInfo context;
    
    @Path("sequental")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTagsSequental() {
        long startTime = System.nanoTime();
        LocalTime begin = LocalTime.now();

        List<TagCounter> dataFeched = Tester.runSequental();
        long endTime = System.nanoTime()-startTime;
        LocalTime end = LocalTime.now();
        long result = ChronoUnit.NANOS.between(begin, end);
        return TagDTO.getTagsAsJson("Sequental fetching",dataFeched, result);
    }
    @Path("parallel")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTagsParrallel() throws ExecutionException, InterruptedException
    {
        LocalTime begin = LocalTime.now();

        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("Antal kerner: " + processors);

        List<TagCounter> dataFeched = Tester.runParrallel();

        LocalTime end = LocalTime.now();
        long result = ChronoUnit.NANOS.between(begin, end);
        return TagDTO.getTagsAsJson("Parallel fetching",dataFeched, result);
    }
    
    
}
