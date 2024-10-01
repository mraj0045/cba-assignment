package com.cba.assignment

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.cba.assignment.common.extension.formatAsAmount
import com.cba.assignment.data.model.Account
import com.cba.assignment.databinding.ActivityMainBinding
import com.cba.assignment.databinding.LayoutAccountDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.fetAccountDetails()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        observeAccountDetails()
    }

    private fun observeAccountDetails() {
        lifecycleScope.launch {
            mainViewModel.accountDetailsFlow().collect {
                updateAccountDetailsIUI(account = it)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun updateAccountDetailsIUI(account: Account) {
        with(binding.contentMain.accountDetailsLayout) {
            dollarPrice.text = account.availableAmount.formatAsAmount()
            dollarBalance.text = account.balanceAmount.formatAsAmount()
            dollarPending.text = (account.availableAmount - account.balanceAmount).formatAsAmount()
            bsbValue.text = account.bankStateBranchNumber
            accountValue.text = account.accountNumber
        }
    }

}