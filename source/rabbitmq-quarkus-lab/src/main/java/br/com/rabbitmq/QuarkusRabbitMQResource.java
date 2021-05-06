package br.com.rabbitmq;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/rabbit")
public class QuarkusRabbitMQResource {

    @Inject
    QuarkusRabbitMQService rabbitMQService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(String message){
        rabbitMQService.send( message );
        return Response.ok( message ).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delay")
    public Response sendDelayedMessage(String message){
        rabbitMQService.sendDelayedMessage( message, 10000L );
        return Response.ok( message ).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/message-priority")
    public Response sendPriorityMessage(){
        rabbitMQService.sendPriorityMessage();
        return Response.ok().build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/message-priority")
    public Response consumePriorityMessage(){
        rabbitMQService.consumePriorityMessage();
        return Response.ok().build();
    }

}
