package CalculateTotalDeliveryFee

import kotlinx.serialization.json.Json
import java.time.ZoneOffset
import java.time.DayOfWeek
import java.time.OffsetDateTime
import kotlin.math.ceil
import indexfile.FeeCalcRequest

class Deliveryfee{

    // Total calculation
    fun SumDeliveryFee(request: FeeCalcRequest): Int {

            val cartValue: Int = request.cart_value
            val deliveryDistance: Int = request.delivery_distance
            val numberOfItems: Int = request.number_of_items
            val deliveryTime: OffsetDateTime = OffsetDateTime.parse(request.time)

            //200€ Free delivery
            if (cartValue >= 20000) {
                val deliveryFee:Int = 0
            return  deliveryFee
            }

            val smallOrderSurcharge: Int = calculateOrderSurcharge(cartValue)
            val distanceFee: Int = calculateDistanceFee(deliveryDistance)
            val itemSurcharge: Int = calculateItemSurcharge(numberOfItems)
            val bulkFee: Int = if (numberOfItems > 12) 120 else 0

            var deliveryFee:Int = distanceFee + itemSurcharge + bulkFee + smallOrderSurcharge

            // Rush Hour check
            if (isRushHour(deliveryTime)) {
                val RushdeliveryFee: Int = calculateRushHourFee(deliveryFee)
                return RushdeliveryFee
            }
            
            // Max 15 euro
            if(deliveryFee >= 1500){deliveryFee = 1500}
            return deliveryFee

    }


    fun calculateOrderSurcharge(cartValue: Int): Int{
            val surcharges: Int
            surcharges = if (cartValue < 1000) {
                1000 - cartValue
            } else 0

        return surcharges
    }


    fun calculateDistanceFee(distance: Int): Int {
            val baseDistanceFee: Int = 100
            val additionalDistance: Int = distance - 499
            val additionalDistanceFee: Int = if (additionalDistance > 0) {
                (ceil(additionalDistance / 500.0) * 100).toInt() 
                //ceiling logic manual =>  + if (additionalDistance % 500 != 0) 100 else 0
            } else 0

        return baseDistanceFee + additionalDistanceFee
    }

    
    // If the number of items is five or more, an additional 50 cent surcharge is added for each item above and including the fifth item. 
    // An extra "bulk" fee applies for more than 12 items of 1,20€
    fun calculateItemSurcharge(numberOfItems: Int): Int {
            val baseItemSurcharge: Int = 50
            var additionalSurcharge: Int = if (numberOfItems >= 5) {
                val extraItems: Int = numberOfItems - 4
                baseItemSurcharge * extraItems 
            } else 0

            //Bulk fee
            additionalSurcharge += if (numberOfItems > 12) 120 else 0

        return additionalSurcharge
    }

    

    fun isRushHour(deliveryTime: OffsetDateTime): Boolean{
            val isBetween15pmAnd19pm: Boolean = deliveryTime.hour in 15..19
            val isFriday: Boolean = deliveryTime.getDayOfWeek() == DayOfWeek.FRIDAY
        
        return isBetween15pmAnd19pm && isFriday

    }


    fun calculateRushHourFee(originalFee: Int): Int {
            val updatedFee: Int = originalFee * 12 / 10
            val deliveryFee: Int = if (updatedFee >= 1500) 1500 else updatedFee
            
        return deliveryFee
    }



}