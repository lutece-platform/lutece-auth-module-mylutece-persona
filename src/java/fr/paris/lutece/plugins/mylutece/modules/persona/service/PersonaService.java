/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
package fr.paris.lutece.plugins.mylutece.modules.persona.service;

import fr.paris.lutece.plugins.mylutece.modules.persona.authentication.PersonaAuthValidation;
import fr.paris.lutece.plugins.mylutece.modules.persona.authentication.PersonaAuthentication;
import fr.paris.lutece.plugins.mylutece.modules.persona.authentication.PersonaUser;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


/**
 * Persona Service
 */
public final class PersonaService
{
    private static final PersonaAuthentication _authService = new PersonaAuthentication(  );
    private static Logger _logger = Logger.getLogger( "persona" );

    /** private constructor */
    private PersonaService(  )
    {
    }

    /**
     * Process the authentication
     * @param request The HTTP request
     * @param strAuthResponse The authentication's response as JSON format
     */
    public static void processAuthentication( HttpServletRequest request, String strAuthResponse )
    {
        _logger.debug( "Process authentication response" );

        PersonaAuthValidation authValidation = PersonaUtils.parseResponse( strAuthResponse );

        if ( authValidation.isOK(  ) )
        {
            PersonaUser user = new PersonaUser( authValidation.getEmail(  ), _authService );
            user.setUserInfo( LuteceUser.BUSINESS_INFO_ONLINE_EMAIL, authValidation.getEmail(  ) );
            SecurityService.getInstance(  ).registerUser( request, user );
            _logger.debug( "Authentication successful : " + strAuthResponse );
        }
        else
        {
            _logger.debug( "Authentication failed : " + strAuthResponse );
        }
    }

    /**
     * Process the logout
     * @param request The HTTP request
     */
    public static void processLogout( HttpServletRequest request )
    {
        _logger.debug( "Process logout" );
        SecurityService.getInstance(  ).logoutUser( request );
    }
}
