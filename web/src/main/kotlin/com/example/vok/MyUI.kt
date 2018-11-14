package com.example.vok

import com.github.mvysny.karibudsl.v8.grid
import com.vaadin.annotations.Title
import com.vaadin.navigator.PushStateNavigation
import com.vaadin.server.VaadinRequest
import com.vaadin.ui.UI
import org.slf4j.LoggerFactory

/**
 * The Vaadin UI which demoes all the features. If not familiar with Vaadin, please check out the Vaadin tutorial first.
 * @author mvy
 */
@Title("Vaadin8 REST Data Provider Example")
@PushStateNavigation
class MyUI : UI() {

    override fun init(request: VaadinRequest) {
        grid<Article> {
            setSizeFull()
        }
    }

    companion object {
        @JvmStatic
        private val log = LoggerFactory.getLogger(MyUI::class.java)
    }
}
