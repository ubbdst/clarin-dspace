/**
 * $Id$
 * $URL$
 * *************************************************************************
 * Copyright (c) 2002-2009, DuraSpace.  All rights reserved
 * Licensed under the DuraSpace Foundation License.
 *
 * A copy of the DuraSpace License has been included in this
 * distribution and is available at: http://scm.dspace.org/svn/repo/licenses/LICENSE.txt
 */
package org.dspace.servicemanager.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dspace.kernel.DSpaceKernel;
import org.dspace.kernel.DSpaceKernelManager;
import org.dspace.kernel.ServiceManager;
import org.dspace.services.RequestService;
import org.dspace.services.SessionService;

/**
 * Test servlet for trying out the jetty server
 * 
 * @author Aaron Zeckoski (aaron@caret.cam.ac.uk)
 */
public class SampleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    private transient SessionService sessionService;
    private transient RequestService requestService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            DSpaceKernel kernel = new DSpaceKernelManager().getKernel();
            if (kernel == null) {
                throw new IllegalStateException("Could not get the DSpace Kernel");
            }
            if (! kernel.isRunning()) {
                throw new IllegalStateException("DSpace Kernel is not running, cannot startup the DirectServlet");
            }
            ServiceManager serviceManager = kernel.getServiceManager();
            sessionService = serviceManager.getServiceByName(SessionService.class.getName(), SessionService.class);
            if (sessionService == null) {
                throw new IllegalStateException("Could not get the DSpace SessionService");
            }
            requestService = serviceManager.getServiceByName(RequestService.class.getName(), RequestService.class);
            if (requestService == null) {
                throw new IllegalStateException("Could not get the DSpace RequestService");
            }
            System.out.println("Servlet initialized");
        } catch (Exception e) {
            throw new IllegalStateException("FAILURE during init of direct servlet: " + e.getMessage(), e);
        }
    }

    /**
     * Now this will handle all kinds of requests and not just post and get
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
        // force all response encoding to UTF-8 / html by default
        res.setContentType("text/html");
        res.setCharacterEncoding("UTF-8");

        // now handle the request
        PrintWriter writer = res.getWriter();
        writer.print(XML_HEADER);
        writer.print(XHTML_HEADER);
        writer.print("DSpaceTest:session=" + sessionService.getCurrentSessionId() + ":request=" + requestService.getCurrentRequestId());
        writer.print(XHTML_FOOTER);
        res.setStatus(HttpServletResponse.SC_OK);

        System.out.println("Serviced request:  DSpace");
    }

    protected static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n";
    protected static final String XHTML_HEADER = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" " +
    "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
    "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
    "<head>\n" +
    "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
    "  <title>DSpace title</title>\n" +
    "</head>\n" +
    "<body>\n";
    // include versions info in the footer now
    protected static final String XHTML_FOOTER = "\n</body>\n</html>\n";

}
