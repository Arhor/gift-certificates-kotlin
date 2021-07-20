package com.epam.esm.gift.web

import com.epam.esm.gift.config.RootConfig
import com.epam.esm.gift.config.WebConfig
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.servlet.FrameworkServlet
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer

class WebAppInitializer : AbstractAnnotationConfigDispatcherServletInitializer() {

    override fun getServletMappings() = arrayOf("/")

    override fun getRootConfigClasses() = arrayOf(RootConfig::class.java)

    override fun getServletConfigClasses() = arrayOf(WebConfig::class.java)

    override fun createDispatcherServlet(servletAppContext: WebApplicationContext): FrameworkServlet {
        return super.createDispatcherServlet(servletAppContext).apply {
            if (this is DispatcherServlet) {
                setThrowExceptionIfNoHandlerFound(true)
            }
        }
    }

}