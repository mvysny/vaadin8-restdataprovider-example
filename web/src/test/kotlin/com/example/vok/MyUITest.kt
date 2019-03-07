package com.example.vok

import com.github.mvysny.dynatest.DynaNodeGroup
import com.github.mvysny.dynatest.DynaTest
import com.github.mvysny.dynatest.expectList
import com.github.mvysny.kaributesting.v8.MockVaadin
import com.github.mvysny.kaributesting.v8._get
import com.github.mvysny.kaributesting.v8.expectRow
import com.github.mvysny.kaributesting.v8.expectRows
import com.github.vokorm.deleteAll
import com.vaadin.ui.Grid
import eu.vaadinonkotlin.restclient.CrudClient
import io.javalin.Javalin
import io.javalin.JavalinEvent
import java.lang.RuntimeException
import java.time.Instant

fun DynaNodeGroup.usingApp() {
    lateinit var javalin: Javalin
    beforeGroup {
        Bootstrap().contextInitialized(null)
        javalin = Javalin.create()
                .disableStartupBanner()
                .configureRest()
                .start(8080)
    }
    afterGroup {
        javalin.stop()
        Bootstrap().contextDestroyed(null)
    }
    beforeEach { MockVaadin.setup({ MyUI() }) }
    afterEach { MockVaadin.tearDown() }
    beforeEach { Article.deleteAll() }
    afterEach { Article.deleteAll() }
}

class MyUITest : DynaTest({
    usingApp()

    test("UI smoke test") {
        val article = Article(title = "foo", text = "bar", created = Instant.ofEpochMilli(2213213111), score = 5)
        article.save()
        val grid = _get<Grid<*>>() // just check whether the Grid is there and populated
        grid.expectRows(1)
        grid.expectRow(0, "${article.id}", "foo", "1970-01-26T14:46:53.111Z", "5")
    }

    group("REST tests") {
        lateinit var client: CrudClient<Article>
        beforeGroup { client = CrudClient("http://localhost:8080/rest/articles/", Article::class.java) }

        test("fetch all") {
            val article = Article(title = "foo", text = "bar", created = Instant.ofEpochMilli(2213213111), score = 5)
            article.save()
            expectList(article) { client.getAll() }
        }
    }
})
