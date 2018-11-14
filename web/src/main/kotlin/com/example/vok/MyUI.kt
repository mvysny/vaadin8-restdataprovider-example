package com.example.vok

import com.github.mvysny.karibudsl.v8.addColumnFor
import com.github.mvysny.karibudsl.v8.grid
import com.vaadin.annotations.Title
import com.vaadin.navigator.PushStateNavigation
import com.vaadin.server.VaadinRequest
import com.vaadin.ui.UI
import eu.vaadinonkotlin.restclient.CrudClient
import eu.vaadinonkotlin.sql2o.overcomeFetchLimit
import eu.vaadinonkotlin.vaadin8.sql2o.DataLoaderAdapter
import eu.vaadinonkotlin.vaadin8.sql2o.generateFilterComponents
import eu.vaadinonkotlin.vaadin8.sql2o.withConfigurableFilter2

/**
 * The Vaadin UI which demoes all the features. If not familiar with Vaadin, please check out the Vaadin tutorial first.
 * @author mvy
 */
@Title("Vaadin8 REST Data Provider Example")
@PushStateNavigation
class MyUI : UI() {

    override fun init(request: VaadinRequest) {
        val crud = CrudClient("http://localhost:8080/rest/articles/", Article::class.java).overcomeFetchLimit(100)
        grid<Article> {
            setSizeFull()
            dataProvider = DataLoaderAdapter(Article::class.java, crud, { it.id!! }).withConfigurableFilter2()

            addColumnFor(Article::id)
            addColumnFor(Article::title)
            addColumnFor(Article::created)
            addColumnFor(Article::score)

            appendHeaderRow().generateFilterComponents(this, Article::class)
        }
    }
}
