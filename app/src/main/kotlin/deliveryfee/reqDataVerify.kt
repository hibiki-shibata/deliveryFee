// checking if numbers are postive and if time format is in ISO format
package reqDataVerify

import indexfile.FeeCalcRequest
import java.time.OffsetDateTime

class reqDataVerify {

    fun jsonVerification(request: FeeCalcRequest): Boolean {
        return  request.cart_value >= 0 &&
                request.delivery_distance >= 0 &&
                request.number_of_items >= 0 &&
                isValidTime(request.time)
    }
    
    
    fun isValidTime(time: String): Boolean {
        return try {
                    OffsetDateTime.parse(time)
                    true
                } catch (e: Exception) {
                    false
                }
    }

}