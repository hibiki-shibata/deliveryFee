 //Testing for Total calculation of CalculateTotalDeliveryFee.kt
package CalculateTotalDeliveryFeeKt

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import serverKt.FeeCalcRequest
import kotlinx.serialization.SerializationException




class FinalCalculation {

    val Deliveryfee = Deliveryfee()

    @Test fun `Testing if the sum up calculation inside of CalculateDeliveryFee expectedly works`() {
        //all zero
        val reqZero = FeeCalcRequest (
            cart_value = 0,
            delivery_distance = 0,
            number_of_items = 0,
            time = "2021-10-12T13:00:00Z"
        )
        val reqZeroResult = Deliveryfee.SumDeliveryFee(reqZero)
        assertEquals(1100, reqZeroResult)


        // At RushHour
        val reqRushHour = FeeCalcRequest (
            cart_value = 790,
            delivery_distance = 2235,
            number_of_items = 4,
            time = "2024-02-02T17:00:00Z"
        )
        val reqRushHourResult = Deliveryfee.SumDeliveryFee(reqRushHour)
        assertEquals(852, reqRushHourResult) 


        // RushHour with big surchages(checkCap)
        val reqBigSurchages = FeeCalcRequest (
            cart_value = 1000,
            delivery_distance = 10000,
            number_of_items = 30,
            time = "2024-02-03T17:00:00Z"
        )
        val reqBigSurchagesResult = Deliveryfee.SumDeliveryFee(reqBigSurchages)
        assertEquals(1500, reqBigSurchagesResult)


        // case when CartValue over 200
        val reqJust200euroFreeDelivery = FeeCalcRequest (
            cart_value = 20000,
            delivery_distance = 10000,
            number_of_items = 30,
            time = "2024-02-02T17:00:00Z"
        )
        val reqJust200euroFreeDeliveryResult = Deliveryfee.SumDeliveryFee(reqJust200euroFreeDelivery)
        assertEquals(0, reqJust200euroFreeDeliveryResult)


        val reqOver200euroFreeDelivery = FeeCalcRequest (
            cart_value = 40000,
            delivery_distance = 10000,
            number_of_items = 30,
            time = "2024-02-02T17:00:00Z"
        )
        val reqOver200euroFreeDeliveryResult = Deliveryfee.SumDeliveryFee(reqOver200euroFreeDelivery)
        assertEquals(0, reqOver200euroFreeDeliveryResult)





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