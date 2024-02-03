 //Testing for Total calculation of CalculateTotalDeliveryFee.kt

package CalculateTotalDeliveryFeeKt

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import serverKt.FeeCalcRequest
import kotlinx.serialization.SerializationException




class FinalCalculation {

    val Deliveryfee = Deliveryfee()

    @Test fun finalCalculation() {
        //all zero
        val requestZero = FeeCalcRequest (
            cart_value = 0,
            delivery_distance = 0,
            number_of_items = 0,
            time = "2021-10-12T13:00:00Z"
        )
        val result1 = Deliveryfee.SumDeliveryFee(requestZero)
        assertEquals(1100, result1)


        // At RushHour
        val rushreq1 = FeeCalcRequest (
            cart_value = 790,
            delivery_distance = 2235,
            number_of_items = 4,
            time = "2024-02-02T17:00:00Z"
        )
        val RushHour = Deliveryfee.SumDeliveryFee(rushreq1)
        assertEquals(852, RushHour) 


        // RushHour with big values(checkCap)
        val bigvalue = FeeCalcRequest (
            cart_value = 1000,
            delivery_distance = 10000,
            number_of_items = 30,
            time = "2024-02-03T17:00:00Z"
        )
        val result2 = Deliveryfee.SumDeliveryFee(bigvalue)
        assertEquals(1500, result2)


        // case when CartValue over 200
        val just200 = FeeCalcRequest (
            cart_value = 20000,
            delivery_distance = 10000,
            number_of_items = 30,
            time = "2024-02-02T17:00:00Z"
        )
        val result3 = Deliveryfee.SumDeliveryFee(just200)
        assertEquals(0, result3)


        val over200 = FeeCalcRequest (
            cart_value = 40000,
            delivery_distance = 10000,
            number_of_items = 30,
            time = "2024-02-02T17:00:00Z"
        )
        val result4 = Deliveryfee.SumDeliveryFee(over200)
        assertEquals(0, result4)





//CALCULATION FAIL PATTERNS

    val cartMinus = FeeCalcRequest (
        cart_value = -40000,
        delivery_distance = 10000,
        number_of_items = 30,
        time = "2024-02-02T17:00:00Z"
    )
    val cartMinusResult = assertFailsWith<Exception> {
        Deliveryfee.SumDeliveryFee(cartMinus)
    }   
    assertEquals("error in calculateOrderSurcharge", cartMinusResult.message)

    val distanceMinus = FeeCalcRequest (
        cart_value = 400,
        delivery_distance = -10000,
        number_of_items = 30,
        time = "2024-02-02T17:00:00Z"
    )
    val distanceMinusResult = assertFailsWith<Exception> {
        Deliveryfee.SumDeliveryFee(distanceMinus)
    }   
    assertEquals("error in calculateDistanceFee", distanceMinusResult.message)


    val numOfItemMinus = FeeCalcRequest (
        cart_value = 400,
        delivery_distance = 10000,
        number_of_items = -30,
        time = "2024-02-02T17:00:00Z"
    )
    val numOfItemMinusResult = assertFailsWith<Exception> {
        Deliveryfee.SumDeliveryFee(numOfItemMinus)
    }   
    assertEquals("error in calculateItemSurcharge", numOfItemMinusResult.message)

        
    }       

}