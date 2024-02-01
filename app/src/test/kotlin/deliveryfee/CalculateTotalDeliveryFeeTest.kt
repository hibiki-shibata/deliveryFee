/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package CalculateTotalDeliveryFee

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertEquals
import indexfile.FeeCalcRequest
import java.time.OffsetDateTime


// https://github.com/woltapp/engineering-summer-intern-2023


class testEachFunctions {

    val Deliveryfee = Deliveryfee()

    @Test fun `calculateOrderSurcharge has to be 10€ - cartValue`() {

        val zero = Deliveryfee.calculateOrderSurcharge(0)
        val one = Deliveryfee.calculateOrderSurcharge(1)
        val small = Deliveryfee.calculateOrderSurcharge(790)
        val just = Deliveryfee.calculateOrderSurcharge(1000)
        val large = Deliveryfee.calculateOrderSurcharge(1234)
 
        assertEquals(1000, zero)
        assertEquals(999, one)
        assertEquals(210, small)
        assertEquals(0, just)
        assertEquals(0, large)   
        
    }

    
    @Test fun `Distancefee always has to be above 1 euro and additional fee is charged 1 euro for next every 500m`() {
        val zero = Deliveryfee.calculateDistanceFee(0)
        val middle = Deliveryfee.calculateDistanceFee(800)
        val just = Deliveryfee.calculateDistanceFee(1000)
        val large = Deliveryfee.calculateDistanceFee(2050)
        val large2 = Deliveryfee.calculateDistanceFee(1700) 

        assertEquals(100, zero)
        assertEquals(200, middle)
        assertEquals(300, just)
        assertEquals(500, large)
        assertEquals(400, large2)
    }

    
    @Test fun `For case the number of items more than 5, 50 cent is added for each items above and including the fifth item and Bulk fee for more than 12 items of 1€ + 20 cent `() {
        val zero = Deliveryfee.calculateItemSurcharge(0)
        val small = Deliveryfee.calculateItemSurcharge(1)
        val just = Deliveryfee.calculateItemSurcharge(5)
        val large = Deliveryfee.calculateItemSurcharge(7)
        val twelve = Deliveryfee.calculateItemSurcharge(12)
        val superLarge = Deliveryfee.calculateItemSurcharge(17)

        assertEquals(0, zero)
        assertEquals(0, small)
        assertEquals(50, just)
        assertEquals(150, large)
        assertEquals(400, twelve)
        assertEquals(770, superLarge)
      
    }


    @Test fun `Friday 3pm - 7pm has to be considered as RushHour`(){
        val FridayOutOfRush = Deliveryfee.isRushHour(OffsetDateTime.parse("2024-02-02T13:00:00Z"))
        val FridayRushhourEdge1 = Deliveryfee.isRushHour(OffsetDateTime.parse("2024-02-02T15:00:00Z"))
        val FridayRushhourMiddle = Deliveryfee.isRushHour(OffsetDateTime.parse("2024-02-02T17:00:00Z"))
        val FridayRushhourEdge2 = Deliveryfee.isRushHour(OffsetDateTime.parse("2024-02-02T19:00:00Z"))
        val NotFriday = Deliveryfee.isRushHour(OffsetDateTime.parse("2024-02-03T17:00:00Z"))

        assertEquals(false, FridayOutOfRush)
        assertEquals(true, FridayRushhourEdge1)
        assertEquals(true, FridayRushhourMiddle)
        assertEquals(true, FridayRushhourEdge2)
        assertEquals(false, NotFriday)

    }

    @Test fun `During the RushHours total surcharge will be multiplied by 12 devided by 10x - However, the fee still cannot be more than the max 15€`(){
        val generalCase = Deliveryfee.calculateRushHourFee(1000)
        val just = Deliveryfee.calculateRushHourFee(1500)
        val larger = Deliveryfee.calculateRushHourFee(2000)

        assertEquals(1200, generalCase)
        assertEquals(1500, just)
        assertEquals(1500, larger)

    }

}

 //FINAL CALCULATION
class FinalCalculation {

    val Deliveryfee = Deliveryfee()

    @Test fun finalCalculation() {

        // general
        val requestGeneral = FeeCalcRequest (
            cart_value = 790,
            delivery_distance = 2235,
            number_of_items = 4,
            time = "2021-10-12T13:00:00Z"
        )
        val deliveryFee = Deliveryfee.SumDeliveryFee(requestGeneral)
        assertEquals(710, deliveryFee)


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


        // RushHour with big values
        val bigvalue = FeeCalcRequest (
            cart_value = 100,
            delivery_distance = 10000,
            number_of_items = 30,
            time = "2024-02-02T17:00:00Z"
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
                
    }       

}

// might be good to moduling testing file
// API testing
// library versino
// ommentout add for instruction of each function
