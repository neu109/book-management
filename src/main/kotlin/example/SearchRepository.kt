package example

import io.micronaut.data.repository.CrudRepository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect

@JdbcRepository(dialect = Dialect.H2)
interface SearchRepository : CrudRepository<Book, Int>{
    fun findByAuthor(author:String):Iterable<Book>?
    fun findByTitle(title:String):Iterable<Book>?
}