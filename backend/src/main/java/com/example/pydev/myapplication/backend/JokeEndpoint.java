package com.example.pydev.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "jokeApi",
        version = "v1",
        resource = "joke",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.pydev.example.com",
                ownerName = "backend.myapplication.pydev.example.com",
                packagePath = ""
        )
)
public class JokeEndpoint {

    private static final Logger logger = Logger.getLogger(JokeEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Joke.class);
    }

    /**
     * Returns the {@link Joke} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code JokeModel} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "joke/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Joke get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting JokeModel with ID: " + id);
        Joke joke = ofy().load().type(Joke.class).id(id).now();
        if (joke == null) {
            throw new NotFoundException("Could not find JokeModel with ID: " + id);
        }
        return joke;
    }

    /**
     * Inserts a new {@code JokeModel}.
     */
    @ApiMethod(
            name = "insert",
            path = "joke",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Joke insert(Joke joke) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that joke.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(joke).now();
        logger.info("Created JokeModel with ID: " + joke.getId());

        return ofy().load().entity(joke).now();
    }

    /**
     * Updates an existing {@code JokeModel}.
     *
     * @param id   the ID of the entity to be updated
     * @param joke the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code JokeModel}
     */
    @ApiMethod(
            name = "update",
            path = "joke/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Joke update(@Named("id") Long id, Joke joke) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(joke).now();
        logger.info("Updated JokeModel: " + joke);
        return ofy().load().entity(joke).now();
    }

    /**
     * Deletes the specified {@code JokeModel}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code JokeModel}
     */
    @ApiMethod(
            name = "remove",
            path = "joke/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Joke.class).id(id).now();
        logger.info("Deleted JokeModel with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "joke",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Joke> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Joke> query = ofy().load().type(Joke.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Joke> queryIterator = query.iterator();
        List<Joke> jokeList = new ArrayList<>(limit);
        while (queryIterator.hasNext()) {
            jokeList.add(queryIterator.next());
        }
        return CollectionResponse.<Joke>builder().setItems(jokeList).setNextPageToken(cursor).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Joke.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find JokeModel with ID: " + id);
        }
    }


}