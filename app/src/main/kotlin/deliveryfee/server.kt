// configuration of ktor-netty server
package serverKt

import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.serialization.json
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.http.HttpStatusCode

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.math.ceil
import kotlinx.serialization.SerializationException

import java.time.OffsetDateTime

import CalculateTotalDeliveryFeeKt.Deliveryfee
import ReqDataVerifyKt.ReqDataVerify


@Serializable
data class FeeCalcRequest(
    val cart_value: Int, // cent
    val delivery_distance: Int, //meter
    val number_of_items: Int,
    val time: String // UTC(ISO 8601)
)


@Serializable
data class FeecCalcResponse(
    val delivery_fee: Int
)


class server {

    fun deliveryFeeServerConfig(){
        embeddedServer(Netty, port = 8080) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = false })
            }
            
            routing {
                post("/delivery-fee") {
                    try {
                        // Request Data verification
                        val request: FeeCalcRequest = call.receive<FeeCalcRequest>()
                        val reqDataVerify: ReqDataVerify = ReqDataVerify()
                        if(!reqDataVerify.jsonVerification(request)){
                            throw SerializationException("keys are ok but value was minus or fractional number. otherwise time format was wrong")
                        }
        
                        // Fee calculation
                        val deliveryFee: Deliveryfee = Deliveryfee()
                        val FinalFee: Int = deliveryFee.sumDeliveryFee(request)
                          
                        // response Int verification
                        if (FinalFee < 0){ 
                            throw Exception("Finalfee was Mius")}
        
                        //Response to Clients
                        call.respond(FeecCalcResponse(FinalFee))
        
                    } catch (e: SerializationException) {                   
                        call.respond(HttpStatusCode.BadRequest, "400: Invalid request format\nType of values are wrong or invalid key included\n\nExample of expected request:\n{\"cart_value\": 10, \"delivery_distance\": 1000, \"number_of_items\": 5, \"time\": \"2024-01-01T12:00:00Z\"}")
                        e.printStackTrace()
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "500: Internal Server Error")
                        e.printStackTrace()
                    }
                }
            }
    
        }.start(wait = true)
    
    }
 
}