package com.amadeus;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.jboss.resteasy.annotations.jaxrs.FormParam;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import com.couchbase.client.core.error.CouchbaseException;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;

@Path("/preferences")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PreferencesResource {

  @Inject
  Cluster cluster;

  private Collection collection;
  private final static String BUCKET_NAME = "preferences";
  private final static Logger LOGGER = Logger.getLogger("com.amadeus.PreferencesController");

  @GET
  @Path("/{id}")
  @Fallback(fallbackMethod = "fallback")
  public Response getPreferencesByUser(@PathParam String id) {
    LOGGER.log(Level.INFO, "START - PreferencesController.java - getPreferencesByUser()");

    if (collection == null)
      collection = cluster.bucket(BUCKET_NAME).defaultCollection();

    String responseValue;
    if (collection.exists(id).exists())
      responseValue = collection.get(id).contentAsObject().toString();
    else {
      LOGGER.log(Level.INFO, "The user with this ID doesn't exists");
      return Response.ok().entity("The user with this ID doesn't exists").build();
    }

    LOGGER.log(Level.INFO, "END - PreferencesController.java - getPreferencesByUser()");
    return Response.ok().entity(responseValue).build();
  }

  @POST
  @Fallback(fallbackMethod = "fallback")
  public Response addPreferences(@FormParam String user, @FormParam String email) {
    LOGGER.log(Level.INFO, "START - PreferencesController.java - addPreferences()");

    if (collection == null)
      collection = cluster.bucket(BUCKET_NAME).defaultCollection();

    JsonObject content;
    try {
      if (!collection.exists(user).exists()) {
        content = JsonObject.create()
            .put("user", user)
            .put("email", email);

        collection.insert(user, content);
      } else {
        LOGGER.log(Level.INFO, "The user with the ID '" + user + "' already exists");
        return Response.ok().entity("The user with this ID already exists").build();
      }

    } catch (CouchbaseException e) {
      LOGGER.log(Level.SEVERE, e.getMessage());
      return Response.serverError().entity("An error has occurred during the petition").build();
    }

    LOGGER.log(Level.INFO, "END - PreferencesController.java - addPreferences()");
    return Response.ok().entity(content.toString()).build();
  }

  @DELETE
  @Path("/{id}")
  @Fallback(fallbackMethod = "fallback")
  public Response deletePreferences(@PathParam String id) {
    LOGGER.log(Level.INFO, "START - PreferencesController.java - deletePreferences()");

    if (collection == null)
      collection = cluster.bucket(BUCKET_NAME).defaultCollection();

    try {
      collection.remove(id);
    } catch (CouchbaseException e1) {
      LOGGER.log(Level.SEVERE, e1.getMessage());
    }

    LOGGER.log(Level.INFO, "END - PreferencesController.java - deletePreferences()");
    return Response.ok().entity("Preferences deleted").build();
  }

  private Response fallback(String param) {
    return Response.serverError().entity("Fallback").build();
  }

  private Response fallback(String param1, String param2) {
    return Response.serverError().entity("Fallback").build();
  }

}
