package com.bt.uta.webservices.async;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rx.Observable;

import com.bt.uta.webservices.async.services.MessageRepository;
import com.bt.uta.webservices.async.util.Mappers;

/**
 * Uses the Servlet 3.0 asynchronous NIO APIs and RxJava to do non-blocking request/response processing
 */
@WebServlet(urlPatterns = "/greeting-async", asyncSupported = true)
public class NonBlockingServlet extends HttpServlet {

    private final MessageRepository messageRepository = new MessageRepository();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final AsyncContext asyncContext = req.startAsync();

        // Event-driven/non-blocking pipeline
        Observable.from(req)
                .map(Mappers.newHttpRequestToDataLayerRequestMapper()) // HTTP Request -> domain request
                .map(Mappers.newDataLayerRequestToDataLayerResponseMapper(messageRepository)) // domain request -> domain result
                .map(Mappers.newDataLayerResponseToResultMapper()) // domain result -> string
                .subscribe(
                        com.bt.uta.webservices.async.util.Actions.newOnNextAction(resp, asyncContext), // Handler for the 'happy path'
                        com.bt.uta.webservices.async.util.Actions.newOnErrorAction(resp, asyncContext)); // Error handler

        // Enhancement: Use Hystrix to protect back-end services from overload
        //        new MessageLookupCommand(messageRepository, req)
        //                .observe()
        //                .map(Mappers.newDataLayerResponseToResultMapper())
        //                .subscribe(
        //                       com.bt.uta.webservices.async.util.Actions.newOnNextAction(resp, asyncContext),
        //                       com.bt.uta.webservices.async.util.Actions.newOnErrorAction(resp, asyncContext));

    }

}
