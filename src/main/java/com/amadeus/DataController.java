package com.amadeus;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import com.amadeus.model.Session;
import com.amadeus.repository.SessionRepository;

@Path("/data")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.WILDCARD)
public class DataController {

  @Inject
  private SessionRepository sessionRepository;

  private final static Logger LOGGER = Logger.getLogger("com.amadeus.DataController");

  @GET
  public Response getData() {
    LOGGER.log(Level.INFO, "START - DataController.java - getData()");

    List<Session> session = sessionRepository.findData();

    LOGGER.log(Level.INFO, "END - DataController.java - getData()");
    return Response.ok().entity(session).build();
  }

  @GET
  @Path("/{id}")
  public Response getDataByUser(@PathParam Long id) {
    LOGGER.log(Level.INFO, "START - DataController.java - getDataByUser()");

    List<Session> session = sessionRepository.findByUser(id);

    LOGGER.log(Level.INFO, "END - DataController.java - getDataByUser()");
    return Response.ok().entity(session).build();
  }

  @POST
  public Response addData(Session session) {
    LOGGER.log(Level.INFO, "START - DataController.java - addData(Session)");

    sessionRepository.addSession(session);

    LOGGER.log(Level.INFO, "END - DataController.java - addData(Session)");
    return Response.ok().entity(session).build();
  }

  @DELETE
  @Path("/{id}")
  public Response deleteData(@PathParam Long id) {
    LOGGER.log(Level.INFO, "START - DataController.java - deleteData()");

    sessionRepository.deleteSession(id);

    LOGGER.log(Level.INFO, "END - DataController.java - deleteData()");
    return Response.ok().entity("Session deleted").build();
  }
}
