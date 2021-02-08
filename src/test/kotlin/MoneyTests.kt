import com.cubetiqs.money.*
import org.junit.Assert
import org.junit.Test

class MoneyTests {
    @Test
    fun exchange_2usd_to_khr_test() {
        applyMoneyConfig {
            setProperties(buildMoneyConfigProperties {
                setDeliEqual(':')
                setDeliSplit(',')
            })
            // parse("USD:1,KHR:4000")
            // appendRate("usd", 1.0)
            // appendRate("khr", 4000.0)
            fromJson(MyBatchRates.getJsonRates())
        }

        // Is valid for money config?
        Assert.assertTrue(MoneyConfig.isValid())

        // arithmetic operators calculation
        val moneyUsd =
            (2 withCurrency "usd") divideWith (2 withCurrency "usd") plusOf 1 minusOf 1 plusOf 1 multiplyOf 2 divideOf 2 divideWith (8000 withCurrency "khr") plusOf 1
        val moneyKhr = moneyUsd exchangeTo "khr"

        // Is correct exchange?
        Assert.assertEquals(8000.0, moneyKhr.getMoneyValue(), 0.0)

        // complex operators and exchanging
        val sum =
            ((moneyUsd + moneyKhr) * Money.TEN) exchangeTo MoneyCurrency.KHR minusWith (Money.ONE exchangeTo MoneyCurrency.KHR)

        Assert.assertEquals(156000.0, sum.getMoneyValue(), 0.0)
    }

    object MyBatchRates {
        fun getJsonRates(): String {
            return """
                {"USD": 1.0,"KHR": 4000.0}
            """.trimIndent()
        }
    }
}