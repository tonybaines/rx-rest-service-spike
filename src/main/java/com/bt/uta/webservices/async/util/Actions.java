package com.bt.uta.webservices.async.util;

import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;
import rx.functions.Action1;

/**
 * Factory methods for the functions which render an HTTP response through the asynchronous servlet API
 */
public class Actions {

    /* The happy path */
    public static Action1<String> newOnNextAction ( final HttpServletResponse resp, final AsyncContext asyncContext){
    return new Action1<String>() {
        public void call(String message) {
            try {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getOutputStream().println("<html><body>" + message + "</body></html>");
            } catch (IOException e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } finally {
                asyncContext.complete();
            }
        }
    };
}

    /* Error handling */
    public static Action1<Throwable> newOnErrorAction ( final HttpServletResponse resp, final AsyncContext asyncContext){
    return new Action1<Throwable>() {
        public void call(Throwable throwable) {
            try {
                resp.getOutputStream().println("<html><body>Error: " + throwable.getMessage() + "</body></html>");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                asyncContext.complete();
            }
        }
    };
}
}
