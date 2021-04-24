package com.winton.demo

import android.Manifest
import android.annotation.TargetApi
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.util.*

/**
 * @author: winton
 * @time: 2018/12/19 7:37 PM
 * @desc: 蓝牙demo
 */
class BluetoothDemoActivity : BaseActivity() {

    private lateinit var bluetoothAdapter: BluetoothAdapter

    private val callback = object : ScanCallback() {
        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.d("test", "errorCode" + errorCode)
        }

        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            Log.d("test", "callbackType:" + callbackType)
            Log.d("test", "ScanResult:" + result)
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
            Log.d("test", "ScanResult:" + results)
        }
    }

    private val lowApiCallback = BluetoothAdapter.LeScanCallback { device, rssi, scanRecord -> Log.d("winton", "run: scanning...");}


    private lateinit var mTVResult: TextView
    private lateinit var mBTOpen: Button
    private lateinit var mBtScan: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_bluetooth)
        mBTOpen = findViewById(R.id.bt_open)
        mBtScan = findViewById(R.id.bt_scan)
        mTVResult = findViewById(R.id.tv_result)
        initData()
        initListener()
    }

    /**
     * 初始化监听器
     */
    private fun initListener() {
        mBTOpen.setOnClickListener {
            when (mBTOpen.text) {
                "关闭蓝牙" -> {

                }
                "打开蓝牙" -> {

                }
            }
        }
        mBtScan.setOnClickListener {
            clickAction()
        }

    }

    /**
     * 初始化数据
     */
    private fun initData() {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
        if (bluetoothAdapter.isEnabled) {
            mBTOpen.text = "关闭蓝牙"
        } else {
            mBTOpen.text = "打开蓝牙"
        }
    }

    /**
     * 权限检查
     */
    private fun checkPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val code = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            return code == PackageManager.PERMISSION_GRANTED
        } else {
            return true
        }

    }

    /**
     * 点击Scan Action
     */
    private fun clickAction() {
        if (checkPermission()) {
            startScan()
        } else {
            ActivityCompat.requestPermissions(
                    this@BluetoothDemoActivity,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
                    , 1001)
        }
    }

    /**
     * 开始扫描
     */
    private fun startScan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bluetoothAdapter.bluetoothLeScanner.startScan(callback)
        } else {
            bluetoothAdapter.startLeScan(lowApiCallback)
        }
    }

    /**
     * 停止扫描
     */
    private fun stopScan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bluetoothAdapter.bluetoothLeScanner.stopScan(callback)
        } else {
            bluetoothAdapter.stopLeScan(lowApiCallback)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001) {
            clickAction()
        }
    }

}