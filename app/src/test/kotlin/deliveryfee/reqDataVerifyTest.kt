package reqDataVerifyKt

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import serverKt.FeeCalcRequest
import java.time.OffsetDateTime

class reqDataverification {

    val reqDataVerify = reqDataVerify()

    @Test  fun `Check the requested values are always postive number`(){

        val generalReq = FeeCalcRequest(
                cart_value = 1000, 
                delivery_distance = 2500,
                number_of_items = 3,
                time = "2024-02-03T05:35:00Z" 
        )

            val isTrue = reqDataVerify.jsonVerification(generalReq)
            assertTrue(isTrue)


        val `Minus cart_value` = FeeCalcRequest(
                cart_value = -1000, 
                delivery_distance = 2500,
                number_of_items = 3,
                time = "2024-02-03T05:35:00Z" 
        )

            val isFalse1 = reqDataVerify.jsonVerification(`Minus cart_value`)
            assertFalse(isFalse1)


        val `Minus delivery_distance` = FeeCalcRequest(
                cart_value = 1000, 
                delivery_distance = -2500,
                number_of_items = 3,
                time = "2024-02-03T05:35:00Z" 
        )

            val isFalse2 = reqDataVerify.jsonVerification(`Minus delivery_distance`)
            assertFalse(isFalse2)


        val `Minus number_of_items` = FeeCalcRequest(
                cart_value = 1000, 
                delivery_distance = 2500,
                number_of_items = -3,
                time = "2024-02-03T05:35:00Z" 
        )

            val isFalse3 = reqDataVerify.jsonVerification(`Minus number_of_items`)
            assertFalse(isFalse3)


        val `Invalid time format` = FeeCalcRequest(
                cart_value = 1000, 
                delivery_distance = 2500,
                number_of_items = -3,
                time = "2024-02-03T05:35:0000Z" 
        )

            val isFalse4 = reqDataVerify.jsonVerification(`Invalid time format`)
            assertFalse(isFalse4)    

    }

}