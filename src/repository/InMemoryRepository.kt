//package com.example.repository
//
//import model.*
//import java.lang.IllegalArgumentException
//import java.util.concurrent.atomic.*
//
//class InMemoryRepository : Repository {
//    private val idCounter = AtomicInteger()
//    private val Movies = ArrayList<Movie>()
//    override suspend fun add(post_description: Movie): Movie {
//        if (Movies.contains(post_description)) {
//            return Movies.find { it == post_description }!!
//        }
//        post_description.id = idCounter.incrementAndGet()
//        Movies.add(post_description)
//        return post_description
//    }
//
//    override suspend fun post_description(id: Int) = post_description(id.toString())
//    override suspend fun post_description(id: String) = Movies.find { it.id.toString() == id }
//        ?: throw IllegalArgumentException("No post_description found for $id.")
//
//
//    override suspend fun Movies() = Movies
//
//    override suspend fun remove(post_description: Movie) {
//        if (!Movies.contains(post_description)) {
//            throw IllegalArgumentException("No post_description found for id ${post_description.id} ")
//        }
//        Movies.remove(post_description)
//    }
//
//    override suspend fun remove(id: Int)=Movies.remove(post_description(id))
//
//    override suspend fun remove(id: String)=Movies.remove(post_description(id))
//
//    override suspend fun clear() = Movies.clear()
//}