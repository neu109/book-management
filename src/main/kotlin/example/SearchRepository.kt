package example

import io.micronaut.data.repository.CrudRepository
import io.micronaut.data.annotation.Repository
import io.micronaut.data.annotation.Query

@Repository
interface SearchRepository : CrudRepository<Book, Int>{
    fun findByAuthor(author:String):Iterable<Book>?
    fun findByTitle(title:String):Iterable<Book>?
}