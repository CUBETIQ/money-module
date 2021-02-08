import com.cubetiqs.money.*
import org.junit.Assert
import org.junit.Test

class MoneyTests {
    private fun initMoneyConfig() {
        applyMoneyConfig {
            setProperties(buildMoneyConfigProperties {
                setDeliEqual(':')
                setDeliSplit(',')
            })

            fromJsonRates(MyBatchRates.getJsonRates())
        }
    }

    object MyBatchRates {
        fun getJsonRates(): String {
            return """
                {"USD": 1.0,"KHR": 4000.0, "eur": 0.5}
            """.trimIndent()
        }
    }

    @Test
    fun exchange_2usd_to_khr_test() {
        initMoneyConfig()

//        applyMoneyConfig {
//            setProperties(buildMoneyConfigProperties {
//                setDeliEqual(':')
//                setDeliSplit(',')
//            })
//            // parse("USD:1,KHR:4000")
//            // appendRate("usd", 1.0)
//            // appendRate("khr", 4000.0)
//            fromJson(MyBatchRates.getJsonRates())
//        }

        // Is valid for money config?
        Assert.assertTrue(MoneyConfig.isValid())

        // arithmetic operators calculation
        val moneyUsd =
            (2 withCurrency "usd") divideWith (2 withCurrency "usd") plusOf 1 minusOf 1 plusOf 1 multiplyOf 2 divideOf 2 divideWith (8000 withCurrency "khr") plusOf 1
        val moneyKhr = moneyUsd exchangeTo "khr"

        // Is correct exchange?
        Assert.assertEquals(8000.0, moneyKhr.getValue(), 0.0)

        // complex operators and exchanging
        val sum =
            ((moneyUsd + moneyKhr) * Money.TEN) exchangeTo MoneyCurrency.KHR minusWith (Money.ONE exchangeTo MoneyCurrency.KHR)

        Assert.assertEquals(156000.0, sum.getValue(), 0.0)
    }

    @Test
    fun moneyGenerator() {
        initMoneyConfig()

        val moneyGen = MoneyObject(
            value = 1.0,
            currency = "usd",
            operator = MoneyObject.MoneyOperator.PLUS,
            with = MoneyObject(
                value = 8000.0,
                currency = "khr",
                operator = MoneyObject.MoneyOperator.MINUS,
                with = MoneyObject(
                    value = 1000.0,
                    currency = "khr",
                )
            )
        )

        val result = moneyGen.compute()

        Assert.assertEquals(2.75, result.getValue(), 0.0)
    }

    @Test
    fun moneyGeneratorBuilder() {
        initMoneyConfig()

        val expected = 72000.0

        val builder = MoneyObject.builder()
            .with(10.0, "usd", '+')
            .with(1.5, "eur", '+')
            .with(8000.0, "khr")
            .with(10000.0, "khr")
            .with(2000.0, "khr")
            .with(.5, "eur", '-')
            .with(1.0, "usd")
            .withCurrency("khr")
            .build()

        val result = builder.compute()

        println(result)

        Assert.assertEquals(expected, result.getValue(), 0.0)
    }

    @Test
    fun moneyGeneratorBuilderWithStringValues() {
        initMoneyConfig()

        val values = "usd:1:+,khr:4000:-,usd:1:+,eur:1:+" // result = 3
        val expected1 = 3.0
        val expected2 = 3.5

        val builder = MoneyObject.builder()
            .withCurrency("usd")
            .with(1.0, "usd", '-')
            .with(4000.0, "khr")
            .with(1.0, "usd")
            .with(1.0, "usd")
            .with(1.0, "usd")
            .with(1.0, "usd")
            .with(1.0, "eur", '-') // 2 usd
            .with(1.0, "usd")
            .with(2.0, "usd", '/')
            .with(2.0, "usd")
            .build() // 3.5

        val result1 = MoneyObject.builder()
            .parseFromString(values)
            .withCurrency("usd")
            .build()
            .compute()
        val result2 = builder.compute()

        Assert.assertEquals(expected1, result1.getValue(), 0.0)
        Assert.assertEquals(expected2, result2.getValue(), 0.0)
    }
}