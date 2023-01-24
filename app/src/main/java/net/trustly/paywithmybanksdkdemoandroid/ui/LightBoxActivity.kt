package net.trustly.paywithmybanksdkdemoandroid.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paywithmybank.android.sdk.interfaces.PayWithMyBank
import com.paywithmybank.android.sdk.interfaces.PayWithMyBankCallback
import com.paywithmybank.android.sdk.views.PayWithMyBankView
import net.trustly.paywithmybanksdkdemoandroid.R

class LightBoxActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_box)

        val establishDataValues = intent.getSerializableExtra(ESTABLISH_DATA) as Map<String, String>

        val lightBoxWidget = findViewById<PayWithMyBankView>(R.id.lightBoxWidget)
        lightBoxWidget.establish(establishDataValues)
            .onReturn(
                (PayWithMyBankCallback { _: PayWithMyBank, _: Map<String, String> ->
                    redirectToScreen(Callback.RETURN)
                })
            ).onCancel(
                (PayWithMyBankCallback { _: PayWithMyBank, _: Map<String, String> ->
                    redirectToScreen(Callback.CANCEL)
                })
            )
    }

    private fun redirectToScreen(callback: Callback) {
        val intent = Intent(this, ResultActivity::class.java)
        when (callback) {
            Callback.RETURN -> {
                intent.putExtra(ResultActivity.RESULT, ResultActivity.Result.RETURN)
            }
            Callback.CANCEL -> {
                intent.putExtra(ResultActivity.RESULT, ResultActivity.Result.CANCEL)
            }
        }
        startActivity(intent)
        finish()
    }

    private enum class Callback {
        RETURN, CANCEL
    }

    companion object {
        const val ESTABLISH_DATA = "establishData"
    }
}
