package example

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import javax.inject.Inject

@Controller("/")
class BookController (@Inject val bookRepository: BookRepository){

    @Post("/add")
    fun addNewBook():String{
        var book = Book(0,"The Stand", 1000)
            bookRepository.save(book)
        return "Saved"
    }

    @Get("/find_all")
    fun findAllList()=bookRepository.findAll()

    @Get("/find_id")
    fun findListById(@QueryValue id: Long)=bookRepository.findById(id)

    @Post("/update")
    fun updateBook(@QueryValue id: Long,title:String,pages:Int):String{
        bookRepository.save(Book(id, title,pages))
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

}