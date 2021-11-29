package com.example.senyumpuan.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.senyumpuan.R
import com.example.senyumpuan.databinding.ActivityDashboardBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.example.senyumpuan.ui.sign_in.SignInActivity


class DashboardActivity : BaseActivity<ActivityDashboardBinding>() {

    private val viewModel: DashboardViewModel by viewModel()

    override fun getViewBinding(): ActivityDashboardBinding =
        ActivityDashboardBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.dashboard)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sign_out_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sign_out){
            showAlertSignOut()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertSignOut() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.sign_out))
            .setMessage(getString(R.string.alert_message))
            .setIcon(R.drawable.ic_warning)
            .setPositiveButton(android.R.string.ok
            ) { _, _ ->
                if (viewModel.signOut()){
                    startActivity(
                        Intent(this, SignInActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
                }
            }
            .setNegativeButton(android.R.string.cancel, null).show()
    }
}