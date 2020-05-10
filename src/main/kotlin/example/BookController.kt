package example

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import javax.inject.Inject

@Controller("/")
class BookController (@Inject val bookRepository: BookRepository,@Inject val searchRepository: SearchRepository){

    @Post("/add")
    fun addNewBook(title: String,author: String,attribute: String):String{
        bookRepository.save(Book(0,title,author,attribute))
        return "Saved"
    }

    @Get("/find_all")
    fun findAllList()=bookRepository.findAll()

    @Get("/find_id")
    fun findListById(@QueryValue id: Long)=bookRepository.findById(id)

    @Post("/update")
    fun updateBook(@QueryValue id: Long,title:String,author:String,attribute:String):String{
        bookRepository.save(Book(id, title, author,attribute))
        return "Updated"
    }

    @Post("/delete")
    fun deleteBook(@QueryValue id: Long):String{
        if (bookRepository.existsById(id)==true) {
            bookRepository.deleteById(id)
            return "Deleted"
        }else
            return "No Book"
    }

    @Get("/find_author")
    fun findListByAuthor(@QueryValue author: String)=searchRepository.findByAuthor(author)

    @Get("/find_title")
    fun findListByTitle(@QueryValue title: String)=searchRepository.findByTitle(title)

}