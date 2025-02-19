package com.quorum.tessera.p2p;

import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import com.quorum.tessera.messaging.Inbox;
import com.quorum.tessera.messaging.MessageId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "peer-to-peer")
@Path("/message")
public class MessageResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(MessageResource.class);

  private final Inbox inbox;

  public MessageResource(Inbox inbox) {
    this.inbox = inbox;
  }

  @Operation(
      summary = "/message/push",
      operationId = "pushMessage",
      description = "store a message in the server inbox")
  @ApiResponse(
      responseCode = "201",
      description = "identifier of received message",
      content =
          @Content(
              mediaType = TEXT_PLAIN,
              schema =
                  @Schema(
                      description = "identifier of received message",
                      type = "string",
                      format = "base64")))
  @PUT
  @Path("/push")
  @Consumes(APPLICATION_OCTET_STREAM)
  public Response push(@Schema(description = "arbitrary message") final byte[] message) {

    LOGGER.debug("Received inbound message");

    try {
      MessageId messageId = inbox.put(message);
      LOGGER.info("Stored message with messageId: {}", messageId);
      return Response.status(Response.Status.CREATED).entity(messageId.toString()).build();
    } catch (Exception ex) {
      LOGGER.warn("Caught exception whilst storing message", ex);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }
}
