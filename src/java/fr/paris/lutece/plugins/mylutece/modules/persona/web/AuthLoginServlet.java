/*
 * Copyright (c) 2002-2012, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.mylutece.modules.persona.web;

import fr.paris.lutece.plugins.mylutece.modules.persona.service.PersonaService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.httpaccess.HttpAccess;
import fr.paris.lutece.util.httpaccess.HttpAccessException;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * AuthLoginServlet
 */
public class AuthLoginServlet extends HttpServlet
{
    private static final String PARAMETER_ASSERTION = "assertion";
    private static final String PARAMETER_AUDIENCE = "audience";
    private static final String URL_VERIFIER = "https://verifier.login.persona.org/verify";

    @Override
    protected void service( HttpServletRequest request, HttpServletResponse response )
        throws ServletException, IOException
    {
        System.out.println( "AuthLogin:doPost" );

        super.service( request, response );

        String strAssertion = request.getParameter( PARAMETER_ASSERTION );
        Map<String, String> mapParams = new HashMap<String, String>(  );
        mapParams.put( PARAMETER_ASSERTION, strAssertion );
        mapParams.put( PARAMETER_AUDIENCE,
            request.getScheme(  ) + "://" + request.getServerName(  ) + ":" +
            ( ( request.getServerPort(  ) == 80 ) ? "" : request.getServerPort(  ) ) );

        HttpAccess httpClient = new HttpAccess(  );

        try
        {
            String strResponse = httpClient.doPost( URL_VERIFIER, mapParams );
            PersonaService.processAuthentication( request, strResponse );
        }
        catch ( HttpAccessException ex )
        {
            AppLogService.error( "Error processing Persona authentication : " + ex.getMessage(  ), ex );
        }
    }
}
