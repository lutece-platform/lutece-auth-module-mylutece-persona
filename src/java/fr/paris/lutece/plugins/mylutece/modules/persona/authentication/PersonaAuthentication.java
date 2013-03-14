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
package fr.paris.lutece.plugins.mylutece.modules.persona.authentication;

import fr.paris.lutece.plugins.mylutece.authentication.PortalAuthentication;
import fr.paris.lutece.portal.service.security.LoginRedirectException;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


import java.util.List;
import java.util.Set;

import javax.security.auth.login.LoginException;

import javax.servlet.http.HttpServletRequest;


/**
 * The Class provides an implementation of the inherited abstract
 * class PortalAuthentication based on OpenID
 */
public class PersonaAuthentication extends PortalAuthentication
{
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Constants
    private static final String PROPERTY_AUTH_SERVICE_NAME = "mylutece-openid.service.name";
    private static final String URL_CALLBACK = "jsp/site/plugins/mylutece/modules/openid/OpenIDProviderCallBack.jsp";
    private static final String CONSTANT_PATH_ICON = "images/local/skin/plugins/mylutece/modules/openid/mylutece-openid.png";
    private static final String ATTRIBUTE_FIRST_NAME = "firstname";
    private static final String ATTRIBUTE_LAST_NAME = "lastname";
    private static final String ATTRIBUTE_EMAIL = "email";
    private static final String MESSAGE_KEY_AUTHENTICATION_FAILED = "module.mylutece.openid.authenticationFailed";
    private static final String PLUGIN_NAME = "mylutece-openid";
    private static final String PROPERTY_HTTPACCESS_PROXYHOST = "httpAccess.proxyHost";
    private static final String PROPERTY_HTTPACCESS_PROXYPORT ="httpAccess.proxyPort";
    private static final String PROPERTY_HTTPACCESS_PROXYUSERNAME ="httpAccess.proxyUserName";
    private static final String PROPERTY_HTTPACCESS_PROXYPASSWORD ="httpAccess.proxyPassword";
    private static final String PROPERTY_HTTPACCESS_DOMAINNAME ="httpaccess.domainName";
    
    private static Logger _logger = Logger.getLogger( "openid" );

    /**
     * Constructor
     */
    public PersonaAuthentication(  )
    {
        super(  );
/*
        // instantiate a ConsumerManager object
        if ( _manager == null )
        {
            try
            {
// init http proxy
                
                
            	boolean bUsingProxy = false;
                ProxyProperties properties = new ProxyProperties(  );
                String strProxyHost = AppPropertiesService.getProperty( PROPERTY_HTTPACCESS_PROXYHOST );
                String strProxyPort = AppPropertiesService.getProperty( PROPERTY_HTTPACCESS_PROXYPORT );
                String strProxyUserName = AppPropertiesService.getProperty( PROPERTY_HTTPACCESS_PROXYUSERNAME );
                String strProxyPassword = AppPropertiesService.getProperty( PROPERTY_HTTPACCESS_PROXYPASSWORD );
                String strDomainName = AppPropertiesService.getProperty( PROPERTY_HTTPACCESS_DOMAINNAME );
                
                if ( StringUtils.isNotBlank( strProxyHost ) )
                {
                	properties.setProxyHostName( strProxyHost );
                	bUsingProxy = true;
                }
                if ( StringUtils.isNotBlank( strProxyPassword ) )
                {
                	properties.setPassword( strProxyPassword );
                }
                
                
                if ( StringUtils.isNotBlank( strProxyPort ) )
                {
                	try
                	{
                		int nProxyPort = Integer.parseInt( strProxyPort );
                		properties.setProxyPort( nProxyPort );
                	}
                	catch ( NumberFormatException nfe )
                	{
                		AppLogService.error( "Error retrieving proxy port : " + strProxyHost );
                	}
                }
                if ( StringUtils.isNotBlank( strProxyUserName ) )
                {
                	properties.setUserName( strProxyUserName );
                }
                if ( StringUtils.isNotBlank( strDomainName ) )
                {
                	properties.setDomain( strDomainName );
                }
                
                if ( bUsingProxy )
                {
                	HttpClientFactory.setProxyProperties( properties );
                }
                _manager = new ConsumerManager(  );
            }
            catch ( ConsumerException e )
            {
                AppLogService.error( "Error instantiating OpenID ConsumerManager : " + e.getMessage(  ), e );
            }
        }
 */
    }

    /**
     * Gets the Authentification service name
     * @return The name of the authentication service
     */
    public String getAuthServiceName(  )
    {
        return AppPropertiesService.getProperty( PROPERTY_AUTH_SERVICE_NAME );
    }

    /**
     * Gets the Authentification type
     * @param request The HTTP request
     * @return The type of authentication
     */
    public String getAuthType( HttpServletRequest request )
    {
        return HttpServletRequest.BASIC_AUTH;
    }

    /**
     * This methods checks the login info in the LDAP repository
     *
     *
     * @return A LuteceUser object corresponding to the login
     * @param strUserName The username
     * @param strUserPassword The password
     * @param request The HttpServletRequest
     * @throws LoginRedirectException This exception is used to redirect the authentication to the provider
     * @throws LoginException The LoginException
     */
    public LuteceUser login( String strUserName, String strUserPassword, HttpServletRequest request )
        throws LoginException, LoginRedirectException
    {
        String strRedirectUrl = getProviderRedirectUrl( request, strUserName );

        if ( strRedirectUrl != null )
        {
            throw new LoginRedirectException( strRedirectUrl );
        }

        return null;
    }

    /**
     * This methods logout the user
     * @param user The user
     */
    public void logout( LuteceUser user )
    {
    }

    /**
     * This method returns an anonymous Lutece user
     *
     * @return An anonymous Lutece user
     */
    public LuteceUser getAnonymousUser(  )
    {
        return new PersonaUser( LuteceUser.ANONYMOUS_USERNAME, this );
    }

    /**
     * Checks that the current user is associated to a given role
     * @param user The user
     * @param request The HTTP request
     * @param strRole The role name
     * @return Returns true if the user is associated to the role, otherwise false
     */
    public boolean isUserInRole( LuteceUser user, HttpServletRequest request, String strRole )
    {
        // Not used
        return false;
    }

    /**
     * Build the http request to send to the provider to validate the authentication
     * @param request The HTTP request
     * @param strOpenID The user OpenID URL
     * @return The URL
     */
    private String getProviderRedirectUrl( HttpServletRequest request, String strOpenID )
    {
/*        String strReturnUrl = getMessageUrl( request, MESSAGE_KEY_AUTHENTICATION_FAILED );

        try
        {
            // perform discovery on the user-supplied identifier
            List discoveries = _manager.discover( strOpenID );

            // attempt to associate with the OpenID provider
            // and retrieve one service endpoint for authentication
            DiscoveryInformation discovered = _manager.associate( discoveries );

            // store the discovery information in the user's session
            request.getSession(  ).setAttribute( "openid-disc", discovered );

            // obtain a AuthRequest message to be sent to the OpenID provider
            AuthRequest authReq = _manager.authenticate( discovered, getReturnUrl( request ) );

            // Attribute Exchange example: fetching the 'email' attribute
            FetchRequest fetch = FetchRequest.createFetchRequest(  );
            fetch.addAttribute( ATTRIBUTE_FIRST_NAME, "http://schema.openid.net/namePerson/first", true );
            fetch.addAttribute( ATTRIBUTE_LAST_NAME, "http://schema.openid.net/namePerson/last", true );
            fetch.addAttribute( ATTRIBUTE_EMAIL, "http://schema.openid.net/contact/email", true );

            // attach the extension to the authentication request
            authReq.addExtension( fetch );

            strReturnUrl = authReq.getDestinationUrl( true );
        }
        catch ( OpenIDException e )
        {
            _logger.error( "OpenId Error building authentication request : " + e.getMessage(  ), e );
        }
        return strReturnUrl;
        */
        return "";
    }

    /**
     * The response URL that will be used by the provider to give its response :
     * authentication validated or not. If OK the response will hold uesr's attributes.
     * @param request The HTTP request
     * @return The URL
     */
    private String getReturnUrl( HttpServletRequest request )
    {
        _logger.debug( "Callback URL : " + AppPathService.getBaseUrl( request ) + URL_CALLBACK );

        return AppPathService.getBaseUrl( request ) + URL_CALLBACK;
    }

    /**
     * processing the authentication response
     * @param request The HTTP request
     * @return The URL depending of the result
     */
    public String verifyResponse( HttpServletRequest request )
    {
/*        String strReturnUrl = getMessageUrl( request, MESSAGE_KEY_AUTHENTICATION_FAILED );

        _logger.debug( "Provider callback - host : " + request.getRemoteHost(  ) + " - IP : " +
            request.getRemoteAddr(  ) );

        PersonaUser user = null;

        try
        {
            // extract the parameters from the authentication response
            // (which comes in as a HTTP request from the OpenID provider)
            ParameterList response = new ParameterList( request.getParameterMap(  ) );

            // retrieve the previously stored discovery information
            DiscoveryInformation discovered = (DiscoveryInformation) request.getSession(  ).getAttribute( "openid-disc" );

            // extract the receiving URL from the HTTP request
            StringBuffer receivingURL = request.getRequestURL(  );
            String queryString = request.getQueryString(  );

            if ( ( queryString != null ) && ( queryString.length(  ) > 0 ) )
            {
                receivingURL.append( "?" ).append( request.getQueryString(  ) );
            }

            // verify the response; ConsumerManager needs to be the same
            // (static) instance used to place the authentication request
            VerificationResult verification = _manager.verify( receivingURL.toString(  ), response, discovered );

            // examine the verification result and extract the verified identifier
            Identifier verified = verification.getVerifiedId(  );
            _logger.debug( "Authentication verification  : " + verified );

            if ( verified != null )
            {
                user = new PersonaUser( verified.getIdentifier(  ), this );

                AuthSuccess authSuccess = (AuthSuccess) verification.getAuthResponse(  );

                if ( authSuccess.hasExtension( AxMessage.OPENID_NS_AX ) )
                {
                    _logger.debug( "Authentication successfull - identifier : " + verified.getIdentifier(  ) );

                    FetchResponse fetchResp = (FetchResponse) authSuccess.getExtension( AxMessage.OPENID_NS_AX );

                    for ( String strKey : (Set<String>) fetchResp.getAttributes(  ).keySet(  ) )
                    {
                        _logger.debug( "Attribute " + strKey + " - value : " +
                            fetchResp.getAttributes(  ).get( strKey ) );
                    }

                    String strFirstName = (String) fetchResp.getAttributes(  ).get( ATTRIBUTE_FIRST_NAME );
                    String strLastName = (String) fetchResp.getAttributes(  ).get( ATTRIBUTE_LAST_NAME );
                    List emails = fetchResp.getAttributeValues( ATTRIBUTE_EMAIL );
                    String email = (String) emails.get( 0 );

                    user.setUserInfo( LuteceUser.NAME_GIVEN, strFirstName );
                    user.setUserInfo( LuteceUser.NAME_FAMILY, strLastName );
                    user.setUserInfo( LuteceUser.BUSINESS_INFO_ONLINE_EMAIL, email );
                }

                SecurityService.getInstance(  ).registerUser( request, user );

                strReturnUrl = AppPathService.getBaseUrl( request ) + AppPathService.getPortalUrl(  ); // success
            }
        }
        catch ( OpenIDException e )
        {
            _logger.error( "OpenId Error in provider response : " + e.getMessage(  ), e );
        }

        return strReturnUrl;
*/
        return "";
        }

    /**
     * Build the URL to display a message
     * @param request The HTTP request
     * @param strMessageKey The message key
     * @return The URL to display the message
     */
/*    private String getMessageUrl( HttpServletRequest request, String strMessageKey )
    {
        UrlItem url = new UrlItem( AppPathService.getBaseUrl( request ) + AppPathService.getPortalUrl(  ) );
        url.addParameter( OpenIDApp.PARAMETER_PAGE, OpenIDApp.PARAMETER_PAGE_VALUE );
        url.addParameter( OpenIDApp.PARAMETER_ERROR, strMessageKey );

        return url.getUrl(  );
    }
*/
    /**
     * 
     *{@inheritDoc}
     */
	public String getIconUrl()
	{
		return CONSTANT_PATH_ICON;
	}

	/**
	 * 
	 *{@inheritDoc}
	 */
	public String getName()
	{
		return PLUGIN_NAME;
	}

	/**
	 * 
	 *{@inheritDoc}
	 */
	public String getPluginName()
	{
		return PLUGIN_NAME;
	}

	/**
	 * 
	 *{@inheritDoc}
	 */
	public boolean isMultiAuthenticationSupported()
	{
		return false;
	}
}
