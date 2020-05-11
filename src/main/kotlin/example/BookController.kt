package example

import io.micronaut.http.annotation.*
import javax.inject.Inject

@Controller("/")
class BookController (@Inject val bookRepository: BookRepository,@Inject val searchRepository: SearchRepository){

    @Post("/add")
    fun addNewBook(title: String, author: String, attribute: String):String{
        bookRepository.save(Book(0,title,author,attribute))
        return "Saved"
    }

    @Get("/find")
    fun findAllList()=bookRepository.findAll()

    @Get("/find/{id}")
    fun findListById(id: Long)=bookRepository.findById(id)

    @Post("/update")
    fun updateBook(id: Long, title:String, author:String, attribute:String):String{
        if (bookRepository.existsById(id)==true) {
            bookRepository.update(Book(id, title, author, attribute))
            return "Updated"
        }else
            return "No Data"
    }

    @Post("/delete")
    fun deleteBook(id: Long):String{
        if (bookRepository.existsById(id)==true) {
            bookRepository.deleteById(id)
            return "Deleted"
        }else
            return "No Data"
    }

    @Get("/find_author")
    fun findListByAuthor(author: String)=searchRepository.findByAuthor(author)

    @Get("/find_title")
    fun findListByTitle(title: String)=searchRepository.findByTitle(title)

}