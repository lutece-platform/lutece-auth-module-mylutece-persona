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

import fr.paris.lutece.plugins.mylutece.web.MyLuteceApp;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.portal.web.xpages.XPageApplication;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;


/**
 * Persona XPage Application
 */
public class PersonaApp implements XPageApplication
{
    // Parameters
    public static final String PARAMETER_PAGE = "page";
    public static final String PARAMETER_PAGE_VALUE = "persona";
    public static final String PARAMETER_ERROR = "error";
    private static final String PARAMETER_ACTION = "action";

    // Templates
    private static final String TEMPLATE_LOGIN_PAGE = "skin/plugins/mylutece/modules/persona/login_form.html";

    // Markers
    private static final String MARK_ERROR_MESSAGE = "error_message";
    private static final String MARK_URL_DOLOGIN = "url_dologin";
    private static final String MARK_USER_REGISTERED = "user_registered";

    // Actions
    private static final String ACTION_LOGIN = "login";
    private static final String PROPERTY_PAGETITLE_LOGIN = "module.mylutece.persona.loginPageTitle";
    private static final String PROPERTY_PATHLABEL_LOGIN = "module.mylutece.persona.loginPagePath";

    /**
     * Build the XPage
     * @param request The HTTP request
     * @param nMode The current mode
     * @param plugin The current plugin
     * @return The XPage
     */
    public XPage getPage( HttpServletRequest request, int nMode, Plugin plugin )
    {
        XPage page = new XPage(  );

        String strAction = request.getParameter( PARAMETER_ACTION );
        Locale locale = request.getLocale(  );

        if ( ( strAction == null ) || strAction.equals( ACTION_LOGIN ) )
        {
            return getLoginPage( page, request, locale );
        }

        return page;
    }

    /**
     * Build the Login page
     * @param page The XPage object to fill
     * @param request The HTTP request
     * @param locale The current locale
     * @return The XPage object containing the page content
     */
    private XPage getLoginPage( XPage page, HttpServletRequest request, Locale locale )
    {
        HashMap model = new HashMap(  );

        String strError = request.getParameter( PARAMETER_ERROR );
        String strErrorMessage = "";

        if ( strError != null )
        {
            strErrorMessage = I18nService.getLocalizedString( strError, locale );
        }

        boolean bUserRegistered = ( SecurityService.getInstance(  ).getRegisteredUser( request ) != null );
        model.put( MARK_ERROR_MESSAGE, strErrorMessage );
        model.put( MARK_URL_DOLOGIN, MyLuteceApp.getDoLoginUrl(  ) );
        model.put( MARK_USER_REGISTERED, bUserRegistered );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_LOGIN_PAGE, locale, model );

        page.setContent( template.getHtml(  ) );
        page.setTitle( I18nService.getLocalizedString( PROPERTY_PAGETITLE_LOGIN, locale ) );
        page.setPathLabel( I18nService.getLocalizedString( PROPERTY_PATHLABEL_LOGIN, locale ) );

        return page;
    }
}
