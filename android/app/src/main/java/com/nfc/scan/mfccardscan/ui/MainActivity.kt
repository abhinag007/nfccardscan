package com.nfc.scan.mfccardscan.ui

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import com.nfc.scan.mfccardscan.lib.CtlessCardService
import com.nfc.scan.mfccardscan.lib.CtlessCardService.ResultListener
import com.nfc.scan.mfccardscan.lib.enums.BeepType
import com.nfc.scan.mfccardscan.lib.model.Application
import com.nfc.scan.mfccardscan.lib.model.Card
import com.nfc.scan.mfccardscan.lib.model.LogMessage
import com.nfc.scan.mfccardscan.ui.util.AppUtils
import io.flutter.embedding.android.FlutterActivity

class MainActivity : FlutterActivity(),
    ResultListener {
    private val METHOD_CHANNEL_NAME = "com.nfc.transaction/method"
    private val TAG = MainActivity::class.java.name
    private val llContainer: LinearLayout? = null
    private var mCtlessCardService: CtlessCardService? = null
    private var mProgressDialog: ProgressDialog? = null
    private var mAlertDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        setContentView(R.layout.activity_main);

//        llContainer = findViewById(R.id.ctless_container);
        mCtlessCardService = CtlessCardService(this, this)
    }

    override fun onResume() {
        super.onResume()
        mCtlessCardService!!.start()
    }

    override fun onCardDetect() {
        Log.d(TAG, "ON CARD DETECTED")
        playBeep(BeepType.SUCCESS)
        showProgressDialog()
    }

    override fun onCardReadSuccess(card: Card) {
        dismissProgressDialog()
        showCardDetailDialog(card)
    }

    override fun onCardReadFail(error: String) {
        playBeep(BeepType.FAIL)
        dismissProgressDialog()
        showAlertDialog("ERROR", error)
    }

    override fun onCardReadTimeout() {
        playBeep(BeepType.FAIL)
        dismissProgressDialog()
        AppUtils.showSnackBar(llContainer, "Timeout has been reached...", "OK")
    }

    override fun onCardMovedSoFast() {
        playBeep(BeepType.FAIL)
        dismissProgressDialog()
        AppUtils.showSnackBar(llContainer, "Please do not remove your card while reading...", "OK")
    }

    override fun onCardSelectApplication(applications: List<Application>) {
        playBeep(BeepType.FAIL)
        dismissProgressDialog()
        showApplicationSelectionDialog(applications)
    }

    private fun openApduLogDetail(logMessages: ArrayList<LogMessage>) {
        val intent = ApduLogActivity.newIntent(this)
        intent.putParcelableArrayListExtra("apduLog", logMessages)
        startActivity(intent)
    }

    private fun playBeep(beepType: BeepType) {
        val toneGen = ToneGenerator(AudioManager.STREAM_ALARM, 100)
        when (beepType) {
            BeepType.SUCCESS -> toneGen.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200)
            BeepType.FAIL -> toneGen.startTone(ToneGenerator.TONE_SUP_ERROR, 200)
            else -> {}
        }
    }

    private fun showProgressDialog() {
        dismissAlertDialog()
        runOnUiThread {
            mProgressDialog = AppUtils.showLoadingDialog(
                this,
                "Reading Card",
                "Please do not remove your card while reading..."
            )
        }
    }

    private fun dismissProgressDialog() {
        runOnUiThread { mProgressDialog!!.dismiss() }
    }

    private fun showAlertDialog(title: String, message: String) {
        runOnUiThread {
            mAlertDialog =
                AppUtils.showAlertDialog(
                    this, title, message, "OK", "SHOW APDU LOGS", false
                ) { dialogInterface: DialogInterface?, button: Int -> mAlertDialog!!.dismiss() }
        }
    }

    private fun showCardDetailDialog(card: Card) {
        runOnUiThread {
            val title = "Card Detail"
            var message =
                """
                Card Brand : ${card.cardType.cardBrand}
                Card Pan : ${card.pan}
                Card Expire Date : ${card.expireDate}
                Card Track2 Data : ${card.track2}
                
                """.trimIndent()
            if (card.emvData != null && !card.emvData
                    .isEmpty()
            ) message += "Card EmvData : " + card.emvData
            Log.d(TAG, message)
            mAlertDialog =
                AppUtils.showAlertDialog(
                    this, title, message, "OK", "SHOW APDU LOGS", false
                ) { dialogInterface: DialogInterface?, button: Int ->
                    when (button) {
                        DialogInterface.BUTTON_POSITIVE, DialogInterface.BUTTON_NEUTRAL -> {
                            mCtlessCardService!!.start()
                            mAlertDialog!!.dismiss()
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            if (card.logMessages != null && !card.logMessages
                                    .isEmpty()
                            ) openApduLogDetail(
                                ArrayList(
                                    card.logMessages
                                )
                            )
                            mAlertDialog!!.dismiss()
                        }
                    }
                }
        }
    }

    private fun showApplicationSelectionDialog(applications: List<Application>) {
        val appNames = arrayOfNulls<String>(applications.size)
        var index = 0
        for (application in applications) {
            appNames[index] = application.appLabel
            index++
        }
        runOnUiThread {
            val title = "Select One of Your Cards"
            mAlertDialog = AppUtils.showSingleChoiceListDialog(
                this, title, appNames, "OK"
            ) { dialogInterface: DialogInterface?, i: Int ->
                mCtlessCardService!!.setSelectedApplication(
                    i
                )
            }
        }
    }

    private fun dismissAlertDialog() {
        if (mAlertDialog != null) runOnUiThread { mAlertDialog!!.dismiss() }
    }
}