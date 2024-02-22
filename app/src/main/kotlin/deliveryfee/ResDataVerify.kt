package ResDataVerifyKt

import serverKt.FeeCalcRequest
import CalculateTotalDeliveryFeeKt.Deliveryfee

class ResDataVerify{

    fun calculateDeliveryFee(request: FeeCalcRequest): Int {
        val deliveryFee = Deliveryfee()
        val finalFee = deliveryFee.sumDeliveryFee(request)
        
        //response Int verification
        if (finalFee < 0) { 
            throw Exception("Final fee was negative")
        }
        
        return finalFee
    }

}
