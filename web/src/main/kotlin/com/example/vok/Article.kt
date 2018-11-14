package com.example.vok

import com.github.vokorm.*
import java.time.Instant
import kotlin.random.Random

data class Article(
        override var id: Long? = null,
        var title: String = "",
        var text: String = "",
        var created: Instant = Instant.now(),
        var score: Int = 0
) : Entity<Long> {
    companion object : Dao<Article> {
        private val word1 = arrayOf("The art of", "Mastering", "The secrets of", "Avoiding", "For fun and profit: ", "How to fail at", "10 important facts about", "The ultimate guide to", "Book of", "Surviving", "Encyclopedia of", "Very much", "Learning the basics of", "The cheap way to", "Being awesome at", "The life changer:", "The Vaadin way:", "Becoming one with", "Beginners guide to", "The complete visual guide to", "The mother of all references:")
        private val word2 = arrayOf("gardening", "living a healthy life", "designing tree houses", "home security", "intergalaxy travel", "meditation", "ice hockey", "children's education", "computer programming", "Vaadin TreeTable", "winter bathing", "playing the cello", "dummies", "rubber bands", "feeling down", "debugging", "running barefoot", "speaking to a big audience", "creating software", "giant needles", "elephants", "keeping your wife happy")
        fun createRandomArticles() {
            val random = Random(1)
            db {
                repeat(1000) {
                    Article(title = "${word1.random()} ${word2.random()}",
                            text = "Lorem Ipsum",
                            created = Instant.ofEpochMilli(System.currentTimeMillis() - random.nextLong(10000000L)),
                            score = random.nextInt(10)
                    ).save()
                }
            }
        }
    }
}
