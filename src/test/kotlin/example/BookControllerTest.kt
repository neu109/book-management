package example

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.kotlintest.specs.StringSpec
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.http.client.RxHttpClient
import io.micronaut.test.annotation.MicronautTest
import io.kotlintest.shouldBe
import io.kotlintest.specs.AbstractAnnotationSpec

@MicronautTest
class BookControllerTest(ctx: ApplicationContext): StringSpec({

    @AbstractAnnotationSpec.Before
    val embeddedServer :EmbeddedServer=ctx.getBean(EmbeddedServer::class.java)
    val client: RxHttpClient = embeddedServer.applicationContext.createBean(RxHttpClient::class.java, embeddedServer.url)

    "1.test the server is running" {
        assert(ctx.getBean(EmbeddedServer::class.java).isRunning())
    }

    "2.test find all(null)" {
        val response = client.toBlocking().exchange(HttpRequest.GET<String>("/find"), String::class.java)
        // "the response is succesful"
        response.status shouldBe HttpStatus.OK
        response.body() shouldBe "[]"
    }

    "3.test add and find all(one data)" {
        // "add one date"
        var response = client.toBlocking().exchange(HttpRequest.POST("/add", Book(id=0,title = "人間失格",author = "太宰治",attribute = "近代文学")), String::class.java)
        // "add two data"
        response = client.toBlocking().exchange(HttpRequest.POST("/add", Book(id=0,title = "羅生門",author = "芥川龍之介",attribute = "近代文学")), String::class.java)
        // "the response is succesful"
        response.status shouldBe HttpStatus.OK
        response.body() shouldBe "Saved"
        response = client.toBlocking().exchange(HttpRequest.GET<String>("/find"), String::class.java)
        val expectedResponse = """[{"id":1,"title":"人間失格","author":"太宰治","attribute":"近代文学"},{"id":2,"title":"羅生門","author":"芥川龍之介","attribute":"近代文学"}]""".trimIndent()
        // "the response is succesful"
        response.status shouldBe HttpStatus.OK
        response.body() shouldBe expectedResponse
    }

    "4.test add and find by id(one data)" {
        val response = client.toBlocking().exchange(HttpRequest.GET<String>("/find/1"), String::class.java)
        val expectedResponse = """{"id":1,"title":"人間失格","author":"太宰治","attribute":"近代文学"}""".trimIndent()
        // "the response is succesful"
        response.status shouldBe HttpStatus.OK
        response.body() shouldBe expectedResponse
    }

    "5.test update" {
        // "update data"
        var response = client.toBlocking().exchange(HttpRequest.POST("/update", Book(id=2,title = "斜陽",author = "太宰治",attribute = "近代文学")), String::class.java)
        // "the response is succesful"
        response.status shouldBe HttpStatus.OK
        response.body() shouldBe "Updated"
        val expectedResponse = """{"id":2,"title":"斜陽","author":"太宰治","attribute":"近代文学"}""".trimIndent()
        response = client.toBlocking().exchange(HttpRequest.GET<String>("/find/2"), String::class.java)
        // "the response is succesful"
        response.status shouldBe HttpStatus.OK
        response.body() shouldBe expectedResponse
    }

    "6.test update no data" {
        val response = client.toBlocking().exchange(HttpRequest.POST("/update", Book(id=3,title = "羅生門",author = "芥川龍之介",attribute = "近代文学")), String::class.java)
        // "the response is succesful"
        response.status shouldBe HttpStatus.OK
        response.body() shouldBe "No Data"
    }

    "7.test delete" {
        // "delete one data"
        var response = client.toBlocking().exchange(HttpRequest.POST("/delete", Book(id=2,title = "",author = "",attribute = "")), String::class.java)
        // "the response is succesful"
        response.status shouldBe HttpStatus.OK
        response.body() shouldBe "Deleted"
        val expectedResponse = """[{"id":1,"title":"人間失格","author":"太宰治","attribute":"近代文学"}]""".trimIndent()
        // "a request is made to index"
        response = client.toBlocking().exchange(HttpRequest.GET<String>("/find"), String::class.java)
        // "the response is succesful"
        response.status shouldBe HttpStatus.OK
        response.body() shouldBe expectedResponse
    }

    "8.test delete no data" {
        val response = client.toBlocking().exchange(HttpRequest.POST("/delete",Book(id = 3,title = "",author = "",attribute = "")), String::class.java)
        // "the response is succesful"
        response.status shouldBe HttpStatus.OK
        response.body() shouldBe "No Data"
    }


})