// This file calculate final delivery fee
package CalculateTotalDeliveryFeeKt

import java.time.DayOfWeek
import java.time.OffsetDateTime
import kotlin.math.ceil
import serverKt.FeeCalcRequest

class Deliveryfee{

    // Total calculation
    fun sumDeliveryFee(request: FeeCalcRequest): Int {        
            val cartValue: Int = request.cart_value
            val deliveryDistance: Int = request.delivery_distance
            val numberOfItems: Int = request.number_of_items
            val deliveryTime: OffsetDateTime = OffsetDateTime.parse(request.time)

            val cartValueForFreeDelivery: Int = 20000
            val deliveryFeeMaxCap: Int = 1500

            //Spec:The delivery is free (0€) when the cart value is equal or more than 200€.
            if (cartValue >= cartValueForFreeDelivery) return 0
            
            // Calculate each type of fee
            val smallOrderSurcharge: Int = calculateOrderSurcharge(cartValue)
            val distanceFee: Int = calculateDistanceFee(deliveryDistance)
            val itemSurcharge: Int = calculateItemSurcharge(numberOfItems)

            var deliveryFeeSubTotal: Int = smallOrderSurcharge + distanceFee + itemSurcharge

            // Check if RushHour
            if (isRushHour(deliveryTime)) {
                val rushDeliveryFee: Int = calculateRushHourFee(deliveryFeeSubTotal, deliveryFeeMaxCap)
                return rushDeliveryFee
            }

            //Spec: The delivery fee can never be more than 15€, including possible surcharges.
            val deliveryFee: Int = if (deliveryFeeSubTotal >= deliveryFeeMaxCap) deliveryFeeMaxCap else deliveryFeeSubTotal


            if(deliveryFee < 0 || deliveryFeeMaxCap < deliveryFee ) throw Exception("Error happened in SumDeliveryFee")
        return deliveryFee

    }



    //Spec:If the cart value is less than 10€, a small order surcharge is added to the delivery price. The surcharge is the difference between the cart value and 10€.
    fun calculateOrderSurcharge(cartValue: Int): Int{
            val surcharges: Int
            val cartMinValue: Int = 1000

            surcharges = if (cartValue <= cartMinValue) {
                cartMinValue - cartValue
            } else 0
            

            if(cartValue < 0 || cartMinValue < surcharges || surcharges < 0) throw Exception("error in calculateOrderSurcharge")    
        return surcharges
    }

// review
    // Spec:1€ is added for every additional 500 meters. Even if the distance would be shorter than 500 meters, the minimum fee is always 1€.
    fun calculateDistanceFee(distance: Int): Int {
            val baseDistanceFee: Int = 200
            val firstMeterThreshold: Int = 1000
            val secondMeterThreshold: Int = 500 //surchage will be added by each distances of this value(meter)
            val distanceFeePer100m: Int = 100
            val additionalDistance: Int = distance - firstMeterThreshold
            val additionalDistanceFee: Int = if (additionalDistance > 0) {
                (ceil(additionalDistance / secondMeterThreshold.toDouble()) * distanceFeePer100m).toInt() //ceil -> rounding up fractional number
            } else 0
            
            val TotaldistanceFee: Int = baseDistanceFee + additionalDistanceFee


            if(distance < 0 || TotaldistanceFee < distanceFeePer100m) throw Exception("error in calculateDistanceFee")
        return TotaldistanceFee
    }

    

    //Spec:If the number of items is five or more, an additional 50 cent surcharge is added for each item above and including the fifth item. An extra "bulk" fee applies for more than 12 items of 1,20€.
    fun calculateItemSurcharge(numberOfItems: Int): Int {
            val baseItemSurcharge: Int = 50
            val additionalSurchargeThreshold: Int = 5
            val bulkFee: Int = 120
            val bulkFeeThreshold: Int = 12

            var additionalSurcharge: Int = if (numberOfItems >= additionalSurchargeThreshold) {
                val extraItems: Int = numberOfItems - (additionalSurchargeThreshold - 1)
                baseItemSurcharge * extraItems 
            } else 0

            //Bulk fee
            additionalSurcharge += if (numberOfItems > bulkFeeThreshold) bulkFee else 0 //CONVERSATIONAL


            if(numberOfItems < 0 || additionalSurcharge < 0) throw Exception("error in calculateItemSurcharge")
        return additionalSurcharge
    }


    
    //Spec:During the Friday rush, 3 - 7 PM(UTC), the delivery fee (the total fee including possible surcharges) will be multiplied by 1.2x. However, the fee still cannot be more than the max (15€).
    fun isRushHour(deliveryTime: OffsetDateTime): Boolean{
            val isBetween3pmAnd7pm: Boolean = deliveryTime.hour in 15..19
            val isFriday: Boolean = deliveryTime.getDayOfWeek() == DayOfWeek.FRIDAY
        
        return isBetween3pmAnd7pm && isFriday
    }

    
    fun calculateRushHourFee(originalFee: Int, deliveryFeeMaxCap: Int): Int {
            val RushHourMultiplier: Double = 1.2
            val updatedFee: Int = (originalFee * RushHourMultiplier).toInt() //CONVERSATIONAL

            val deliveryFee: Int = if (updatedFee >= deliveryFeeMaxCap) deliveryFeeMaxCap else updatedFee

        
            if(originalFee < 0 || deliveryFeeMaxCap < deliveryFee || deliveryFee < 0) throw Exception("error in calculateRushHourFee")
        return deliveryFee
    }

}


// spec flaws
// should I apply bulk fee when the number of item is just 12?
// should I round up when return value of RushHours multiplies?
// 